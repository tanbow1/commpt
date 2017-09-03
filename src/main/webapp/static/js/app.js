/**
 * Created by Tanbo on 2017/9/3.
 */
var sysinfo = {
    sysname: "公共服务管理系统",
    version: "v1.0.0",
    footerinfo: "Copyright © 2017 tanbo"
}

$(function () {
    $(".sys__sysinfo").text(sysinfo.sysname);

    $(".sys__footerinfo").text(sysinfo.footerinfo + "  版本：" + sysinfo.version);
})