
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<ul>
    <li>
        <a href="register-view">Registration</a>
    </li>
    <li>
        <a href="login-view">Login</a>
    </li>
</ul>
<c:if test="${not empty sessionScope.message}">
    <div class="message">
        <c:out value="${sessionScope.message}" escapeXml="false"/>
    </div>
    <c:remove var="message" scope="session"/>
</c:if>
</body>
</html>
