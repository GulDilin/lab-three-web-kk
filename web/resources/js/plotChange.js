function getDotCoor() {
    const plot_container = document.getElementById("svg-plot");
    console.log(plot_container);
    let rect = plot_container.getBoundingClientRect();
    let y_dot = (event.clientY - rect.top);
    let x_dot = (event.clientX - rect.left);
    console.log(y_dot, x_dot);
    let y = (150 - y_dot);
    let x = (-150 + x_dot);
    let isDotAllowed = true;
    let result = "";
    if (isDotAllowed) {
        let R = document.getElementById("r").value;
        console.log(R);
        y = y / 120 * R;
        x = x / 120 * R;
        x = Math.floor(x*100)/100;
        y = Math.floor(y*100)/100;

        let dot = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        dot.setAttribute("r", "3");
        dot.setAttribute("cx", Math.floor(x_dot));
        dot.setAttribute("cy", Math.floor(y_dot));
        document.getElementById("svg-plot").appendChild(dot);
        if (!check(x, y, R)) {
            dot.setAttribute("stroke", "#AD2D2D");
            dot.setAttribute("fill", "#AD2D2D");
        } else {
            dot.setAttribute("stroke", "green");
            dot.setAttribute("fill", "green");
        }
        dot.setAttribute("cr", R);
        result = x + " " + y + " " + R;
        console.log(result);
        sendDot([{name: 'x', value: x}, {name: 'y', value: y}, {name: 'r', value: R}]);

    }
}

function changeDotPos(newR) {
    if (isNumber(document.getElementById("r").value)) {
        let dotsList = document.querySelectorAll("circle");
        console.log(dotsList);
        dotsList.forEach(function (dot) {
            let x_dot = parseInt(dot.getAttribute("cx"));
            let y_dot = dot.getAttribute("cy");
            console.log(x_dot + " " + y_dot);
            let R = dot.getAttribute("cr");
            console.log(R);
            let y = (150 - y_dot);
            let x = (-150 + x_dot);
            y = y * R;
            x = x * R;
            R = newR;
            y = y / R;
            x = x / R;
            x = Math.floor(x*100)/100;
            y = Math.floor(y*100)/100;
            x_dot = x + 150;
            y_dot = 150 - y;
            if (!check((-150 + x_dot) / 120 * R, (150 - y_dot) / 120 * R, R)) {
                dot.setAttribute("stroke", "#AD2D2D");
                dot.setAttribute("fill", "#AD2D2D");
            } else {
                dot.setAttribute("stroke", "green");
                dot.setAttribute("fill", "green");
            }
            dot.setAttribute("cx", Math.floor(x_dot));
            dot.setAttribute("cy", Math.floor(y_dot));
            dot.setAttribute("cr", R);

        })
    }
}

function check(x, y, r) {
    return checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r);

}

function checkRectangle(x, y, r) {
    return (x <= r * 0.5) && (x >= 0) && (y >= -r) && (y <= 0);
}

function checkCircle(x, y, r) {
    return (x * x + y * y <= r * r) && (x <= 0) && (y >= 0);
}

function checkTriangle(x, y, r) {
    return (-x - r <= y) && (x <= 0) && (y <= 0);
}