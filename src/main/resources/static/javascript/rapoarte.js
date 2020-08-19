$(document).ready(
    $(document).on('click','#htmlReport',function () {
        window.print();
    }),
    jQuery.validator.addMethod("dateLessThan", function(value, element, param) {
        let dateStart = new Date(value);
        let dateEnd = new Date($(param).val());
        let currentDate = new Date();
        if(!$(param).val().trim()) {
            return this.optional(element) || (dateStart<=currentDate);
        } else {
            return (dateStart <= dateEnd);
        }
    }, "Incorrect value!"),
    jQuery.validator.addMethod("lessThanCurrentDate", function(value, element) {
        let dateEnd = new Date(value);
        let currentDate = new Date();
        return this.optional(element) || (dateEnd<=currentDate);
    }, "Incorrect value!"),
    $(function () {
        $("form[name='report']").validate({
            rules: {
                accountId: "required",
                startDate: {
                    required: true,
                    dateLessThan: '#endDate'
                },
                endDate: {
                    required: true,
                    lessThanCurrentDate: ''
                },

            },
            messages: {
                accountId: "Selectati contul sursa!",
                startDate: {
                    required: "Selectati data de inceput!",
                    dateLessThan: "Data incorecta!",
                },
                endDate: {
                    required: "Selectati data de sfarsit!",
                    lessThanCurrentDate: "Data incorecta!",
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    })
);