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
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <script src="../jquery/jquery-3.4.1.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>-->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
</head>
<body>

<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>


       <!--------MERCATO -------->
        <div class="container page-top">
            <% User u=((CollectionOwn)request.getSession().getAttribute("logged")).getOwner();   %>
            <% Platform platform=Platform.getInstance();   %>
            <%ArrayList<Exchange> ex=(ArrayList<Exchange>)request.getSession().getAttribute("exchangesList");%>
            <%if(request.getSession().getAttribute("category")!=null ||request.getSession().getAttribute("class")!=null ||request.getSession().getAttribute("type")!=null|| request.getSession().getAttribute("card")!=null) {  %>
                <%if(request.getSession().getAttribute("category")!=null ||!request.getSession().getAttribute("class").equals("") ||!request.getSession().getAttribute("type").equals("")|| !request.getSession().getAttribute("card").equals("")) {  %>
                     <%ex= platform.filtersExchanges(u.getUsername(),(String)request.getSession().getAttribute("card"),(String)request.getSession().getAttribute("category"),(String) request.getSession().getAttribute("class"),(String)request.getSession().getAttribute("type"));   %>
                <% }  %>
            <% }  %>
                <%for(int i=0;i<ex.size();i++){%>
                <%int idtrans= ex.get(i).getId_trans();     %>
                <div class="col-lg-3 ">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                   <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <%int il=0;%>
                    <% for(int id:ex.get(i).get_id_card_owm())   {  %>
                     <%if(il==0){%>
                        <%il++;%>
                        <div class="item active">
                            <%CardsDao c= new CardsDaoImpl();    %>
                            <%Card card=c.findByID(id);    %>
                            <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                            <div class="carousel-caption">
                                <button  onclick="showCards()">Show Cards Wanted</button>
                                <div class="class1" style="max-height: 275px;">
                                    <% for(int idWanted:ex.get(i).getId_card_wanted())   {  %>
                                      <%if(idWanted!=0){%>
                                        <%card=c.findByID(idWanted);    %>
                                        <img  data-src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png"  class="img-little" alt="">
                                      <%}%>
                                    <%}%>
                                    <form  method="post" action="../homepage">
                                        <input type="submit" name="btn"  value="<%=i%>"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <%}else if(id!=0 && il!=0){%>
                    <div class="item">
                        <%CardsDao c= new CardsDaoImpl();    %>
                        <%Card card=c.findByID(id);    %>
                        <img src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                        <div class="carousel-caption">
                            <button onclick="showCards()">Show Cards Wanted</button>
                            <div class="class1">
                              <% for(int idWanted:ex.get(i).getId_card_wanted())   {  %>
                                <%if(idWanted!=0){%>
                                    <%card=c.findByID(idWanted);    %>
                                    <img  data-src="../img/<%=card.getCategoria()%>/<%=(card.getNome()).replaceAll("\\s","")%>.png"  class="img-little" alt="">
                                <%} %>
                              <%}%>
                                <form  method="post" action="../homepage">
                                    <input type="submit" name="btn"  value="accept" id="<%=i%>"/>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%}%>
                  <!--  <div class="item">
                        <img src="../img/Pokemon/Axew.png" alt="...">
                        <div class="carousel-caption">
                            <button type="button" class="btn btn-default" onclick="show()">Mostra</button>
                            <div id="id1" class="class1">

                            </div>
                        </div>
                    </div>-->
                    ...
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
                </div>
                 </div>
                <%}%>






      <!--     <div class="col-lg-3 ">
            <div id="carousel-example-generic1" class="carousel slide" data-ride="carousel">-->
                <!-- Indicators -->
         <!--       <ol class="carousel-indicators">-->
                    <%//for(int i=0; i<e.getExchange(1).getId_card_owm().length;i++){ %>
             <!--       <li data-target="#carousel-example-generic1" data-slide-to="<%//i%>" class="active"></li>-->
                    <%//}%>
                 <!--   <li data-target="#carousel-example-generic1" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic1" data-slide-to="2"></li>-->
             <!--   </ol>-->

                <!-- Wrapper for slides -->
          <!--      <div class="carousel-inner" role="listbox">
                    <%//for(int i:e.getExchange(1).getId_card_owm())   {  %>
                    <div class="item active">
                        <%// Card ca=  c.findByID(i);   %>
                          <img src="../img/<%//ca.getCategoria()%>/<% //(ca.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                        <div class="carousel-caption">

                        </div>
                    </div>-->
                    <%// }%>

                <!--    <div class="item">
                        <img src="../img/Pokemon/Axew.png" alt="...">
                        <div class="carousel-caption">
                        </div>
                    </div>-->
                    ...
              <!--  </div>-->


                <!-- Controls -->
         <!--       <a class="left carousel-control" href="#carousel-example-generic1" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic1" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>-->
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
<% String role=(String) request.getSession().getAttribute("role");%>
<%if (role!=null){%>
<input type="hidden" id="role" value=<%= role %> />
<%}%>
<!-- jQuery CDN - Slim version (=without AJAX)
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
 Popper.JS
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<-- Bootstrap JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!--<script src="../bootstrap-3.3.7/js/bootstrap.min.js"></script>-->
<!--<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>-->
<script>



    function show() {
        document.getElementById('id1').style.maxHeight = "200px";
        var images = document.querySelectorAll("#id1 img");
        for(var i = 0; i < images.length; i++)
        {
            images[i].src = images[i].getAttribute('data-src');
        }
    }

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


    var mostra = document.getElementById("mostra");
    var save = document.getElementById("save");


    var exchangeNotification=document.getElementById("role");
    console.log("this is var role: "+exchangeNotification);
    if(exchangeNotification != null) {
        Swal.fire({
            position: 'bottom-end',
            icon: 'success',
            title: 'Lo scambio è stato postato con successo',
            showConfirmButton: false,
            timer: 3000
            <% request.getSession().setAttribute("role",null); %>
        })
    }
</script>
</body>

</html>