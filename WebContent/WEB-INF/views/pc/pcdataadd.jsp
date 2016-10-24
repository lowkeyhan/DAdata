<%@ page language="java" contentType="text/html; charset=UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %> 

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta charset="UTF-8">
    <title></title>
  
    <link href="${ctx}/static/dingding/weui.min.css" rel="stylesheet" />
    <script src="${ctx}/static/dingding/zepto.min.js"></script>
    <link href="${ctx}/static/public/lookview.css" rel="stylesheet" />
    <style>
         body {
            background-color: #f0eff4;
        }

        .weui_media_box {
            display: block;
        }

        .weui_media_desc {
            color: #000000 !important;
        }
        .zhuguan{
		   color: white;
		   background-color: #5cb85c;
		   border-radius: 4px;
		   padding-left: 5px;
		   padding-right: 5px;
		}
		.list{
			overflow-x: scroll;
		}
		.weui_cell_select .ssss:after {
    content: " ";
    display: inline-block;
    -webkit-transform: rotate(45deg);
    transform: rotate(45deg);
    height: 6px;
    width: 6px;
    border-width: 2px 2px 0 0;
    border-color: #c8c8cd;
    border-style: solid;
    position: relative;
    top: -2px;
    position: absolute;
    top: 50%;
    margin-top: -3px;
        right: 43%;

}
.container{  
    position:relative;  
}  
.left_side{  
    position:absolute;  
    top:0px;  
    left:0px; 
      width:50px;  
}  
.right_side{  
    position:absolute;  
    top:0px;  
    right:0px;   
    width:50px;
}  
.row{
    padding: 5px 5px;
}
    </style>
</head>
<body>
<div class="weui_cells" style="margin-top: 0px;border-top: 0px;">
	      
<div class="weui_cell row">
       <div class="weui_cell_bd weui_cell_primary container" > 
          <a href="javascript: goweek('last');"   class="weui_btn weui_btn_mini weui_btn_default left_side">&lt;</a> 
          <div class="content" ><p style=" text-align: center;    line-height: 25px;" id="weekdata"></p></div>
          <a href="javascript:goweek('next');"  class="weui_btn weui_btn_mini weui_btn_default right_side">&gt;</a>
       </div>
</div>
</div>
 <form id="dataform"  method="POST">
      <input type="hidden" id="oper" name="oper" value="${oper}"/>
     <input type="hidden" id="datatime" name="datatime" value="${weekdata.datatime}"/>
     <input type="hidden" id="id" name="id" value="${weekdata.id}"/>
     <input type="hidden" id="weeknum" name="weeknum" value="${weekdata.weeknum}"/>
     <div class="weui_cells weui_cells_form" style="margin-top:0px">
          <div class="weui_cell weui_cell_select weui_select_after">
              <div class="weui_cell_hd">
                  <label for="" class="weui_label">选择地区</label>
              </div>
              <div class="ssss  weui_cell_primary">
                  <select class="weui_select " name="provincename" id="province">
                     
                  </select>
               </div>
              <div class="weui_cell_bd weui_cell_primary">   
                   <select class="weui_select" name="cityname" id="city" >
                   <option value="" >选择地区</option>
                  </select>
              </div>
          </div>
          <div class="weui_cell weui_cell_select weui_select_after" style="display:none">
              <div class="weui_cell_hd">
                  <label for="" class="weui_label">选择公司</label>
              </div>
              <div class="weui_cell_bd weui_cell_primary">
                   <select class="weui_select" name="companyname" id="company">
                   <option value="前景无忧" >前景无忧</option>
                    <option value="其他公司" >其他公司</option>
                  </select>
              </div>
          </div>
      </div>
      <div class="weui_cells weui_cells_form">
          <div class="weui_cell">
              <div class="weui_cell_hd"><label class="weui_label">采集率%</label></div>
              <div class="weui_cell_bd weui_cell_primary">
                  <input class="weui_input" id="collectionrate"  name="collectionrate" style="text-align:right "
                         type="number" value='${weekdata.collectionrate}'>
              </div>
          </div>
          <div class="weui_cell">
              <div class="weui_cell_hd"><label class="weui_label">上周对比</label></div>
              <div class="weui_cell_bd weui_cell_primary">
                  <input class="weui_input" id="crcompare"  name="crcompare" style="text-align:right "
                         type="number" value='${weekdata.crcompare}'>
              </div>
          </div>
           </div>
           <div class="weui_cells weui_cells_form">
      <div class="weui_cell">
              <div class="weui_cell_hd"><label class="weui_label">安装数量</label></div>
              <div class="weui_cell_bd weui_cell_primary">
             <input class="weui_input" id="installnum"  name="installnum" style="text-align:right "
                         type="number" value='${weekdata.installnum}'>
              </div>
          </div>
          <div class="weui_cell">
              <div class="weui_cell_hd"><label class="weui_label">上周对比</label></div>
              <div class="weui_cell_bd weui_cell_primary">
                  <input class="weui_input" id="inscompare"  name="inscompare" style="text-align:right "
                         type="number" value='${weekdata.inscompare}'>
              </div>
          </div>
      </div>
      <div class="weui_cells weui_cells_form">
          <div class="weui_cell weui_cell_select weui_select_after">
              <div class="weui_cell_hd">
                  <label for="" class="weui_label">数据排名</label>
              </div>
              <div class="weui_cell_bd weui_cell_primary">
                   <select class="weui_select" name="rank" id="rank">
                    <option value="1" >1</option>
                    <option value="2" >2</option>
                     <option value="3" >3</option>
                  </select>
              </div>
          </div>
            <div class="weui_cell">
              <div class="weui_cell_hd"><label class="weui_label">数据备注</label></div>
              <div class="weui_cell_bd weui_cell_primary">
                  <input class="weui_input" id="shuoming"  name="shuoming" 
                          value='${weekdata.shuoming}'>
              </div>
          </div>
      </div>
  </form>
  <div  class="list" id="allrecord" class="approval-tline-box">

</div>
 <div id="btnlist">
<div  class="approve-foot show">
<div class="tFlexbox tAlignCenter tJustifyCenter" id="btnadd" >
	  	<div class="tFlex1 approval-action tTap agree"  onclick="submitform()" >保存</div>
	  	  	
</div>
</div>
 </div>  
</body>
<script type="text/javascript">
//var deptid="${deptid}";
var authconfig=$.parseJSON('${authconfig}');
</script>
  <!-- 钉钉js -->
 <script src='http://g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js'></script>
<script src='${ctx}/static/public/PCpublic.js'></script>
<script src='${ctx}/static/public/dateutil.js'></script>
<script type="text/javascript">

DingTalkPC.ready(function(){
	initnav('${ctx}');
	$("#allrecord").height($(window).height()-440);
	var s=new Date();
	var daystr=s.setDate(s.getDate()-7);
	var data=getWeekFristAndLastData(daystr);
	var weeknum=getWeekNumber(daystr);
	$("#weekdata").html("第"+weeknum+ "周("+data[0]+"-"+data[1]+")");
	$("#weeknum").val(weeknum);
	$("#datatime").val(data[1]);
	getprovince('${ctx}','',$("#province"),'0');
	$('#province').change(function(){ 
		getprovince('${ctx}','',$("#city"),$('#province').val());
	});
	//获得授权登陆code
	DingTalkPC.runtime.permission.requestAuthCode({
	    corpId: authconfig.corpId,
	    onSuccess: function(result) {
	    $.ajax({
	    	  type: 'POST',
	    	  url: '${ctx}/data/login',
	    	  data: { code: result.code,
	    		  datatime:data[1]},
	    	  dataType: 'json',
	    	  success: function(data){
	    		  getrecord("allrecord",data.userdata);
	    	  },
	    	  error: function(xhr, type,error){
	    	    alert('Ajax error!');
	    	  }
	    	});
	    },
	    onFail : function(err) {}

	});
	
	
	
	
});
DingTalkPC.error(function(err) {
	alert('sdfs');
	alert('dd error: ' + JSON.stringify(err));
});

/**
 * go  next:下一周;last:上一周
 */
function goweek(go){
	var s=new Date($("#datatime").val());
	var daystr;
	if(go=="next"){
		daystr=s.setDate(s.getDate()+7);
	}else{
		daystr=s.setDate(s.getDate()-7);
	}
	
	var data=getWeekFristAndLastData(daystr);
	var weeknum=getWeekNumber(daystr);
	$("#weekdata").html("第"+weeknum+ "周("+data[0]+"-"+data[1]+")");
	$("#weeknum").val(weeknum);
	$("#datatime").val(data[1]);
	 $.ajax({
   	  type: 'POST',
   	  url: '${ctx}/data/login',
   	  data: { 
   		  datatime:$("#datatime").val()},
   	  dataType: 'json',
   	  success: function(data){
   		  getrecord("allrecord",data.userdata);
   	  },
   	  error: function(xhr, type,error){
   	    alert('Ajax error!');
   	  }
   	});
}


function getprovince(ctx,selectid,selectobj,pid){
	$.ajax({
	  	  type: 'POST',
	  	  url: ctx+'/province/getprovince',
	  	  data:{
	  		  pid:pid,
	  	  },
	  	  dataType: 'json',
	  	  success: function(data){
	  		 var selectstr="<option value=\"\" >选择地区</option>";
	  		  if(pid=="0"){
	  			selectstr="<option value=\"选择省份\" >选择省份</option>";
	  		  }
	  		 
	  		 $.each(data.userdata,function(i,n){
	  			 if(n.provincename==selectid){
	  				selectstr+="<option value=\""+n.provincename+"\"  selected='selected' >"+n.provincename+"</option>";
	  			 }else{
	  				selectstr+="<option value=\""+n.provincename+"\"  >"+n.provincename+"</option>";
	  			 }
	          });
	  		selectobj.html(selectstr);
	  	  },
	  	  error: function(xhr, type,error){
	  	    alert('Ajax error!');
	  	  }
	  });	
}


//
function submitform(){
	//$("#collectionrate").val(0);
	 // $("#installnum").val(0);
	 if($("#collectionrate").val()==""||$("#collectionrate").val()=="0"){
		 opentoast("请填写采集率","error");
		 return;
	 }
	 if($("#installnum").val()==""||$("#installnum").val()=="0"){
		 opentoast("请填写安装量","error");
		 return;
	 }
	 if($("#province").val()=="选择省份"){
		 opentoast("请选择省份","error");
		 return;
	 }
	$.ajax({
  	  type: 'POST',
  	  url: '${ctx}/data/add',
  	  data: $("#dataform").serialize(),
  	  dataType: 'json',
  	  success: function(data){
  		opentoast(data.message,"success");    
  	  //location.href="${ctx}/plan/planedit?id=${plan.planid}";
  	  	 clearinput();
  		 $.ajax({
	    	  type: 'POST',
	    	  url: '${ctx}/data/login',
	    	  data: { 
	    		  datatime:$("#datatime").val()},
	    	  dataType: 'json',
	    	  success: function(data){
	    		  getrecord("allrecord",data.userdata);
	    	  },
	    	  error: function(xhr, type,error){
	    	    alert('Ajax error!');
	    	  }
	    	});
  	  },
  	  error: function(xhr, type,error){
  	    alert('Ajax error!');
  	  }
  	});
}
function editdata(id){
	$.ajax({
  	  type: 'POST',
  	  url: '${ctx}/data/getonedata',
  	  data: { 
  		  id:id},
  	  dataType: 'json',
  	  success: function(data){
  		  var onedata=data.userdata;
  		var s=new Date(onedata.datatime);
  		var data=getWeekFristAndLastData(s);
  		var weeknum=getWeekNumber(s);
  		$("#weekdata").html("第"+weeknum+ "周("+data[0]+"-"+data[1]+")");
	  	$("#oper").val("edit");
	  	$("#datatime").val(data[1]);
	  	$("#weeknum").val(onedata.weeknum);
	  	$("#id").val(onedata.id);
	  	$("#province").val(onedata.provincename);
	  	getprovince('${ctx}',onedata.cityname,$("#city"),onedata.provincename);
	  	$("#company").val(onedata.companyname);
	  	$("#collectionrate").val(onedata.collectionrate);
	  	$("#crcompare").val(onedata.crcompare);
	  	$("#installnum").val(onedata.installnum);
	  	$("#inscompare").val(onedata.inscompare);
	  	$("#rank").val(onedata.rank);
	  	$("#shuoming").val(onedata.shuoming);
	  	if($('.del').length==0){
	  	 $('#btnadd').append("<div class=\"tFlex1 approval-action tTap del\"  onclick=\"deldata('"+onedata.id+"')\" >删除</div>"); 
	  	};
	  	},
  	  error: function(xhr, type,error){
  	    alert('Ajax error!');
  	  }
  	});
}
function clearinput(){
	$("#oper").val("add");
  	$("#id").val("");;
  	$("#collectionrate").val("");
  	$("#crcompare").val("");
  	$("#installnum").val("");
  	$("#inscompare").val("");
  	$("#shuoming").val("");
  	if($('.del').length>0){
  		$('.del').remove();
  	}
  	
}
function deldata(id){
	$.ajax({
	  	  type: 'POST',
	  	  url: '${ctx}/data/delonedata',
	  	  data: { 
	  		  id:id},
	  	  dataType: 'json',
	  	  success: function(data){
	  		opentoast(data.userdata,"success");   
	  		clearinput();
	  		$.ajax({
		    	  type: 'POST',
		    	  url: '${ctx}/data/login',
		    	  data: { 
		    		  datatime:$("#datatime").val()},
		    	  dataType: 'json',
		    	  success: function(data){
		    		  getrecord("allrecord",data.userdata);
		    	  },
		    	  error: function(xhr, type,error){
		    	    alert('Ajax error!');
		    	  }
		    	});
	  	  },
	  	  error: function(xhr, type,error){
	  	    alert('Ajax error!');
	  	  }
	  	});
}
</script>
</html>