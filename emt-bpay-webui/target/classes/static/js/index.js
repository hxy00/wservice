$().ready(function() {
    $.ajax({
        contentType: 'application/json; charset=utf-8',
        url: "/PayAccountByPayeeId",
        data: {
            'company_code': '900001',
            'payee_id': '100450100006'
        },
        dataType: "json",
        cache: false,
        async: false,

        success: function(returnData) {
            // alert(JSON.stringify(returnData));
        }
    });

    $('#leftDiv').load('left.html');
    $('#northDiv').load('header.html');
    $('body').layout({
        applyDefaultStyles: false,
        north__size: 53,
        west__size: 230
    });

    var AdminLTEOptions = {
        sidebarExpandOnHover: true,
        //BoxRefresh Plugin
        enableBoxRefresh: true,
        //Bootstrap.js tooltip
        enableBSToppltip: true
    };


});

function addTabs(id, name, url, close) {
    var item = { 'id': id, 'name': name, 'url': url, 'closable': close };
    closableTab.addTab(item);
}



