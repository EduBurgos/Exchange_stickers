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

    <link rel="stylesheet" href="../bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <link rel="stylesheet" href="../stylesheets/animate.css">

    <script src="../jquery/jquery-3.4.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Custom styles for this template -->

    <link rel="stylesheet" href="../stylesheets/userprofile.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>-->

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
                <ul class="nav nav-tabs" id="myTab" role="tablist">

                    <li class="nav-item" onclick="cleaAttr()">
                        <a class="nav-link active" id="mycollection-tab" data-toggle="tab" href="#mycollection" role="tab" aria-controls="mycollection" aria-selected="true">My collection</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" id="exchangeables-tab" data-toggle="tab" href="#exchangeables" role="tab" aria-controls="exchangeables" aria-selected="false">SNITCH CARD</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" id="myexchanges-tab" data-toggle="tab" href="#myexchanges" role="tab" aria-controls="myexchanges" aria-selected="false">My excahnges</a>
                    </li>
                </ul>

            </div>

        </div>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel fade in show active" class="tab-pane fade in active" id="mycollection" aria-labelledby="mycollection-tab">
                <div class="row">

                    <%int conta=0;%>
                    <% ArrayList<Card> filterArray =null;   %>
                    <%Platform platform=Platform.getInstance();      %>
                    <%if(request.getSession().getAttribute("category")!=null ||request.getSession().getAttribute("class")!=null ||request.getSession().getAttribute("type")!=null|| request.getSession().getAttribute("card")!=null) {  %>
                        <%if(request.getSession().getAttribute("category")!=null ||!request.getSession().getAttribute("class").equals("") ||!request.getSession().getAttribute("type").equals("")|| !request.getSession().getAttribute("card").equals("")) {  %>
                            <% filterArray= platform.filtersCollections(u.getUsername(),(String)request.getSession().getAttribute("card"),(String)request.getSession().getAttribute("category"),(String) request.getSession().getAttribute("class"),(String)request.getSession().getAttribute("type"));%>
                        <%}%>
                    <%}%>

                    <% if(filterArray!=null){       %>
                        <%for(Card filter: filterArray){%>
                            <div class="col-lg-2 col-md-2 col-xs-2 thumb">
                            <%conta++;%>
                                <img src="../img/<%=filter.getCategoria()%>/<%=(filter.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                                </div>
                            <%if (conta%6==0){%>
                                 </div>
                                 <div class="row">
                             <%}%>
                        <%}%>
                    <%if(filterArray.size()==0){       %>
                           <h4>Search produced no results.</h4>
                        <%}%>
                    <%} else{ %>
                    <%for(Card entry : c.getCardsOwn().keySet()){%>
                            <%for(int i=0;i<c.getCardsOwn().get(entry);i++){%>
                                <div class="col-lg-2 col-md-2 col-xs-2 thumb">
                                <%conta++;%>
                                <img src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                                </div>
                            <%}%>
                        <%if (conta%6==0){%>
                            </div>
                            <div class="row">
                        <%}%>
                    <%}%>
                                <%}%>
                </div>
            </div>
            <!-- QUESTO PERMETTE SAPERE LE CARTE ALTRUI -->
            <div role="tabpanel" class="tab-pane" id="exchangeables" aria-labelledby="exchangeables-tab">

                <form  method="get" action= "../userprofile">

                    <label for="nickname">Nickname</label>
                    <input type="text" id="nickname" name="Nickname" placeholder="Insert Nickname">
                    <!--TASTO CERCA -->
                    <input type="submit" value="Search" >
                </form>

            </div>

            <!-- Visualizza scambi -->
            <!-- Visualizza scambi -->
            <div role="tabpanel" class="tab-pane" id="myexchanges" aria-labelledby="myexchanges-tab">
                <%// Platform platform=Platform.getInstance();   %>
                <%  ArrayList<Exchange> ex= platform.getAllMyExchnages(u);%>
                <div class="row">
                    <%for(int i=0;i<ex.size();i++){%>
                <div id="carousel<%=i%>" class="carousel slide col-sm-3" style="height: 32rem;">
                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <%int attivo=0;%>
                        <% for (int ca: ex.get(i).get_id_card_owm()) { %>
                        <% CardsDao cardsDao = new CardsDaoImpl();
                            Card card=cardsDao.findByID(ca); %>
                        <%if(attivo==0){%>
                        <%attivo=1;%>
                        <div class="item active card" >
                            <%}else{%>
                            <div class="item card">
                                <%}%>

                                <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="card-img-top img-fluid d-block w-100" alt="First slide">
                                <img src="../img/Yu-Gi-Oh!/AnimaImmortale.png" style="display: none" id="toShow<%=card.getNome()%><%=ex.indexOf(ca)%>" onmouseenter="close()">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-sm-8" style="margin-left: 5px">
                                            <button type="button" class="btn btn-dark" onclick="showDiv(<%=i%>)">Show cards wanted</button>
                                        </div>
                                        <div class="col-sm-3" style="margin-left: 5px" >
                                            <form method="get" action= "../userprofile">
                                                <input type="hidden" value="<%=ex.get(i).getId_trans()%>" name="delete">
                                                <button type="submit" class="btn btn-danger">
                                                    <span class="glyphicon glyphicon-trash"></span>
                                                </button>
                                                </input>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <%}%>
                        <a class="left carousel-control" href="#carousel<%=i%>" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel<%=i%>" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
                    <div class="col-sm-3 toHide" id="toHide<%=i%>" style="display: none">
                        <div id="carousel<%=i%>W" class="carousel slide" style="height: 32rem;">
                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                                <%int attivoW=0;%>
                                <% for (int ca: ex.get(i).getId_card_wanted()) { %>
                                <% CardsDao cardsDao = new CardsDaoImpl();
                                    Card card=cardsDao.findByID(ca); %>
                                <%if(attivoW==0){%>
                                <%attivoW=1;%>
                                <div class="item active card">
                                    <%}else{%>
                                    <div class="item card">
                                        <%}%>
                                        <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="card-img-top img-fluid d-block w-100" alt="First slide">
                                    </div>
                                    <%}%>
                                    <a class="left carousel-control" href="#carousel<%=i%>W" role="button" data-slide="prev">
                                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="right carousel-control" href="#carousel<%=i%>W" role="button" data-slide="next">
                                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </div>
                            </div>
                    </div>
                    <%if ((i%2==0)&(i!=0)){ %>
                        </div>
                        <div class="row">
                    <%}%>


                    <%}%>
                </div>
            </div>

            </div>

        </div>
    </div>

</div>
</div>
</body>
<script>
    function cleaAttr()
    {
        <% request.getSession().removeAttribute("snitch"); %>
        location.reload();
    }
    function showDiv(i) {

        var x= document.getElementById("toHide"+i);
        if (x.style.display === "none") {
                x.style.display = "block";

                if (document.getElementsByClassName("toHideActive").length!=0)
                {
                    var y = document.getElementsByClassName("toHideActive")[0];
                    y.classList.remove("toHideActive");
                    y.style.display="none";
                }
                x.classList.add("toHideActive");
            }
        else
        {
            x.style.display="none";
            x.classList.remove("toHideActive");
        }
    }
</script>

</html>