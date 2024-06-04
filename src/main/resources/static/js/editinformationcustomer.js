function editCustomerInformation() {
    $(document).ready(function () {
        $('#error').text('');
        $('#profile-img-error').text('');
        $('#name-error').text('');
        $('#point-error').text('');
        $('#roll-chance-error').text('');
        $('#phone-error').text('');
        $('#address-error').text('');
        inputProfileImg = $('#profile-img').prop('files')[0] ? $('#profile-img').prop('files')[0] : null;
        inputName = $('#name').val();
        inputPoint = $('#point').val();
        inputRollChance = $('#roll-chance').val();
        inputPhone = $('#phone').val();
        inputAddress = $('#address').val();
        valid = true;
        if (inputProfileImg !== null && inputProfileImg.size > 10000000) {
            valid = false;
            $('#profile-img-error').text('We only support images smaller than 10MB');
        }
        if (inputName.trim().length === 0) {
            valid = false;
            $('#name-error').text('Please enter name');
        }
        if (inputPoint < 0) {
            valid = false;
            $('#point-error').text('Point cannot be less than 0');
        }
        if (inputRollChance < 0) {
            valid = false;
            $('#roll-chance-error').text('Roll chance cannot be less than 0');
        }
        regex = /^(?:[0-9] ?){7,11}$/;
        if (!regex.test(inputPhone)) {
            valid = false;
            $('#phone-error').text('Please enter phone number');
        }
        if (inputAddress.trim().length === 0) {
            valid = false;
            $('#address-error').text('Please enter address');
        }
        if (!valid) {
            return;
        }
        formData = new FormData($('#customer-info')[0]);
        $.ajax({
            url: '/admin/editinformationCustomer',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.status === 'OK') {
                    location.href = response.target;
                }
                else if (response.status === 'Invalid') {
                    $('#error').text('Invalid page, please refresh');
                }
                else if (response.status === 'Fail') {
                    $('#error').text('This request cannot be completed, please try again later');
                }
            }
        });
    });
}