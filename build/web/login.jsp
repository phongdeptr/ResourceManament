<%-- 
    Document   : login
    Created on : Jun 27, 2021, 7:30:26 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta name="viewport" content="width=device-width, initial-" />
        <link rel="stylesheet" href="css/main.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>

    <body>
        <div class="container">
            <h1>Resource Manager Login Page</h1>
            <form method="post" action="LoginController">
                <div class="form-group">
                    <input class="form-control" type="text" name="username" value="" placeholder="Username" /><br />
                    <input class="form-control" type="password" name="password" value="" placeholder="Password" /><br />
                    <div class="g-recaptcha" data-sitekey="6LeeaM4aAAAAAIHX_glk6WUQVn3yHboqq7apUxeJ"></div>
                    <button class="btn-primary" type="submit">Login</button>
                </div>
            </form>
            <a href="register.jsp">Register Now</a>
        </div>
    </body>

</html>
