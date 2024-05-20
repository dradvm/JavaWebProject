function login() {
    $('#username-error').text('');
    $('#password-error').text('');
    $('#error').text('');
    inputUsername = $('#username').val();
    inputPassword = $('#password').val();
    valid = true;
    if (inputUsername.trim().length === 0) {
        $('#username-error').text("Username can't be empty or filled with spaces");
        valid = false;
    }
    if (inputPassword.trim().length === 0) {
        $('#password-error').text("Password can't be empty or filled with spaces");
        valid = false;
    }
    if (!valid) {
        return;
    }
    $(document).ready(function () {
        $.ajax({
            type: 'POST',
            url: '/auth/login',
            data: {
                username: inputUsername,
                password: inputPassword
            },
            success: function (response) {
                if (response === 'Fail') {
                    $('#error').text("Incorrect username or password");
                }
                else if (response === 'Suspended') {
                    $('#error').text("This account has been suspended");
                }
                else if (response === 'Caterer' || response === 'Customer') {
                    location.href = '/';
                }
                else if (response === 'Admin') {
                    location.href = '/admin/dashboard';
                }
            }
        });
    });
}