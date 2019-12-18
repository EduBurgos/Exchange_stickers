<%@ page import="dao.CardsDao" %>
<%@ page import="userSide.Exchange" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="platform.Platform" %>
<%@ page import="collection.Card" %>
<!-- NAVIGATION BAR : this page contains the top navigation bar with links to Home, Reservations and Catalogue ;
it is included in each page of the application -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Custom styles for this template -->
    <meta charset="UTF-8">
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="../stylesheets/navbar.css">
</head>
<body>
<% Platform platform=Platform.getInstance();   %>
<!-------- NAVBAR------->
<div class="container">
    <nav class="navbar navbar-icon-top  navbar-default  navbar-fixed-top ">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!--a class="navbar-brand" ><strong>Caporetto Team!</strong></a>-->
                <a class="navbar-brand"id="logo"></a>

            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="../views/homepage.jsp"><i class="fa fa-home"></i><strong> Home </strong><span class="sr-only">(current)</span></a></li>
                    <li>
                        <a href="../views/exchange.jsp">
                            <i class="fa fa-refresh"></i>
                            <strong>Exchange</strong>
                        </a>
                    </li>

                </ul>
                <!---SEARCH WITH FILTERS--->
                <form role="form" method="get" action="../Search">

                    <div class="row1">
                        <div class="col-sm-3">
                            <div class="input-group" id="adv-search">
                                <input type="text" name="filterCard" class="form-control" placeholder="Search Card..." />
                                <div class="input-group-btn">
                                    <div class="btn-group" role="group">
                                        <div class="dropdown dropdown-lg">
                                            <button type="button" class="btn btn-warning dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" ><span class="glyphicon glyphicon-filter" >&nbsp;</span><span class="caret" >&nbsp;</span></button>
                                            <div class="dropdown-menu dropdown-menu-right" role="menu" id="menuS">
                                                <div class="form-horizontal" >
                                                    <div class="form-group" id="checklistCategory" >
                                                        <label >Category</label>
                                                        <div class="checkbox" >
                                                            <label><input type="checkbox" name="filterCategory" value="Hearthstone">Hearthstone</label>
                                                        </div>
                                                        <div class="checkbox">
                                                            <label><input type="checkbox" name="filterCategory" value="Pokemon">Pokemon</label>
                                                        </div>
                                                        <div class="checkbox">
                                                            <label><input type="checkbox" name="filterCategory" value="Yu-Gi-Oh!">Yu-Gi-Oh!</label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label> Card Class</label>
                                                        <input class="form-control" type="text" name="filterClass" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Card Type</label>
                                                        <input class="form-control" type="text" name="filterType" />
                                                    </div>
                                                    <button type="submit" class="btn btn-success active"><span class="glyphicon glyphicon-search" aria-hidden="true"></span><span class="label-icon" >Search</span></button>
                                                </div>
                                            </div>
                                        </div>
                                        <button id="searchBtn1" type="submit" class="btn btn-success active" ><span class="glyphicon glyphicon-search" ></span> </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form> <!--- END SEARCH WITH FILTERS--->


                <ul class="nav navbar-nav navbar-right">
                    <!-- NOTIFICATION -->
                    <%ArrayList<Exchange> notifications=(ArrayList<Exchange>)request.getSession().getAttribute("exchangesToNotify");%>
                    <%int notificationNumber =0;%>
                    <% if(notifications != null){notificationNumber = notifications.size();}%>
                    <% if(notificationNumber>0) {%>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="removeIndex()">
                            <span class="glyphicon glyphicon-bell"><strong id="notifNumber"><%=notificationNumber%></strong>
                            </span>
                            <strong>Notification</strong>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background: whitesmoke">
                        <%for (Exchange exchange : notifications) {%>


                                <div class="dropdown-item">
                                    <p>Lo scambio:</p>
                                    <%String givenCards= "";%>
                                    <%for (int id : exchange.get_id_card_owm()){%>
                                    <% givenCards+= platform.findCardByID(id).getNome()+", ";%>
                                        <%}%>
                                    <p>
                                    <strong><%=givenCards%></strong>
                                    </p>
                                    <p>
                                    per
                                    </p>
                                    <%String takenCards= "";%>
                                    <%for (int id : exchange.getId_card_wanted()){%>
                                    <% takenCards+= platform.findCardByID(id).getNome()+", ";%>
                                    <%}%>
                                    <p>
                                       <strong><%=takenCards%></strong>
                                    </p>
                                    <p>
                                        è stato accettato da <%=exchange.getUsername_offerente()%>
                                    </p>
                                </div>
                                <div class="divider"></div>

                            <%}%>
                        </div>
                    </li>
                    <%} else{%>
                    <li class="nav-item dropdown" >
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="glyphicon glyphicon-bell"></span>
                            <strong>Notification</strong>
                        </a>
                        <div class="dropdown-menu" style="background: whitesmoke">
                            Nessuna notifica
                        </div>
                    </li>
                            <%}%>

                    <!-- END NOTIFICATION -->
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
                            <i class="fa fa-question">
                            </i>
                            <strong> Help </strong>
                        </a>
                        <ul>
                            <div class="dropdown-menu" id="info">
                                <p>
                                    <strong>Some example text that's free-flowing within the dropdown menu.</strong>
                                </p>
                                <p class="mb-0">
                                    <strong>And this is more example text.</strong>
                                </p>
                                <p>
                                    <strong>Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullamco laboriosam, nisi ut aliquid ex ea commodi consequatur. Duis aute irure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</strong>
                                </p>
                            </div>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
                            <i class="fa fa-user-circle-o"></i>
                            <strong> Account </strong> <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" id="menuP">
                            <li><a href=" ../views/userprofile.jsp" ><strong> Profile</strong></a></li>
                            <li role="separator" class="divider"></li>
                            <form method="post" action="../logout">
                                <li><button type="submit" class="btn btn-primary-outline"> <i class="glyphicon glyphicon-log-out"></i><strong> Logout </strong></button></li>
                            </form>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div><!------END NAVBAR------->

<!-----SCROLL-TO-TOP------>
<button onclick="topFunction()" id="myBtn" title="Go to top"><span class="glyphicon glyphicon-upload" ></span></button>
<!------END SCROLL-TO-TOP------->

<!-----SCRIPT OF THE NAVBAR AND SCROLL TO TOP-------->

<script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>

<script>
    // When the user scrolls down 20px from the top of the document, show the button
    window.onscroll = function() {scrollFunction()};

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            document.getElementById("myBtn").style.display = "block";
        } else {
            document.getElementById("myBtn").style.display = "none";
        }
    }

    // When the user clicks on the button, scroll to the top of the document
    function topFunction() {
        document.documentElement.scrollTop = 0;
    }
    function cleaAttr()
    {
        <% request.getSession().removeAttribute("snitched"); %>
        location.href(" ../views/userprofile.jsp").load;
    }

    function removeIndex() {
        document.getElementById("notifNumber").style.display = "none";
        <% request.getSession().setAttribute("exchangesToNotify",null); %>
    }
</script>

<!-----SCRIPT OF THE SEARCH WITH FILTERS----->



</body>
</html>
<!-- jQuery CDN - Slim version (=without AJAX) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<!-- Popper.JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<script src="../bootstrap-3.3.7/js/bootstrap.js"></script>
</body>
</html>