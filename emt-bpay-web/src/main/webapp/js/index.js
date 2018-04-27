$().ready(function() {
/*	var _d =  {
		'company_code':'900001',
		'payee_id':'100450100006',
		'new_deposit':'100'
	};


	$.ajax({
		contentType: 'application/json; charset=utf-8',
		url: _BPAY_SERVICE_URL + "SetDeposit",
		data: {
			
			'depositJson': encodeURI(JSON.stringify(_d))
		},
		dataType: "json",
		cache: false,
		async: false,

		success: function(returnData) {
			alert(JSON.stringify(returnData));
			alert(returnData.messages);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
*/
	$('#leftDiv').load('left.html');
	$('#northDiv').load('header.html');
	$('body').layout({
		applyDefaultStyles: false,
		north__size: 53,
		west__size: 230
	});

	var AdminLTEOptions = {
		sidebarExpandOnHover: true,
		enableBoxRefresh: true,
		enableBSToppltip: true
	};

});

function addTabs(id, name, url, param, close) {
	var item = {
		'id': id,
		'name': name,
		'url': url,
		'param': param,
		'closable': close
	};
//	alert("index = "+item.param);
	// console.log('index----------------------------->');
	// console.log('item.param----------------------------->'+ JSON.stringify(param));
	closableTab.addTab(item);
	// console.log("tab 加载完成。。。。。。。。。。。。");
}