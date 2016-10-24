
DingTalkPC.config({
		agentId : authconfig.agentid,
		corpId : authconfig.corpId,
		timeStamp : authconfig.timeStamp,
		nonceStr : authconfig.nonceStr,
		signature : authconfig.signature,
		jsApiList : [ 'runtime.info', 'runtime.permission.requestAuthCode','biz.contact.choose',
						'device.notification.confirm', 'device.notification.alert',
						'device.notification.prompt', 'biz.ding.post','biz.util.uploadImage',
						'biz.util.openLink','biz.navigation.setRight','biz.navigation.setLeft',
						'device.notification.showPreloader','device.notification.hidePreloader','biz.navigation.close' ]
});

function gotourl(url){
	location.href=url;
}

function initnav(ctx){
	
	//左侧标题 
	
	//dd.biz.navigation.setLeft({
	//    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
	//    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
	//    showIcon: true,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
	//    text: '返回',//控制显示文本，空字符串表示显示默认文本
	//    onSuccess : function(result) {
	        /*
	        {}
	        */
	        //如果control为true，则onSuccess将在发生按钮点击事件被回调
	 //   	location.href=ctx+"/data/mindex";
	 //   },
	//    onFail : function(err) {}
	//});
	
	
}

/***
 * 获得本月的起止时间
 */
function getCurrentMonth(year,month) {
     //起止日期数组  
     var startStop = new Array();
     //获取当前时间  
     var currentDate = new Date(year,month,1);
     //获得当前月份0-11  
     var currentMonth = currentDate.getMonth();
     //获得当前年份4位年  
     var currentYear = currentDate.getFullYear();
     //求出本月第一天  
     var firstDay = new Date(currentYear, currentMonth, 1);


     //当为12月的时候年份需要加1  
     //月份需要更新为0 也就是下一年的第一个月  
     if (currentMonth == 11) {
         currentYear++;
         currentMonth = 0; //就为  
     } else {
         //否则只是月份增加,以便求的下一月的第一天  
         currentMonth++;
     }


     //一天的毫秒数  
     var millisecond = 1000 * 60 * 60 * 24;
     //下月的第一天  
     var nextMonthDayOne = new Date(currentYear, currentMonth, 1);
     //求出本月的最后一天  
     //var lastDay = new Date(nextMonthDayOne.getTime() - millisecond);

     //添加至数组中返回  
     startStop.push(firstDay);
     startStop.push(nextMonthDayOne);
     //返回  
     return startStop;
 };

/**弹出toast
 * @param message 信息
 * @param type 类型 success, error
 */
function opentoast(message,type){
	DingTalkPC.device.notification.toast({
	    type: type, //toast的类型 alert, success, error, warning, information, confirm
	    text: message, //提示信息
	    duration: 3, //显示持续时间，单位秒，最短2秒，最长5秒
	    delay: 0, //延迟显示，单位秒，默认0, 最大限制为10
	    onSuccess : function(result) {
	        /*{}*/
	    },
	    onFail : function(err) {}
	});
}


function getnameimg(username){
	var nameimg=username;
	if(username.length>2){
		nameimg=username.substr(username.length-2,2);
	}
	return nameimg;
}

function getTime(ts) {    
    var t,y,m,d,h,i,s;  
    t = ts ? new Date(ts) : new Date();  
    y = t.getFullYear();  
    m = t.getMonth()+1;  
    d = t.getDate();  
    h = t.getHours();  
    i = t.getMinutes();  
    s = t.getSeconds();  
    // 可根据需要在这里定义时间格式  
    return y+'-'+(m<10?'0'+m:m)+'-'+(d<10?'0'+d:d)+' '+(h<10?'0'+h:h)+':'+(i<10?'0'+i:i)+':'+(s<10?'0'+s:s);  
}
function getTimeymd(ts) {    
    var t,y,m,d,h,i,s;  
    t = ts ? new Date(ts) : new Date();  
    y = t.getFullYear();  
    m = t.getMonth()+1;  
    d = t.getDate();  
    h = t.getHours();  
    i = t.getMinutes();  
    s = t.getSeconds();  
    // 可根据需要在这里定义时间格式  
    return y+'-'+(m<10?'0'+m:m)+'-'+(d<10?'0'+d:d);  
}
//MMdd
function getDatestr(ts) {    
    var t,y,m,d,h,i,s;  
    t = ts ? new Date(ts) : new Date();  
    y = t.getFullYear();  
    m = t.getMonth()+1;  
    d = t.getDate();  
    h = t.getHours();  
    i = t.getMinutes();  
    s = t.getSeconds();  
    // 可根据需要在这里定义时间格式  
    return (m<10?'0'+m:m)+'/'+(d<10?'0'+d:d);  
}
//获得时间线
function getrecord(objid,userdata){
	
	  		
		var listdept="";
	listdept+="<div class=\"approval-tline\" >";
	 $.each(userdata,function(i,n){
		listdept+="<div class=\"time-node\" onclick=\"editdata('"+n.id+"')\" >";
		listdept+="<div class=\"node-status\"><i class=\"weui_icon_info\" ></i></div>";
		listdept+="<div class=\"nodebox\" >";
		listdept+="	<div class=\"arrow\"></div>";
		listdept+="	<div class=\"nodebox-inner\">";
		listdept+="	<div class=\"node-avatar\">";
		listdept+=getnameimg(n.operationer);
		listdept+="</div>";
		listdept+="		<p class=\"username\">"+n.provincename+n.cityname+"</p>";
			listdept+="		<p class=\"result-line\">"+n.companyname+"：采集率"+n.collectionrate+"%；安装量"+n.installnum+"</p>";
			if(n.shuoming!=null&&n.shuoming!=""){
  				listdept+="	<p class=\"result-line\">"+n.shuoming+"</p>";
	  			
  			}
			listdept+="		<div class=\"opreratetime\">"+getTime(n.operationdate)+"</div>";
		listdept+="	</div>";
		listdept+="</div>";
		listdept+="</div>";
      });
	listdept+="</div>";
     document.getElementById(objid).innerHTML=listdept;
	  	  
}