<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="java.util.concurrent.CancellationException" %>

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
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
</head>
<body>
<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>

<!---------PROFILE-->
<div class="cover-photo"></div>
<div class="body">
    <section class="left-col user-info">
        <div class="profile-avatar">
            <div class="inner"></div>
        </div>
        <%CollectionOwn c = (CollectionOwn)request.getSession().getAttribute("logged"); %>
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
        <ul class="nav nav-tabs" role="tablist">
        <!--    <li role="presentation" class="active"><a href="#ultimoaggiornamento" aria-controls="ultimoaggiornamento" role="tab" data-toggle="tab">Ultimo Aggiornamento</a></li> -->
            <form action="../homepage" method="get"  name="mycollection" onclick="clearform();">
                <li  onclick="mycollection.submit();" role="presentation"><a href="userprofile.jsp" aria-controls="mycollection" role="tab" data-toggle="tab">My Collection</a></li>
                <!--   <li role="presentation"><a href="#exchangeables" aria-controls="exchangeables" role="tab" data-toggle="tab">Exchangeables</a></li> -->
            </form>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="mycollection">
                    <%for(Card entry : c.getCardsOwn()){%>
                    <div class="col-lg-2 col-md-2 col-xs-2 col-2 thumb">
                        <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                    </div>
                    <%}
                    %>

                </div>
            <!--      <div role="tabpanel" class="tab-pane" id="exchangeables">


                  </div>-->

        </div>
    </div>

</div>
<!-- jQuery CDN - Slim version (=without AJAX) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<!-- Popper.JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
    function clearform() {
        $("#search_frm")[0].reset();
    }
</script>

</body>
</html>