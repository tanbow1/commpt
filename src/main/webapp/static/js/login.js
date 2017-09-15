/**
 * Created by Tanbo on 2017/9/3.
 */
$(function () {
    $("#username").on('input', function () {
        $("#tipMsg").text('');
        if ('' == this.value.trim()) {
            $("#username").addClass('form-control-warning');
            $("#username").parents("div:first").addClass('has-warning');
        } else {
            $("#username").removeClass('form-control-warning').addClass('form-control-success');
            $("#username").parents("div:first").removeClass('has-warning').addClass('has-success');
        }
    });

    $("#password").on('input', function () {
        $("#tipMsg").text('');
        if ('' == this.value.trim()) {
            $("#password").addClass('form-control-warning');
            $("#password").parents("div:first").addClass('has-warning');
        } else {
            $("#password").removeClass('form-control-warning').addClass('form-control-success');
            $("#password").parents("div:first").removeClass('has-warning').addClass('has-success');
        }
    });
})

function loginsys(btnObj) {
    btnObj.setAttribute('disabled', 'disabled');
    var loadingTagObj = $(btnObj).find('i.fa').eq(0);
    loadingTagObj.addClass('fa-spinner fa-spin');
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();

    if ('' == username) {
        btnObj.removeAttribute('disabled');
        loadingTagObj.removeClass('fa-spinner fa-spin');
        $("#username").addClass('form-control-warning');
        $("#username").parents("div:first").addClass('has-warning');
        return;
    }

    if ('' == password) {
        btnObj.removeAttribute('disabled');
        loadingTagObj.removeClass('fa-spinner fa-spin');
        $("#password").addClass('form-control-warning');
        $("#password").parents("div:first").addClass('has-warning');
        return;
    }

    $.ajax({
        url: "/comm/login",
        data: {
            reqData: {
                username: username,
                password: password
            }
        },
        type: 'post',
        timeout: SYS_TIMEOUT,
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            btnObj.removeAttribute('disabled');
            loadingTagObj.removeClass('fa-spinner fa-spin');
            alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            btnObj.removeAttribute('disabled');
            loadingTagObj.removeClass('fa-spinner fa-spin');
            if (checkResponseText(responseText)) {
                $.cookie(SYS_PREFIX + 'ACCESS_TOKEN', responseText.repData.COMMONPT_ACCESS_TOKEN);
                $.cookie(SYS_PREFIX + 'REFRESH_TOKEN', responseText.repData.COMMONPT_REFRESH_TOKEN);

                window.location.href = '/comm/toPage/index';
            } else {
                $("#tipMsg").text(responseText.msg);
            }
        }


    });

}

function registsys() {

}