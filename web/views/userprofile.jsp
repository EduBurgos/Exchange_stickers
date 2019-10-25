<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>

<%--
  Created by IntelliJ IDEA.
  User: Utente
  Date: 09/07/2019
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>
<%-- <%@ page contentType="text/html;charset=UTF-8" language="java" %> --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <!--
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <meta name="viewport" content="width=device-width, initial-swq  cale=1"/>
    -->
    <link rel="stylesheet" href="../stylesheets/userprofile.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>


</head>
<body>
<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>

<div class="cover-photo"></div>
<div class="body">
    <section class="left-col user-info">
        <div class="profile-avatar">
            <div class="inner"></div>
        </div>
        <% CollectionOwn c; %>
        <% if(request.getSession().getAttribute("snitch")!=null){ %>
        <% c= (CollectionOwn)request.getSession().getAttribute("snitch");}

        else{ %>
        <% c= (CollectionOwn)request.getSession().getAttribute("logged");} %>
        <% User u = c.getOwner();%>

        <h1><%=u.getUsername()%></h1>

        <div class="meta">
            <p><i class="name"></i>Name: <%=u.getNome()+" "+u.getCognome()%></p>
            <p><i class="email"></i>E-mail: <%=u.getEmail()%></p>
        </div>
    </section>
    <div class="section center-col content">
        <!-- Nav -->
        <!--
        <nav class="profile-nav">
            <ul>
                <li class="collection">My Collection</li>
                <li>Scambiabili</li>
            </ul>
        </nav>
        -->

        <!-- Nav tabs -->
        <div class="row">
            <div class="col-10 col-md-10 col-lg-10 col-sm-10 col-xs-10">
                <ul class="nav nav-tabs" role="tablist">
                    <!--    <li role="presentation" class="active"><a href="#ultimoaggiornamento" aria-controls="ultimoaggiornamento" role="tab" data-toggle="tab">Ultimo Aggiornamento</a></li> -->
                    <li role="presentation" class="active" ><a href="#mycollection" aria-controls="mycollection" role="tab" data-toggle="mycollectionTab">My Collection</a></li>
                    <li role="presentation"><a href="#exchangeables" aria-controls="exchangeables" role="tab" data-toggle="tab">SNITCH CARD</a></li>
                </ul>
            </div>
            <div class="col-2 col-md-2 col-lg-2 col-sm-2 col-xs-2 align-self-end">
                <button type="button" class="btn btn-default btn-lg" onclick="cleaAttr()">
                    <span class="glyphicon glyphicon-home" ></span>
                </button>
            </div>
        </div>

        <!-- Tab panes -->
        <div class="tab-content">

            <div role="tabpanel" class="tab-pane fade in active" id="mycollection">
                <%for(Card entry : c.getCardsOwn()){%>
                <div class="col-lg-2 col-md-2 col-xs-2 thumb">
                    <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                </div>
                <%}
                %>
            </div>

            <!-- QUESTO PERMETTE SAPERE LE CARTE ALTRUI -->

            <div role="tabpanel" class="tab-pane" id="exchangeables">

                <form  method="get" action= "../userprofile">

                    <label for="nickname">Nickname</label>
                    <input type="text" id="nickname" name="Nickname" placeholder="Insert Nickname">
                    <!--TASTO CERCA -->
                    <input type="submit" value="Search" >
                </form>

            </div>
        </div>

    </div>
</div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
    function cleaAttr()
    {
        <% request.getSession().removeAttribute("snitch"); %>
        location.reload();
    }
</script>

</body>
</html>