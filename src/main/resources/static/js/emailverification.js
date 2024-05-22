function verify() {
    $(document).ready(function () {
        inputCode = $('#code').val();
        if (inputCode >= 100000 && inputCode <= 999999) {
            $.ajax({
                type: 'POST',
                url: '/auth/verifyEmail',
                data: {
                    code: inputCode
                },
                success: function (response) {
                    if (response === 'Expired') {
                        $('#errror').text('');
                    }
                    else if (response === 'Incorrect') {
                        $('#error').text('Incorrect code');
                    }
                    else if (response === 'OK') {
                        location.href = '/';
                    }
                }
            });
        }
        else {
            $('#error').text('Please enter valid code');
        }
    });
}