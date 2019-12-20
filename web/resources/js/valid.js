var isXvalid = false;
let TF = document.getElementById("x");
console.log("x= " + TF);
TF.oninput = valid(TF);

function valid(element) {
    console.log("try valid")
    const errmsg = document.getElementById("error-message");
    let X = element.value.replace(/,/, '.');
    let isValid = isNumber(X);
    isValid = isValid && (X < 5) && (X > -5);
    if (!isValid) {
        element.style.borderColor = "red";
    } else {
        element.style.borderColor = "green";
    }
    if (!isValid) {
        errmsg.textContent = "Error";
    } else {
        errmsg.textContent = " ";
    }
    isXvalid = isValid;
    console.log("x valid: " + isXvalid);
    disableButtons(!isValid);
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && !isNaN(n - 0)
}


function getR() {
    let r_field = document.getElementById("r");
    let num = r_field.value;
    return num;
}

function disableButtons(isDisabled) {
    let button = document.getElementById("subm");
    console.log(" ");
    button.disabled = isDisabled;
    console.log("disabled = " + isDisabled);
}
