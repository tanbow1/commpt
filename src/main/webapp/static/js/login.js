/**
 * Created by Tanbo on 2017/9/3.
 */
$(function () {
    $("#username").on('input',function () {
        $("#tipMsg").text('');
        if('' == this.value.trim()){
            $("#username").addClass('form-control-warning');
            $("#username").parents("div:first").addClass('has-warning');
        }else{
            $("#username").removeClass('form-control-warning').addClass('form-control-success');
            $("#username").parents("div:first").removeClass('has-warning').addClass('has-success');
        }
    });

    $("#password").on('input',function () {
        $("#tipMsg").text('');
        if('' == this.value.trim()){
            $("#password").addClass('form-control-warning');
            $("#password").parents("div:first").addClass('has-warning');
        }else{
            $("#password").removeClass('form-control-warning').addClass('form-control-success');
            $("#password").parents("div:first").removeClass('has-warning').addClass('has-success');
        }
    });
})

function loginsys() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();

    if ('' == username) {
        $("#username").addClass('form-control-warning');
        $("#username").parents("div:first").addClass('has-warning');
        return;
    }

    if ('' == password) {
        $("#password").addClass('form-control-warning');
        $("#password").parents("div:first").addClass('has-warning');
        return;
    }

    if (false) {
        $("#username").removeClass('form-control-success').addClass('form-control-warning');
        $("#username").parents("div:first").removeClass('has-success').addClass('has-warning');

        $("#password").removeClass('form-control-success').addClass('form-control-warning');
        $("#password").parents("div:first").removeClass('has-success').addClass('has-warning');

        $("#tipMsg").text('用户名或密码有误');
        return;
    }else{
       window.location.href = '/comm/index';
    }

}

function registsys() {

}