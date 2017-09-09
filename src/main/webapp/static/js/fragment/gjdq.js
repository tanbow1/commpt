/**
 * Created by Tanbo on 2017/9/9.
 */
$(function () {

    initGjdqTable();

})

function initGjdqTable() {
    $.ajax({
        url: "/comm/getJsonData",
        data: {
            accessToken: $.cookie(SYS_PREFIX + 'ACCESS_TOKEN'),
            refreshToken: $.cookie(SYS_PREFIX + 'REFRESH_TOKEN'),
            serviceName: "dmService",
            methodName: "test"
        },
        type: 'post',
        timeout: SYS_TIMEOUT,
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            if (checkResponseText(responseText)) {
                console.log(responseText);
            } else {
                $("#tipMsg").text(responseText.msg);
            }
        }


    });
}