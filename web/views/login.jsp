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
                    <form method="post" action="../signUp">
                        <div class="top-row">
                            <div class="field-wrap">
                                <label>
                                    First Name<span class="req">*</span>
                                </label>
                                <input type="text" required autocomplete="on" name="name"/>
                            </div>
                            <div class="field-wrap">
                                <label>
                                    Last Name<span class="req">*</span>
                                </label>
                                <input type="text" required autocomplete="on" name="surname"/>
                            </div>
                        </div>
                        <div class="field-wrap">
                            <label>
                                Username<span class="req">*</span>
                            </label>
                            <input type="text" required autocomplete="on" name="username"/>
                        </div>
                        <div class="field-wrap">

                            <label>
                                Mail<span class="req">*</span>
                            </label>
                            <input type="text" required autocomplete="on" name="mail"/>
                        </div>
                        <button type="submit" class="button button-block"/>Get Started</button>
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
                        <button class="button button-block"/>Log In</button>
                    </form>
                </div>
            </div> <!--log in -->
        </div><!--container -->
    </div><!--col -->
</div><!--row-->
</body>
</html>