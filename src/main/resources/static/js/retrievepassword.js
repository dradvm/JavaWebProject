function retrieve() {
    $(document).ready(function () {
        $('#error').text('');
        inputEmail = $('#email').val();
        regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!regex.test(inputEmail)) {
            $('#error').text('Please enter a valid email');
        }
        else {
            $.ajax({
                type: 'POST',
                url: '/auth/checkRetrieveEmail',
                data: {
                    email: inputEmail
                },
                success: function (response) {
                    if (response === 'Email') {
                        $('#error').text('Please enter a valid email');
                    }
                    else if (response === 'Unregistered') {
                        $('#error').text('This email has not been registered');
                    }
                    else {
                        location.href = response;
                    }
                }
            });
        }
    });
}