$(function() {
	if(typeof($.cookie('menusf')) == "undefined"){
		$.cookie('menusf', 'ok');
		$("#sidebar").attr("class","");
	}else{
		$("#sidebar").attr("class","");
	}
});

//保存缩放菜单状态
function menusf(){
	if(document.getElementsByName('menusf')[0].checked){
		$.cookie('menusf', '', { expires: -1 });
		$("#sidebar").attr("class","menu-min");
	}else{
		$.cookie('menusf', 'ok');
		$("#sidebar").attr("class","");
	}
}
