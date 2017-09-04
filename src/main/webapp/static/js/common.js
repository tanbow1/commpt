/**
 * Created by Tanbo on 2017/9/2.
 */

var SYS_TIMEOUT = 60000;
var SYS_PREFIX = "COMMONPT_";

function checkResponseText(responseText) {
    if (responseText.code == '1') {
        return true;
    } else {
        return false;
    }
}