
function toRad(deg) {
    return deg * (Math.PI / 180.0);
}
function randomRange(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}
function easeOutSine(x) {
    return Math.sin((x * Math.PI) / 2);
}
// get percent between 2 number
function getPercent(input, min, max) {
    return (((input - min) * 100) / (max - min)) / 100
}
const canvas = document.getElementById("canvas")
const ctx = canvas.getContext("2d")
const width = document.getElementById("canvas").width
const height = document.getElementById("canvas").height

const centerX = width / 2
const centerY = height / 2
const radius = width / 2

// let items = document.getElementsByTagName("textarea")[0].split("\n");
let items = []

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/minigame/getDataOfWheel',
        success: function (response) {
            items = response
            console.log(response)
            draw()
        }
    });
})
let currentDeg = 0
let step = 360 / items.length
let itemDegs = {}
let colors = [
    { r: 133, g: 255, b: 200 },
    { r: 0, g: 214, b: 117 }
]

function createWheel() {
    // items = document.getElementsByTagName("textarea")[0].split("\n");
    step = 360 / items.length
    draw()
}

function draw() {
    let step = 360 / items.length
    ctx.beginPath();
    ctx.arc(centerX, centerY, radius, toRad(0), toRad(360))
    ctx.fillStyle = `rgb(${255},${255},${255})`
    ctx.lineTo(centerX, centerY);
    ctx.fill()

    let startDeg = currentDeg;
    for (let i = 0; i < items.length; i++, startDeg += step) {
        let endDeg = startDeg + step

        color = colors[i % 2]
        let colorStyle = `rgb(${color.r},${color.g},${color.b})`

        ctx.beginPath();
        rad = toRad(360 / step);
        ctx.arc(centerX, centerY, radius - 2, toRad(startDeg), toRad(endDeg))
        let colorStyle2 = `rgb(${color.r - 30},${color.g - 30},${color.b - 30})`
        ctx.fillStyle = colorStyle2
        ctx.lineTo(centerX, centerY);
        ctx.fill()

        // ctx.beginPath();
        // rad = toRad(360 / step);
        // ctx.arc(centerX, centerY, radius - 30, toRad(startDeg), toRad(endDeg))
        // ctx.fillStyle = colorStyle
        // ctx.lineTo(centerX, centerY);
        // ctx.fill()

        // draw text
        ctx.save();
        ctx.translate(centerX, centerY);
        ctx.rotate(toRad((startDeg + endDeg) / 2));
        ctx.textAlign = "center";

        ctx.fillStyle = "#fff";
        ctx.font = 'bold 16px Segoe UI Semibold';
        ctx.fillText(items[i], 120, 10);
        ctx.restore();

        itemDegs[items[i]] =
        {
            "startDeg": startDeg,
            "endDeg": endDeg
        }

    }
}


let speed = 0
let maxRotation = 0
let pause = false
let winItem = 0
function animate() {
    if (pause) {
        return
    }
    // if (!startTime) startTime = timestamp;
    // const elapsed = (timestamp - startTime) / 1000; // time in seconds

    // speed = easeOutSine(elapsed / 5) * 20
    // if (elapsed >= 5) {
    //     speed = 0
    //     pause = true
    // }
    speed = easeOutSine(getPercent(currentDeg, maxRotation, 0)) * 20
    if (speed < 0.01) {
        speed = 0
        pause = true
        $(document).ready(function () {
            $.ajax({
                type: 'POST',
                url: '/minigame/update',
                data: { value: winItem },
                success: function (response) {
                    console.log(response)
                    winItem = 0
                }
            });
        })
    }
    currentDeg += speed
    draw()
    window.requestAnimationFrame(animate);
}

function spin() {
    if (speed != 0) {
        return
    }

    maxRotation = 0;
    currentDeg = 0
    createWheel()
    draw();
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: '/minigame/spin',
            success: function (response) {
                console.log(response)
                winItem = response
                maxRotation = (360 * 10) - 90 - itemDegs[winItem].endDeg + randomRange(1, itemDegs[winItem].endDeg - itemDegs[winItem].startDeg - 1)
                itemDegs = {}
                // startTime = null
                pause = false
                window.requestAnimationFrame(animate);
            }
        });
    })

}