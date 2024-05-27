selectRank();

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

function submit(editEmail) {
    $(document).ready(function () {
        $('#error').text('');
        $('#password-error').text('');
        $('#rank-end-date-error').text('');
        $('#name-error').text('');
        $('#phone-error').text('');
        $('#address-error').text('');
        inputProfileImg = $('#profile-img').files.length > 0 ? $('#profile-img').files[0] : null;
        inputPassword = $('#password').val();
        inputRankID = $('#rank-id').val();
        inputRankStartDate = $('#rank-start-date').val();
        inputRankEndDate = $('#rank-end-date').val();
        inputName = $('#name').val();
        inputPhone = $('#phone').val();
        inputGender = $('#gender').val();
        inpputAddress = $('#address').val();
        inputDistrictID = $('#district-id').val();
        inputBirthday = $('#birthday').val();
        inputActive = $('#active').val();
        valid = true;
        if (inputPassword.trim().length > 0 && inputPassword.trim().length < 8) {
            valid = false;
            $('#password-error').text("Password must have at least 8 characters, leave this field empty if you don't want to change it");
        }
        startDate = new Date(inputRankStartDate);
        endDate = new Date(inputRankEndDate);
        if (endDate < startDate) {
            valid = false;
            $('#rank-end-date-error').text('Rank ending date must be after rank starting date');
        }
        if (name.trim().length === 0) {
            valid = false;
            $('#name-error').text('Please enter name');
        }
        regex = /^(?:[0-9] ?){7,11}$/;
        if (!regex.test(inputPhone)) {
            valid = false;
            $('#phone-error').text('Please enter phone number');
        }
        if (inpputAddress.trim().length === 0) {
            valid = false;
            $('#address-error').text('Please enter address');
        }
        if (!valid) {
            return;
        }
        $.ajax({
            url: 'admin/editinformationCaterer',
            type: 'POST',
            data: {
                email: editEmail,
                profileImage: inputProfileImg,
                password: inputPassword,
                rankID: inputRankID,
                rankStartDate: inputRankStartDate,
                rankEndDate: inputRankEndDate,
                name: inputName,
                phone: inputPhone,
                gender: inputGender,
                address: inpputAddress,
                districtID: inputDistrictID,
                birthday: inputBirthday,
                active: inputActive
            },
            success: function (response) {
                if (response.status === 'OK') {
                    location.href = response.target;
                }
                else if (response.status === 'Invalid') {
                    $('#error').text('Invalid page, please refresh');
                }
            }
        });
    });
}