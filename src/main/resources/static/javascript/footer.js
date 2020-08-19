$(document).ready(
    $(function () {
        let viewport = $(window).outerHeight();
        let header = $("nav").outerHeight();
        let footer = $("footer").outerHeight();
        $(".container-lg").css("min-height", function(){
            return viewport-footer-header;
        });
    }),
    $(window).resize(function () {
        let viewport = $(window).outerHeight();
        let header = $("nav").outerHeight();
        let footer = $("footer").outerHeight();
        $(".container-lg").css("min-height", function(){
            return viewport-footer-header;
        });
    })
);