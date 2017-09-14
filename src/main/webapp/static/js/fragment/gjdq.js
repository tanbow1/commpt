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
            methodName: "getGjdqListPagination",
            methodParams: [{
                index: 1,
                type: 'int',
                value: 1
            }, {
                index: 2,
                type: 'int',
                value: 10
            }]
        },
        type: 'post',
        timeout: SYS_TIMEOUT,
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            if (checkResponseText(responseText)) {
                $("#tb_gjdq").datagrid({
                    data: responseText.repData.gjdqList,
                    fitColumns: true,
                    rownumbers: true,
                    pagination: true,
                    multiSort: true,
                    striped: true,
                    singleSelect: true,
                    onClickCell: onClickCell,
                    onClickRow: onClickRow,
                    onAfterEdit: onAfterEdit,
                    onCheck: onCheck,
                    columns: [[
                        {field: 'ck', checkbox: true},
                        {
                            field: 'gjdqMcZ', title: '中文名称', width: 100,
                            editor: {
                                type: 'text'
                            }
                        },
                        {
                            field: 'gjdqMcE', title: '英文名称', width: 100,
                            editor: {
                                type: 'text'
                            }
                        },
                        {
                            field: 'gjdqMcdm', title: '国籍地区代码', width: 100,
                            editor: {
                                type: 'text'
                            }
                        },
                        {
                            field: 'gjdqDhdm', title: '电话代码', width: 100,
                            editor: {
                                type: 'numberbox'
                            }
                        },
                        {
                            field: 'gjdqId', title: '自定义编码', width: 100,
                            editor: {
                                type: 'numberbox'
                            }
                        },
                        {
                            field: 'yxbj',
                            title: '有效标记(1:有效,0:无效)',
                            width: 100,
                            align: 'center',
                            formatter: function (value, row, index) {
                                return value;
                            },
                            editor: {
                                type: 'checkbox', options: {
                                    on: '1', off: '0'
                                }
                            }
                        }
                    ]],
                    toolbar: [{
                        iconCls: 'icon-save',
                        handler: function () {
                            saveEdit();
                        }
                    }, '-', {
                        iconCls: 'icon-add',
                        handler: function () {
                            addRecord();
                        }
                    }, '-', {
                        iconCls: 'icon-remove',
                        handler: function () {
                            removeRecord();
                        }
                    }]
                })
                ;
            } else {
                alert(responseText.msg);
            }
        }

    });
}

var editIndex = undefined,
    checkIndex = undefined;
function endEditing() {
    if (editIndex == undefined) {
        return true
    }
    if ($('#tb_gjdq').datagrid('validateRow', editIndex)) {
        $('#tb_gjdq').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
//row edit
function onClickRow(index) {
    if (editIndex != index) {
        if (endEditing()) {
            $('#tb_gjdq').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            editIndex = index;
        } else {
            $('#tb_gjdq').datagrid('selectRow', editIndex);
        }
    }
}
//cell edit
function onClickCell(index, field) {
    if (editIndex != index) {
        if (endEditing()) {
            $('#tb_gjdq').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#tb_gjdq').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editIndex = index;
        } else {
            setTimeout(function () {
                $('#tb_gjdq').datagrid('selectRow', editIndex);
            }, 0);
        }
    }
}
function onAfterEdit(index, row, changes) {
    if (!isEmptyObject(changes)) {

    }
}
function onCheck(index, row) {
    checkIndex = index;
}
function saveEdit() {
    var rows = $('#tb_gjdq').datagrid('getChanges');
    if (endEditing()) {
        $('#tb_gjdq').datagrid('acceptChanges');
    }
    var removeRecords = $('#tb_gjdq').datagrid('getSelections');
    if (removeRecords.length > 0) {
        commonAjax('/comm/getJsonData2', 'dmService', 'addGjdqBatch', {records: JSON.stringify(removeRecords)}).then(function (resultData) {
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
    }
}
function addRecord() {
    if (endEditing()) {
        $('#tb_gjdq').datagrid('appendRow', {'yxbj': '1'});
        editIndex = $('#tb_gjdq').datagrid('getRows').length - 1;
        $('#tb_gjdq').datagrid('selectRow', editIndex)
            .datagrid('beginEdit', editIndex);
    }
}
function removeRecord() {
    var removeRecords = $('#tb_gjdq').datagrid('getSelections');
    if (removeRecords.length > 0) {
        commonAjax('/comm/getJsonData2', 'dmService', 'deleteGjdqBatch', {records: JSON.stringify(removeRecords)}).then(function (resultData) {
            if (checkResponseText(resultData)) {
                $('#tb_gjdq').datagrid('deleteRow', checkIndex);
                easyMsg.toast(resultData.msg);
            } else {
                easyDialog.alert(resultData.msg, function () {
                    easyDialog.close();
                });
            }
        }, function (textStatus) {
            easyMsg.alert(textStatus, 'error');
        });
    }
}