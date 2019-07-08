<!DOCTYPE html>
<html lang="it">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Caporetto Team</title>
      <!-- Font Icon -->
    <!--<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
-->
    <!-- Main css -->
    <link rel="stylesheet" href="static/ok.css">
    <!-- Non funziona! -->
    <script type="text/javascript" src="js/disable_button.js"></script>
  </head>
  <body>
  <div id="sfondo"></div>
         <div class="container">
            <div class="signup-content">
             <form method="post" id="signup-form" class="signup-form">
              <div class="form-group">
                <h2>CAPORETTO TEAM </h2>
                        <input type="radio" name="RadioButton" id="singin" class="singin" checked="true" onclick="enableTxtBox" value="signin"/>
                        SIGN-IN
                        <span/><span/>
                        <input type="radio" name="RadioButton" id="login" class="login" onclick="enableTxtBox1" value="signup" />
                        LOG-IN
              </div>
              <div class="form-group">
                    <input type="text"  class="form-input" name="name" id="user" placeholder="user"/>
              </div>
              <div class="form-group">
                    <input type="text" class="form-input" name="password" id="password" placeholder="Password"/>
                    <span toggle="#password" class="zmdi zmdi-eye field-icon toggle-password"></span>
             </div>
             <div class="form-group">
                    <input type="submit" name="signUp" id="signupB" class="form-submit submit" value="Sign up" />
             </div>
             </form>
            </div>
         </div>
  </div>
  </body>
</html>
