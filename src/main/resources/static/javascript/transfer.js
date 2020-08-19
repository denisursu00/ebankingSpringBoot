$(document).ready(
    jQuery.validator.addMethod("lessThan", function(value, element, param) {
        return this.optional(element) || parseFloat(value) <= parseFloat($(param).val()) ;
    }, "Incorrect value!"),
    $(function () {
        $("form[name='transfer']").validate({
            rules: {
                sourceAccountId: "required",
                destinationClientId: "required",
                destinationAccountId: "required",
                transferSum: {
                    required:true,
                    number:true,
                    min: 0,
                    lessThan: '#sourceAccountSum'
                }
            },
            messages: {
                sourceAccountId: "Selectati contul sursa!",
                destinationClientId: "Selectati clientul destinatar!",
                destinationAccountId: "Selectati contul destinatar!",
                transferSum: {
                    required: "Introduceti suma de transfer!",
                    number: "Suma incorecta!",
                    min: "Suma trebuie sa fie pozitiva!",
                    lessThan: "Suma de transfer trebuie sa fie mai mica decat suma din contul sursa!"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    }),
    $.post("/getSourceAccounts", function (response) {
        $('#sourceAccountId').on("change",function () {
            $.each(response, function (index, account) {
                if( account["id"] == $('#sourceAccountId option:selected').val() ){
                    $('#sourceAccountSum').val(account["sum"]);
                };
            });
        });
    }),
    $(document).on('change','#destinationClientId',function () {
        $('#destinationAccountId').find('option').remove();
        $("<option selected disabled hidden>").val("").text("Alegeti contul destinatie").appendTo($('#destinationAccountId'));
        $.post("/getDestinationAccounts",{clientId: $('#destinationClientId option:selected').val()}, function (response) {
            $.each(response, function (index, account) {
                $("<option>").val(account["id"]).text("Cont "+account["accountNumber"]).appendTo($('#destinationAccountId'));
            });
        })
    })
);