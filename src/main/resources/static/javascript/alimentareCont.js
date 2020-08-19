$(document).ready(
    $(function () {
        $("form[name='alimentareCont']").validate({
            rules: {
                accountNumber: "required",
                depositSum: {
                    required:true,
                    number:true,
                    min:0
                }
            },
            messages: {
                accountNumber: "Selectati contul!",
                depositSum: {
                    required: "Introduceti suma de alimentare!",
                    number: "Suma incorecta!",
                    min: "Suma trebuie sa fie pozitiva!"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    }),
    $.post("/getSourceAccounts", function (response) {
        $('#accountNumber').on("change",function () {
            $.each(response, function (index, account) {
                if( account["id"] == $('#accountNumber option:selected').val() ){
                    $('#accountSum').val(account["sum"]);
                };
            });
        });
    })
);