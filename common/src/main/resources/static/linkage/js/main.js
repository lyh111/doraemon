$(function () {

    var $distpicker = $('#distpicker');

    $distpicker.distpicker({});

    $('#reset').click(function () {
        $distpicker.distpicker('reset');
    });

});
