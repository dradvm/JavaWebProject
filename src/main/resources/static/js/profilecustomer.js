function editProfile() {
    $(document).ready(function () {
       $('#error').text('');
       $('#profile-img-error').text('');
       $('#name-error').text('');
       $('#phone-error').text('');
       $('#address-error').text('');
       inputProfileImg = $('#profile-img').prop('files')[0] ? $('#profile-img').prop('files')[0] : null;
       inputName = $('#name').val();
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
       formData = new FormData($('#profile')[0]);
       $.ajax({
            url: "/customer/editProfile",
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                if (response.status === 'Invalid') {
                    $('#error').text('Invalid page, please refresh');
                }
                else if (response.status === 'Fail') {
                    $('#error').text('The request cannot be completed, try again later');
                }
                if (response.status === 'OK') {
                    location.href = response.target;
                }
            }
       });
    });
}