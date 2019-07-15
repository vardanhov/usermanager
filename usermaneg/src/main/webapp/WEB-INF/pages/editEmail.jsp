<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>

    </title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<h1>Edit Email</h1>
<form action="<%=request.getContextPath()%>/user/edit-email" method="post" class="form-register">
    <%-- show message if exists, then remove it --%>
    <c:if test="${not empty sessionScope.message}">
        <div class="message">
            <c:out value="${sessionScope.message}" escapeXml="false"/>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <table>
        <c:if test="${not empty errors and errors.containsKey(\"email\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"email\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Old Email</td>
            <td>
                <input type="text" name="email" value="<c:out value="${param.email}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"newEmail\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"newEmail\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>New Email</td>
            <td>
                <input type="text" name="newEmail" value="<c:out value="${param.newEmail}"/>"/>
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