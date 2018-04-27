/**
 * Created by dsj on 2017/3/20.
 */

/*var vm_account = new Vue({
	// 选项
	el: '#app',
	data: {}
})
*/
var table;
$(function() {
	table = $('#example1').DataTable({

		"searching": false,
		"lengthChange": false,
		"ordering": true,
		"info": true,
		"processing": true,
		"serverSide": true,
		//ajax: _BPAY_SERVICE_URL + "PayAccount/PageAccount?pageIndex=1&pageSize=10",
		ajax: function(data, callback, settings) {
			//封装请求参数
			var PayAccountVO = {
				'payee_id': $.trim($("#payee_id").val()),
				'company_code': $.trim($("#company_code").val()),
				'payee_name': $.trim($("#payee_name").val()),
				'agency_name': $.trim($("#agency_name").val())
			};
			var param = {
				'pageSize': data.length, //页面显示记录条数，在页面显示每页显示多少项的时候
				'start': data.start, //开始的记录序号
				'pageIndex': (data.start / data.length) + 1, //当前页码
				'accountJson': encodeURI(JSON.stringify(PayAccountVO))
			};
			//param.pageSize = data.length; //页面显示记录条数，在页面显示每页显示多少项的时候
			//param.start = data.start; //开始的记录序号
			//param.pageIndex = (data.start / data.length) + 1; //当前页码

			//console.log(param);
			//ajax请求数据
			$.ajax({
				type: "POST",
				url: _BPAY_SERVICE_URL + "AccountByPage",
				cache: false, //禁用缓存
				data: param, //传入组装的参数
				dataType: "json",
				success: function(result) {
					//console.log("-------------begin 获取后台数据-----------------------");
					//console.log(result);
					//setTimeout仅为测试延迟效果
					setTimeout(function() {
						//封装返回数据
						var returnData = {};
						returnData.draw = data.draw; //这里直接自行返回了draw计数器,应该由后台返回
						returnData.recordsTotal = result.total; //返回数据全部记录
						returnData.recordsFiltered = result.total; //后台不实现过滤功能，每次查询均视作全部结果
						returnData.data = result.list; //返回的数据列表
						//console.log(returnData);
						//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
						//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
						callback(returnData);
					}, 200);
				}
			});
		},
		createdRow: function(row, data, index) {
			$('td', row).eq(4).html("¥" + formatFinance(data['deposit'], 2, 1));
			$('td', row).eq(5).html('<span class="label ' + _ACCOUNT_STATUS_CSS[data['account_status']] + '">' + _ACCOUNT_STATUS[data['account_status']] + '</span>');

		},
		columns: [{
				"data": "company_code"
			},
			/*{ "data": "account_id" },*/
			{
				"data": "agency_name"
			},
			{
				"data": "payee_id"
			},
			{
				"data": "payee_name"
			},
			{
				"data": "deposit"
			},
			{
				"data": "account_status"
			}
		],
		language: _DATATABLES_LANG
	});

	$('#myModal').on('shown.bs.modal', function() {
		$("#leftDiv").css("z-index", -1);
	})

	$('#myModal').on('hidden.bs.modal', function() {
		$("#leftDiv").css("z-index", 9999);
	})

	$('#example1 tbody').on('click', 'tr', function() {
		if($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
	/*$('#button').click( function () {
	    table.row('.selected').remove().draw( false );
	} );*/

});

function openMyModel2() {
	$('#myModal').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function search() { 
	table.ajax.reload();
}

function reset() {
	$('#searchForm').reset();
}

function editWin() {
	if(table.rows('.selected').data().length > 0) {
		//		alert(JSON.stringify(table.rows('.selected').data()[0]));
		//		alert(table.rows('.selected').data()[0].company_code);
		        var _url = 'BPay/PayAccount/PayAccountInfo.html';
		//        _url +=  '?company_code='+ table.rows('.selected').data()[0].company_code;
		//        _url += '&payee_id=' + table.rows('.selected').data()[0].payee_id;
		        
		//var _url1 = "BPay/PayAccount/PayAccountInfo.html";
		//console.log("-----------------");
		console.log("打开editWin Url ----->" + _url);
		var param = {
			company_code: table.rows('.selected').data()[0].company_code,
			payee_id: table.rows('.selected').data()[0].payee_id
		};
		addTabs('payAccountInfo', '帐户信息', _url, param, true);

	} else {
		alert("请选择");
	}
}