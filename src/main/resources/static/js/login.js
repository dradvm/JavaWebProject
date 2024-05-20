function login() {
    $(document).ready(function () {
        $.ajax({
            type: 'POST',
            url: '/auth/login',
            contentType: 'application/json',
            data: JSON.stringify({
                username: $('#username').val(),
                password: $('#password').val()
            }),
            success: () => {
                console.log($('#username').val());
                console.log($('#password').val());
            }
        });
    });
}