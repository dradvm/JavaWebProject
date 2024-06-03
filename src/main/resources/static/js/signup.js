changeForm();

function changeForm() {
    $(document).ready(function() {
        role = $('#role').val();
        if (role === 'customer') {
            $('#customer-city').val(1);
            getDistrictList(role);
            $('#caterer-form').css({
                display: 'none'
            });
            $('#customer-form').css({
                display: 'block'
            });
        }
        else if (role === 'caterer') {
            $('#caterer-city').val(1);
            getDistrictList(role);
            $('#customer-form').css({
                display: 'none'
            });
            $('#caterer-form').css({
                display: 'block'
            });
        }
    });
}

function getDistrictList(role) {
    $(document).ready(function () {
        if (role === 'customer') {
            first = true;
            cityID = $('#customer-city').val();
            districtList = $('.customer-district');
            districtList.each(function (index) {
                if ($(this).attr('city') === cityID) {
                    $(this).css({
                        display: 'block'
                    });
                    if (first) {
                        $('#customer-district').val($(this).attr('value'));
                        first = false;
                    }
                } 
                else {
                    $(this).css({
                        display: 'none'
                    });
                }
            });
        }
        else if (role === 'caterer') {
            first = true;
            cityID = $('#caterer-city').val();
            districtList = $('.caterer-district');
            districtList.each(function (index) {
                if ($(this).attr('city') === cityID) {
                    $(this).css({
                        display: 'block'
                    });
                    if (first) {
                        $('#caterer-district').val($(this).attr('value'));
                        first = false;
                    }
                } 
                else {
                    $(this).css({
                        display: 'none'
                    });
                }
            });
        }
    });
}

function signup(role) {
    $(document).ready(function () {
        if (role === 'customer') {
            $('#customer-error').text('');
            $('#customer-email-error').text('');
            $('#customer-password-error').text('');
            $('#customer-name-error').text('');
            $('#customer-phone-error').text('');
            $('#customer-address-error').text('');
            inputEmail = $('#customer-email').val();
            inputPassword = $('#customer-password').val();
            inputName = $('#customer-name').val();
            inputPhone = $('#customer-phone').val();
            inputGender = $('#customer-gender').val();
            inputBirthday = $('#customer-birthday').val();
            inputAddress = $('#customer-address').val();
            inputDistrict = $('#customer-district').val();
            valid = true;
            regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (!regex.test(inputEmail)) {
                valid = false;
                $('#customer-email-error').text('Invalid email');
            }
            if (inputPassword.trim().length < 8) {
                valid = false;
                $('#customer-password-error').text('Password must have at least 8 characters and not contain only spaces');
            }
            if (inputName.trim().length === 0) {
                valid = false;
                $('#customer-name-error').text('Please enter your name');
            }
            regex = /^(?:[0-9] ?){7,11}$/;
            if (!regex.test(inputPhone)) {
                valid = false;
                $('#customer-phone-error').text('Invalid phone number');
            }
            if (inputAddress.trim().length === 0) {
                valid = false;
                $('#customer-address-error').text('Please enter your address');
            }
            if (!valid) {
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/auth/signupCustomer',
                data: {
                    email: inputEmail,
                    password: inputPassword,
                    name: inputName,
                    phone: inputPhone,
                    gender: inputGender,
                    birthday: inputBirthday,
                    address: inputAddress,
                    district: inputDistrict
                },
                success: function (response) {
                    if (response.status === 'Fail') {
                        $('#customer-error').text('Invalid page, please refresh');
                    }
                    else if (response.status === 'Used') {
                        $('#customer-email-error').text('This email has been used');
                    }
                    else if (response.status === "OK") {
                        location.href = response.target;
                    }
                }
            });
        }
        else if (role === 'caterer') {
            $('#caterer-error').text('');
            $('#caterer-email-error').text('');
            $('#caterer-password-error').text('');
            $('#caterer-name-error').text('');
            $('#caterer-phone-error').text('');
            $('#caterer-address-error').text('');
            $('#caterer-payment-information-error').text('');
            inputEmail = $('#caterer-email').val();
            inputPassword = $('#caterer-password').val();
            inputName = $('#caterer-name').val();
            inputPhone = $('#caterer-phone').val();
            inputGender = $('#caterer-gender').val();
            inputBirthday = $('#caterer-birthday').val();
            inputAddress = $('#caterer-address').val();
            inputDistrict = $('#caterer-district').val();
            inputPaymentInformation = $('#caterer-payment-information').val();
            inputDescription = $('#caterer-description').val();
            valid = true;
            regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            if (!regex.test(inputEmail)) {
                valid = false;
                $('#caterer-email-error').text('Invalid email');
            }
            if (inputPassword.trim().length < 8) {
                valid = false;
                $('#caterer-password-error').text('Password must have at least 8 characters and not contain only spaces');
            }
            if (inputName.trim().length === 0) {
                valid = false;
                $('#caterer-name-error').text('Please enter your name');
            }
            regex = /^(?:[0-9] ?){7,11}$/;
            if (!regex.test(inputPhone)) {
                valid = false;
                $('#caterer-phone-error').text('Invalid phone number');
            }
            if (inputAddress.trim().length === 0) {
                valid = false;
                $('#caterer-address-error').text('Please enter your address');
            }
            regex = /^[A-Za-z0-9]+ ([0-9]+ ?){1,}$/;
            if (!regex.test(inputPaymentInformation)) {
                valid = false;
                $('#caterer-payment-information-error').text('Please enter valid payment information matching [Bank name] [Account number]');
            }
            if (!valid) {
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/auth/signupCaterer',
                data: {
                    email: inputEmail,
                    password: inputPassword,
                    name: inputName,
                    phone: inputPhone,
                    gender: inputGender,
                    birthday: inputBirthday,
                    address: inputAddress,
                    district: inputDistrict,
                    paymentInformation: inputPaymentInformation,
                    description: inputDescription
                },
                success: function (response) {
                    if (response.status === 'Fail') {
                        $('#caterer-error').text('Invalid page, please refresh');
                    }
                    else if (response.status === 'Used') {
                        $('#caterer-email-error').text('This email has been used');
                    }
                    else if (response.status === "OK") {
                        location.href = response.target;
                    }
                }
            });
        }
    });
}