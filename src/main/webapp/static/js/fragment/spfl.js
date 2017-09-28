/**
 * Created by Tanbo on 2017/9/9.
 */

$(function () {
    spflTreegridOpts();

    initSpflTable();
})

function spflTreegridOpts() {
    $('#tb_spfl').treegrid({
        rownumbers: true,
        animate: true,
        collapsible: true,
        fitColumns: true,
        showFooter: false,
        url: 'comm/getJsonData2?serviceName=dmService&methodName=getProductTypeTree',
        title: '商品分类',
        idField: 'typeId',
        treeField: 'typeName',
        columns: [[
            {title: '名称', field: 'typeName', width: 100},
            {title: '描述', field: 'typeDesc', width: 100},
            {
                title: '有效标记', field: 'yxbj', width: 100, align: 'center', formatter: function () {
                return "";
            }
            }
        ]]
    });
}

function initSpflTable() {

}