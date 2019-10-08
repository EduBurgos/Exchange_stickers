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

<div id="tot">
    <form action="../exchange"  method="POST" id="myForm">
    <%CollectionOwn c = (CollectionOwn)request.getSession().getAttribute("logged"); %>

        <div class="leftbox">
            <script>
                var CardsToGiveArray = new Array();
                var toGive = "cardsToGive";
            </script>
                <div style="overflow: auto; width: 100%; height: 100%">
                        <%for(Card entry : c.getCardsOwn()){%>

                    <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=entry.getId()%>" onclick="chooseCards(CardsToGiveArray, <%=entry.getId()%>, toGive)">
                        <input onclick="" value="<%=entry.getId()%>">
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

                <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=u.getId()%>" onclick=" chooseCards(CardsToTakeArray, <%=u.getId()%> , toTake)">
                    <input onclick="" value="<%=u.getId()%>">
                    <img id="catalogImages" src="../img/<%=u.getCategoria()%>/<%=(u.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid" alt="">
                    </input>
                </div>

                <%}%>
            </div>
        </div>
    </div>
    <div id="start" >
            <button  type="Submit" class="btn" id="startbutton" onclick="startExchange(CardsToGiveArray, CardsToTakeArray)">AVVIA TRATTATIVA</button>
    </div>
    </form>

<script>
    function chooseCards(array, card, value){
        if(array.includes(card) == true){
            document.getElementById(card).style.filter = "opacity(100%)";
            removeFromArray(array, card);
            alert("a card is just removed, array:"+array);
        } else{
            document.getElementById(card).style.filter = "opacity(40%)";
            addToArray(array, card, value);
            alert("a card is just added, array:"+array);
        }

    }
    // method to add an element selected to array, we will use it to create the arrays to pass to servlet(one for cards to send and the other to receive)
    function addToArray(array, card, value){
        array.push(card);
        addAttr(value);
    }

    function removeFromArray(array, card){
        var pos = array.indexOf(card);
        array.splice(pos, 1);
        addAttr("");
    }
    
    function startExchange(array1, array2) {
        array1.forEach(addValue1ToArray);
        array2.forEach(addValue2ToArray);
        document.getElementById("myForm").submit();
    }

    function addValue1ToArray(item, index, arr) {
        arr[index] = item.setAttribute("name=\"cardToGive\"");
    }

    function addValue2ToArray(item, index, arr) {
        arr[index] = item.setAttribute("name=\"cardToTake\"");
    }

    function addAttr(x) {
        var me = document.currentScript;
        me.setAttribute("name", x);
    }

    function passToTheDarkSide(){
        document.getElementById(card).style.filter = "opacity(100%)";
    }

</script>
</body>
</html>