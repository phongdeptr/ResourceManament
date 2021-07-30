/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Dao;
import dto.Pagenation;
import dto.Request;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchRequestController", urlPatterns = {"/SearchRequestController"})
public class SearchRequestController extends HttpServlet {

    private static final String UNAUTHORIZE_ACCESS = "index.jsp";
    private static final String AUTHORIZE_ADMIN_ACCESS = "admin.jsp";
    private static final String AUTHORIZE_MEMBER_ACCESS = "history.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        UserDTO dto = (UserDTO) session.getAttribute("LOGIN_USER");
        String content = request.getParameter("content");
        String index = request.getParameter("pageNum");
        String url = "login.jsp";
        if (dto != null && dto.isIsActive() && dto.isIsAdmin()) {
            Dao dao = new Dao();
            Pagenation<Request> p = new Pagenation<>();
            if (index == null) {
                index = "1";
            }
            if (content == null) {
                content = "";
            }
            int pageNum = Integer.parseInt(index);
            dao.getRequest(content, pageNum, p);
            session.setAttribute("LAST_KEYWORD", content);
            session.setAttribute("SEARCH_REQUEST_RESULT_PAGE", p);
            url = AUTHORIZE_ADMIN_ACCESS;
        } else if (dto != null && dto.isIsActive() && !dto.isIsAdmin()) {
            Dao dao = new Dao();
            Pagenation<Request> p = new Pagenation<>();
            if (index == null) {
                index = "1";
            }
            if (content == null) {
                content = "";
            }
            url = AUTHORIZE_MEMBER_ACCESS;
            int pageNum = Integer.parseInt(index);
            List<Request> requestList = dao.getRequest(content, pageNum, p);
            session.setAttribute("LAST_KEYWORD", content);
            session.setAttribute("REQUEST_HISTORY", requestList);
        } else {
            url = UNAUTHORIZE_ACCESS;
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
