<%@ page import="collection.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="collection.Catalogue" %>
<%@ page import="userSide.Exchange" %>

<html>
<head>
    <title>Exchange</title>
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../stylesheets/exchange.css">

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</head>

<body>
    <!-------- NAVBAR------->
    <jsp:include page="navbar.jsp"/>

<div id="tot">

    <%CollectionOwn c = (CollectionOwn)request.getSession().getAttribute("logged"); %>

        <div class="leftbox">
                <div style="overflow: auto; width: 100%; height: 100%">

                        <%for(Card entry : c.getCardsOwn()){%>
                    <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                                <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                    </div>
                        <%}%>
            </div>
    </div>

        <div class="rightbox">
            <div style="overflow: auto; width: 100%; height: 100%">

                <% CollectionOwnDaoImpl collection = new CollectionOwnDaoImpl();%>
                <% UserDaoImpl allUsers = new UserDaoImpl();%>
                <% for (User u : allUsers.findAll()) {%>
                <% for (Card entry : (collection.getCollentionOwn(u))){%>
                <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                    <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                </div>
                <%}%>
                <%}%>

            </div>
        </div>

<div id="start">
    <button type="button" class="btn" id="startbutton">Basic</button>
</div>

</div>

</body>
</html>