getDistrictList('customer');

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
            regex = /^(?:[0-9] ?){7,10}$/;
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
                        if (response.general === 'fail') {
                            $('#customer-error').text('Invalid page, please refresh');
                            return;
                        }
                        if (response.email === 'fail') {
                            $('#customer-email-error').text('Invalid email');
                        }
                        if (response.password === 'fail') {
                            $('#customer-password-error').text('Password must have at least 8 characters and not contain only spaces');
                        }
                        if (response.name === 'fail') {
                            $('#customer-name-error').text('Please enter your name');
                        }
                        if (response.phone === 'fail') {
                            $('#customer-phone-error').text('Invalid phone number');
                        }
                        if (response.address === 'fail') {
                            $('#customer-address-error').text('Please enter your address');
                        }
                        if (response.general === 'used') {
                            $('#customer-email-error').text('This email has been used');
                        }
                    }
                    else if (response.status === "OK") {
                        location.href = '/auth/toEmailverification';
                    }
                }
            });
        }
    });
}