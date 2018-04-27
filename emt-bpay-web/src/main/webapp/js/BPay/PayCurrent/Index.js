var nowYear = 0;
var nowMonth = 0;
var _companyCode = "";
var _payeeId = "";
var _userId = "";
var _userName = "";
var _token = "";
var table;

$(function() {
	_companyCode = GetQuery("companyCode");
	//_payeeId = GetQuery("payeeId");
	_userId = GetQuery("userId");
	_payeeId = _userId.substr(0, 12);
	_token = GetQuery("token");
	_userName = GetQuery("userName");

	var _year = "";
	var _month = "";
	var _message = "";

	if(_companyCode.length <= 0) {
		_message += "<b>没有传入套帐号</b><br>";
	}
	if(_payeeId.length <= 0) {
		_message += "<b>没有传入网点号</b><br>";
	}

	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "GetCurrentYear",
		cache: false, //禁用缓存
		//data: param, //传入组装的参数
		dataType: "text",
		async: false,
		success: function(result) {
			nowYear = result;
			_year = "" + result;
			$("#yearS").text(_year);
		}
	});

	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "GetCurrentMonth",
		cache: false, //禁用缓存
		//data: param, //传入组装的参数
		async: false,
		dataType: "text",
		success: function(result) {
			_month = "" + result;
			nowMonth = result;
			$("#monthS").text(_month);
		}
	});

	if(_message.length > 0) {
		BootstrapDialog.show({
			type: BootstrapDialog.TYPE_DANGER,
			title: '系统提示',
			message: _message,
			buttons: [{
				label: '关闭',
				cssClass: 'btn-primary',
				action: function(dialogItself) {
					dialogItself.close();
				}
			}]
		});
		return;
	}

	$('#bDateTxt').datepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true,
		clearBtn: true, //清除按钮
		todayBtn: true, //今日按钮
		format: "yyyy-mm-dd"
	});
	$('#eDateTxt').datepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true,
		clearBtn: true, //清除按钮
		todayBtn: true, //今日按钮
		format: "yyyy-mm-dd"
	});

	$(".select2").select2({
		minimumResultsForSearch: -1
	});

	PayAccountByPayeeId(_companyCode, _payeeId);
	getFundStatus(_companyCode, _payeeId, $("#yearS").text(), $("#monthS").text());
	loadData();

	//nowYear   
	var _y = parseInt(nowYear) - 1;
	$("#yearSel option").remove();
	$("#yearSel").append("<option value='" + _y + "'>" + _y + "年</option>");
	$("#yearSel").append("<option selected='selected' value='" + nowYear + "'>" + nowYear + "年</option>");
	//$("#monthSel").val(nowMonth);
	$("#monthSel option").remove();
	for(var i = 1; i <= 12; i++) {

		if(i == parseInt(nowMonth)) {
			$("#monthSel").append("<option selected='selected' value='" + i + "'>" + i + "月</option>");
		} else {
			$("#monthSel").append("<option value='" + i + "'>" + i + "月</option>");
		}

	}

});

function getFundStatus(companyCode, payeeId, year, month) {

	var statusParam = {
		company_code: companyCode,
		payee_id: payeeId,
		year: year,
		month: month,
		user_id: _userId,
		sys_id: '200001',
		token: _token
	};

	var param = {
		'paramJson': encodeURI(JSON.stringify(statusParam))
	};

	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "DocTypeGroupReport",
		cache: false, //禁用缓存
		data: param, //传入组装的参数
		dataType: "json",
		success: function(result) {
			if(result.retcode == "0") {
				var _data = result.data[0];
				$("#banlanceH").text("¥" + formatFinance(_data.banlance, 2, 1));
				$("#debitH").text("¥" + formatFinance(_data.debit_sum, 2, 1)); //收
				$("#creditH").text("¥" + formatFinance(_data.credit_sum, 2, 1)); //支
			} else {
				var _msg = "";
				for(var i = 0; i < result.data.length; i++) {
					_msg = result.data[i] + "<br>";
				}
				BootstrapDialog.show({
					type: BootstrapDialog.TYPE_DANGER,
					title: '系统提示',
					message: _msg,
					buttons: [{
						label: '关闭',
						cssClass: 'btn-primary',
						action: function(dialogItself) {
							dialogItself.close();
						}
					}]
				});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			//alert("XMLHttpRequest = " + JSON.stringify(XMLHttpRequest));
			//alert("textStatus = " + textStatus);
			//alert("errorThrown = " + errorThrown);
		}
	});

}

function lastMonth() {
	var _month = $("#monthS").text() - 1;
	if(_month >= 1) {
		$("#monthS").text(_month < 10 ? "0" + _month : _month);
	} else {
		$("#monthS").text(12);
		var _year = $("#yearS").text();
		$("#yearS").text(_year - 1);
	}
	getFundStatus(_companyCode, _payeeId, $("#yearS").text(), $("#monthS").text());
}

function nextMonth() {
	var _month = parseInt($("#monthS").text());
	_month += 1;
	var _year = parseInt($("#yearS").text());

	if(_month <= 12) {
		$("#monthS").text(_month < 10 ? "0" + _month : _month);
	} else {
		$("#monthS").text("01");
		_year += 1;
	}
	$("#yearS").text(_year);

	if(_month > nowMonth && _year >= nowYear) {
		BootstrapDialog.show({
			type: BootstrapDialog.TYPE_DANGER,
			title: '系统提示',
			message: '还没有到' + _year + '年' + _month + "月， 没有该月份的数据！",
			buttons: [{
				label: '关闭',
				cssClass: 'btn-primary',
				action: function(dialogItself) {
					dialogItself.close();
				}
			}]
		});
		$("#monthS").text(nowMonth);
		$("#yearS").text(nowYear);
	}

	getFundStatus(_companyCode, _payeeId, $("#yearS").text(), $("#monthS").text());

}

function PayAccountByPayeeId(company_code, payee_id) {
	var accountParam = {
		company_code: company_code,
		payee_id: payee_id,
		user_id: _userId,
		token: _token,
		sys_id: '200001'
	};

	var param = {
		'paramJson': encodeURI(JSON.stringify(accountParam))
	};

	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "PayAccountByPayeeId",
		cache: false, //禁用缓存
		data: param, //传入组装的参数
		dataType: "json",
		async: false,
		success: function(result) {
			if(parseInt(result.retcode) == 0) {
				$("#agencyNameLable").text(result.data[0].agency_name);
				$("#payeeIdLable").text("网点编号："+result.data[0].payee_id);
			} else {
				var _msg = "";
				for(var i = 0; i < result.data.length; i++) {
					_msg = result.data[i] + "<br>";
				}
				BootstrapDialog.show({
					type: BootstrapDialog.TYPE_DANGER,
					title: '系统提示',
					message: _msg,
					buttons: [{
						label: '关闭',
						cssClass: 'btn-primary',
						action: function(dialogItself) {
							dialogItself.close();
						}
					}]
				});
			}
		}
	});
}

function loadData() {

	table = $('#currentTable').DataTable({

		"searching": false,
		"lengthChange": false,
		"ordering": false,
		"info": true,
		"processing": true,
		"bStateSave": true,
		"serverSide": true,
		//ajax: _BPAY_SERVICE_URL + "PayAccount/PageAccount?pageIndex=1&pageSize=10",
		ajax: function(data, callback, settings) {
			//封装请求参数
			var PayAccountVO = {
				'payee_id': _payeeId,
				'company_code': _companyCode,
				'doc_type': $.trim($("#docTypeSel").val()),
				'doc_code': $.trim($("#docCodeTxt").val()),
				'b_reg_time': $.trim($("#bDateTxt").val()),
				'e_reg_time': $.trim($("#eDateTxt").val()),
				'user_id': _userId,
				'token': _token

			};
			//alert(JSON.stringify(PayAccountVO));
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
				url: _BPAY_SERVICE_URL + "CurrentByPage",
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
			$('td', row).eq(2).html("¥" + formatFinance(data['debit'], 2, 1));
			$('td', row).eq(3).html("¥" + formatFinance(data['credit'], 2, 1));
			$('td', row).eq(4).html("¥" + formatFinance(data['banlance'], 2, 1));
			//$('td', row).eq(5).html('<span class="label ' + _ACCOUNT_STATUS_CSS[data['account_status']] + '">' + _ACCOUNT_STATUS[data['account_status']] + '</span>');

		},
		columns: [{
				"data": "doc_code"
			},
			/*{ "data": "account_id" },*/
			{
				"data": "abstract"
			},
			{
				"data": "debit"
			},
			{
				"data": "credit"
			},
			{
				"data": "banlance"
			},
			{
				"data": "reg_time"
			}
		],
		language: _DATATABLES_LANG
	});

}

function search() { 
	var _msg = "";
	if($.trim($("#bDateTxt").val()).length > 0 && $.trim($("#eDateTxt").val()).length == 0) {
		_msg = "记帐时间段错误,请选择结束日期<br>";
	}
	if($.trim($("#bDateTxt").val()).length == 0 && $.trim($("#eDateTxt").val()).length > 0) {
		_msg = "记帐时间段错误,请选择开始日期<br>";
	}
	if($.trim($("#bDateTxt").val()).length > 0 && $.trim($("#eDateTxt").val()).length > 0) {
		if(datedifference($("#bDateTxt").val(), $("#eDateTxt").val()) < 0) {
			_msg = "记帐时间段错误,开始日期不能晚于结束日期<br>";
		}
	}

	if(_msg.length > 0) {
		BootstrapDialog.show({
			type: BootstrapDialog.TYPE_DANGER,
			title: '系统提示',
			message: _msg,
			buttons: [{
				label: '关闭',
				cssClass: 'btn-primary',
				action: function(dialogItself) {
					dialogItself.close();
					return;
				}
			}]
		});
	} else {
		table.ajax.reload();
	}
}

function reset1() {
	$('#searchForm')[0].reset();
	$("#docTypeSel").val(null).trigger("change");
}

function download() {
	var _y = $("#yearSel").val();
	var _m = $("#monthSel").val();

	/*	var param = {
			'company_code': _companyCode, //页面显示记录条数，在页面显示每页显示多少项的时候
			'payee_id': _payeeId, //开始的记录序号
			'year': _y, //当前页码
			'month':_m,
		    'userId': _userId,
		    'token': _token	 
		};
		var  _param =  encodeURI(JSON.stringify(param));
	*/

	var url = _BPAY_SERVICE_URL + "ExportCurrent?company_code=" + _companyCode + "&payee_id=" + _payeeId + "&year=" + _y +
		"&month=" + _m + "&user_id=" + _userId + "&token=" + _token + "&sys_id=200001";
	window.open(url);

}