
function toggleModalOrder() {
    if (document.querySelector("#modalOrder").classList.contains("d-none")) {
        document.querySelector("#modalOrder").classList.remove("d-none")
    }
    else {
        document.querySelector("#modalOrder").classList.add("d-none")
    }
}
function updateValue(button, operation) {
    // Get the input element
    const input = button.parentElement.querySelector('input[name="quantity"]');
    // Parse the current value of the input
    let value = parseInt(input.value);
    // Update the value based on the operation
    if (operation === 'minus' && value > parseInt(input.min)) {
        value -= 1;
    } else if (operation === 'add' && value < parseInt(input.max)) {
        value += 1;
    }
    // Set the new value back to the input
    input.value = value;
}
$(document).ready(function () {
    displayOrderedDishes();
    $('#districtID').prop('selectedIndex', 0);
});

function changeAddress() {
    if ($('#address-type').val() === 0) {
        $('#address-type-0').css({
            display: 'block'
        });
        $('#address-type-1').css({
            display: 'none'
        });
    }
    else {
        $('#address-type-0').css({
            display: 'none'
        });
        $('#address-type-1').css({
            display: 'block'
        });
    }
}

function addDish(event, form) {
    event.preventDefault();
    $(document).ready(function () {
        data = new FormData(form);
        id = data.get('id');
        quantity = 0;
        if (data.get('quantity') !== '') {
            quantity = data.get('quantity');
        }
        form.reset();
        $.ajax({
            url: '/addDish',
            type: 'POST',
            data: {
                id: id,
                quantity: quantity
            },
            success: function () {
                displayOrderedDishes();
            }
        });
    });
}

function writeOrderedDishTag(dish) {
    id = dish.id;
    name = dish.name;
    img = dish.img;
    price = dish.price;
    quantity = dish.quantity;
    result = `<div>
                    <img src="` + img + `"/>
                    <div>` + name + `</div>
                    <div>` + (price * quantity) + `</div>
                    <form onsubmit="addDish(event, this)">
                        <input type="hidden" name="id" value="` + id + `">
                        <input type="hidden" name="quantity" value="-1">
                        <button type="submit">-</button>
                    </form>
                    <div>` + quantity + `</div>
                    <form onsubmit="addDish(event, this)">
                        <input type="hidden" name="id" value="` + id + `">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit">+</button>
                    </form>
                </div>`;
    result = `<div class="mb-2 border rounded px-3 py-2" ng-repeat="(index, item) in order">
                    <div class="d-flex align-items-center justify-content-between">
                        <div style="width: 20%;" class="rounded overflow-hidden">
                            <img src="${img}" class="w-100"/>
                        </div>
                        <div style="width: 60%;">
                            <div class="d-flex align-items-center justify-content-between">
                                <div class="fw-medium">${name}</div>
                                <div class="fw-medium">$${price}</div>
                            </div>
                            <div class="mt-2 d-flex align-items-center justify-content-between">
                                <div class="d-flex border rounded overflow-hidden">
                                    <form onsubmit="addDish(event, this)">
                                        <input type="hidden" name="id" value="` + id + `">
                                        <input type="hidden" name="quantity" value="-1">
                                        <button  class="my-btn bg-main text-white fw-bold px-3 py-1" type="submit">-</button>
                                    </form>
                                    <input class="border-0 text-center" style="outline: 0; width: 30px;"
                                        type="number" name="product-qty" min="0" max="999" readonly
                                        value="${quantity}">
                                    <form onsubmit="addDish(event, this)">
                                        <input type="hidden" name="id" value="` + id + `">
                                        <input type="hidden" name="quantity" value="1">
                                        <button  class="my-btn bg-main text-white fw-bold px-3 py-1" type="submit">+</button>
                                    </form>
                                </div>
                                <div class="fw-medium fs-5">
                                    ${(price * quantity)}
                                </div>
                            </div>
                        </div>
                        <form onsubmit="addDish(event, this)" style="width: 10%; cursor: pointer;"
                            class="bg-danger d-flex align-items-center justify-content-around p-2 rounded"
                            >
                            <input type="hidden" name="id" value="` + id + `">
                            <input type="hidden" name="quantity" value="${-quantity}">
                            <button type="submit"><i class="fa-solid fa-trash text-white"></i></button>
                        </form>
                    </div>
                </div>`
    return result;
}

function displayOrderedDishes() {
    $(document).ready(function () {
        $.ajax({
            url: '/getOrderListFE',
            type: 'POST',
            success: function (response) {
                newHtml = '';
                count = 0
                for (let index = 0; index < response.length; index++) {
                    if (response[index].quantity > 0) {
                        newHtml += writeOrderedDishTag(response[index]);
                        count++;
                    }
                }
                changeStateModal(count != 0)
                $('#orderDishes').html(newHtml);
                calcTotal();
            }
        });
    });
}

function changeStateModal(check) {

    var list = document.querySelector("#orderHasList")
    var modal = document.querySelector("#orderEmpty")
    if (list == null || modal == null) {
        return
    }
    if (check) {
        if (modal.classList.contains("d-none")) {

        }
        else {
            modal.classList.add("d-none")
        }
        if (list.classList.contains("d-none")) {
            list.classList.remove("d-none")
        }
        else {

        }
    }
    else {
        if (modal.classList.contains("d-none")) {
            modal.classList.remove("d-none")
        }
        if (list.classList.contains("d-none")) {

        }
        else {
            list.classList.add("d-none")
        }
    }
}

function calcTotal() {
    $(document).ready(function () {
        $.ajax({
            url: '/calcTotal',
            type: 'GET',
            data: {
                voucherID: $('#selectedVoucher').val(),
                usePoint: $('#usePoint').val()
            },
            success: function (response) {
                $('#total').text('$' + response);
            }
        });
    });
}

function createOrder() {
    $(document).ready(function () {
        $('#orderTime-error').text('');
        $('#address-0-error').text('');
        $('#error').text('');
        voucherID = $('#selectedVoucher').val();
        usePoint = $('#usePoint').val();
        orderTime = $('#orderTime').val();
        addressType = $('#address-type').val();
        address0 = $('#address-0').val();
        districtID = $('#districtID').val();
        address1 = 0;
        note = $('#note').val();
        if (addressType == 1) {
            address1 = $('#address-1').val();
        }
        valid = true;
        regex = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$/;

        const inputDate = new Date(orderTime);


        const currentDate = new Date();
        if (!regex.test(orderTime) || inputDate < currentDate) {
            valid = false;
            $('#orderTime-error').text('Please enter valid time');
        }
        if (addressType == 0 && address0.trim().length === 0) {
            valid = false;
            $('#address-0-error').text('Please enter address');
        }
        if (!valid) {
            return;
        }
        $.ajax({
            url: '/createOrder',
            type: 'POST',
            data: {
                voucherID: voucherID,
                usePoint: usePoint,
                orderTime: orderTime,
                addressType: addressType,
                address0: address0,
                districtID: districtID,
                address1: address1,
                note: note
            },
            success: function (response) {
                if (response.status === 'Fail') {
                    $('#error').text('The request cannot be completed at the moment, please try again later');
                }
                else if (response.status === 'OK') {
                    setTimeout(function () {
                        $('#error').text('Created successfully');
                        location.href = response.target;
                    }, 3000);
                }
            }
        });
    });
}