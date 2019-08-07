<%@ page import="collection.Card" %>
<%@ page import="dao.CardsDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="dao.CollectionOwnDao" %>
<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="java.util.Map" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <title>Caporetto homepage</title>

    <!-- Custom styles for this template -->
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../stylesheets/homepage.css">
    <link rel="stylesheet" href="../stylesheets/navbar.css">

    <!-- jQuery CDN - Slim version (=without AJAX) -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Popper.JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</head>
<body>
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
                <a class="navbar-brand" href="#"><strong>Caporetto Team</strong></a>

            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="#"><i class="fa fa-home"></i><strong> Home </strong><span class="sr-only">(current)</span></a></li>
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

<div class="container page-top">

    <div class="row">

        <% CollectionOwnDaoImpl collection = new CollectionOwnDaoImpl();%>
        <% UserDaoImpl allUsers = new UserDaoImpl();%>
        <% for (User i : allUsers.findAll()) {%>
        <% for (Map.Entry<Card, Integer> entry : (collection.create_collection(i)).entrySet()){%>
        <div class="col-lg-3 col-md-4 col-xs-5 thumb">
            <img src="../img/<%=entry.getKey().getCategoria()%>/<%=(entry.getKey().getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
        </div>
        <%}%>




        <%}%>









    </div>
</div>


</div>
</div>

</div>
</body>
</html>