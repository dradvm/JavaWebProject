$(document).ready(function () {
    $('#lineChartAdmin').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/lineChartFeedback',
            data: {selectedValue: selectedValue},
            success: function (response) {
                console.log(response)
                if (!lineChart) {
                    initLineChart(response.labels, response.data)
                } else {
                    updateLineChart(response.labels, response.data)
                }
            }
        });
    });

    $("#lineChartAdmin").trigger("change")

    $.ajax({
        type: 'GET',
        url: '/admin/pieChartRank',
        success: function (response) {
            initPieChart(Object.keys(response), Object.values(response));
        }
    });


    $('#barChartAdmin').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/barChart',
            data: {selectedValue: selectedValue},
            success: function (response) {
                console.log(response)
                if (!barChart) {
                    initBarChart(response.labels, response.datasets)
                } else {
                    updateBarChart(response.labels, response.datasets)
                }
            }
        });
    });
    $("#barChartAdmin").trigger("change")
});
const ctx = document.getElementById('myChart');
var lineChart
function initLineChart(labels, data) {
    lineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                    data: data,
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            plugins: {

                legend: {
                    display: false
                },
            },
            responsive: true
        }
    });
}
function updateLineChart(labels, data) {
    lineChart.data.labels = labels
    lineChart.data.datasets[0].data = data
    lineChart.update()
}

const ctx2 = document.getElementById('myChart2');
var pieChart;

function initPieChart(labels, data) {
    var backgroundColors = [];
    var borderColors = [];

    // Số lượng rank có thể được cập nhật tùy thuộc vào dữ liệu trả về từ controller
    var numRanks = data.length;

    // Tạo mảng màu cho các rank
    for (var i = 0; i < numRanks; i++) {
        var color = getRandomColor(); // Hàm getRandomColor() có thể được tạo ra để tạo màu ngẫu nhiên
        backgroundColors.push(color.background);
        borderColors.push(color.border);
    }

    pieChart = new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                    data: data,
                    backgroundColor: backgroundColors,
                    borderColor: borderColors,
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: "Caterer Rank Comparison"
                }
            }
        }
    });
}


const ctx3 = document.getElementById('myChart3');
var barChart
function initBarChart(labels, datasets) {
    barChart = new Chart(ctx3, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: datasets
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
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
function updateBarChart(labels, datasets) {
    barChart.data.labels = labels
    barChart.data.datasets = datasets
    barChart.update()
}
