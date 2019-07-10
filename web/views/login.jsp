<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Caporetto Team</title>
    <!-- Main css -->
    <link rel="stylesheet" href="../stylesheets/login.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="main">
    <div class="container">
        <div class="signup-content">
            <ul class="nav nav-tabs auto">
                <li class="active"><a data-toggle="tab" href="#login-link">Login</a></li>
                <li><a data-toggle="tab" href="#singup-link">SignUp</a></li>
            </ul>
            <div class="tab-content">
                <div id="signup-link" class="tab-pane fade in active">
                    <h1>Sign Up for Free</h1>
                    <form method="post">
                        <div class="form-group">
                            <div class="top-row">
                                <div class="field-wrap">
                                    <label>
                                        First Name<span class="req">*</span>
                                    </label>
                                    <input type="text" required autocomplete="off" />
                                </div>

                                <div class="field-wrap">
                                    <label>
                                        Last Name<span class="req">*</span>
                                    </label>
                                    <input type="text"required autocomplete="off"/>
                                </div>
                            </div>
                            <div class="field-wrap">
                                <label>
                                    Username<span class="req">*</span>
                                </label>
                                <input type="text"required autocomplete="on"/>
                            </div>

                            <div class="field-wrap">
                                <label>
                                    Set A Password<span class="req">*</span>
                                </label>
                                <input type="password"required autocomplete="off"/>
                            </div>
                            <button type="submit" class="button button-block"/>Get Started</button>
                        </div>
                    </form>
                </div>
                <div id="login-link" class="tab-pane fade">
                    <h1>Welcome Back!</h1>
                    <form  method="post">
                        <div class="form-group">
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
                                <input type="password"required autocomplete="off" name="password" id=password"/>
                            </div>
                            <p class="forgot"><a href="#">Forgot Password?</a></p>
                            <button class="button button-block"/>Log In</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
