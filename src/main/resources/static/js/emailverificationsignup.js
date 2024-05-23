function verify() {
    $(document).ready(function () {
        inputCode = $('#code').val();
        if (inputCode >= 100000 && inputCode <= 999999) {
            $.ajax({
                type: 'POST',
                url: '/auth/verifyEmailSignup',
                data: {
                    code: inputCode
                },
                success: function (response) {
                    if (response === 'Expired') {
                        $('#errror').text('This code has expired, please refresh the page');
                    }
                    else if (response === 'Incorrect') {
                        $('#error').text('Incorrect code');
                    }
                    else if (response === 'Customer') {
                        location.href = '/';
                    }
                    else if (response === 'Caterer') {
                        location.href = '/rank/toBuyrankSignup';
                    }
                    else if (response === 'Fail') {
                        $('#error').text('We cannot handle your request at the moment, please try again later');
                    }
                }
            });
        }
        else {
            $('#error').text('Please enter valid code');
        }
    });
}