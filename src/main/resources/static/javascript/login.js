$(document).ready(
    $(function () {
        $("form[name='login']").validate({
            rules: {
                username: "required",
                password: "required"
            },
            messages: {
                username: "Introduceti username-ul!",
                password: "Introduceti parola!"
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    })
);