<%@ page import="collection.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.CollectionOwnDaoImpl" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="userSide.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="collection.Card" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="collection.Catalogue" %>
<%@ page import="userSide.Exchange" %>
<%@ page import="dao.CardsDaoImpl" %>

<html>
<head>
    <title>Exchange</title>
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../stylesheets/exchange.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>


</head>

<body>
    <!-------- NAVBAR------->
    <jsp:include page="navbar.jsp"/>


    <form action="../exchange"  method="POST" id="myForm">
        <div id="tot">
        <%CollectionOwn c = (CollectionOwn)request.getSession().getAttribute("logged"); %>

            <div class="leftbox">
                <script>
                    var CardsToGiveArray = new Array();
                    var toGive = "cardsToGive";
                </script>
                <div style="overflow: auto; width: 100%; height: 100%">
                    <%for(Card entry : c.getCardsOwn()){%>

                    <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=entry.getId()%>" onclick="chooseCards(CardsToGiveArray, <%=entry.getId()%>, toGive)" >
                        <input type="hidden" onclick="selDeselCards(CardsToGiveArray, toGive)" value="<%=entry.getId()%>" id="<%=entry.getId() + "input"%>">
                                <img id="catlogim" src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                        </input>
                    </div>

                        <%}%>
                </div>
            </div>

            <div class="rightbox">
                <script>
                    var CardsToTakeArray = new Array();
                    var toTake = "cardsToTake";
                </script>
                <div style="overflow: auto; width: 100%; height: 100%">
                    <% CardsDaoImpl allCards = new CardsDaoImpl();%>
                    <% for (Card u : allCards.findAllGeneric()) {%>

                    <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=u.getId()%>" onclick="chooseCards(CardsToTakeArray, <%=u.getId()%> , toTake)">
                        <input type="hidden" onclick="selDeselCards(CardsToTakeArray, toTake)" value="<%=u.getId()%>" id="<%=u.getId() + "input"%>">
                            <img id="catalogImages" src="../img/<%=u.getCategoria()%>/<%=(u.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                        </input>
                    </div>

                    <%}%>
                </div>
            </div>
        </div>
        <div id="start" >
            <button  type="Submit" class="btn" id="startbutton">AVVIA TRATTATIVA</button>
        </div>

    </form>

    <div id="cardsToGive">

    </div>
    <div id="cardsToTake">

    </div>
<script>
    function chooseCards(array, card, value){
        var id = card + "input";
        var me = document.getElementById(id);

        if(array.includes(card) == true){
            document.getElementById(card).style.filter = "opacity(100%)";
            removeFromArray(array, card);
            me.setAttribute("name", "");
            alert("a card is just removed, array:"+array);
        } else{
            document.getElementById(card).style.filter = "opacity(40%)";
            addToArray(array, card);
            me.setAttribute("name", value);
            alert("a card is just added, array:"+array);
            showPicture("catalogImages", "cardsToGive");
        }

    }
    // method to add an element selected to array, we will use it to create the arrays to pass to servlet(one for cards to send and the other to receive)
    function addToArray(array, card){
        array.push(card);
    }

    function removeFromArray(array, card){
        var pos = array.indexOf(card);
        array.splice(pos, 1);
    }
    
    function startExchange(array1, array2) {
        document.getElementById("myForm").submit();
    }

    function selDeselCards(array, action){
        var me = document.currentScript;
        var value = me.getAttribute("value");
        if(array.includes(value) == true){
            me.removeAttribute("name");
            removeFromArray(array, value);
            //alert("a card is just removed, array:"+array);
        } else{
            me.setAttribute("name", action);
            addToArray(array, value, action);
            //alert("a card is just added, array:"+array);
        }

    }


    function showPicture(id, where) {
        var img = document.getElementById(id);
        var cln = img.cloneNode(true);
        var src = document.getElementById(where);
        src.appendChild(cln);
        document.body.appendChild(cln);
        //src.appendChild(img);
    }
</script>
</body>
</html>