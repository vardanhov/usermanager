<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>login form</title>
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>Login</h1>
<form action="<%=request.getContextPath()%>/login" method="post" class="form-login">
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
       <tr>
           <td colspan="2" style="text-align: right">
               <input type="submit" value="login"/>
           </td>
       </tr>
       <tr>
           <td  style="text-align: left">
              <a href="forgot-password-view" > Forgot Password</a>
           </td>
       </tr>
   </table>
</form>
</body>
</html>