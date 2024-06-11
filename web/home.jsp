<%-- 
    Document   : home
    Created on : May 21, 2024, 2:18:54 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="style.css" rel="stylesheet">
	<script src="mycode.js">
            
</script>
        <title>Home work</title>
    </head>
    <body>
        <% 
        
        HttpSession existingSession = request.getSession(false);
        if (existingSession == null || existingSession.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
        } else {
           
        %>
        <%@ include file="/includes/header.jsp" %>


        <%@ include file="/includes/student.jsp" %>


        <%@ include file="/includes/footer.jsp" %>
        <% } %>
        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
