<%@ page import="com.egs.example.data.model.UserProfile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Registration</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Registration</h1>
<form action="<%=request.getContextPath()%>/register" method="post" class="form-register">

    <%-- show message if exists, then remove it --%>
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
            <td>Name</td>
            <td>
                <input type="text" name="name" value="<c:out value="${param.name}"/>"/>
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
                <input type="text" name="surname" value="<c:out value="${param.surname}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"email\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"email\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Email</td>
            <td>
                <input type="text" name="email" value="<c:out value="${param.email}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"password\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"password\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Password</td>
            <td>
                <input type="password" name="password" value="<c:out value="${param.password}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"confirm-password\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"confirm-password\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Confirm Password</td>
            <td>
                <input type="password" name="confirm-password" value="<c:out value="${param.password}"/>"/>
            </td>
        </tr>
        <c:if test="${not empty errors and errors.containsKey(\"profile\")}">
            <tr>
                <td colspan="2" class="field-error">
                    <c:out value="${errors.get(\"profile\")}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>Profile</td>
            <td>
                <select name="profile" value="<c:out value="${param.profile}"/>">
                    <option></option>
                    <c:forEach var="item" items="<%=UserProfile.values()%>">
                        <option value="${item}" <c:if test="${param.profile eq item}">Selected</c:if>>${item}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right">
                <input type="submit" value="register"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
