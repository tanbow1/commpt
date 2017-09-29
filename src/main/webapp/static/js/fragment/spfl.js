/**
 * Created by Tanbo on 2017/9/9.
 */

$(function () {
    spflTreegridOpts();
})

function spflTreegridOpts() {
    $('#tb_spfl').treegrid({
        rownumbers: true,
        animate: true,
        collapsible: true,
        fitColumns: true,
        showFooter: true,
        url: 'comm/getJsonData2?serviceName=dmService&methodName=getProductTypeTree',
        title: '商品分类',
        idField: 'typeId',
        treeField: 'typeName',
        columns: [[
            {title: '名称', field: 'typeName', editor: 'text', width: 100},
            {title: '描述', field: 'typeDesc', editor: 'text', width: 100},
            {
                title: '有效标记', field: 'yxbj', width: 100, align: 'center', formatter: function (value) {
                return value;
            }, editor: {
                type: 'checkbox', options: {
                    on: '1', off: '0'
                }
            }
            },
            {
                title: '是否包含子节点', field: 'haschildren', width: 100, align: 'center', formatter: function (value) {
                return value;
            }, editor: {
                type: 'checkbox', options: {
                    on: '1', off: '0'
                }
            }
            }
        ]],
        toolbar: [{
            iconCls: 'icon-reload',
            handler: function () {
                reloadSpflRecord();
            }
        }, '-', {
            iconCls: 'icon-save',
            handler: function () {
                saveSpflEdit();
            }
        }, '-', {
            iconCls: 'icon-edit',
            handler: function () {
                startSpflEdit();
            }
        }, '-', {
            iconCls: 'icon-reset',
            handler: function () {
                cancelSpflEdit();
            }
        }]
    });
}

var spflEditingId;
function startSpflEdit() {
    if (spflEditingId != undefined) {
        $('#tb_spfl').treegrid('select', spflEditingId);
        return;
    }
    var row = $('#tb_spfl').treegrid('getSelected');
    if (row) {
        spflEditingId = row.typeId
        $('#tb_spfl').treegrid('beginEdit', spflEditingId);
    }
}
function cancelSpflEdit() {
    if (spflEditingId != undefined) {
        $('#tb_spfl').treegrid('cancelEdit', spflEditingId);
        spflEditingId = undefined;
    }
}
function saveSpflEdit() {
    if (spflEditingId != undefined) {
        var t = $('#tb_spfl');
        t.treegrid('endEdit', spflEditingId);
        var record = $('#tb_spfl').treegrid('getSelected', spflEditingId);
        for (var v in record) {
            if (v == 'children' || v == 'state') {
                delete record[v];//移除子节点或展开状态
            }
        }

        var records = [];
        //暂时只进行单条记录，后台支持批量，需传入数组
        records.push(record);

        commonAjax('comm/getJsonData2', 'dmService', 'updateSpflBatch', {records: JSON.stringify(records)}).then(function (resultData) {
            if (checkResponseText(resultData)) {
                easyMsg.alert(resultData.msg, 'info');
            } else {
                easyDialog.alert(resultData.msg, function () {
                    easyDialog.close();
                });
            }
        }, function (textStatus) {
            easyMsg.alert(textStatus, 'error');
        });
        spflEditingId = undefined;
    }
}

function reloadSpflRecord() {
    $('#tb_spfl').treegrid({
        url: 'comm/getJsonData2?serviceName=dmService&methodName=getProductTypeTree'
    });
}