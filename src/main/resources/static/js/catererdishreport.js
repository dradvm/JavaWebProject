
var ctx = document.getElementById('myChart');
var barChart
var data
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: '/caterer/myCaterer/dishes/barChartDish',
        success: function (response) {
            initBarChart(response, $("#byType").val(), $("#bySort").val())
            data = response
        }
    });
    $('#byType').change(function () {
        var selectedValue = $(this).val();
        updateBarChart(data, selectedValue, $("#bySort").val())
    });

    $('#bySort').change(function () {
        var selectedValue = $(this).val();
        updateBarChart(data, $("#byType").val(), selectedValue)
    });

})
function initBarChart(data, type, sort) {
    var dataShow = Array.from(data)
    var d = sort === "Ascending" ? 1 : -1
    dataShow.sort(function (a, b) {
        if (type === "Order") {
            if (a.order > b.order) {
                return d
            }
            else if (a.order < b.order) {
                return -d
            }
        }
        else {
            if (a.quantity > b.quantity) {
                return d
            }
            else if (a.quantity < b.quantity) {
                return -d
            }
        }
        if (a.name > b.name) {
            return d
        }
        else if (a.name < b.name) {
            return -d
        }
        return 0
    })
    barChart = new Chart(ctx, {
        type: 'bar',
        data: {
            axis: "y",
            labels: dataShow.slice(0, 10).map((item) => item.name),
            datasets: [
                {
                    label: "Quantity",
                    data: dataShow.slice(0, 10).map((item) => item.quantity)
                },
                {
                    label: "Order",
                    data: dataShow.slice(0, 10).map((item) => item.order)
                },
            ]
        },
        options: {
            indexAxis: 'y',
            scales: {
                y: {
                    beginAtZero: true
                },

            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
            },
            aspectRatio: 1 | 2
        }
    });
}
function updateBarChart(data, type, sort) {
    var dataShow = Array.from(data)
    var d = sort === "Ascending" ? 1 : -1
    dataShow.sort(function (a, b) {
        if (type === "Order") {
            if (a.order > b.order) {
                return d
            }
            else if (a.order < b.order) {
                return -d
            }
        }
        else {
            if (a.quantity > b.quantity) {
                return d
            }
            else if (a.quantity < b.quantity) {
                return -d
            }
        }
        if (a.name > b.name) {
            return d
        }
        else if (a.name < b.name) {
            return -d
        }
        return 0
    })
    barChart.data.labels = dataShow.slice(0, 10).map((item) => item.name)
    barChart.data.datasets[0].data = dataShow.slice(0, 10).map((item) => item.quantity)
    barChart.data.datasets[1].data = dataShow.slice(0, 10).map((item) => item.order)
    barChart.update()
}