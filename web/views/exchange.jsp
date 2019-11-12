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
                <%for(Card entry : c.getCardsOwn().keySet()){%>
                <%for(int i =0; i<c.getCardsOwn().get(entry); i++){%>
                <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=entry.getId() + "divToGive"%>" onclick="chooseCardsToGive(CardsToGiveArray, <%=entry.getId()%>, toGive, <%=i%>)" >
                    <input type="hidden" onclick="selDeselCards(CardsToGiveArray, toGive)" value="<%=entry.getId()%>" id="<%=entry.getId() + "input"%>">
                    <img id="<%=entry.getId() + "cardsToGive" + i%>" src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid catlogim" alt="">
                    </input>
                </div>

                <%}%>
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

                <div class="col-lg-3 col-md-4 col-xs-6 thumb" onclick="chooseCards(CardsToTakeArray, <%=u.getId()%> , toTake)" id="<%=u.getId() + "divToTake"%>">
                    <input type="hidden" onclick="selDeselCards(CardsToTakeArray, toTake)" value="<%=u.getId()%>" id="<%=u.getId() + "input"%>">
                    <img id="<%=u.getId() + "cardsToTake"%>" src="../img/<%=u.getCategoria()%>/<%=(u.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid catalogImages" alt="">
                    </input>
                </div>

                <%}%>
            </div>
        </div>
    </div>
    <div id="recap">
        <div  id="cardsToGiveRecap">
            <h1>DIV RECAP DA DARE</h1>
        </div>
        <div id="cardsToTakeRecap">
            <h1>DIV RECAP DA PRENDERE</h1>
        </div>
    </div>
    <div id="start" >
        <script>
            if (CardsToTakeArray.length > 5 || CardsToGiveArray.length > 5 || CardsToTakeArray == null || CardsToGiveArray == null) {
                document.body.innerHTML += "<div id=\"start\" ><button type=\"submit\" class=\"btn\" id=\"startbutton\" disabled>AVVIA TRATTATIVA</button></div>";
            } else{
                document.body.innerHTML += "<div id=\"start\" ><button type=\"submit\" class=\"btn\" id=\"startbutton\" onclick='startChange(CardsToGiveArray, CardsToTakeArray)'>AVVIA TRATTATIVA</button></div>";
            }
            //var startButton = document.getElementById('startbutton');
            //startButton.setAttribute('id', 'startbutton');
            //startButton.disabled = false;
            //startButton.setAttribute('disabled', '');
            /* if (5>array1.length && 5>array2){
                var startButton = document.createElement('button');
                startButton.setAttribute('id', 'startbutton')
                startButton.setAttribute('disabled', 'true');
            } else {
                var startButton = document.createElement('button');
                startButton.setAttribute('id', 'startbutton')
                var startButton = document.getElementById('startbutton');
                startButton.setAttribute('disabled', 'false');
            } */
        </script>

    </div>

</form>


<script>
    //------------------------------------------------------------
    function chooseCardsToGive(array, card, value, i){
        var id = card.toString().concat("input");
        var idImg = card.toString() + value + i.toString();
        console.log("l'idImg è "+idImg);
        var me = document.getElementById(id);


        if(array.includes(card) == true){
            removePictureToGive(idImg, value, i);
            document.getElementById(idImg).style.filter = "opacity(100%)";
            removeFromArray(array, card);
            me.setAttribute("name", "");
            alert("a card is just removed, array:"+array);
        } else{
            showPictureToGive(idImg, value, i);
            document.getElementById(idImg).style.filter = "opacity(40%)";
            addToArray(array, card);
            me.setAttribute("name", value);
            alert("a card is just added, array:"+array);
        }
    }





    function showPictureToGive(id, where,i) {
        var idWhereToAdd = where.toString().concat("Recap");
        var completeWhere = where.concat(i.toString());
        console.log("completeWhere = "+completeWhere);
        var idDiv = id.replace(completeWhere, "divToGive");
        console.log("idDiv: "+idDiv);
        var idDivCopy = idDiv.concat("Copy").concat(i.toString());
        console.log("idDivCopy: "+idDivCopy);
        var divCard = document.getElementById(idDiv);
        var divCln = divCard.cloneNode(true);
        var img = document.getElementById(id);
        var imgCln = img.cloneNode(true);
        var src = document.getElementById(idWhereToAdd);
        divCln.setAttribute("id", idDivCopy);
        divCln.removeAttribute("onclick");  //per disabilitare interazione con la copia di recap
        src.appendChild(divCln);
    }

    function removePictureToGive(id, where, i) {
        //var idWhereToAdd = where.toString().concat("Recap");
        var completeWhere = where.concat(i.toString());
        console.log("completeWhere = "+completeWhere);
        var idDiv = id.replace(completeWhere, "divToGive");
        var idDivCopy = idDiv.concat("Copy").concat(i.toString());
        //var imgToRemove = document.getElementById(idDivCopy);
        console.log(idDiv);
        console.log(idDivCopy);
        //var divCard = document.getElementById(idDiv);
        //var divCln = divCard.cloneNode(true);
        var img = document.getElementById(id);
        //var imgCln = img.cloneNode(true);
        //var src = document.getElementById(idWhereToAdd);
        console.log("idDivCopy = "+idDivCopy);
        var elem = document.getElementById(idDivCopy);
        console.log("elem = "+elem);
        return elem.parentNode.removeChild(elem);
    }
    //------------------------------------------------------------
    function chooseCards(array, card, value){

        var id = card.toString().concat("input");
        var idImg = card.toString() + value;
        var me = document.getElementById(id);
        if(array.includes(card) == true){
            removePicture(idImg, value);
            //document.getElementById(idImg).style.filter = "opacity(100%)";
            removeFromArray(array, card);
            me.setAttribute("name", "");
            alert("a card is just removed, array:"+array);
        } else{
            showPicture(idImg, value);
            //document.getElementById(idImg).style.filter = "opacity(40%)";
            addToArray(array, card);
            me.setAttribute("name", value);
            alert("a card is just added, array:"+array);
        }
    }
    function showPicture(id, where) {
        var idWhereToAdd = where.toString().concat("Recap");
        var idDiv = id.replace(where, "divToTake");
        var idDivCopy = idDiv.concat("Copy");
        console.log(idDiv);
        var divCard = document.getElementById(idDiv);
        var divCln = divCard.cloneNode(true);
        var img = document.getElementById(id);
        var imgCln = img.cloneNode(true);
        var src = document.getElementById(idWhereToAdd);
        divCln.setAttribute("id", idDivCopy);
        divCln.removeAttribute("onclick");  //per disabilitare interazione con la copia di recap
        src.appendChild(divCln);
    }

    function removePicture(id, where) {
        var idWhereToAdd = where.toString().concat("Recap");
        var idDiv = id.replace(where, "divToTake");
        var idDivCopy = idDiv.concat("Copy");
        var imgToRemove = document.getElementById(idDivCopy);
        console.log(idDiv);
        console.log(idDivCopy);
        var divCard = document.getElementById(idDiv);
        var divCln = divCard.cloneNode(true);
        var img = document.getElementById(id);
        var imgCln = img.cloneNode(true);
        var src = document.getElementById(idWhereToAdd);
        var elem = document.getElementById(idDivCopy);
        return elem.parentNode.removeChild(elem);
    }
    function selDeselCards(array, action) {
        var me = document.currentScript;
        var value = me.getAttribute("value");
        if (array.includes(value) == true) {
            me.removeAttribute("name");
            removeFromArray(array, value);
            //alert("a card is just removed, array:"+array);
        } else {
            me.setAttribute("name", action);
            addToArray(array, value, action);
            //alert("a card is just added, array:"+array);
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
    function startChange(array1, array2) {
        if (array1.length > 5 || array2.length > 5 ) {
            alert("non puoi selezionare più di 5 carte");
        } else if(array1.length == 0 || array2.length ==0 ){
            alert("devi selezionare almeno una carta");
        } else{
            document.getElementById("myForm").submit();
        }
    }
</script>
</body>
</html>