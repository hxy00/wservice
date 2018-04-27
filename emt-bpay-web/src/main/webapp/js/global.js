/**
 * Created by dsj on 2017/3/27.
 */
//清分服务URL
var _BPAY_SERVICE_URL =  "https://emt-gateway.cmaotai.com/bpay/";  // "http://127.0.0.1:9900/bpay/";  // "http://121.40.223.116:9900/bpay/";  //

//DataTabables国际化
var _DATATABLES_LANG = {
	"sProcessing": "处理中...",
	"sLengthMenu": "每页 _MENU_ 项",
	"sZeroRecords": "没有匹配结果",
	"sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
	"sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
	"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	"sInfoPostFix": "",
	"sSearch": "搜索:",
	"sUrl": "",
    "sZeroRecords": "抱歉， 没有找到",
    "sInfoEmpty": "没有数据",
	"sLoadingRecords": "载入中...",
	"sInfoThousands": ",",
	"oPaginate": {
		"sFirst": "首页",
		"sPrevious": "上页",
		"sNext": "下页",
		"sLast": "末页",
		"sJump": "跳转"
	},
	"oAria": {
		"sSortAscending": ": 以升序排列此列",
		"sSortDescending": ": 以降序排列此列"
	}
};

//帐户状态
var _ACCOUNT_STATUS = ['待激活','已激活','已冻结','已注销'];
//帐户状态CSS
var _ACCOUNT_STATUS_CSS = ['label-primary','label-success','label-danger','label-warning'];