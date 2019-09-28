<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	<title>Basic Window - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	</head>
	<body>
		<div class="easyui-accordion" style="width: 1066px; height: auto;">
			<div title="操作栏" data-options="" style="overflow: auto; padding: 10px;">
				<ul class="easyui-tree">
					<div style="margin: 10px 0;"></div>
					user：<input required="true" id="SearchUserName">&nbsp;
					passworld：<input required="true" id="SearchSex">&nbsp;
					<a href="#" class="easyui-linkbutton" id="search">查询</a>&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" id="add">新增</a>&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" id="delete">删除</a>&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" id="edit">编辑</a>&nbsp;&nbsp;
				</ul>
			</div>
		</div>
		
		
		<div id="CommonWin">
			<form id="from1">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td><input type="hidden" name="id" id="id" /></td>
					</tr>
					<tr>
						<td>学号：<input type="text" name="stu_id" id="stu_id"></td>
					</tr>
					<tr>
						<td>科目id<input type="text" name="course_id" id="course_id"></td>
					</tr>
					<tr>
						<td>成绩：<input type="text" name="score" id="score"></td>
					</tr>
					<tr>
						<td><input type="button" value="保存" onclick="saveOrEdit()" /></td>
						<td><input type="reset" value="重置"/></td>
					</tr>
				</table>
			</form>
		</div>
		
		
	
		<div id="CommonWin2">
			<form id="from1">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td>查询学号：<input type="text" name="stu_id" id="searchId"></td>
					</tr>
					<tr>
						<td><input type="button" value="查询" onclick="searchStu()" /></td>
						<td><input type="reset" value="重置"/></td>
					</tr>
					<tr>
						<td>学号：<input type="text" name="stu_id" id="stu_id2"></td>
					</tr>
					<tr>
						<td>科目id<input type="text" name="course_id" id="course_id2"></td>
					</tr>
					<tr>
						<td>成绩：<input type="text" name="score" id="score2"></td>
					</tr>
				</table>
		
			</form>
			
		</div>
		//
		<table id="dg">
		
		</table>
		
	
		
<script type="text/javascript">
			var isEdit = true;
			$(function() {
				//初始化 函数
				$('#dg').datagrid({
					url: 'servlet/StuServlet?op=queryPage', 
					method: 'post',
					iconCls: 'icon-save',
					fitColumns: true,
					singleSelect: true,
						rownumbers: true ,
						collapsible:true,
						
						 pagination:true,
					pageNumber:1,pageSize:7,pageList:[7],
	toolbar:'',
					columns: [[
							{field:'StuId',title:'学生ID',width:80},
							{field:'CourseId',title:'科目ID',width:120},
							{field:'Score',title:'成绩',width:80}
							
						]],
					width: 1066,

					rowStyler: function(index, row) {
						if(index % 2 == 0) {
							return 'background-color:#808080;';
						}
					},
					striped: true
					
				});
				
			
				$("#CommonWin,#CommonWin2").window({
					width:650,
					height:330,
					modal:true,
					minimizable: false,
					maximizable: "false",
					droppables: false,
					resizable:false,
					collapsible:false,
					title:'学生信息',
					left:250,
					top:150
				}).window("close");
				
				
				
				$("#add").click( function(){
					isEdit = false;
					$("#CommonWin").window("open");
				});
				
				
				//修改功能
				$('#edit').click(function(){
					isEdit = true;
					
					var $selectRow = $("#dg").datagrid("getSelected"); 
					if($selectRow){
						
						$.get('servlet/StuServlet?op=queryStuById&StuId='+ $selectRow.StuId,
							function(gdData){
							
								var jpent = JSON.parse(gdData);
		
								$("#stu_id").val(jpent.StuId);
								$("#course_id").val(jpent.CourseId);
								$("#score").val(jpent.Score);
								
							});
						
						$("#CommonWin").window("open");
					}else{
						//提示 你为选中修改的 数据
						$.messager.alert('提示操作人员','请你先选择要操作的行..','goodsInfo');
					}
					
				});
				
				
				
				
			});
			//通过参数 判读 执行那个方法
			function saveOrEdit(){
				if(isEdit){
						saveEdit();
				}else{
						saveAdd();
				}
			}
			
			//编辑保存方法
				function saveEdit(){
					//将表单数据 序列化
				 	var formData = $("#from1").serialize();
				 	//传递 到后台 保存数据：使用 ajax操作
				 	//$.post('保存地址','参数',回调函数());
				 	$.post('servlet/StuServlet?op=saveOrUpdate',formData,function(){
				 		//关闭弹出层
				 		//显示出来
						$("#CommonWin").window("close");
				 		//列表数据 刷新
				 		$("#dg").datagrid("reload");
				 	});
				}
				
				//添加保存方法
				function saveAdd(){
				//将表单数据 序列化
				 	var formData = $("#from1").serialize();
				 	//传递 到后台 保存数据：使用 ajax操作
				 	//$.post('保存地址','参数',回调函数());
				 	$.post('servlet/StuServlet?op=saveOrUpdate',formData,function(){
				 		//关闭弹出层
				 		//显示出来
						$("#CommonWin").window("close");
				 		//列表数据 刷新
				 		$("#dg").datagrid("reload");
				 	});
				}
				
				$("#delete").click(function(){
						var $selectRow = $("#dg").datagrid("getSelected"); 
							
							if($selectRow){
							$.get('servlet/StuServlet?op=deleteRow&StuId='+ $selectRow.StuId);
							$("#dg").datagrid("reload");	
					}else{
					alert('你未选中行！！')}	
				})
				
				$("#search").click(function(){
					$('#CommonWin2').window("open");
				});
				function searchStu(){
				var v= $('#searchId').val();
				$.get('servlet/StuServlet?op=searchStu&StuId='+ v,function(gdData){
				var jpent = JSON.parse(gdData);
		
								$("#stu_id2").val(jpent.StuId);
								$("#course_id2").val(jpent.CourseId);
								$("#score2").val(jpent.Score);
				$("#CommonWin2").datagrid("reload");
				});
				
					
				};
		</script>
	</body>
</html>
