<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="platform.Platform" %>
<%@ page import="userSide.Exchange" %>
<%@ page import="dao.*" %>

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
                    <li role="presentation"><a href="#myexchanges" aria-controls="myexchanges" role="tab" data-toggle="myexchanges">My exchanges</a></li>
                    <li role="presentation"><a href="#filters" aria-controls="filters" role="tab" data-toggle="tab">Filter</a></li>

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
                <%for(Card entry : c.getCardsOwn().keySet()){%>
                <div class="col-lg-2 col-md-2 col-xs-2 thumb">
                    <%for(int i=0;i<c.getCardsOwn().get(entry);i++){%>
                        <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                    <%}
                    %>
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

            <!-- Visualizza scambi -->
            <div role="tabpanel" class="tab-pane" id="myexchanges">
                <% Platform platform = Platform.getInstance();
                    ArrayList<Exchange> ex= platform.getAllMyExchnages(u);%>
                <% for(int i =0 ; i< ex.size(); i++) { %>
                    <% for (int ca: ex.get(i).get_id_card_owm()) { %>
                        <% CardsDao cardsDao = new CardsDaoImpl();
                             Card card=cardsDao.findByID(ca); %>
                            <div class="col-4">
                                <div class="card" style="width: 18rem;">
                                    <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="card-img-top">
                                    <div class="card-body">
                                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                        <button type="button" class="btn btn-dark" onclick="ShowDiv()">Go somewhere</button>
                                    </div>
                                </div>
                                <div class="row" id="toHide"></div>
                            </div>
                    <% } %>
                <%}%>
            </div>



              <div role="tabpanel" class="tab-pane" id="filters">

                    <form  method="get" action= "../Search">
                        <% ArrayList<Card> filterArray=null ;     %>
                        <%//Platform platform=Platform.getInstance();      %>
                    <%if(request.getSession().getAttribute("category")!=null ||request.getSession().getAttribute("class")!=null ||request.getSession().getAttribute("type")!=null|| request.getSession().getAttribute("card")!=null) {  %>
                      <% filterArray= platform.filtersCollections(u.getUsername(),(String)request.getSession().getAttribute("card"),(String)request.getSession().getAttribute("category"),(String) request.getSession().getAttribute("class"),(String)request.getSession().getAttribute("type"));%>
                        <% }  %>
                        <% if(filterArray!=null){      %>
                    <%for(Card filter: filterArray){%>
                        <div class="col-lg-2 col-md-2 col-xs-2 thumb">
                        <img src="../img/<%=filter.getCategoria()%>/<%=(filter.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                        </div>
                    <%}
                    %>
                        <%}
                        %>
                    </form>
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
    function showDiv() {
        var x = document.getElementById("toHide");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
</script>

</body>
</html>