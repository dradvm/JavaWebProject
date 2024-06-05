function changePassword() {
    $(document).ready(function () {
        $('#error').text('');
        inputPassword = $('#password').val();
        inputNewPassword = $('#new-password').val();
        inputConfirmPassword = $('#confirm-password').val();
        if (inputNewPassword.trim().length < 8) {
            $('#error').text('Password must have at least 8 characters and not contain only spaces');
            return;
        }
        if (inputConfirmPassword !== inputNewPassword) {
            $('#error').text('Confirm password does not match new password');
            return;
        }
        $.ajax({
            url: "/auth/changePassword",
            type: 'POST',
            data: {
                password: inputPassword,
                newPassword: inputNewPassword,
                confirmPassword: inputConfirmPassword
            },
            success: function (response) {
                if (response.status === 'Fail') {
                    $('#error').text('Invalid page, please refresh');
                }
                else if (response.status === 'Incorrect') {
                    $('#error').text('Incorrect password');
                }
                else if (response.status === 'OK') {
                    location.href = response.target;
                }
            }
        });
    });
}