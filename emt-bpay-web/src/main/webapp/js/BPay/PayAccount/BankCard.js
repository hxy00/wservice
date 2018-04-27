var _companyCode = "";
var _payeeId = "";
var _token = "";
var _userId = "";
var _userName = "";
//var _bankList = new Array('中国工商银行','中国建设银行','中国农业银行','中国银行','招商银行','交通银行','中信银行','中国民生银行','兴业银行','上海浦东发展银行','中国光大银行','农村商业银行','广东发展银行','华夏银行','农村信用合作社','北京银行','国家邮政局邮政储汇局','平安银行','渤海银行股份有限公司','民生银行','光大银行','农村合作银行','上海银行','经销商编号','青海银行');
var _bankList = new Array('中国工商银行', '中国建设银行', '中国农业银行', '中国银行', '招商银行', '交通银行', '中信银行', '中国民生银行');

$(function() {
	var _message = "";
	_companyCode = GetQuery("companyCode");
	_userId = GetQuery("userId");
	_payeeId = _userId.substr(0, 12);
	_token = GetQuery("token");
	_userName = GetQuery("userName");

	if(_companyCode.length <= 0) {
		_message += "<b>没有传入套帐号</b><br>";
	}
	if(_payeeId.length <= 0) {
		_message += "<b>没有传入网点号</b><br>";
	}
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
	PayAccountByPayeeId();
	BankCardByPayeeId();
	loadData();
	loadBank();

	$('#BankCardModal').on('hidden.bs.modal', function(e) {
		$('#mFormData')[0].reset();
	})

  
})

function loadBank() {
	$("#bankNameSel option").remove();
	$("#bankNameSel").append("<option value='' selected='selected'>请选择</option>");
	for(x in _bankList) {
		$("#bankNameSel").append("<option value='" + _bankList[x] + "'>" + _bankList[x] + "</option>");
	}
}

function addBank(bankName) {
	//alert($.inArray(bankName, _bankList));
	if($.inArray(bankName, _bankList) < 0) {
		$("#bankNameSel").append("<option value='" + bankName + "' selected='selected'>" + bankName + "</option>");
	}
}

function PayAccountByPayeeId() {
	var accountParam = {
		company_code: _companyCode,
		payee_id: _payeeId,
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
				$("#payeeIdLable").text("网点编号：" + result.data[0].payee_id);

				var _wait = parseInt(result.data[0].wait_verify);
				var _card = parseInt(result.data[0].card_num);
				//alert((_wait + _card));
				if((_wait + _card) > 1) {
					$("#NewBankCard").hide();
				} else {
					$("#NewBankCard").show();
				}

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

function BankCardByPayeeId() {
	$("#BankCardListDiv").html("");
	var accountParam = {
		company_code: _companyCode,
		payee_id: _payeeId,
		user_id: _userId,
		token: _token,
		sys_id: '200001'
	};

	var param = {
		'paramJson': encodeURI(JSON.stringify(accountParam))
	};

	var HtmlStr = "";
	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "BankCardByPayeeId",
		cache: false, //禁用缓存
		data: param, //传入组装的参数
		dataType: "json",
		async: false,
		success: function(_data) {

			if(_data.retcode == "0") {
				var _count = _data.data.length;
				if(_count > 1) {
					$("#NewBankCard").hide();
				}
				for(var i = 0; i < _count; i++) {
					var _state = "";
					var _color = "bg-aqua";
					if(_data.data[i].card_status == "1") {
						_color = "bg-yellow";
						_state = "&nbsp;<b>(变更中)</b>";
					}

					HtmlStr += "<div class='col-lg-3 col-xs-6' id='NewBankCard'>";
					HtmlStr += "<div class='small-box " + _color + "'>";
					HtmlStr += "<div class='inner'>";
					HtmlStr += "<h3>" + _data.data[i].bank_name + "</h3>";
					HtmlStr += " <p>" + _data.data[i].account_id + _state + "</p>";
					HtmlStr += " <p>" + _data.data[i].account_name + "</p>";
					HtmlStr += "</div>";
					HtmlStr += "<div class='icon'>";
					HtmlStr += "<i class='fa fa-credit-card'></i>";
					HtmlStr += "</div>";
					HtmlStr += "<a href='#' class='small-box-footer' onclick=BackInfoBySn(\'" + _data.data[i].sn + "\')>申请变更 <i class='fa fa-pencil-square'></i></a>";
					HtmlStr += "</div>";
					HtmlStr += "</div>";
				}

				$("#BankCardListDiv").append(HtmlStr);

			} else {
				var _msg = "调用失败,retcode=" + _data.retcode + ",retmsg=" + _data._retmsg;
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

function addBankCard() {
	$('#BankCardModal').modal({
		backdrop: 'static',
		keyboard: false
	});
}

function save() {

	var _bank_name = $("#bankNameSel").val();
	var _account_id = $("#accountIdText").val().trim();
	var _account_name = $("#accountNameText").val().trim();
	var _branch = $("#branchText").val().trim();
	var _branch_code = $("#branchCodeText").val().trim();
	var _bank_city_name = $("#bankCityNameText").val().trim();;
	var _is_prop = $("#isPropSel").val();
	var _sn = $("#snText").val().trim();
x
	var msg = "";
	if(_bank_name.trim().length == 0)
		msg += "请选择银行名称!<br>";
	if(_account_id.trim().length == 0)
		msg += "银行账号不能为空!<br>";
	if(_account_name.trim().length == 0)
		msg += "银行帐户名称不能为空!<br>";
	if(_branch.trim().length == 0)
		msg += "开户支行不能为空!<br>";
	if(_branch_code.trim().length == 0)
		msg += "开户支行代码不能为空!<br>";
	if(_bank_city_name.trim().length == 0)
		msg += "开户行所在城市不能为空!<br>";
	if(_is_prop.trim().length == 0)
		msg += "请选择对公对私标志!<br>";

	var fileName = $("#exampleInputFile").val();
	if(fileName == "") {
		msg += "请上传附件!";
	}

	if(msg.trim().length > 0) {

		BootstrapDialog.show({
			type: BootstrapDialog.TYPE_DANGER,
			title: '系统提示',
			message: msg,
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

	var bankInfo = {
		'token': _token,
		'company_code': _companyCode,
		'payee_id': _payeeId,
		'sys_id': '200001',
		'user_id': _userId,
		'bank_name': _bank_name,
		'account_id': _account_id,
		'account_name': _account_name,
		'branch': _branch,
		'branch_code': _branch_code,
		'bank_city_name': _bank_city_name,
		'is_prop': _is_prop,
		'join_sn': _sn
	};

	//alert(JSON.stringify(bankInfo));

	var param = {
		'accountJson': encodeURI(JSON.stringify(bankInfo))
	};
	var formData = new FormData();
	formData.append('file', $('#exampleInputFile')[0].files[0]);
	formData.append('accountJson', encodeURI(JSON.stringify(bankInfo)));

	/*waitingDialog.show('正在上传.....', {
		dialogSize: 'sm',
		progressType: 'warning'
	});
	*/
	$("#saveBut").button('toggle');
	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "InsertPayBankCardApply",
		cache: false, //禁用缓存
		//data: param, //传入组装的参数
		data: formData,
		dataType: "json",
		//contentType: "application/json;charset=UTF-8",
		async: false,
		processData: false,
		contentType: false,
		beforeSend: beforeSave,
		success: successSave,
		success: function(result) {
			if(result.retcode == "0") {
				//alert(JSON.stringify(result));
				BootstrapDialog.show({
					type: BootstrapDialog.TYPE_SUCCESS,
					title: '系统提示',
					message: "已经成功提交，请耐心等待",
					buttons: [{
						label: '关闭',
						cssClass: 'btn-primary',
						action: function(dialogItself) {
							dialogItself.close();
						}
					}]
				});

				table.ajax.reload();
				$('#modal-default').modal('hide');
				$('#BankCardModal').modal('hide');
				BankCardByPayeeId();
			} else {

				var _msg = "";
				for(var i = 0; i < result.data.length; i++) {
					_msg += result.data[i] + "<br>";
				}
				BootstrapDialog.show({
					type: BootstrapDialog.TYPE_DANGER,
					title: '系统提示',
					message: _msg,
					buttons: [{
						label: '关闭',
						cssClass: 'btn-primary',
						action: function(dialogItself) {
							$('#modal-default').modal('hide');
							dialogItself.close();
						}
					}]
				});

			}
		}
	});
}

function beforeSave() {
	$('#modal-default').modal({
		keyboard: false
	});

}

function successSave() {
	$('#modal-default').modal('hide');
}

function fileChange(target) {
	//检测上传文件的类型 
	var imgName = document.all.exampleInputFile.value;
	var ext, idx;
	if(imgName == '') {
		document.all.submit_upload.disabled = true;
		alert("请选择需要上传的文件!");
		return;
	} else {
		idx = imgName.lastIndexOf(".");
		if(idx != -1) {
			ext = imgName.substr(idx + 1).toUpperCase();
			ext = ext.toLowerCase();
			// alert("ext="+ext);
			if(ext != 'jpg' && ext != 'png' && ext != 'jpeg' && ext != 'gif') {
				document.all.submit_upload.disabled = true;
				alert("只能上传.jpg  .png  .jpeg  .gif类型的文件!");
				return;
			}
		} else {
			document.all.submit_upload.disabled = true;
			alert("只能上传.jpg  .png  .jpeg  .gif类型的文件!");
			return;
		}
	}

	//检测上传文件的大小        
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	if(isIE && !target.files) {
		var filePath = target.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}

	var size = fileSize / 1024;

	if(size > 2000) {
		document.all.submit_upload.disabled = true;
		alert("文件大小不能超过2M");
	} else {
		document.all.submit_upload.disabled = false;
	}
}

function BackInfoBySn(Sn) {

	var accountParam = {
		sn: Sn,
		user_id: _userId,
		token: _token,
		sys_id: '200001'
	};

	var param = {
		'paramJson': encodeURI(JSON.stringify(accountParam))
	};

	$.ajax({
		type: "POST",
		url: _BPAY_SERVICE_URL + "BankCardBySn",
		cache: false, //禁用缓存
		data: param, //传入组装的参数
		dataType: "json",
		async: false,
		success: function(_data) {
			if(_data.retcode == "0") {
				addBank(_data.data[0].bank_name);
				$("#bankNameSel").val(_data.data[0].bank_name);
				$("#snText").val(_data.data[0].sn);
				$("#accountIdText").val(_data.data[0].account_id);
				$("#accountNameText").val(_data.data[0].account_name);
				$("#branchText").val(_data.data[0].branch);
				$("#branchCodeText").val(_data.data[0].branch_code);
				$("#bankCityNameText").val(_data.data[0].bank_city_name);
				$("#isPropSel").val(_data.data[0].is_same_city);
				$('#BankCardModal').modal({
					keyboard: false
				});
			}
		}
	});

}

function loadData() {

	table = $('#bankLogTable').DataTable({

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
				'sys_id': '200001',
				'user_id': _userId,
				'token': _token
			};
			var param = {
				'pageSize': data.length, //页面显示记录条数，在页面显示每页显示多少项的时候
				'start': data.start, //开始的记录序号
				'pageIndex': (data.start / data.length) + 1, //当前页码
				'paramJson': encodeURI(JSON.stringify(PayAccountVO))
			};
			//param.pageSize = data.length; //页面显示记录条数，在页面显示每页显示多少项的时候
			//param.start = data.start; //开始的记录序号
			//param.pageIndex = (data.start / data.length) + 1; //当前页码

			//console.log(param);
			//ajax请求数据
			$.ajax({
				type: "POST",
				url: _BPAY_SERVICE_URL + "BankCardApplyByPage",
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
			if(data['verify_id'] == "0") {
				$('td', row).eq(0).html("<button type='button' class='btn btn-block btn-info btn-xs' onclick=CancelApply(\'" + data['id'] + "\')><i class='fa fa-reply'></i>&nbsp;取消</button>");
			} else {
				$('td', row).eq(0).html("");
			}

			//$('td', row).eq(3).html("¥" + formatFinance(data['credit'], 2, 1));
			//$('td', row).eq(4).html("¥" + formatFinance(data['banlance'], 2, 1));
			//$('td', row).eq(5).html('<span class="label ' + _ACCOUNT_STATUS_CSS[data['account_status']] + '">' + _ACCOUNT_STATUS[data['account_status']] + '</span>');

		},
		columns: [{
				"data": "verify_id",
				render: function(data, type, full, meta) {
					if(data != null) {
						/*	if(data == "0") {
								return " <button type='button' class='btn btn-block btn-info btn-xs' onclick=BackInfoBySn(\'" + _data.data[i].sn + "\')><i class='fa fa-reply'></i>&nbsp;取消</button>";
							} else {
								return "";
							}*/
						return data;
					} else {
						return "";
					}
				}
			}, {
				"data": "create_time"
			},
			{
				"data": "join_sn",
				render: function(data, type, full, meta) {
					if(data != null && data.length > 10) {
						return "<span class='label label-warning'>变更</span>";
					} else {
						return "<span class='label label-success'>新增</span>";
					}
				}
			},
			{
				"data": "bank_name",
				render: function(data, type, full, meta) {
					//alert(JSON.stringify(data));
					if(data != null) {
						return data;
					} else {
						return "";
					}
				}
			},
			{
				"data": "account_id"
			},
			{
				"data": "account_name"
			},
			{
				"data": "verify_id",
				render: function(data, type, full, meta) {
					if(data != null) {
						if(data == "0") {
							return "<span class='badge bg-orange'><i class='fa fa-sign-out'></i>&nbsp;待审核</span>";
						} else if(data == "1") {
							return "<span class='badge bg-olive'><i class='fa fa-check'></i>&nbsp;通&nbsp;&nbsp;&nbsp;&nbsp;过</span>";
						} else if(data == "2") {
							return "<span class='badge bg-maroon'><i class='fa fa-ban'></i>&nbsp;不通过</span>";
						} else if(data == "3") {
							return "<span class='badge bg-navy'><i class='fa fa-close'></i>&nbsp;作&nbsp;&nbsp;&nbsp;&nbsp;废</span>";
						} else {
							return data;
						}
					} else {
						return "";
					}
				}
			},
			{
				"data": "remark",
				render: function(data, type, full, meta) {
					//alert(JSON.stringify(data));
					if(data != null) {
						return data;
					} else {
						return "";
					}
				}
			}

		],
		language: _DATATABLES_LANG
	});

}

function CancelApply(id) {

	var accountParam = {
		company_code: _companyCode,
		payee_id: _payeeId,
		id: id,
		user_id: _userId,
		token: _token,
		sys_id: '200001'
	};

	var param = {
		'paramJson': JSON.stringify(accountParam)
	};

	BootstrapDialog.show({
		title: '系统提示',
		message: '是否取消变更申请？',
		buttons: [{
			label: '取消申请',
			cssClass: 'btn-danger',
			icon: 'fa fa-paper-plane',
			action: function(dialog) {
				// begin ajax
				$.ajax({
					type: "POST",
					url: _BPAY_SERVICE_URL + "CancelApply",
					cache: false, //禁用缓存
					data: param, //传入组装的参数
					dataType: "json",
					async: false,
					success: function(_data) {
						//alert(JSON.stringify(_data));
						if(_data.retcode == "0") {
							BootstrapDialog.show({
								type: BootstrapDialog.TYPE_SUCCESS,
								title: '系统提示',
								message: "成功取消",
								buttons: [{
									label: '关闭',
									cssClass: 'btn-primary',
									action: function(dialogItself) {
										dialogItself.close();
										dialog.close();
									}
								}]
							});

							table.ajax.reload();

						} else {

							var _msg = "";
							for(var i = 0; i < _data.data.length; i++) {
								_msg += _data.data[i] + "<br>";
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
				//end ajax
			}
		}, {
			label: '关闭',
			cssClass: 'btn-primary',
			icon: 'fa fa-close',
			action: function(dialog) {
				dialog.close();
			}
		}]
	});

}
//  ajax bootstrap waiting
//   https://bootsnipp.com/snippets/featured/quotwaiting-forquot-modal-dialog