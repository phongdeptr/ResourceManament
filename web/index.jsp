<%-- 
    Document   : index.jsp
    Created on : Jul 2, 2021, 10:22:48 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>resource</title>
        <link rel="stylesheet" href="css/admin.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" 
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" 
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </head>
    <body>
        <%@include file="member_header.jspf" %>
        <div class="search_container" id="search">
            <form action="MainController">
                <select name="request_resourceID" required="true">
                    <c:forEach var ="resource" items="${sessionScope.BOOK_RESOURCE}">
                        <option value="${resource.resourceID}">${resource.resourceName}</option>
                    </c:forEach>
                </select>
                <button name="action" type="submit" value="bookResource">Book Resource</button>
            </form>
        </div>
    </body>
</html>
