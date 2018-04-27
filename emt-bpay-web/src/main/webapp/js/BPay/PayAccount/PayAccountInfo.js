$(function() {
  
/*    var str = location.href;
    alert(str);
*/
 console.log("详细页开始加载" + document.URL);
  
  var obj = closableTab.getTabParam('payAccountInfo');
  console.log("详细页payee_id ------>", obj.payee_id); 
   
	$('input').iCheck({
		checkboxClass: 'icheckbox_square',
		radioClass: 'iradio_square-red' 
	});
})

 

 