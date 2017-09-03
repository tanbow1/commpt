(function($){
    function getCacheContainer(t){
        var view = $(t).closest('div.datagrid-view');
        var c = view.children('div.datagrid-editor-cache');
        if (!c.length){
            c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
        }
        return c;
    }
    function getCacheEditor(t, field){
        var c = getCacheContainer(t);
        return c.children('div.datagrid-editor-cache-' + field);
    }
    function setCacheEditor(t, field, editor){
        var c = getCacheContainer(t);
        c.children('div.datagrid-editor-cache-' + field).remove();
        var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
        e.append(editor);
    }

    var editors = $.fn.datagrid.defaults.editors;
    for(var editor in editors){
        var opts = editors[editor];
        (function(){
            var init = opts.init;
            opts.init = function(container, options){
                var field = $(container).closest('td[field]').attr('field');
                var ed = getCacheEditor(container, field);
                if (ed.length){
                    ed.appendTo(container);
                    return ed.find('.datagrid-editable-input');
                } else {
                    return init(container, options);
                }
            }
        })();
        (function(){
            var destroy = opts.destroy;
            opts.destroy = function(target){
                if ($(target).hasClass('datagrid-editable-input')){
                    var field = $(target).closest('td[field]').attr('field');
                    setCacheEditor(target, field, $(target).parent().children());
                } else if (destroy){
                    destroy(target);
                }
            }
        })();
    }
})(jQuery);


var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#dg').datagrid('validateRow', editIndex)){
        var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'productid'});
        var productname = $(ed.target).combobox('getText');
        $('#dg').datagrid('getRows')[editIndex]['productname'] = productname;
        $('#dg').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickRow(index){
    if (editIndex != index){
        if (endEditing()){
            $('#dg').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            editIndex = index;
        } else {
            $('#dg').datagrid('selectRow', editIndex);
        }
    }
}
function accept(){
    if (endEditing()){
        $('#dg').datagrid('acceptChanges');
    }
}
function reject(){
    $('#dg').datagrid('rejectChanges');
    editIndex = undefined;
}
function getChanges(){
    var rows = $('#dg').datagrid('getChanges');
    alert(rows.length+' rows are changed!');
}