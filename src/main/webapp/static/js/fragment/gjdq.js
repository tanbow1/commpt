/**
 * Created by Tanbo on 2017/9/9.
 */
$(function () {

    gjdqDatagridOpts();

    initGjdqTable(DEFAULT_PAGESTART, DEFAULT_PAGESIZE);

})

function gjdqDatagridOpts() {
    $("#tb_gjdq").datagrid({
        loadMsg: loadMsg,
        title: '国家地区',
        collapsible: true,
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        multiSort: true,
        striped: true,
        singleSelect: true,
        onClickCell: onClickCellGjdq,
        onClickRow: onClickRowGjdq,
        onAfterEdit: onAfterEditGjdq,
        onCheck: onCheckGjdq,
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
                field: 'gjdqMcdm', title: '国家地区代码', width: 100,
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
                field: 'sc', title: '与中国时差', width: 100,
                editor: {
                    type: 'text'
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
            iconCls: 'icon-reload',
            handler: function () {
                reloadGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-save',
            handler: function () {
                saveEditGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-add',
            handler: function () {
                addGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-remove',
            handler: function () {
                removeGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-import',
            handler: function () {
                importGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-export',
            handler: function () {
                exportGjdqRecord();
            }
        }]
    });

    //分页
    $("#tb_gjdq").datagrid("getPager").pagination({
        loading: true,
        showPageList: true,
        showRefresh: true,
        pageSize: DEFAULT_PAGESIZE,
        pageList: [10, 20, 50, 100],
        //layout: ['first', 'links', 'last'],
        onRefresh: function (pageNumber, pageSize) {
            initGjdqTable(pageNumber, pageSize);
        },
        onSelectPage: function (pageNumber, pageSize) {
            initGjdqTable(pageNumber, pageSize);
        },
        buttons: [{
            iconCls: 'icon-export',
            text: '导出Excel',
            handler: function () {
                exportGjdqRecord();
            }
        }, '-', {
            iconCls: 'icon-import',
            text: '从Excel导入',
            handler: function () {
                importGjdqRecord();
            }
        }]
    });
}

function initGjdqTable(pageNumber, pageSize) {
    easyMsg.progresson();

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
                value: pageNumber
            }, {
                index: 2,
                type: 'int',
                value: pageSize
            }]
        },
        type: 'post',
        timeout: SYS_TIMEOUT,
        dataType: 'json',
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            easyMsg.progressoff();
            alert(textStatus);
        },
        success: function (responseText, textStatus, XMLHttpRequest) {
            easyMsg.progressoff();
            if (checkResponseText(responseText)) {
                var gjdqCount = responseText.repData.gjdqCount;
                if (gjdqCount > 0) {
                    $("#tb_gjdq").datagrid('loadData', responseText.repData.gjdqList);
                }

                $("#tb_gjdq").datagrid("getPager").pagination('refresh', {
                    total: gjdqCount,
                    pageNumber: pageNumber
                }).pagination('loaded');

            } else {
                alert(responseText.msg);
            }
        }

    });
}

var editGjdqGridIndex = undefined,
    checkedGjdqGridIndex = undefined;
function endGjdqRecordEditing() {
    if (editGjdqGridIndex == undefined) {
        return true
    }
    if ($('#tb_gjdq').datagrid('validateRow', editGjdqGridIndex)) {
        $('#tb_gjdq').datagrid('endEdit', editGjdqGridIndex);
        editGjdqGridIndex = undefined;
        return true;
    } else {
        return false;
    }
}
//row edit
function onClickRowGjdq(index) {
    if (editGjdqGridIndex != index) {
        if (endGjdqRecordEditing()) {
            $('#tb_gjdq').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            editGjdqGridIndex = index;
        } else {
            $('#tb_gjdq').datagrid('selectRow', editGjdqGridIndex);
        }
    }
}
//cell edit
function onClickCellGjdq(index, field) {
    if (editGjdqGridIndex != index) {
        if (endGjdqRecordEditing()) {
            $('#tb_gjdq').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#tb_gjdq').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editGjdqGridIndex = index;
        } else {
            setTimeout(function () {
                $('#tb_gjdq').datagrid('selectRow', editGjdqGridIndex);
            }, 0);
        }
    }
}
function onAfterEditGjdq(index, row, changes) {
    if (!isEmptyObject(changes)) {

    }
}
function onCheckGjdq(index, row) {
    checkedGjdqGridIndex = index;
}
function saveEditGjdqRecord() {
    var rows = $('#tb_gjdq').datagrid('getChanges');
    if (endGjdqRecordEditing()) {
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
function addGjdqRecord() {
    if (endGjdqRecordEditing()) {
        $('#tb_gjdq').datagrid('appendRow', {'yxbj': '1'});
        editGjdqGridIndex = $('#tb_gjdq').datagrid('getRows').length - 1;
        $('#tb_gjdq').datagrid('selectRow', editGjdqGridIndex)
            .datagrid('beginEdit', editGjdqGridIndex);
    }
}
function removeGjdqRecord() {
    var removeRecords = $('#tb_gjdq').datagrid('getSelections');
    if (removeRecords.length > 0) {

        easyMsg.confirm('删除记录？', function () {
            commonAjax('/comm/getJsonData2', 'dmService', 'deleteGjdqBatch', {records: JSON.stringify(removeRecords)}).then(function (resultData) {
                if (checkResponseText(resultData)) {
                    $('#tb_gjdq').datagrid('deleteRow', checkedGjdqGridIndex);
                    easyMsg.toast(resultData.msg + ":已删除");
                } else {
                    easyDialog.alert(resultData.msg, function () {
                        easyDialog.close();
                    });
                }
            }, function (textStatus) {
                easyMsg.alert(textStatus, 'error');
            });
        })

    }
}
//重置该datagrid整个数据
function reloadGjdqRecord() {
    gjdqDatagridOpts();

    initGjdqTable(DEFAULT_PAGESTART, DEFAULT_PAGESIZE);
}

//导入导出
function importGjdqRecord() {
//导入本地excel到数据库
    $("#comm_fileuploadDialog_content tbody").find('input[name="uploadFile"]:first')[0].value = '';
    $("#comm_fileuploadDialog_content tbody").find('tr:not(":first")').remove();
    $("#comm_fileuploadDialog").dialog('open');
    bootstraProgress.init("comm_fileuploadDialog_content");

}
function exportGjdqRecord() {
//导出数据到本地excel
    window.open('/comm/getJsonData2?serviceName=dmService&methodName=exportGjdqToExcel', 'exportFile');
}
// 校验当前file
function checkFile(fileInputObj) {

}
function uploadFiles() {
    var files = getFileObjs();
    if (files.length == 0) {
        easyMsg.toast("未选择文件");
    } else {
        $("#comm_fileuploadDialog_content").ajaxSubmit({
            url: "comm/uploadFiles",
            dataType: "json",
            type: "POST",
            data: {
                reqData: {
                    test: 'test',
                    name: 'tanbo'
                },
                serviceName: "dmService",
                methodName: "importGjdqFromExcel"
            },
            timeout: SYS_TIMEOUT,
            resetForm: false,//上传后重置表单
            beforeSubmit: function () {
                bootstraProgress.init("comm_fileuploadDialog_content");
            },
            uploadProgress: function (event, position, total, percentComplete) {
                bootstraProgress.processing("comm_fileuploadDialog_content", percentComplete);
            },
            success: function (data) {
                if (checkResponseText(data)) {
                    easyMsg.confirm(data.msg + ' 继续上传？', function () {
                        reset();
                    }, function () {
                        $("#comm_fileuploadDialog").dialog('close');
                    })

                } else {
                    bootstraProgress.error("comm_fileuploadDialog_content");
                    easyMsg.alert(data.msg);
                }
            },
            error: function (error) {
                bootstraProgress.error("comm_fileuploadDialog_content");
                easyMsg.alert(error.statusText + ":上传出错");
            }
        });
    }
}
