<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>List User</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/js/jquery-1.11.1.min.js" />"></script>
    <script src="<c:url value="/js/bootstrap.min.js" />"></script>
</head>

<body>
<div class="container">
    <div class="col-md-offset-1 col-md-10">
        <hr/>
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>
        <br/><br/>
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">User List</div>
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered">
                    <tr>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Password</th>
                    </tr>
                    <c:choose>
                        <c:when test="${not empty users}">
                            <c:forEach var="user" items="${users}">
                                <c:url var="deleteLink" value="/delete">
                                    <c:param name="id" value="${user.id}"/>
                                </c:url>
                                <tr>
                                    <td>${user.name}</td>
                                    <td>${user.surname}</td>
                                    <td>${user.email}</td>
                                    <td>${user.password}</td>
                                    <td>
                                        <a href="${deleteLink}"
                                             onclick="if (!(confirm('Are you sure you want to delete this user?'))) return false">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>No User Found</c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>