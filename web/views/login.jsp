<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="static javax.swing.text.html.CSS.getAttribute" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../stylesheets/bootstrap.min.css">
    <link rel="stylesheet" href="../stylesheets/login.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<div class="row">
    <div class="col-7">
        <div class="container-fluid">
            <div id="slider" class="carousel slide" data-ride="carousel" data-interval="3000">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100 img-fluid" src="../img/images/drago.png" alt="Firts slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100 img-fluid" src="../img/images/pokemon.png" alt="Second slide">
                    </div>

                </div>
            </div>
        </div>
    </div> <!--slider-->
    <div class="col-5">
        <!-- Tabs logIn sign up -->
        <div class="container">
            <ul class="nav nav-tabs">
                <li class="active nav-item"><a data-toggle="tab" href="#login-link">Login</a></li>
                <li class="nav-item"><a data-toggle="tab" href="#signup-link">SignUp</a></li>
            </ul>
            <!-- Sign Up form start -->
            <div class="tab-content">
                <div id="signup-link" class="tab-pane fade show active">
                    <h1>Sign Up for Free</h1>
                    <form  method="post" action="../signUp">
                        <div class="top-row">
                            <div class="field-wrap">
                                <label>
                                    First Name<span class="req">*</span>
                                </label>
                                <input type="text" required autocomplete="on" name="FirstName"/>
                            </div>
                            <div class="field-wrap">
                                <label>
                                    Last Name<span class="req">* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                </label>
                                <input type="text" required autocomplete="on" name="LastName"/>
                            </div>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Username<span class="req">* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </label>
                            <input type="text" required autocomplete="on" name="Username"/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Email<span class="req">* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
                            </label>
                            <input type="text" required autocomplete="on" name="Email" id="email" onkeydown="validate(this.value);"/>
                            <span id='emailmessage'></span>
                        </div>

                        <div class="field-wrap">
                            <label>
                                Password<span class="req">* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
                            </label>
                            <input type="password" required autocomplete="off" name="Password" id="newpassword" onkeyup='check();' />

                        </div>

                        <div class="field-wrap">
                            <label>
                                Re-Type Password<span class="req">*</span>
                            </label>
                            <input type="password" required autocomplete="off" name="ReType" id="confirm_password"  onkeyup='check();'/>
                            <span id='message'></span>
                        </div>

                        <button type="submit" class="button button-block" id="startedButton"/>Get Started</button>

                        <%if (request.getParameter("Get Started") != null){

                        }%>

                        <script>
                            var check = function() {
                                var message=document.getElementById('message');
                                if (document.getElementById('newpassword').value!=document.getElementById('confirm_password').value) {
                                    message.style.color = 'red';
                                    message.style.fontWeight= 'bold';
                                    message.innerHTML = 'not matching';
                                    document.getElementById('startedButton').disabled=true;
                                }
                                else{
                                  message.innerHTML = '';
                                  document.getElementById('startedButton').disabled=false;
                                }
                            };


                            var regex = /^([A-z0-9.+_-]+)*@([A-z0-9._-]+\.)+([A-z]{2,6})$/;
                            var emailMessage= document.getElementById("emailmessage");
                            function validate(email){
                                if(!regex.test(email))
                                {
                                    emailMessage.innerHTML	= "not a valid email";
                                    emailMessage.style.color = "red";
                                    emailMessage.style.fontWeight= 'bold';
                                    document.getElementById('startedButton').disabled=true;
                                }
                                else
                                {
                                    emailMessage.innerHTML	= "";
                                    document.getElementById('startedButton').disabled=false;
                                }
                            }

                        </script>


                        <% if(request.getSession().getAttribute("message")!=null){ %>
                        <h3><%=request.getSession().getAttribute("message") %></h3>
                        <%}%>

                    </form>
                </div><!--sign up -->

                <!--logIn form Start -->
                <div id="login-link" class="tab-pane fade">
                    <h1>Welcome Back!</h1>
                    <form  method="post" action="../login">
                        <div class="field-wrap">
                            <label>
                                Username
                            </label>
                            <input type="text" required autocomplete="on" name="name" id="name"/>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Password<span class="req">*</span>
                            </label>
                            <input type="password" required autocomplete="off" name="password" id="password"/>
                        </div>
                        <p class="forgot"><a href="#">Forgot Password?</a></p>
                        <button class="button button-block" />Log In</button>

                        <% if(request.getSession().getAttribute("errorMessage")!=null){ %>
                        <h3><%=request.getSession().getAttribute("errorMessage") %></h3>
                        <%}%>




                    </form>
                </div>
            </div> <!--log in -->
        </div><!--container -->
    </div><!--col -->
</div><!--row-->
</body>
</html>