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