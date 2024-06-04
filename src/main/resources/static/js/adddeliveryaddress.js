function addDeliveryAddress() {
    $(document).ready(function () {
       $('#error').text('');
       $('#address-error').text('');
       inputAddress = $('#address').val();
       inputDistrictID = $('#district-id').val();
       if (inputAddress.trim().length === 0) {
           $('#address-error').text('Please enter address');
           return;
       }
       $.ajax({
            url: '/customer/addDeliveryaddress',
            type: 'POST',
            data: {
                address: inputAddress,
                districtID: inputDistrictID
            },
            success: function (response) {
                if (response.status === 'Fail') {
                    $('#error').text('Invalid page, please refresh');
                }
                else if (response.status === 'OK') {
                    location.href = response.target;
                }
            }
       });
    });
}