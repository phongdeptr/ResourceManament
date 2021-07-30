<%-- 
    Document   : history.jsp
    Created on : Jul 9, 2021, 10:09:01 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/admin.css">
        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="member_header.jspf" %>

        <div class="form-container">
            <form action="MainController">
                <input type="text" name="content">
                <button type="submit" name="action" value="view-history">Search</button>
            </form>
        </div>
        <div class="container-table">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Resource Name</th>
                        <th>Request Date</th>
                        <th>Request Status</th>
                        <th>Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="requests" items="${sessionScope.REQUEST_HISTORY}" varStatus="theCount">
                        <tr>
                    <form action="MainController">
                        <input name="deleteID" type="hidden" value="${requests.requestID}">
                        <td>${theCount.count}</td>
                        <td>${requests.requestResource.resourceName}</td>
                        <td>${requests.requestDate}</td>
                        <td>${requests.requestStatus}</td>
                        <c:if test="${requests.requestStatus == 'New'}" var="testCancel">
                            <td><button type="submit" name="action" value="cancel">Cancel</button></td>
                        </c:if>
                        <c:if test="${!testCancel}">
                            <td>is Processed</td>
                        </c:if>
                    </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
