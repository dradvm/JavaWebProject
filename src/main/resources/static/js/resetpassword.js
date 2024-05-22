function resetPassword() {
    $(document).ready(function () {
        $('#error').text('');
        inputNewPassword = $('#new-password').val();
        inputConfirmPassword = $('#confirm-password').val();
        if (inputNewPassword.trim().length < 8) {
            $('#error').text('Password must have at least 8 characters and not contain only spaces');
        } 
        else if (inputNewPassword !== inputConfirmPassword) {
            $('#error').text("New password and confirm password don't match");
        } 
        else {
            $.ajax({
                type: 'POST',
                url: '/auth/resetPassword',
                data: {
                    password: inputNewPassword
                },
                success: function (response) {
                    if (response === 'Fail') {
                        $('#error').text("We couldn't reset your password at the moment, please try again later");
                    }
                    if (response === 'Invalid') {
                        $('#error').text('Password must have at least 8 characters and not contain only spaces');
                    }
                    if (response === 'OK') {
                        $('#error').text('Your password has been changed successfully, redirecting to login page...');
                        setTimeout(function () {
                            location.href = '/auth/toLogin';
                        }, 3000);
                    }
                }
            });
        }
    });
}