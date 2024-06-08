
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
function confirmSubmit(event, info) {
    event.preventDefault(); // Ngăn chặn form gửi ngay lập tức

    // Hiển thị popup xác nhận
    if (confirm(`Payment Information: ${info} (Press OK if you have paid)`)) {
        // Nếu người dùng nhấn OK, submit form
        event.target.submit();
    } else {
        // Nếu người dùng nhấn Cancel, không làm gì
        return false;
    }
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

function confirmDelete() {
    return confirm('Are you sure you want to cancel this order?');
}
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