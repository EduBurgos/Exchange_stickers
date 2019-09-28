<%@ page import="collection.Card" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="dao.*" %>
<%@ page import="userSide.Exchange" %>
<%@ page import="platform.Platform" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Caporetto homepage</title>

    <!-- Custom styles for this template -->
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="../stylesheets/homepage.css">

</head>
<body>

<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>

       <!--------MERCATO -------->
        <div class="container page-top">
                <div class="row">

<!-- INIZIO COMENTO
                    <%// Platform market1 = Platform.getInstance(); %>
                <h6><%// market1.toString(); %></h6>
                    <%// ArrayList<Exchange>  market = market1.getExchange();  %>
                    <%// for (Exchange x : market) {%>
                    <%// System.out.printf("<td>"+x.getId_user()+"<td>");%>
                    <%// } %>
      FINE COMENTO-->

                    <%ArrayList<Exchange> ec = (ArrayList<Exchange>) request.getSession().getAttribute("market");%>

                    <% if(ec == null){%>
                       <script>alert("DIO BAO BAO")</script>
                   <%} %>

                    <% ExchangeCardDAOImpl market = new ExchangeCardDAOImpl(); %>



                </div> <!-----END ROW---->

        </div><!----- container page-top----->


</div>
</div>

</div>

<!-- jQuery CDN - Slim version (=without AJAX)
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
 Popper.JS
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

</body>

</html>