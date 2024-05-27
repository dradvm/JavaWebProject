$(document).ready(function () {
    selectRank();
});

function selectRank() {
    $(document).ready(function () {
        inputRankID = $('#rank-id').val();
        $('.rank-fee').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                    display: 'block'
                });
            } 
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
        $('.rank-cpo').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                    display: 'block'
                });
            } 
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
        $('.rank-max-dish').each(function () {
            if ($(this).attr('rank') === inputRankID) {
                $(this).css({
                    display: 'block'
                });
            } 
            else {
                $(this).css({
                    display: 'none'
                });
            }
        });
    });
}

function editCatererInformation() {
    $(document).ready(function () {
        $('#error').text('');
        $('#profile-img-error').text('');
        $('#password-error').text('');
        $('#name-error').text('');
        $('#phone-error').text('');
        $('#address-error').text('');
        inputProfileImg = $('#profile-img').prop('files')[0] ? $('#profile-img').prop('files')[0] : null;
        inputPassword = $('#password').val();
        inputRankID = $('#rank-id').val();
        inputName = $('#name').val();
        inputPhone = $('#phone').val();
        inputAddress = $('#address').val();
        valid = true;
        if (inputProfileImg !== null && inputProfileImg.size > 10000000) {
            valid = false;
            $('#profile-img-error').text('We only support images smaller than 10MB');
        }
        if (inputPassword.trim().length > 0 && inputPassword.trim().length < 8) {
            valid = false;
            $('#password-error').text("Password must have at least 8 characters, leave this field empty if you don't want to change it");
        }
        if (inputName.trim().length === 0) {
            valid = false;
            $('#name-error').text('Please enter name');
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
        formData = new FormData($('#caterer-info')[0]);
        $.ajax({
            url: '/admin/editinformationCaterer',
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