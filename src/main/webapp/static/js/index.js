$(function () {

    easyuiTabOption.addPanel('mainpage', {
        text: "首页",
        closable: false,
        openType: "1",
        url: "/templates/html/fragment/spfl.html"
    });

    $("#mainmenutree").tree({
        url: '/comm/getMaintree',
        onClick: function (node) {
            $(this).tree('toggle', node.target);//点击菜单任意位置展开

            easyuiTabOption.addPanel('mainpage', node);
        }
    });
})


function logout() {
    window.location.href = '/comm/toPage/login';
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

                $.cookie(SYS_PREFIX + 'ACCESS_TOKEN', responseText.repData.resultData.accessToken);
                $.cookie(SYS_PREFIX + 'REFRESH_TOKEN', responseText.repData.resultData.refreshToken);

            } else {
                $("#tipMsg").text(responseText.msg);
            }
        }


    });
}