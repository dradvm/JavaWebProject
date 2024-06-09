document.addEventListener('DOMContentLoaded', function () {
    var ctx = document.getElementById('orderStatusChart').getContext('2d');
    var finishedOrders = document.getElementById('finishedOrders').innerText;
    var paidOrders = document.getElementById('paidOrders').innerText;
    var waitingOrders = document.getElementById('waitingOrders').innerText;

    var orderStatusChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Finished Orders', 'Paid Orders', 'Waiting Orders'],
            datasets: [{
                label: '# of Orders',
                data: [finishedOrders, paidOrders, waitingOrders],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)'
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
                tooltip: {
                    enabled: true
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
});
