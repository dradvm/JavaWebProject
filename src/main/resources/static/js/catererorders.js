
$(document).ready(function () {
    $('#lineChartAdmin').change(function () {
        var selectedValue = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/caterer/myCaterer/orders/getDataLineChart',
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

});
const ctx = document.getElementById('myChart');
var lineChart
function initLineChart(labels, data) {
    lineChart = new Chart(ctx, {
        data: {
            labels: labels,
            datasets: [{
                type: 'bar',
                label: 'New order',
                data: data.order,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                yAxisID: 'y1'
            }, {
                type: 'line',
                label: 'Revenue',
                data: data.revenue,
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 1,
                yAxisID: 'y'
            }]
        },
        options: {
            scales: {
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                    beginAtZero: true
                },
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'right',
                    beginAtZero: true,
                    // grid line settings
                    grid: {
                        drawOnChartArea: false, // only want the grid lines for one axis to show up
                    },
                },
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
    lineChart.data.datasets[0].data = data.order
    lineChart.data.datasets[1].data = data.revenue
    lineChart.update()
}




$(document).ready(function () {
    var table = $('#orderList').DataTable({
        pageLength: 10,
        lengthMenu: [5, 10],
        "scrollX": true, // Thêm thanh cuộn ngang
        "autoWidth": false,
        columns: [
            { width: "10%" },
            { width: "10%" },
            { width: "15%" },
            { width: "10%" },
            { width: "10%" },
            { width: "10%" },
            { width: "15%" },
            { width: "20%" },
        ]
    });
});


function closeModal() {
    document.querySelector("#Modal").classList.add("d-none")
}
function openModal(id) {
    document.querySelector("#ModalBody").classList.add("d-none")
    document.querySelector("#Spinner").classList.remove("d-none")
    document.querySelector("#Modal").classList.remove("d-none")
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: '/orders/getOrderDetails',
            data: { id: id },
            success: function (response) {
                console.log(response)
                html = ""
                data = response.orderDetails
                data.forEach((item) => {
                    html += `
                    <tr>
                        <td class="w-25"><img src="${item.dishImage}" alt=""
                                class="w-100"></td>
                        <td class="w-25">${item.dishName}</td>
                        <td class="w-25">${item.quantity}</td>
                        <td class="w-25">${item.dishPrice}</td>
                    </tr>
                    `
                    console.log(item)
                })
                $("#address").text(response.address)
                $("#point").text(response.point)
                $("#voucher").text(response.voucher)

                $("#orderDetails").html(html)

                document.querySelector("#ModalBody").classList.remove("d-none")
                document.querySelector("#Spinner").classList.add("d-none")
            }
        });
    })

}
function confirmSubmit(event) {
    event.preventDefault();
    let reason = prompt('Please enter the reason for canceling this order (max 200 characters):');
    if (reason !== null) {
        if (reason.trim().length == 0) {
            alert('Reason cannot empty');
            return false;
        }
        if (reason.trim().length > 200) {
            alert('Reason cannot exceed 200 characters');
            return false;
        }
        const reasonInput = event.target.querySelector('#reason');
        reasonInput.value = reason.trim();
        event.target.submit()
        return true;
    }
    return false
}
function confirmSubmitReport(event) {
    event.preventDefault();
    let reason = prompt('Please enter the reason for your report (max 200 characters):');
    if (reason !== null) {
        if (reason.trim().length == 0) {
            alert('Reason cannot empty');
            return false;
        }
        if (reason.trim().length > 200) {
            alert('Reason cannot exceed 200 characters');
            return false;
        }
        const reasonInput = event.target.querySelector('#reasonReport');
        reasonInput.value = reason.trim();
        event.target.submit()
        return true;
    }
    return false
}

