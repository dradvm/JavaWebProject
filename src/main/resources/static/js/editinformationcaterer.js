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
        $('#name-error').text('');
        $('#phone-error').text('');
        $('#address-error').text('');
        $('#point-error').text('');
        $('#rank-end-date-error').text('');
        inputProfileImg = $('#profile-img').prop('files')[0] ? $('#profile-img').prop('files')[0] : null;
        inputRankID = $('#rank-id').val();
        inputName = $('#name').val();
        inputPhone = $('#phone').val();
        inputAddress = $('#address').val();
        inputPoint = $('#point').val();
        endDate = new Date($('#rank-end-date').val());
        startDate = new Date($('#rank-start-date').text());
        valid = true;
        if (inputProfileImg !== null && inputProfileImg.size > 10000000) {
            valid = false;
            $('#profile-img-error').text('We only support images smaller than 10MB');
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
        if (inputPoint === '' || inputPoint < 0) {
            valid = false;
            $('#point-error').text('Point cannot be less than 0');
        }
        if (endDate <= startDate) {
            valid = false;
            $('#rank-end-date-error').text('Rank ending date must be after rank starting date');
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