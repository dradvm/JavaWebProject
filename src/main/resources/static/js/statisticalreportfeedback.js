$(document).ready(function () {
    $('#lineChartAdmin').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/lineChartFeedback',
            data: { selectedValue: selectedValue },
            success: function (response) {
                console.log(response)
                if (!lineChart) {
                    initLineChart(response.labels, response.data)
                }
                else {
                    updateLineChart(response.labels, response.data)
                }
            }
        });
    });

    $("#lineChartAdmin").trigger("change")

    $.ajax({
        type: 'GET',
        url: '/admin/pieChartFeedback',
        success: function (response) {
            initPieChart(response.labels, response.data);
        }
    });

    $('#barChartAdmin').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/barChart',
            data: { selectedValue: selectedValue },
            success: function (response) {
                console.log(response)
                if (!barChart) {
                    initBarChart(response.labels, response.datasets)
                }
                else {
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
    pieChart = new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: [
                    'rgba(75, 192, 192, 0.5)', // Màu cho phản hồi tích cực
                    'rgba(255, 99, 132, 0.5)', // Màu cho phản hồi tiêu cực
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 99, 132, 1)',
                ],
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
                    text: "Feedback Comparison"
                }
            },
            aspectRatio: 0.35, // Set the aspect ratio to 1:1 (square)
            maintainAspectRatio: false // Disable automatic aspect ratio calculation
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
