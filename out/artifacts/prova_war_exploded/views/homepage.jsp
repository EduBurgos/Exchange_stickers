<%@ page import="collection.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
@args ArrayList<Card> cards
<html lang="en">

    <head>

      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <meta name="description" content="">
      <meta name="author" content="">

      <title>Caporetto homepage</title>


      <!-- Custom styles for this template -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <!-- Our Custom CSS -->
        <link rel="stylesheet" href="web/stylesheets/homepage.css">
        <link rel="stylesheet" href="web/stylesheets/bootstrap-material-design.css">

        <!-- jQuery CDN - Slim version (=without AJAX) -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <!-- Popper.JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        
        <script type="text/javascript">
            $(document).ready(function () {
                $('#sidebarCollapse').on('click', function () {
                    $('#sidebar').toggleClass('active');
                    $(this).toggleClass('active');
                });
            });
        </script>


    </head>

    <body>
    	 <div class="wrapper">
            <!-- Sidebar Holder -->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <h3>Caporetto Team</h3>
                </div>
                <ul class="list-unstyled components">
                    <li class="active">
                        <a href="#">Home</a>
                    </li>
                    <li class="#">
                        <a href="#">Profile</a>
                    </li>
                    <li>
                        <a href="#">Exchange</a>
                    </li>
                    <li>
                        <a href="#">Chat</a>
                    </li>
                </ul>
            </nav>

            <!-- Page Content Holder -->
            <div id="content">

                <nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-static-top">
                    <div class="container-fluid">
                        <button type="button" id="sidebarCollapse" class="navbar-btn">
                            <span></span>
                            <span></span>
                            <span></span>
                        </button>
                        <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fas fa-align-justify"></i>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="nav navbar-nav ml-auto">
                                <li class="nav-item">
    								<form class="form-inline mr-auto">
    									<!--btn btn-outline-success btn-sm my-0-->
    						        	<input class="input-group" type="text" placeholder="Search" aria-label="Search">
    						        	<span></span><span></span>
    						        		<button class="searchbtn" type="submit">
    						        			Search
    						        		</button>
    						        </form>      
    							</li>
                            </ul>
                        </div>
                    </div>
                </nav>
                 <!--Card box-->
                <div class="card_box">
                    <div class="card_des">
                        <!--Immagine-->
                        <p class="card_price">
                            <p class="card_price">
                                @for(card:cards){
                                @card.getId(); 
                                }
                            </p>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>