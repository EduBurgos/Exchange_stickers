<!---This page keeps css of homepage.jsp  when in homepage servlet forward  is done --->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    response.sendRedirect("views/homepage.jsp");
%>
</body>
</html>
