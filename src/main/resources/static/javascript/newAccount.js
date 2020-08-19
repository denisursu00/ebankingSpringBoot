$(document).ready(
    $(function () {
        $("form[name='newAccount']").validate({
            rules: {
                accountNumber: "required"
            },
            messages: {
                accountNumber: "Introduceti numarul contului!"
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    })
);