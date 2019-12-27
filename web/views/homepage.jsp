<!-- HOMEPAGE: this page shows all available exchanges that can be accepted by the user who logged.-->

<%@ page import="collection.Card" %>
<%@ page import="userSide.User" %>
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
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <script src="../jquery/jquery-3.4.1.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
</head>
<body>

<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>

        <div class="container page-top">

            <% User u=((CollectionOwn)request.getSession().getAttribute("logged")).getOwner();   %>
            <% Platform platform=Platform.getInstance();   %>
            <%ArrayList<Exchange> ex=(ArrayList<Exchange>)request.getSession().getAttribute("exchangesList");%>

            <!---It is used to show exchanges filtered  by users using the search filter of the navbar --->
            <%if(request.getSession().getAttribute("category")!=null ||request.getSession().getAttribute("class")!=null ||request.getSession().getAttribute("type")!=null|| request.getSession().getAttribute("card")!=null) {  %>
                <%if(request.getSession().getAttribute("category")!=null ||!request.getSession().getAttribute("class").equals("") ||!request.getSession().getAttribute("type").equals("")|| !request.getSession().getAttribute("card").equals("")) {  %>
                     <%ex= platform.filtersExchanges(u.getUsername(),(String)request.getSession().getAttribute("card"),(String)request.getSession().getAttribute("category"),(String) request.getSession().getAttribute("class"),(String)request.getSession().getAttribute("type"));   %>
                <% }  %>
            <% }  %>


            <div class="row">
                <h1> TRATTATIVE DISPONIBILI:
                                             </h1>
                <%if(ex.size()==0){%>
                    <h4>Nessuna trattativa disponibile.</h4>
                <% }  %>
                <%for(int i=0;i<ex.size();i++){%>
                    <% User u1=platform.findUser(ex.get(i).getId_user());   %>
                    <div id="carousel<%=i%>" class="carousel slide col-sm-3">
                    <!-- Wrapper for slides -->
                    <div class="display: inline">
                        <form  method="post" action="../homepage">
                            <label style="color:darkblue"><strong><%=u1.getUsername()   %></strong></label>
                            <input type="hidden" name="btn"  value="<%=i%>"/>
                            <input type="submit"  class="btn-primary"  value="accept" name="btn"  id="<%=i%>"/>
                    </form>
                    </div>
                    <div class="carousel-inner" role="listbox">
                        <%int attivo=0;%>
                        <% for (int ca: ex.get(i).get_id_card_owm()) { %>
                            <% Card card=platform.findCardByID(ca);    %>
                            <%if(attivo==0){%>
                                 <%attivo=1;%>
                                 <div class="item active card" style="width: 18rem">
                            <%}else{%>
                            <div class="item card" style="width: 18rem">
                        <%}%>

                                <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="card-img-top img-fluid d-block w-100" alt="First slide">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-sm-8" style="margin-left: 5px">
                                            <button type="button" class="btn btn-dark" onclick="showDiv(<%=i%>)">Show cards wanted</button>
                                        </div>
                                        <div class="col-sm-3" style="margin-left: 5px" >

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
                        <div id="carousel<%=i%>W" class="carousel slide">
                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                                <%int attivoW=0;%>
                                <% for (int ca: ex.get(i).getId_card_wanted()) { %>
                                    <% Card card=platform.findCardByID(ca);    %>
                                    <%if(attivoW==0){%>
                                        <%attivoW=1;%>
                                        <div class="item active card" id="cardW" >
                                    <%}else{%>
                                        <div class="item card" id="cardW" >
                                     <%}%>
                                    <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="card-img-top img-fluid d-block w-100" alt="First slide">
                                    </div>
                                <%}%>
                                    <a class="left carousel-control" href="#carousel<%=i%>W" role="button" data-slide="prev" id="push">
                                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="right carousel-control" href="#carousel<%=i%>W" role="button" data-slide="next" id="push">
                                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <%if (((i+1)%3==0)&(i!=0)){ %>
                    </div>
                    <div class="row">
                        <%}%>


                        <%}%>
                    </div>
                </div>
<!-- INIZIO COMENTO IMPLEMENTAZIONE 1 MARKET
                    <%// Platform market1 = Platform.getInstance(); %>
                <h6><%// market1.toString(); %></h6>
                    <%// ArrayList<Exchange>  market = market1.getExchange();  %>
                    <%// for (Exchange x : market) {%>
                    <%// System.out.printf("<td>"+x.getId_user()+"<td>");%>
                    <%// } %>
      FINE COMENTO-->

                    <!-- INIZIO COMENTO IMPLEMENTAZIONE 2 MARKET

                    <%// ArrayList<Exchange> ec = (ArrayList<Exchange>) request.getSession().getAttribute("market");%>
                    <%// if(ec == null){%>
                       <script>alert("DIO BAO BAO")</script>
                   <%//} %>
                    <%// ExchangeCardDAOImpl market = new ExchangeCardDAOImpl(); %>
                    <%// for(Exchange x : ec) {
                        /*System.out.println("inizio stampa exchange: ");
                        System.out.println(x.getId_user());
                        System.out.println(x.getId_trans());
                        System.out.println(x.getId_card_owm());
                        System.out.println(x.getId_card_wanted());
                        System.out.println(x.isTrans_comp());
                    }*/
                    %>
FINE COMENTO-->


         <!--   <div class="container">
                <div id="myCarousel" class="carousel slide" data-ride="carousel">-->

                    <!-- Wrapper for slides -->
              <!--      <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <div class="divA">
                                <div class="divAA">
                                    <div class="div1" class="targetDiv">
                                        <p>Div1 Intro: Hidden until Button1 is clicked</p>
                                    </div>
                                </div>
                                <p>
                                    Div1 Short to be shown by default.
                                    <span class="div1" class="targetDiv">Hidden span until Button1 is clicked</span>
                                </p>
                                <a class="showSingle" target="1">Button1</a>
                                <div class="divAAA">
                                    <div class="div1" class="targetDiv">
                                            <img class="what" src="../img/Pokemon/Axew.png" alt="...">
                                            <img class="what" src="../img/Pokemon/Axew.png" alt="...">
                                            Div1 Details: Hidden until Button1 is clicked.
                                        <a target="2">Accetta</a>
                                        <INPUT TYPE="BUTTON" VALUE="Save" class="styled-button-2" id="save">
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>




                    </div>-->

                    <!-- Left and right controls -->
                <!--    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>-->



        </div> <!-----END ROW---->



        </div><!----- container page-top----->


</div>
</div>

</div>

<!-- START NOTIFICATION PART-->

<!-- EXCHANGE SETTING NOTIFICATION -->
<% String role=(String) request.getSession().getAttribute("role");%>
<%if (role!=null){%>
<script>
    Swal.fire({
        position: 'bottom-end',
        icon: 'success',
        title: 'Lo scambio è stato postato con successo',
        showConfirmButton: false,
        timer: 2500})
</script>
<% request.getSession().setAttribute("role",null); %>
<%}%>

<!-- EXCHANGE ACCEPTANCE NOTIFICATION -->
<% String doneExchangeNotif=(String) request.getSession().getAttribute("doneExchange");%>
<%if ("true".equalsIgnoreCase(doneExchangeNotif)){%>
<script>
Swal.fire({
position: 'bottom-end',
icon: 'success',
title: 'Lo scambio è avvenuto con successo',
showConfirmButton: false,
timer: 2500})
</script>
<%request.getSession().setAttribute("doneExchange",null); %>
<%}%>

<%if ("false".equalsIgnoreCase(doneExchangeNotif)) { %>
<script>
Swal.fire({
title: 'C\'è stato qualche problema nell\'esecuzione dello scambio.\n Riprova',
icon: 'error',
showConfirmButton: true})
</script>
<% request.getSession().setAttribute("doneExchange",null); %>
<%}%>
<!-- END NOTIFICATION PART-->

<!-- jQuery CDN - Slim version (=without AJAX)
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
 Popper.JS
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<-- Bootstrap JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!--<script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>-->
<!--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>-->
<script>


    //da cancellare
    function show() {
        document.getElementById('id1').style.maxHeight = "200px";
        var images = document.querySelectorAll("#id1 img");
        for(var i = 0; i < images.length; i++)
        {
            images[i].src = images[i].getAttribute('data-src');
        }
    }

    // da cancellare
    var op=0;
    function showCards() {
        document.getElementsByClassName("class1")[0].style.maxHeight = "200px";
        var images = document.querySelectorAll(".class1 > img");
        for(var i = 0; i < images.length; i++)
        {
            images[i].src = images[i].getAttribute('data-src');
            images[i].classList.toggle('off');
        }
        showOpacity();

    }

    // da cancellare
function showOpacity(){
    var imagesactive = document.querySelectorAll(".item > img");
    if(imagesactive[0].style.opacity==='1') {
        for (var i = 0; i < imagesactive.length; i++) {
            imagesactive[i].style.opacity = '0.3';

        }
        op=1;
    }else{
        for (var i = 0; i < imagesactive.length; i++) {
            imagesactive[i].style.opacity = '1';

        }
        op=0;
    }
}


    jQuery(function(){
        jQuery('.showSingle').click(function(){
            jQuery('.targetDiv').hide();
            jQuery('.div'+jQuery(this).attr('target')).show();
        });
    });

    /**
     *  Method shows wanted cards when button " show card wanted" is click.
     *  @param i counter
     */

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

   // da cancellare
   var mostra = document.getElementById("mostra");
    var save = document.getElementById("save");


</script>
</body>

</html>