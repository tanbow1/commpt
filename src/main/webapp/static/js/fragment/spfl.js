/**
 * Created by Tanbo on 2017/9/9.
 */

$(function () {
    spflTreegridOpts();

    initSpflTable();
})

function spflTreegridOpts() {
    $('#tb_spfl').treegrid({
        url: '',
        title: '商品分类',
        idField: 'typeId',
        treeField: 'name',
        columns: [[
            {title: '名称', field: 'typeName', width: 100},
            {title: '描述', field: 'typeDesc', width: 100},
            {title: '有效标记', field: 'yxbj', width: 100}
        ]]
    });
}

function initSpflTable() {

}