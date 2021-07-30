<%-- Document : admin Created on : Jun 26, 2021, 11:01:34 PM Author : ADMIN --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <%@include file="admin-header.jspf" %>
    <div class="request-manager" id="request_manager">
        <div class="content_header">
            <h3>Request Manager</h3>
        </div>
        <div class="search_container" id="search">
            <form action="MainController">
                <input name="content" type="text" value="${sessionScope.LAST_KEYWORD}"
                       style="border-radius: 5px 0px 0px 0px" />
                <input name="action" type="hidden" value="search-request" />
                <button type="submit" style="border-radius: 0px 0px 5px 0px"><i
                        class="bi bi-search"></i></button>
            </form>
        </div>

        <c:set value="${sessionScope.SEARCH_REQUEST_RESULT_PAGE}" var="page" />
        <div class="request_result_container">
            <c:forEach var="requests" items="${page.result}">
                <div class="result_container">
                    <section class="section_result">
                        <p>
                            <span>User info</span><br>
                            <span>User ID: ${requests.requester.userID}</span><br>
                            <span>User Name: ${requests.requester.fullName}</span><br>
                            <span>Request Date: ${requests.requestDate}</span><br>
                            <span>Request Resource: ${requests.requestResource.resourceName}</span><br>
                            <span>Request Status: ${requests.requestStatus}</span><br>
                        </p>
                    </section>
                    <div class="overlay">
                        <div>
                            <input type="button" name="action" value="accept" class="btn-accept" onclick="processRequest('${requests.requestID}', 'accept', '${requests.requestResource.resourceID}', '${requests.requester.userID}')">
                            <input type="button" name="action" value="reject" class="btn-accept"  onclick="processRequest('${requests.requestID}', 'reject', '${requests.requestResource.resourceID}', '${requests.requester.userID}')">
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="result_container_footer">
                <span>Page: </span>
                <c:forEach var="pageNum" begin="1" end="${page.totalResult}">
                    <a
                        href="MainController?action=search-request&content=${sessionScope.LAST_KEYWORD}&pageNum=${pageNum}">${pageNum}</a>
                </c:forEach>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script src="js/admin.js"></script>
</body>
</html>