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
                if (response === 'OK') {
                    console.log('OK');
                }
                if (response === 'Fail') {
                    $('#error').text('Invalid page, please refresh');
                }
            }
        });
    });
}