//子页面不用iframe，用div展示
var closableTab = {
	//添加tab
	addTab: function(tabItem) { //tabItem = {id,name,url,closable}
		console.log(tabItem);
		var id = "tab_seed_" + tabItem.id;
		var container = "tab_container_" + tabItem.id;
		
		 
		var paramid= "tab_param_" + tabItem.id;
        //var paramJson=tabItem.param.toJSONString();

		$("li[id^=tab_seed_]").removeClass("active");
		$("div[id^=tab_container_]").removeClass("active");

		if(!$('#' + id)[0]) {
			var li_tab = '<li role="presentation" class="" id="' + id + '"><a href="#' + container + '" onclick="closableTab.activeTab(\'' + tabItem.id + '\')"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 2px 15px" title="' + tabItem.id + '">' + tabItem.name;
			if(tabItem.closable) {
				li_tab = li_tab + '<i class="glyphicon glyphicon-remove small" tabclose="' + id + '" style="position: absolute;right:4px;top: 4px;"  onclick="closableTab.closeTab(this)"></i></a></li> ';
			} else {
				li_tab = li_tab + '</a></li>';
			}

			var tabpanel = '<div role="tabpanel" class="tab-pane" id="' + container + '" style="width: 100%;">' +
				'正在加载...' +
				'</div><input type="hidden" id="'+paramid+'" />';

			$('.nav-tabs').append(li_tab);
			$('.tab-content').append(tabpanel);
			console.log("tabItem.url--->" + tabItem.url);
			
			//保存参数值
			$("#" + paramid).data("param",tabItem.param);
			
			
			$('#' + container).load(tabItem.url, tabItem.param, function(response, status, xhr) {
				console.log("开始加载............................");

				if(status == 'error') { //status的值为success和error，如果error则显示一个错误页面
					$(this).html(response);
				}
			});
			
			  console.log("tab保存参数是  -----》" + $("#" + paramid).val());
			
		}
		$("#" + id).addClass("active");
		$("#" + container).addClass("active");
	},

	//关闭tab
	closeTab: function(item) {

		var val = $(item).attr('tabclose');
		var containerId = "tab_container_" + val.substring(9);

		if($('#' + containerId).hasClass('active')) {
			var _obj = $('#' + val).prev().html();
			var _str = _obj.substring(_obj.indexOf("title=\""), _obj.length);
			_str = _str.substring(_str.indexOf("\"") + 1, _str.length);
			_str = _str.substring(0, _str.indexOf("\""));
			//this.isClose = false;
			closableTab.activeTab(_str);
			$('#' + val).prev().addClass('active');
			$('#' + containerId).prev().addClass('active');
		}

		$("#" + val).remove();
		$("#" + containerId).remove();

	},
	activeTab: function(item) {

		var id = "tab_seed_" + item;
		var container = "tab_container_" + item;

		//如果选项卡的title为空的话，说明已经移出，所以不能激活
		if($("#" + id).text().length > 0) {
			//begin 所有的选项卡移出激活状态
			$("li[id^=tab_seed_]").removeClass("active");
			$("div[id^=tab_container_]").removeClass("active");
			//end 所有的选项卡移出激活状态

			$("#" + id).addClass("active");
			$("#" + container).addClass("active");
		}
	},
	getTabParam: function(item) {
		//  获取选项卡中的参数对象。
        var paramid= "tab_param_" +  item;
        console.log("开始获取选项卡数据--->" + paramid);
		var id = "tab_seed_" + item;
		var container = "tab_container_" + item;
		if($("#" + id).text().length > 0) {
			 var paramJson = 	$("#" + paramid).data("param");
             //console.log("获取到选择卡id是  -----》" + paramJson.name);
             return paramJson;
		}
		return null;
	}
}