function logout() {
    window.location.href = '/comm/tologin';
}

function refreshToken() {
    $.ajax({
        url: "/comm/refreshToken",
        data: {
            accessToken: $.cookie(SYS_PREFIX + 'ACCESS_TOKEN'),
            refreshToken: $.cookie(SYS_PREFIX + 'REFRESH_TOKEN')
        },
        type: 'post',
        timeout: SYS_TIMEOUT,
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            if (checkResponseText(responseText)) {
                alert(responseText.msg);
            } else {
                $("#tipMsg").text(responseText.msg);
            }
        }


    });
}