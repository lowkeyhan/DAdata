<%@ page language="java" contentType="text/html; charset=UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %> 

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<title></title>
		<link href="${ctx}/static/dingding/weui.min.css" rel="stylesheet" />
		<link href="${ctx}/static/public/lookview.css" rel="stylesheet" />
		<style type="text/css">
		.showdata{
			display:none;
			  overflow-x: scroll;
		}
		</style>
</head>
<body>
		<div id="main3" style="width: 100%;height:300px;"></div>
		<div id="list" class="list">
			
		</div>
</body>
 <script src="${ctx}/static/dingding/zepto.min.js"></script>
<script type="text/javascript">
//var deptid="${deptid}";
var authconfig=$.parseJSON('${authconfig}');
</script>
  <!-- 钉钉js -->
 <script src='${ctx}/static/dingding/dingtalk.js'></script>
<script src='${ctx}/static/public/public.js'></script>
<script type="text/javascript" src="${ctx}/static/public/echarts.min.js"></script>

	<script type="text/javascript" src="${ctx}/static/public/chartpaopao.js" ></script>
<script type="text/javascript">
var sdChart = echarts.init(document.getElementById('main3'));
var color="#000000";
var linecolor="#000";
var backgroundcolor="#fbf9fe";
var toolback="#fff";
var size=5;
dd.ready(function(){
	initnav('${ctx}');
	dd.biz.navigation.setLeft({
	    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
	    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
	    showIcon: true,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
	    text: '首页',//控制显示文本，空字符串表示显示默认文本
	    onSuccess : function(result) {
	        /*
	        {}
	        */
	        //如果control为true，则onSuccess将在发生按钮点击事件被回调
	    	location.href="${ctx}/data/mchartindex?dd_nav_bgcolor=FF30A8A5";
	    },
	   onFail : function(err) {}
	});
	
	$("#list").height($(window).height());
	setcolor("${color}");
	binddata(1,2);
});
dd.error(function(err) {
	alert('sdfs');
	alert('dd error: ' + JSON.stringify(err));
});


//当页面ready的时候，执行回调:
Zepto(function($) { 
	// sdChart.setOption(option);
	
	
});
/**
 * 邦定图标数据
 */
function binddata(stime,etime){
	var nowdata=new Date();
	var year=nowdata.getFullYear();
	$.ajax({
  	  type: 'POST',
  	  url: '${ctx}/data/getcitydata',
  	  data: { 
  		  stime:year+"/1/1" ,
  		  etime:year+"/12/31",
  		  pname:"${pname}"
  	  },
  	  dataType: 'json',
  	  success: function(data){
  		 var series = [];
  		 var lendata=[];
  		 var xAxisData=[];
  		 var yAxisData=[];
  		  $.each(data.userdatatwo,function(i,n){
  			  //图例数组
  			  lendata.push(n.provincename);
  			  //数据
  			  var weekdata = [];
  			 //获取数据
			  $.each(data.userdata,function(j,m){
				  var dtime=getDatestr(m.datatime);
				  //各个图例数据
				  if(n.provincename==m.cityname){
		  			  var onedata=[dtime,m.collectionrate,m.cityname,m.installnum,m.rank];
		  			  weekdata.push(onedata);
				  }
				  //x轴
				  if(xAxisData.indexOf(dtime)<0){
					  xAxisData.push(dtime);
	  			  }
  		  		});
  			 //所有数据
  			 weekdata.sort(function(a,b){
  				 			if(a[0]>b[0]){
  				 					return 1;
  				 				}else{
  				 					return -1;
  				 				}
            				
            				});
  			
  		  	 var oneseries={name: n.provincename,type: 'line',data: weekdata};
  		  	 series.push(oneseries);
  		  });
  		xAxisData.sort(function(a,b){
 			if(a>b){
 					return 1;
 				}else{
 					return -1;
 				}
			
			});
		sdChart.setOption(getoption(xAxisData,yAxisData,series,lendata));
		var citylist="";
		var len=lendata.length;
		for(var k=0;k<len;k++){
			citylist+="<div class=\"weui_cells weui_cells_access\" onclick=\"showdata('"+k+"','"+lendata[k]+"',"+len*65+")\" style=\"margin-top: 0px;border-top: 0px;\">";
			citylist+="<div class=\"weui_cell\">";
			citylist+="	<div class=\"weui_cell_bd weui_cell_primary\">";
			citylist+="		<p id=\"weekday\">"+lendata[k]+"</p>";
			citylist+="	</div>";
			citylist+="	<div class=\"weui_cell_ft\"></div>";
			citylist+="</div>";
			citylist+="</div>";
			citylist+="<div id=\""+k+"\" class=\"showdata approval-tline\" ></div>";
		}
		document.getElementById("list").innerHTML=citylist;
		setcolor("${color}");
  	  },
  	  error: function(xhr, type,error){
  	    alert('Ajax error!');
  	  }
  	});
}
function showdata(objid,cityname,height){
	if($("#"+objid).css("display")!="none"){
		$(".showdata").hide();
		return;
	}
	$(".showdata").hide();
	
	$("#"+objid).height($(window).height()-height);
	$("#"+objid).show();
	if($("#"+objid).html()==""){
		getrecord(objid,0,cityname,"${ctx}");
	}
	 
}


/*
 * 获取图标配置项
 */
function getoption(xAxisData,yAxisData,series,legenddata){
	var option = {
			backgroundColor: backgroundcolor,
		    tooltip: {
		        trigger: 'axis',
		        backgroundColor: toolback,
		        borderColor: '#777',
		        borderWidth: 1,
		        textStyle:{
					color:linecolor,
					fontSize:12,
				},
		    },
		    legend: {
		        data:legenddata,
		        textStyle:{
		        	color:color
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: xAxisData,
		        axisLine: {
		            lineStyle: {
		                color: linecolor
		            }
		        }
		    },
		    yAxis: {
		        type: 'value',
		        minInterval: 0.50,
		        min:60,
		        max:100,
		        axisLine: {
		            lineStyle: {
		                color: linecolor
		            }
		        }
		    },
		    dataZoom: [ {
		        start: 0,
		        end: 100,
		        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
		        handleSize: '80%',
		        handleStyle: {
		            color: '#fff',
		            shadowBlur: 3,
		            shadowColor: 'rgba(0, 0, 0, 0.6)',
		            shadowOffsetX: 2,
		            shadowOffsetY: 2
		        },
		        textStyle:{
		        	color:color
		        }
		    }],
		    series: series
		};
	
	return option;
}

function setcolor(obj){
	if(obj=="true"){
		linecolor="#fff";
		backgroundcolor="#404a59";
		toolback="#000";
		color="#FFFFFF";
		$(".weui_cell").css("background-color","#404a59");
		$(".weui_cell").css("color","#FFFFFF");
		document.body.style.backgroundColor="#404a59";
		$(".time-node").css("background-color","#404a59");
		$(".time-node").css("color","#FFFFFF");
	}
}

/*
 * 获得历史数据
 */
function getrecord(objid,page,cityname,ctx){
	$.ajax({
	  	  type: 'POST',
	  	  url: ctx+'/data/getcitydatalist',
	  	  data:{
	  		cityname:cityname,
	  		page:page,
	  		size:size
	  	  },
	  	  dataType: 'json',
	  	  success: function(data){
	  		var count=0;
	  			var listdept="";
	  		//listdept+="<div class=\"approval-tline\" >";
	  			if($("#morecard")){$("#morecard").remove();}
	  		 $.each(data.userdata,function(i,n){
	  			count+=1;
	  			listdept+="<div class=\"time-node\" >";
	  			listdept+="<div class=\"node-status\"><i class=\"weui_icon_info\" ></i></div>";
	  			listdept+="<div class=\"nodebox\" >";
	  			listdept+="	<div class=\"arrow\"></div>";
	  			listdept+="	<div class=\"nodebox-inner\">";
	  			listdept+="	<div class=\"node-avatar\">";
	  			listdept+=getnameimg(n.cityname);
	  			listdept+="</div>";
	  			listdept+="		<p class=\"username\">"+getTimeymd(n.datatime)+"</p>";
	  			listdept+="	<p class=\"result-line\">采集率:"+n.collectionrate+"%;安装量:"+n.installnum+";排名:"+n.rank+"</p>";
	  			if(n.shuoming!=null&&n.shuoming!=""){
	  				listdept+="	<p class=\"result-line\">"+n.shuoming+"</p>";
		  			
	  			}
	  			listdept+="		<div class=\"opreratetime\">"+getTime(n.operationdate)+"</div>";
	  			listdept+="	</div>";
	  			listdept+="</div>";
	  			listdept+="</div>";
	          });
	  		 if(count==size){
	  			listdept+="<div class=\"time-node\" id=\"morecard\" onclick=\"getrecord('"+objid+"',"+page+1+",'"+cityname+"','"+ctx+"')\" >";
	  			listdept+="<div class=\"node-status\"><i class=\"weui_icon_info\" ></i></div>";
	  			listdept+="<div class=\"nodebox\" >";
	  			listdept+="	<div class=\"arrow\"></div>";
	  			listdept+="	<div class=\"nodebox-inner\">";
	  			listdept+="	<div class=\"node-avatar\">";
	  			listdept+=getnameimg(cityname);
	  			listdept+="</div>";
	  			listdept+="		<p class=\"username\">点击加载更多</p>";
	  			listdept+="	<p class=\"result-line\"></p>";
	  			listdept+="		<div class=\"opreratetime\"></div>";
	  			listdept+="	</div>";
	  			listdept+="</div>";
	  			listdept+="</div>";
	  		 }
	  		//listdept+="</div>";
	         $("#"+objid).append(listdept);
	  	  },
	  	  error: function(xhr, type,error){
	  	    alert('Ajax error!');
	  	  }
	  });
}

</script>
</html>