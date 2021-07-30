/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Pagenation;
import dto.Request;
import dto.RequestResource;
import dto.ResourceCategory;
import dto.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ADMIN
 */
public class Dao {

    public String createVerification(String email) {
        String veficationNumber = "";
        Random random = new Random();
        Connection con = null;
        PreparedStatement stm = null;
        String sql = "INSERT INTO [dbo].[tblAccountVerifications]\n"
                + "           ([VerificationCode]\n"
                + "           ,[userID])\n"
                + "VALUES(?,?)";
        for (int i = 0; i < 4; i++) {
            int randomNumber = (random.nextInt(8) + 1);
            veficationNumber = veficationNumber + randomNumber;
        }
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, veficationNumber);
            stm.setString(2, email);
            if (stm.executeUpdate() > 0) {
                return veficationNumber;
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return veficationNumber;
    }

    public boolean checkVerfication(String userID, String code) {
        Connection con = null;
        PreparedStatement stm = null;
        PreparedStatement activeStm = null;
        ResultSet rs = null;
        boolean isVerfication = false;
        String sql = "SELECT [VerificationCode] FROM tblAccountVerifications where userID = ?";
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            con.setAutoCommit(false);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String verificationCode = rs.getString("VerificationCode");
                if (code.equals(verificationCode)) {
                    isVerfication = true;
                    sql = "UPDATE tblUsers SET [status] = 1 where userID = ?";
                    activeStm = con.prepareStatement(sql);
                    activeStm.setString(1, userID);
                    int res = activeStm.executeUpdate();
                    if (res == 0) {
                        throw new SQLException();
                    } else {
                        isVerfication = true;
                        con.commit();
                    }
                    break;
                } else {
                    isVerfication = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (activeStm != null) {
                    activeStm.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return isVerfication;
    }

    public UserDTO checkLogin(String userID, String password) {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT [userID]\n"
                + "      ,[password]\n"
                + "	  ,[status]\n"
                + "      ,[fullName]\n"
                + "	  ,(SELECT isManager FROM tblRoles where roleID = tblUsers.roleID) as [isAdmin]\n"
                + "  FROM [tblUsers]"
                + "where userID = ? and password = ?";
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareCall(sql);
            stm.setString(1, userID);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserID(rs.getString("userID"));
                dto.setFullName(rs.getString("fullName"));
                dto.setIsActive(rs.getBoolean("status"));
                dto.setIsAdmin(rs.getBoolean("isAdmin"));
                return dto;
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {

            }
        }
        return null;
    }

    public List<Request> getRequest(String requestName, int pageNumber, Pagenation<Request> p) {
        List<Request> result = null;
        Connection con = null;
        PreparedStatement stm = null;
        PreparedStatement stmTotalPage = null;
        ResultSet rs = null;
        ResultSet rsCount = null;
        String sql = "SELECT requestID\n"
                + "	    ,requestUserID\n"
                + "	   ,(SELECT fullName FROM tblUsers where requestUserID = userID) as [requesterName]\n"
                + "	   ,requestResource\n"
                + "	   , resourceName\n"
                + "	   , requestDate\n"
                + "	   , [status]\n"
                + "	   , requestContent\n"
                + "FROM tblRequests join tblResources on tblRequests.requestResource = tblResources.resourceID\n"
                + "  WHERE resourceName LIKE ?\n"
                + "  ORDER BY requestDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + requestName + "%");
            stm.setInt(2, (pageNumber - 1) * 20);
            stm.setInt(3, 20);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                RequestResource resource = new RequestResource(
                        rs.getString("requestResource"),
                        rs.getString("resourceName"));
                UserDTO requester = new UserDTO();
                requester.setFullName(rs.getString("requesterName"));
                requester.setUserID(rs.getString("requestUserID"));
                Request request = new Request(requester, rs.getString("requestID"), resource,
                        rs.getDate("requestDate").toString(), rs.getString("status"), rs.getString("requestContent"));
                result.add(request);
            }
            if (result != null) {
                p.setResult(result);
                p.setPageNum(pageNumber);
                stmTotalPage = con.prepareStatement("SELECT (COUNT(requestID)) as [totalRequest] \n FROM [tblRequests] WHERE requestContent LIKE ?");
                stmTotalPage.setString(1, "%" + requestName + "%");
                rsCount = stmTotalPage.executeQuery();
                while (rsCount.next()) {
                    p.setTotalResult((int) Math.ceil((double) rsCount.getInt("totalRequest") / 20));
                    System.out.println("Total page: " + Math.ceil(rsCount.getInt("totalRequest") / 20));
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rsCount != null) {
                    rsCount.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (stmTotalPage != null) {
                    stmTotalPage.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return result;
    }

    public boolean processRequest(String requestID, String status, String resourceID, String userID) {
        boolean isProcessSuccess = false;
        String sql = "UPDATE [dbo].[tblRequests]\n"
                + "   SET\n"
                + "   [status] = ?\n"
                + "   ,[isProcess] = ?\n"
                + " WHERE  [requestID] = ?";
        Connection con = null;
        PreparedStatement stm = null;
        PreparedStatement stm_INSERT = null;
        try {
            con = dbutil.DBUtil.getConnection();
            con.setAutoCommit(false);
            stm = con.prepareStatement(sql);
            stm.setString(1, status);
            stm.setBoolean(2, true);
            stm.setInt(3, Integer.parseInt(requestID));
            if (stm.executeUpdate() > 0) {
                isProcessSuccess = true;
            }
            if (status.equals("Accept")) {
                String sqlInsert = "INSERT INTO tblUserResource(resourceID,requestUserID) VALUES(?,?)";
                stm_INSERT = con.prepareStatement(sqlInsert);
                stm_INSERT.setString(1, resourceID);
                stm_INSERT.setString(2, userID);
                stm_INSERT.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm_INSERT != null) {
                    stm_INSERT.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {

            }
        }

        return isProcessSuccess;
    }

    public List<RequestResource> getUserResource(String userID, String content, String date, String category, int pageNum, Pagenation<RequestResource> p) {
        List<RequestResource> resourceList = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PreparedStatement stmTotalPage = null;
        ResultSet rsCount = null;
        String sql = "SELECT resourceName,\n"
                + "(SELECT categoryName FROM tblResourceCategory where categoryID = resourceCataegory ) as categoryName\n"
                + ",resourceDirectory\n"
                + "FROM tblUserResource join tblResources on tblResources.resourceID = tblUserResource.resourceID\n"
                + "where resourceName LIKE ? and  requestUserID = ? and usingDate = ? and resourceCataegory = ?\n"
                + "ORDER BY usingDate OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(2, userID);
            stm.setString(1, "%" + content + "%");
            stm.setDate(3, Date.valueOf(date));
            stm.setString(4, category);
            stm.setInt(5, (pageNum - 1) * 20);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (resourceList == null) {
                    resourceList = new ArrayList<>();
                }
                RequestResource resource = new RequestResource();
                resource.setResourceName(rs.getString("resourceName"));
                resource.setResourceDirectory(rs.getString("resourceDirectory"));
                ResourceCategory resourceCategory = new ResourceCategory();
                resourceCategory.setCategoryName(rs.getString("categoryName"));
                resource.setResourceCategory(resourceCategory);
                resourceList.add(resource);
            }
            if (resourceList != null) {
                p.setResult(resourceList);
                p.setPageNum(pageNum);
                sql = "SELECT COUNT([resourceID]) as totalResource\n"
                        + " FROM[tblUserResource] where [requestUserID] = ?";
                stmTotalPage = con.prepareStatement(sql);
                stmTotalPage.setString(1, userID);
                rsCount = stmTotalPage.executeQuery();
                while (rsCount.next()) {
                    p.setTotalResult((int) Math.ceil((double) rsCount.getInt("totalResource") / 20));
                    System.out.println("Total page: " + Math.ceil(rsCount.getInt("totalResource") / 20));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmTotalPage != null) {
                    stmTotalPage.close();
                }
                if (rsCount != null) {
                    rsCount.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return resourceList;
    }

    public List<ResourceCategory> getCategory() {
        List<ResourceCategory> categoryList = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT categoryID, categoryName from tblResourceCategory";
        try {

            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (categoryList == null) {
                    categoryList = new ArrayList<>();
                }
                ResourceCategory category = new ResourceCategory(rs.getString("categoryID"), rs.getString("categoryName"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return categoryList;
    }

    public boolean creatAccount(String userid, String password, String fullname, String phone, String address) {
        boolean isCreate = false;
        String sql = "INSERT INTO tblUsers(userID,password, fullName, phone, address) values(?,?,?,?,?)";
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userid);
            stm.setString(2, password);
            stm.setString(3, fullname);
            stm.setString(4, phone);
            stm.setString(5, address);
            if (stm.executeUpdate() > 0) {
                isCreate = true;
            }

        } catch (SQLException e) {
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return isCreate;
    }

    public boolean checkDuplicateEmail(String email) {
        boolean isDuplicate = false;
        String sql = "SELCT userID FROM tblUsers where userID = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                isDuplicate = true;
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return isDuplicate;
    }

    public List<RequestResource> getResourceInfo(String userID) {
        List<RequestResource> resource = null;
        String sql = "SELECT resourceID, resourceName FROM [tblResources] "
                + "where resourceID not in (select resourceID "
                + "from tblUserResource where requestUserID = ?)";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (resource == null) {
                    resource = new ArrayList<>();
                }
                resource.add(new RequestResource(rs.getString("resourceID"), rs.getString("resourceName")));
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return resource;
    }

    public List<Request> getRequestHistory(String userID, String keyword) {
        String sql = "SELECT [requestID]\n"
                + "      ,[requestUserID]\n"
                + "      ,[requestResource]\n"
                + "	  ,(SELECT resourceName FROM tblResources where requestResource = resourceID) as resourceName\n"
                + "      ,[requestDate]\n"
                + "      ,[status]\n"
                + "      ,[requestContent]\n"
                + "      ,[isProcess]\n"
                + "  FROM [tblRequests]\n"
                + "where requestUserID = ?\n"
                + "and \n"
                + "EXISTS (select resourceName from tblResources where requestResource = resourceID and resourceName LIKE ?)"
                + "\norder by requestDate";
        List<Request> requests = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, "%" + keyword + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                if (requests == null) {
                    requests = new ArrayList<>();
                }
                requests.add(new Request(null, rs.getString("requestID"), new RequestResource(rs.getString("requestResource"), rs.getString("resourceName")),
                        rs.getDate("requestDate").toString(), rs.getString("status"), rs.getString("requestContent")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return requests;
    }

    public boolean isProcessRequest(int requestID) {
        String sql = "SELECT status from [tblRequests] where requestID = ?";
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean isProcess = false;

        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, requestID);
            rs = stm.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                if (status.contains("Accept") || status.contains("Reject") || status.contains("Inactive")) {
                    isProcess = true;
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }

        return isProcess;
    }

    public void bookResource(String requesterID, String resourceID, String requestContent) {
        String sql = "INSERT INTO [tblRequests]\n"
                + "           ([requestUserID]\n"
                + "           ,[requestResource]\n"
                + "           ,[status]\n"
                + "           ,[requestContent]\n"
                + "           ,[isProcess])"
                + "VALUES(?,?,?,?,?)";
        PreparedStatement stm = null;
        Connection con = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, requesterID);
            stm.setString(2, resourceID);
            stm.setString(3, "New");
            stm.setString(4, requestContent);
            stm.setBoolean(5, false);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public void cancelRequest(int requestID) {
        String sql = "UPDATE tblRequests SET [status] = ? where requestID = ?";
        PreparedStatement stm = null;
        Connection con = null;
        try {
            con = dbutil.DBUtil.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, "Inactive");
            stm.setInt(2, requestID);
            stm.executeUpdate();

        } catch (SQLException e) {
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
    }

}
