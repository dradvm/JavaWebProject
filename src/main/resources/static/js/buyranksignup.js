option = 1;

choose(1);

function choose(id) {
    option = id;
    $(document).ready(function () {
        $('.rank').each(function (index) {
            if (index === option - 1) {
                $(this).css({
                    border: 'thin solid red'
                });
            } 
            else {
                $(this).css({
                    border: 'thin solid black'
                });
            }
        });
    });
}

function buy() {
    $(document).ready(function () {
        $('#error').text('');
        $.ajax({
            type: 'POST',
            url: '/rank/verifyBuyOptionSignup',
            data: {
                id: option
            },
            success: function (response) {
                if (response === 'Fail') {
                    $('#error').text('We cannot handle your request at the moment, please try again later');
                }
                else {
                    location.href = response;
                }
            }
        });
    });
}