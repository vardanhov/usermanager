<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Welcome</title>
    <link href="<%=request.getContextPath()%>/css/welcome.css" rel="stylesheet" type="text/css"/>
</head>
<body>
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
    <td>
        <input type="button" value="Edit User"
               onclick="window.open('edit-user-view','name','width=600,height=400')"/>
    </td>
    <td>
        <input type="button" value="Edit Email"
               onclick="window.open('edit-email-view','name','width=600,height=400')"/>
    </td>
    <td>
        <input type="button" value="Reset Password"
               onclick="window.open('reset-password-view','name','width=600,height=400')"/>
    </td>
</tr>
<br>
<br>
<a href="<%=request.getContextPath()%>/logout">Logout</a>

</body>
</html>