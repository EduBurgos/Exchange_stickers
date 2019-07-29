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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="../stylesheets/userprofile.css">
</head>
<body>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">Caporetto Team</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <!-- <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
                <li><a href="../views/homepage.jsp">Home</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Exchange</a></li>
                <li><a href="#">Chat</a></li>
            </ul>
            <form class="navbar-form navbar-left">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>






<header class="site-header"></header>
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


                    <ul class="image-grid col-1">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-2">
                        <li></li>
                    </ul>

                    <!--
                    <div class="col-sm-4">
                        <h3>Tab 1</h3>
                        <p>olakjaj</p>
                    </div>

                    <div class="col-sm-4">
                        <h3>Tab 1</h3>
                        <p>olakjaj</p>
                    </div>

                    <div class="col-sm-4">
                        <h3>Tab 1</h3>
                        <p>olakjaj</p>
                    </div> -->
            </div>
            <div role="tabpanel" class="tab-pane" id="mycollection">

                    <ul class="image-grid col-1">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-2">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-3">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-4">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-5">
                        <li></li>
                    </ul>
                    <ul class="image-grid col-6">
                        <li></li>
                    </ul>
                    <!--
                    <div class="col-sm-6">
                        <h3>Tab 2</h3>
                        <p>olakjaj</p>
                    </div>

                    <div class="col-sm-6">
                        <h3>Tab 2</h3>
                        <p>olakjaj</p>
                    </div>
                    -->

            </div>

        <div role="tabpanel" class="tab-pane" id="exchangeables">

                <ul class="image-grid col-1">
                    <li></li>
                </ul>
                <ul class="image-grid col-2">
                    <li></li>
                </ul>
                <ul class="image-grid col-3">
                    <li></li>
                </ul>
                <ul class="image-grid col-4">
                    <li></li>
                </ul>



        </div>

    </div>









        <!-- Wil hyped X-->
        <!--
        <div class="unit user-hyped">
            <h3><a href="http://lookbook.nu/user/17530-Willabelle-O">Ultimo aggiornamento</a> My collection<a href="#more-looks-url"></a></h3>
            <p class="time">12 hours ago</p>

            <ul class="image-grid col-1">
                <li></li>
            </ul>
            <ul class="image-grid col-2">
                <li></li>
            </ul>
            <ul class="image-grid col-3">
                <li></li>
            </ul>
            <ul class="image-grid col-4">
                <li></li>
            </ul>
            <ul class="image-grid col-5">
                <li></li>
            </ul>
            <ul class="image-grid col-6">
                <li></li>
            </ul>

            <a href="#more-looks-url" class="more">View more <i class="fa fa-angle-down"></i></a>
            -->
        </div>

        <!-- Carte scambiabili-->

    </section>
    <section class="right-col">

    </section>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>