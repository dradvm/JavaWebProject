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