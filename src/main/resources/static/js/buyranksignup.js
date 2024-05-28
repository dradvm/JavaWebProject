option = 0;

$(document).ready(function () {
    choose(parseInt($('.rank').first().attr('rank')));
});

function choose(id) {
    option = id;
    $(document).ready(function () {
        $('.rank').each(function () {
            if (parseInt($(this).attr('rank')) === id) {
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