<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>

    </title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>

<h1>Edit User</h1>
<form action="<%=request.getContextPath()%>/edit-user" method="post" class="form-register">
    <c:if test="${not empty sessionScope.message}">
        <div class="message">
            <c:out value="${sessionScope.message}" escapeXml="false"/>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <table>

            <c:if test="${not empty errors and errors.containsKey(\"name\")}">
                <tr>
                    <td colspan="2" class="field-error">
                        <c:out value="${errors.get(\"name\")}"/>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td> Name</td>
                <td>
                    <input type="text" name="name" value="<c:out value="${user.name}"/>"/>
                </td>
            </tr>

            <c:if test="${not empty errors and errors.containsKey(\"surname\")}">
                <tr>
                    <td colspan="2" class="field-error">
                        <c:out value="${errors.get(\"surname\")}"/>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>Surname</td>
                <td>
                    <input type="text" name="surname" value="<c:out value="${user.surname}"/>"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right">
                    <input type="submit" value="Change"/>
                </td>
            </tr>
        </table>
</form>
</body>
</html>