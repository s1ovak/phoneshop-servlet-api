<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404 - Product not found</title>
</head>
<body>
<h2>
    404 - Product not found
</h2>
<%
    out.println(exception.getMessage());
%>
</body>
</html>
