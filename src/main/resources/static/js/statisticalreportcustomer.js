$(document).ready(function () {
    $('#lineChartCustomer').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/lineChartCustomer',
            data: { selectedValue: selectedValue },
            success: function (response) {
                if (!lineChart) {
                    initLineChart(response.labels, response.data);
                }
                else {
                    updateLineChart(response.labels, response.data);
                }
            }
        });
    });
    $("#lineChartCustomer").trigger("change");
    $('#barChartCustomer').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/admin/barChartCustomer',
            data: { selectedValue: selectedValue },
            success: function (response) {
                if (!barChart) {
                    initBarChart(response.labels, response.datasets);
                }
                else {
                    updateBarChart(response.labels, response.datasets);
                }
            }
        });
    });
    $("#barChartCustomer").trigger("change");
});
const ctx = document.getElementById('myChart');
var lineChart;
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
                }
            },
            responsive: true
        }
    });
}
function updateLineChart(labels, data) {
    lineChart.data.labels = labels;
    lineChart.data.datasets[0].data = data;
    lineChart.update();
}
const ctx2 = document.getElementById('myChart2');
var barChart;
function initBarChart(labels, datasets) {
    barChart = new Chart(ctx2, {
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
                    position: 'top'
                }
            },
            aspectRatio: 1 | 2
        }
    });
}
function updateBarChart(labels, datasets) {
    barChart.data.labels = labels;
    barChart.data.datasets = datasets;
    barChart.update();
}
