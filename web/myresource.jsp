<%-- 
    Document   : myresource
    Created on : Jul 1, 2021, 9:23:36 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <%@include file="admin-header.jspf" %>
        <div class = "request-manager" id ="request_manager">
            <div class="content_header">
                <h3>Resource</h3>
            </div>
            <c:set value="${sessionScope.RESOURCE_RESULT}" var="page"/>
            <div class="search_container" id="search">
                <form action="MainController">
                    <input name="content" type="text" value="${sessionScope.LAST_RESOURCE_KEYWORD}" style="border-radius: 5px 0px 0px 0px"/>
                    <input name="action" type="hidden" value="get-resource"/>
                    <button type="submit" style="border-radius: 0px 0px 5px 0px"><i class="bi bi-search"></i></button>
                    <select name="category" required="true">
                        <c:forEach var ="category" items="${sessionScope.RESORUCE_CATEGORY}">
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <input type="date" name="date" required="true">
                </form>            
            </div>
            <div class="request_result_container">
                <div>
                    <c:forEach var="resource" items="${page.result}">
                        <div class="result_container">
                            <section class="section_result">
                                <p>
                                    <span>Resource ID: ${resource.resourceID}</span><br>
                                    <span>Resource Name: ${resource.resourceName}</span><br>
                                    <span>Resource Category: ${resource.resourceCategory.categoryName}</span><br>
                                </p>
                            </section>
                        </div>
                    </c:forEach>
                </div>
                <div class="result_container_footer">
                    <span>Page: </span>
                    <c:forEach var="pageNum" begin="1" end="${page.totalResult}">
                        <a href="MainController?action=search_resource&category=${sessionScope.LAST_RESOURCE_CATEGORY}&content=${sessionScope.LAST_RESOURCE_KEYWORD}&pageNum=${pageNum}">${pageNum}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
