<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>

    </title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<h1>Reset Password</h1>
<form action="<%=request.getContextPath()%>/user/reset-password" method="post" class="form-register">
    <c:if test="${not empty sessionScope.message}">
        <div class="message">
            <c:out value="${sessionScope.message}" escapeXml="false"/>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <table>
        <c:if test="${not empty errors and errors.containsKey(\"oldPass\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"oldPass\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Old Password</td>
            <td>
                <input type="password" name="oldPass" value="<c:out value="${param.oldPass}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"newPass\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"newPass\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>New Password</td>
            <td>
                <input type="password" name="newPass" value="<c:out value="${param.newPass}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"confirmPass\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"confirmPass\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Confirm Password</td>
            <td>
                <input type="password" name="confirmPass" value="<c:out value="${param.confirmPass}"/>"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right">
                <input type="submit" value="Reset"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>