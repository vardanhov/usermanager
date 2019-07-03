<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Welcome</title>
    <link href="<%=request.getContextPath()%>/css/welcome.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<c:if test="${not empty sessionScope.message}">
    <div class="message">
        <c:out value="${sessionScope.message}" escapeXml="false"/>
    </div>
    <c:remove var="message" scope="session"/>
</c:if>
<table>
    <tr>
        <td>Email</td>
        <td>${user.email}</td>
    </tr>
    <tr>
        <td>Name</td>
        <td>${user.name}</td>
    <tr>
        <td>Surname</td>
        <td>${user.surname}</td>
    </tr>
</table>
<br>
<tr>
    <td><input type="file" name="fname"/></td><br><br>
    <td><input type="submit" value="Upload"/></td>
</tr>
<br><br>
<tr>
    <td>
        <input type="button" value="Edit User"    onclick="window.location.href='edit-user-view'"/>
    </td>
    <td>
        <input type="button" value="Edit Email"    onclick="window.location.href='edit-email-view'"/>
    </td>
    <td>
        <input type="button" value="Reset Password" onclick="window.location.href='reset-password-view'"/>
    </td>
</tr>
<br>
<br>
<a href="<%=request.getContextPath()%>/logout">Logout</a>

</body>
</html>