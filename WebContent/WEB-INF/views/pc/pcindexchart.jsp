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
		<link rel="stylesheet" href="${ctx}/static/public/chartslist.css" />
  
</head>
<body>
<div class="weui_cells" style="margin-top: 0px;border-top: 0px;">
	        <div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	                <p id="weekday">各省情况</p>
	            </div>
	            <div class="weui_cell_ft">
	            	
	          <select id="monthselect" style="width: 120px;border: solid 1px #80F1BE;border-radius:10px;padding-left: 5px;padding-right: 5px;background-color:#D0F1BE  ;width: 80px;direction: ltr;margin-right: 20px;"><option>一月</option>
	          	<option value="1">一月</option>
	          	<option value="2">二月</option>
	          	<option value="3">三月</option>
	          	<option value="4">四月</option>
	          	<option value="5">五月</option>
	          	<option value="6">六月</option>
	          	<option value="7">七月</option>
	          	<option value="8">八月</option>
	          	<option value="9">九月</option>
	          	<option value="10">十月</option>
	          	<option value="11">十一月</option>
	          	<option value="12">十二月</option>
	          </select>
	            </div>
	             <div class="weui_cell_ft">
	           <input class="weui_switch" type="checkbox" id="iscolor" onchange="setcolor(this)">
	            </div>
	        </div>
	    </div>
		<div id="main3" style="width: 100%;height:300px;border-bottom:1px solid #d9d9d9;"></div>
		<div id="list" class="list">
			<div class="weui_cells weui_cells_access" id="datalist">
				
			</div>

		</div>
</body>
 <script src="${ctx}/static/dingding/zepto.min.js"></script>
<script type="text/javascript">
//var deptid="${deptid}";
var authconfig=$.parseJSON('${authconfig}');
</script>
  <!-- 钉钉js -->
  <script src='http://g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js'></script>
<script src='${ctx}/static/public/PCpublic.js'></script>
<script type="text/javascript" src="${ctx}/static/public/echarts.min.js"></script>

	<script type="text/javascript" src="${ctx}/static/public/chartpaopao.js" ></script>
<script type="text/javascript">
var sdChart = echarts.init(document.getElementById('main3'));
DingTalkPC.ready(function(){
	initnav('${ctx}');
	$("#list").height($(window).height()-345);
	var nowtime=new Date();
	var nowmonth=nowtime.getMonth()+1;
	$("#monthselect").val(nowmonth);
	var time=getCurrentMonth(nowtime.getFullYear(),nowmonth-1);
	sdChart.setOption(sdoption);
	binddatalist();
	//获取数据
	binddata(time[0],time[1]);
	$('#monthselect').change(function(){ 
		var nowtime=new Date();
		var time=getCurrentMonth(nowtime.getFullYear(),$('#monthselect').val()-1);
		binddata(time[0],time[1]);
	});
});
DingTalkPC.error(function(err) {
	alert('sdfs');
	alert('dd error: ' + JSON.stringify(err));
});


//当页面ready的时候，执行回调:
Zepto(function($) { 
	
});
/**
 * 邦定图标数据
 */
function binddata(stime,etime){
	
	$.ajax({
  	  type: 'POST',
  	  url: '${ctx}/data/getpaopao',
  	  data: { 
  		  stime:stime ,
  		  etime:etime,
  		  pname:""
  	  },
  	  dataType: 'json',
  	  success: function(data){
  		  //[1, "上海", 97.77,34294523,"一"]
  		 var xAxisData = [];
		 var weekdata = [];
		 var yAxisData = [];
  		  //获取x轴
  		  $.each(data.userdatatwo,function(i,n){
  			  xAxisData.push(n.provincename);
  		  });
  		  //获取数据
			  $.each(data.userdata,function(i,n){
				  var dtime=getDatestr(n.datatime);
  			  var onedata=[n.provincename,dtime,n.collectionrate,n.installnum,n.rank];
  			  weekdata.push(onedata);
  			  if(yAxisData.indexOf(dtime)<0){
  				  yAxisData.push(dtime);
  			  }
  			  
  			  
  		  });
			 sdChart.setOption(getoption(xAxisData,yAxisData,weekdata));
  	  },
  	  error: function(xhr, type,error){
  	    alert('Ajax error!');
  	  }
  	});
}



/**
 * 邦定下部列表
 */
function binddatalist(){
	//getdatalist
	$.ajax({
	  	  type: 'POST',
	  	  url: '${ctx}/data/getdatalist',
	  	  data: { 
	  	  },
	  	  dataType: 'json',
	  	  success: function(data){
	  		  //获取数据
	  		  var datalist="";
				  $.each(data.userdata,function(i,n){
			  			datalist+="<a class=\"weui_cell\" onclick=gocitypage(\"${ctx}/data/pccitypage?pname="+encodeURI(n.provincename)+"&color=\") >";
			  			datalist+="<div class=\"weiui_cell_title\">";
			  			datalist+="<p style=\"line-height: 23px;\">"+n.provincename+"<br>"+getTimeymd(n.datatime)+"</p>";
		  				datalist+="</div>";
						datalist+="<div class=\"weiui_cell_content\">";
						datalist+="	安装量："+n.installnum+"    ";
						if(n.inscompare>=0){
							datalist+="<font color=\"red\" class=\"compare\" >"+n.inscompare+"</font>";
						}else{
							datalist+="<font color=\"green\" class=\"compare\" >"+n.inscompare+"</font>";
						}
						datalist+="<br>采集率："+n.collectionrate+"%    ";
						if(n.crcompare>=0){
							datalist+="<font color=\"red\" class=\"compare\" >"+n.crcompare+"</font>";
						}else{
							datalist+="<font color=\"green\" class=\"compare\" >"+n.crcompare+"</font>";
						}
						datalist+="</div>";
						datalist+="<div class=\"jiantou\"></div>";
						datalist+="</a>";
	  		  		});
				  document.getElementById("datalist").innerHTML=datalist;
	  	  },
	  	  error: function(xhr, type,error){
	  	    alert('Ajax error!');
	  	  }
	  	});
}
function gocitypage(url){
	location.href=url+document.getElementById("iscolor").checked;
}

function setcolor(obj){
	if(obj.checked==true){
		sdChart.setOption(yjoption);
		$(".weui_cell").css("background-color","#404a59");
		$(".weui_cell").css("color","#FFFFFF");
		document.body.style.backgroundColor="#404a59";
	}else{
		sdChart.setOption(rjoption);
		document.body.style.backgroundColor="#FFFFFF";
		$(".weui_cell").css("background-color","#FFFFFF");
		$(".weui_cell").css("color","#000000");
	}
}



var sdoption = {
		//backgroundColor: '#404a59',
		backgroundColor: '#fbf9fe',
		color: [
			'#2db7f5'
		],
		grid: {
			x: '13%',
			x2: '5%',
			y: '1%',
			y2: '10%'
		},
		tooltip: {
			padding: 5,
			backgroundColor: '#fff',
			borderColor: '#777',
			borderWidth: 1,
			textStyle:{
				color:'#000',
				fontSize:12,
			},
			formatter: function(obj) {
				var value = obj.value;
				return '<div style="border-bottom: 1px solid #777; font-size: 12px;padding-bottom: 2px;">'  + value[1] + value[0] + '：' + value[2] + '%</div>采集率：' + value[2] + '%<br>安装量：'+ value[3] + '<br>排名：第'+ value[4]
			}
		},
		xAxis: {
			splitLine: {
				show: false
			}
		},
		yAxis: {
			//name: '地区',
			//nameLocation: 'end',
			splitLine: {
				show: false
			}
		},
		visualMap: [{
			left: 'right',
			top: '10%',
			dimension: 2,
			textStyle: {
				color: '#fff'
			},
			min: 80.00,
			max: 100.00,
			itemWidth: 30,
			itemHeight: 120,
			show: false,
			calculable: true,
			precision: 0.01,
			text: ['圆形大小：采集率'],
			textGap: 30,
			inRange: {
				symbolSize: [1, 40],
				color: ['#2db7f5']
			}
		}],
		series: [{
			name: '前景无忧',
			type: 'scatter',
			itemStyle: itemStyle,
			label: {
				normal: {
					show: true,
					formatter: function(obj) {
						var value = obj.value;
						return value[2];
					},
					textStyle: {
						fontSize: 10,
						//泡泡字体颜色
						color: '#000'
					}
				},

			}
		}]
	};
	
	
function getoption(xAxisData,yAxisData,weekdata){
	var dataoption = {
			tooltip: {
				formatter: function(obj) {
					var value = obj.value;
					return '<div style="border-bottom: 1px solid #777; font-size: 12px;padding-bottom: 2px;">'  + value[1] + value[0] + '：' + value[2] + '%</div>采集率：' + value[2] + '%<br>安装量：'+ value[3] + '<br>排名：第'+ value[4]
				}
			},
			xAxis: {
				type: 'category',
				data: xAxisData
			},
			yAxis: {
				data: yAxisData,
				type: 'category'
			},
			series: [{
				data: weekdata
			}]
		};
	return dataoption;
}	

</script>
</html>