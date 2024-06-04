function editDeliveryAddress() {
    $(document).ready(function () {
       $('#error').text('');
       $('#address-error').text('');
       inputID = $('#id').val();
       inputAddress = $('#address').val();
       inputDistrictID = $('#district-id').val();
       if (inputAddress.trim().length === 0) {
           $('#address-error').text('Please enter address');
           return;
       }
       $.ajax({
            url: '/customer/editDeliveryaddress',
            type: 'POST',
            data: {
                id: inputID,
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