/**
 * Created by Tanbo on 2017/9/2.
 */

var SYS_TIMEOUT = 60000;
var SYS_PREFIX = "COMMONPT_";
var commomMessageTitle = "系统提示";

function checkResponseText(responseText) {
    try {
        if (responseText.code == '1') {
            return true;
        } else {
            return false;
        }
    } catch (e) {
        console.error(e.message);
        return false;
    }
}

/**
 * 转大写
 *
 * @param {}
 *            strg
 * @return {String}
 */
function zdx(strg) {
    var number = Math.round(strg * 100) / 100;
    number = number.toString(10).split(".");
    var a = number[0];
    if (a.length > 12)
        return "数值超出范围！支持的最大数值为 999999999999.99";
    var e = "零壹贰叁肆伍陆柒捌玖";
    var num1 = "";
    var len = a.length - 1;
    for (var i = 0; i <= len; i++)
        num1 += e.charAt(parseInt(a.charAt(i)))
            + [["圆", "万", "亿"][Math.floor((len - i) / 4)], "拾", "佰", "仟"][(len - i)
            % 4];
    if (number.length == 2 && number[1] != "") {
        var a = number[1];
        for (var i = 0; i < a.length; i++)
            num1 += e.charAt(parseInt(a.charAt(i))) + ["角", "分"][i];
    }
    num1 = num1.replace(/零佰|零拾|零仟|零角/g, "零");

    num1 = num1.replace(/亿万/, "亿");

    num1 = num1.replace(/^圆零?/, "");

    num1 = num1.replace(/零零/g, "");

    num1 = num1.replace(/零圆/, "圆");

    if (num1 != "" && !/分$/.test(num1))
        num1 += "整";
    return num1;

}

/**
 * sessionStorage
 * @type {{put: sessionOption.put, get: sessionOption.get, clearByKey: sessionOption.clearByKey, clearAll: sessionOption.clearAll}}
 */
var sessionOption = {
    put: function (key, val) {
        try {
            sessionStorage[SYS_PREFIX + key] = JSON.stringify(val);
            return true;
        } catch (e) {
            console.error("信息缓存失败:" + e.message);
            return false;
        }
    },
    get: function (key) {
        try {
            return JSON.parse(sessionStorage[SYS_PREFIX + key]);
        } catch (e) {
            return false;
        }
    },
    clearByKey: function (key) {
        sessionStorage.removeItem(key);
    },
    clearAll: function () {
        for (var i = 0; i < sessionStorage.length; i++) {
            sessionOption.clearByKey(sessionStorage.key(i));
            i--;
        }
    }
}

/**
 * easyui tab页操作
 * @type {{addPanel: easyuiTabOption.addPanel, updateTab: easyuiTabOption.updateTab, closePanel: easyuiTabOption.closePanel, closeAllTabs: easyuiTabOption.closeAllTabs}}
 */
var easyuiTabOption = {
    //tab页新增panel
    addPanel: function (tabId, targetNode) {
        var title = targetNode.text;
        if (targetNode.openType == '1') {

            if ($('#' + tabId).tabs("exists", title)) {
                $('#' + tabId).tabs("select", title);
                return;
            }

            if ($('#' + tabId).tabs('tabs').length > 10) {
                $.messager.alert(commomMessageTitle, '请先关闭部分选项卡!',
                    'warning');
                return;
            }

            $('#' + tabId).tabs('add', {
                title: title,
                collapsible: true,
                href: targetNode.url,
                closable: true,
                cache: false
            });
        }

        if (targetNode.openType == '2') {
            $('#' + tabId).attr('src', targetNode.url);
        }

        if (targetNode.openType == '3') {
            window.open(targetNode.url);
        }
    },
    //更新当前tab页
    updateTab: function (tabId) {
        var tabObj = $('#' + tabId);
        tabObj.tabs("update", {
            tab: tabObj.tabs("getSelected"),
            options: {
                title: tabObj.tabs("getSelected").panel("options").title,
                href: tabObj.tabs("getSelected").panel("options").href
            }
        });
    },
    //关闭指定或当前tab页
    closePanel: function (tabId, nodeTitle) {
        if (nodeTitle == undefined) {
            $('#' + tabId).tabs("close", $('#' + tabId).tabs("getSelected").panel("options").title);
        } else {
            $('#' + tabId).tabs("close", nodeTitle);
        }
    },
    //关闭所有可关闭的tab页
    closeAllTabs: function (tabId) {
        var tabs = $('#' + tabId).find(".tabs li");
        tabs.each(function (index, n) {
            var tabTitle = $(".tabs-closable", this).text();
            $('#' + tabId).tabs("close", tabTitle);
        });
    }

}


/**
 *
 * @param {}
 *            input type="file"对象
 * @param {}
 *            readtype 读取方式 url ：返回base64字符串 ,bin:返回二进制字符串,txt:文本字符串
 * @param {}
 *            encoding 读取文本时编码 UTF-8 / GBK
 */
function getFileAsStr(input, readtype, encoding) {
    if ("" == input.value) {
        alert('未取得文件');
        return;
    }
    var deferred = $.Deferred();
    var fileObj = new Object();
    var file = input.files[0];
    if (window.FileReader) {
        var fr = new FileReader();
        fr.onloadend = function (e) {
            fileObj.fileType = file.type;
            fileObj.fileStr = e.target.result;
            deferred.resolve(fileObj);
        };
        if ("url" == readtype) {
            fr.readAsDataURL(file);
        }
        if ("bin" == readtype) {
            fr.readAsBinaryString(file);
        }
        if ("txt" == readtype) {
            if (encoding == null) {
                encoding = "UTF-8";
            }
            fr.readAsText(file, encoding);
        }
    } else {
        alert('浏览器不支持HTML5的FileReader');
    }
    return deferred.promise();
}

/**
 * 获取url上携带的参数
 * @param keyName
 * @returns {null}
 */
function getUrlParamByKey(keyName) {
    var reg = new RegExp("(^|&)" + keyName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (null != r)
        return unescape(r[2]);
    return null;
}

/**
 * 通用 ajax
 * @param requestUrl
 * @param dataObj
 * @param serviceName service名
 * @param methodName 方法名
 * @param methodParamsArr method 入参数组
 * @returns {*}
 */
function commonAjax(requestUrl, serviceName, methodName, dataObj, methodParamsArr) {
    var deferred = $.Deferred();
    $.ajax({
        url: requestUrl,
        data: {
            accessToken: $.cookie(SYS_PREFIX + 'ACCESS_TOKEN'),
            refreshToken: $.cookie(SYS_PREFIX + 'REFRESH_TOKEN'),
            serviceName: serviceName,
            methodName: methodName,
            methodParams: methodParamsArr,
            reqData: dataObj
        },
        type: "post",
        timeout: SYS_TIMEOUT,
        dataType: "json",
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            deferred.resolve(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            deferred.resolve(responseText);
        }
    });
    return deferred;
}
