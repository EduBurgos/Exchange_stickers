<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>

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
    <link rel="stylesheet" href="../stylesheets/navbar.css">
    <link rel="stylesheet" href="../stylesheets/userprofile.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>


</head>
<body>
<!-------- NAVBAR------->
<!--<div class="container">-->
<nav class="navbar navbar-icon-top  navbar-default navbar-fixed-top ">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><strong>Caporetto Team</strong></a>

        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#"><i class="fa fa-home"></i><strong> Home </strong><span class="sr-only"></span></a></li>
                <li>
                    <a href="#">
                        <i class="fa fa-refresh">
                            <span class="badge badge-primary">5</span>
                        </i>
                        <strong>Exchange</strong>
                    </a>
                </li>

            </ul>
            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search Cards">
                    <!------  <input class="form-control mr-sm-2" type="text" placeholder="Search Cards "> -------->
                </div>
                <button type="submit" class="btn btn-success active">Search</button>
                <!------<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>------->
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        <i class="fa fa-comment">
                            <span class="badge badge-danger">11</span>
                        </i>
                        <strong> Chat </strong>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-question">
                        </i>
                        <strong> Help </strong>
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
                        <i class="fa fa-user-circle-o"></i>
                        <strong> Account </strong> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#"><strong>User Settings </strong></a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">  <strong> Logout </strong> </a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
</div>

<div class="cover-photo"></div>
<div class="body">
    <section class="left-col user-info">
        <div class="profile-avatar">
            <div class="inner"></div>
        </div>
        <h1>StellaTheBest</h1>

        <div class="meta">
            <p><i class="name"></i> Name: Carlotta Verde </p>
            <p><i class="email"></i> E-mail: carlottaverde@gmail.com </p>
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
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#ultimoaggiornamento" aria-controls="ultimoaggiornamento" role="tab" data-toggle="tab">Ultimo Aggiornamento</a></li>
            <li role="presentation"><a href="#mycollection" aria-controls="mycollection" role="tab" data-toggle="tab">My Collection</a></li>
            <li role="presentation"><a href="#exchangeables" aria-controls="exchangeables" role="tab" data-toggle="tab">Exchangeables</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="ultimoaggiornamento">
                <% CollectionOwnDaoImpl collection1 = new CollectionOwnDaoImpl();%>
                <% UserDaoImpl utente= new UserDaoImpl();%>
                <% User u =utente.findByUsername("StellaTheBest");%>
                <% ArrayList keyList = new ArrayList(collection1.create_collection(u).keySet());%>
                <%     for (int i = keyList.size() - 1; i >= 0; i--) { %>
                <% Card key = (Card)keyList.get(i); %>
                <div class="col-lg-4 col-md-4 col-xs-12 thumb">
                    <img src="../img/<%=key.getCategoria()%>/<%=(key.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                </div>

                <%}%>


            </div>
            <div role="tabpanel" class="tab-pane" id="mycollection">

                <% CollectionOwnDaoImpl collection = new CollectionOwnDaoImpl();%>
                <% for (Map.Entry<Card, Integer> entry : (collection.create_collection(u)).entrySet()){%>
                <div class="col-lg-4 col-md-4 col-xs-12 thumb">
                    <img src="../img/<%=entry.getKey().getCategoria()%>/<%=(entry.getKey().getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                </div>
                <%}%>
            </div>

            <div role="tabpanel" class="tab-pane" id="exchangeables">





            </div>

        </div>
    </div>


    <section class="right-col">

    </section>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>