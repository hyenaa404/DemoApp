<%-- 
    Document   : login
    Created on : Jun 6, 2024, 4:21:49 AM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <link href="style.css" rel="stylesheet">
        <title>Home work</title>
    </head>
    <body>

        <%@ include file="/includes/header.jsp" %>


        <% 
        
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute("username") != null) {
            response.sendRedirect("home.jsp");
        } else {
           
        %> <div class ="center login">
            <form action="login" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="user"><br><br>

                <label for="password">Password:</label>
                <input type="password" id="password" name="pass"><br><br>

                <input type="submit" value="Login">
            </form>
            </br> <p>${message}</p>
            <% } %>
        </div>

        <%@ include file="/includes/footer.jsp" %>

        <script src="js/Jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>