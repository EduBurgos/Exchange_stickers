programma
<%@ page import="collection.Card" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="collection.CollectionOwn" %>
<%@ page import="platform.Platform" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
    <title>Exchange</title>
    <script src="../jquery/jquery-3.4.1.js"></script>
    <link href="../bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href='https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../stylesheets/exchange.css">
    <link rel="stylesheet" href="../bootstrap-3.3.7/js/bootstrap.min.js">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

</head>

<body>

<% Platform platform = Platform.getInstance(); %>

<!-------- NAVBAR------->
<jsp:include page="navbar.jsp"/>


<form action="../exchange"  method="POST" id="myForm">
    <div id="tot">
        <%CollectionOwn c = (CollectionOwn)request.getSession().getAttribute("logged"); %>

        <!-------- DIV CARTE DA SELEZIONARE DA DARE------->
        <div class="leftbox">
            <script>
                var CardsToGiveArray = new Array();
                var toGive = "cardsToGive";
                var temporaryArray = new Array();
            </script>
            <div style="overflow: auto; width: 100%; height: 100%">
                <%int j =0;%>
                <%for(Card entry : c.getCardsOwn().keySet()){%>
                <%for(int i =0; i<c.getCardsOwn().get(entry); i++){%>
                <div class="col-lg-3 col-md-4 col-xs-6 thumb" id="<%=entry.getId() + "divToGive"+j%>" onclick="chooseCardsToGive(CardsToGiveArray, <%=entry.getId()%>, toGive, <%=j%>, temporaryArray)" >
                    <input type="hidden" onclick="selDeselCards(CardsToGiveArray, toGive)" value="<%=entry.getId()%>" id="<%=entry.getId() + "input"%>">
                    <img id="<%=entry.getId() + "cardsToGive" + j%>" src="../img/<%=entry.getCategoria()%>/<%=(entry.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid catlogim" alt="">
                    </input>
                </div>
                <%j++;%>
                <%}%>
                <%}%>
            </div>
        </div>
        <!-------- FINE DIV CARTE DA SELEZIONARE DA DARE------->

        <!-------- DIV CARTE DA SELEZIONARE DA PRENDERE------->
        <div class="rightbox">
            <script>
                var CardsToTakeArray = new Array();
                var toTake = "cardsToTake";
            </script>
            <div style="overflow: auto; width: 100%; height: 100%">
                <% ArrayList<Card> allCards = platform.allCardsCatalog();%>
                <% for (Card u : allCards) {%>

                <div class="col-lg-3 col-md-4 col-xs-6 thumb" onclick="chooseCards(CardsToTakeArray, <%=u.getId()%> , toTake)" id="<%=u.getId() + "divToTake"%>">
                    <input type="hidden" onclick="selDeselCards(CardsToTakeArray, toTake)" value="<%=u.getId()%>" id="<%=u.getId() + "input"%>">
                    <img id="<%=u.getId() + "cardsToTake"%>" src="../img/<%=u.getCategoria()%>/<%=(u.getNome()).replaceAll("\\s","")%>.png" class="zoom img-fluid catalogImages" alt="">
                    </input>
                </div>

                <%}%>
            </div>
        </div>
    </div>
    <!--------FINE DIV CARTE DA SELEZIONARE DA PRENDERE------->

    <!-------- DIV RECAP------->
    <div id="recap">
        <div  id="cardsToGiveRecap">
            <h1>DIV RECAP DA DARE</h1>
        </div>
        <div id="cardsToTakeRecap">
            <h1>DIV RECAP DA PRENDERE</h1>
        </div>
    </div>
    <!-------- FINE DIV RECAP------->

    <!-------- DIV PULSANTE START------->
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
    <!-------- FINE DIV PULSANTE START------->

</form>


<script>
    //------------------------------------------------------------
    /**
     * main selection method to add or remove a card to give
     * @param array Is the array made by cards id
     * @param card Card id(it's not ought to be unique for each owned card)
     * @param value String that identify the div where will be cloned the image
     * @param i Int rappresents the index used to identify each card
     * @param tempArray Is the array made by cards index
     */
    function chooseCardsToGive(array, card, value, i, tempArray){
        var id = card.toString().concat("input");//create the input id
        var idImg = card.toString() + value + i.toString();//create the img id
        console.log("l'idImg è "+idImg);
        var me = document.getElementById(id);//retrieve input element
        if(tempArray.includes(i) == true){
            removePictureToGive(idImg, value, i);
            document.getElementById(idImg).style.filter = "opacity(100%)";
            removeFromArray(array, card);
            removeFromArray(tempArray, i);
            me.setAttribute("name", "");
            console.log("a card is just removed, array:"+array);
            Swal.fire({
                icon: 'success',
                title: 'carta rimossa dalla trattativa',
                showConfirmButton: false,
                timer: 1500})
        } else{
            showPictureToGive(idImg, value, i);
            document.getElementById(idImg).style.filter = "opacity(40%)";
            addToArray(array, card);
            addToArray(tempArray, i);
            me.setAttribute("name", value);
            console.log("a card is just added, array:"+array);
            Swal.fire({
                icon: 'success',
                title: 'carta aggiunta alla trattativa',
                showConfirmButton: false,
                timer: 1500})
        }
    }

    /**
     * method to add the selected card in recap div to give
     * @param id String that rapresents the image id of card we want to add
     * @param where
     * @param idCard Int that rapresents the id of card we want to add
     */
    function showPictureToGive(id, where,i) {
        var idWhereToAdd = where.toString().concat("Recap");//create id div where the card will be cloned
        var completeWhere = where.concat(i.toString());
        console.log("completeWhere = "+completeWhere);
        var idDiv = id.replace(completeWhere, "divToGive");
        idDiv = idDiv.concat(i.toString());
        console.log("idDiv: "+idDiv);
        var idDivCopy = idDiv.concat("Copy").concat(i.toString());
        console.log("idDivCopy: "+idDivCopy);
        var divCard = document.getElementById(idDiv);
        var divCln = divCard.cloneNode(true);
        var img = document.getElementById(id);
        var imgCln = img.cloneNode(true);
        var src = document.getElementById(idWhereToAdd);//retrieve div recap where will be cloned
        divCln.setAttribute("id", idDivCopy);//set unique id to clone
        //divCln.removeAttribute("onclick");  //per disabilitare interazione con la copia di recap
        divCard.removeAttribute("style");//set color img without opacity filter
        src.appendChild(divCln);//add the clone div to div recap
    }

    /**
     * method to remove the selected card in recap div to give
     * @param id String that rappresents the id of card we want to remove
     * @param where
     * @param i Int that rapresents unique index
     */
    function removePictureToGive(id, where, i) {
        //var idWhereToAdd = where.toString().concat("Recap");
        var completeWhere = where.concat(i.toString());
        console.log("completeWhere = "+completeWhere);
        var idDiv = id.replace(completeWhere, "divToGive");
        idDiv = idDiv.concat(i.toString());
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

    /**
     * method to add a cards in the array that will be sent to server. This method works on divs
     * @param array Array where il be added or removed ids
     * @param card Int that rapresents the card id
     * @param value It's a string that rapresent where I'm working(toGive or toTake)
     */
    function chooseCards(array, card, value){
        var id = card.toString().concat("input");
        var idImg = card.toString() + value;
        var me = document.getElementById(id);
        showPicture(idImg, value, card);
        addToArray(array, card);
        me.setAttribute("name", value);
        console.log("a card is just added, array:"+array);
        //alert
        Swal.fire({
            icon: 'success',
            title: 'carta aggiunta alla trattativa',
            showConfirmButton: false,
            timer: 1500})
    }

    /**
     * method to remove a cards from the array that will be sent to server
     * @param array
     * @param card
     * @param value
     */
    function removeSelectedCard(array, card, value) {
        console.log("inizia il removeSelectedCard");
        var id = card.toString().concat("input");
        var idImg = card.toString() + value;
        var me = document.getElementById(id);
        removePicture(idImg, value);
        removeFromArray(array, card);
        me.setAttribute("name", "");
        console.log("a card is just removed, array:"+array);
        Swal.fire({
            icon: 'success',
            title: 'carta rimossa dalla trattativa',
            showConfirmButton: false,
            timer: 1500})
    }

    /**
     * method to add the selected card in recap div to take
     * @param id Rapresents the image id of card we want to add
     * @param where
     * @param idCard Rapresents the id of card we want to add
     */
    function showPicture(id, where, idCard) {
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
        divCln.setAttribute("onclick", "removeSelectedCard(CardsToTakeArray,"+ idCard+" , toTake)");
        src.appendChild(divCln);
    }
    /**
     * method to remove the selected card in recap div to take
     * @param id Rapresents the id of card we want to remove
     * @param where
     */
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

    /**
     * Method used to add and remove attribute 'name', which is the way to manipulate and sent cards we choose
     * @param array rapresenyts wich array will be modified
     * @param action rapresents what div we will operate in
     */
    function selDeselCards(array, action) {
        var me = document.currentScript;
        var value = me.getAttribute("value");
        if (array.includes(value) == true) {
            me.removeAttribute("name");
            removeFromArray(array, value);
        } else {
            me.setAttribute("name", action);
            addToArray(array, value, action);
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

    /**
     * method to start exchange, except if user selected no cards or more than 5
     * @param array1 Array with cards to give
     * @param array2 Array with cards to take
     */
    function startChange(array1, array2) {
        if (array1.length > 5 || array2.length > 5 ) {
            Swal.fire({
                icon: 'warning',
                title: 'non puoi selezionare più di 5 carte',
                showConfirmButton: false,
                timer: 2000})
        } else if(array1.length == 0 || array2.length ==0 ){
            Swal.fire({
                icon: 'warning',
                title: 'devi selezionare almeno una carta',
                showConfirmButton: false,
                timer: 2000})
        } else{
            document.getElementById("myForm").submit();
        }
    }
</script>
</body>
</html>
