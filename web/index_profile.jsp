<!---This page keeps css of userprofile.jsp  when in profile servlet forward  is done --->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
<%
    response.sendRedirect("views/userprofile.jsp");
%>
</body>
</html>
