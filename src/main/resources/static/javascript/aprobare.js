$(document).ready(
    $(document).on('change','input[type="checkbox"]',function () {
        $('input[name="' + this.name + '"]').not(this).prop('checked',false);
    }),
    $(document).on('click','input[type="button"]',function () {
        let pairs = $('form').serializeArray();
        for(let i=0;i<pairs.length;i++) {
            $.post("aprobare", {accountId: pairs[i].name, state:pairs[i].value});
        }
        location.href='home';
    })
);