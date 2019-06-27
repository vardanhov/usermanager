<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>

    </title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<h1>Send Password</h1>
<form action="<%=request.getContextPath()%>/send-email" method="post" class="form-register">
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
            <td> Email</td>
            <td>
                <input type="text" name="email" value="<c:out value="${user.email}"/>"/>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="text-align: right">
                <input type="submit" value="Send Password"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>