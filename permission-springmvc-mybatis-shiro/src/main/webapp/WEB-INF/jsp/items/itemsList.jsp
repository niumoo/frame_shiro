<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
</head>

<script type="text/javascript">
	function queryItems(){
		document.itemsForm.action="${pageContext.request.contextPath }/items/queryItems.action";
		document.itemsForm.submit();
	}
	function deleteItems(){
		document.itemsForm.action="${pageContext.request.contextPath }/items/deleteItems.action";
		document.itemsForm.submit();
	}

</script>
<body>
	<form name="itemsForm"
		action="${pageContext.request.contextPath }/items/queryItems.action"
		method="post">
		查询条件：
		<table width="100%" border=1>
			<tr>
				<td>商品名称：<input type="text" name="itemsCustom.name"></td>
				<td><input type="button" onclick="queryItems()" value="查询" />
					<input type="button" onclick="deleteItems()" value="删除" /></td>
			</tr>
		</table>
		商品列表：
		<table width="100%" border=1>
			<tr>
				<td>选择</td>
				<td>商品名称</td>
				<td>商品价格</td>
				<td>生产日期</td>
				<td>商品描述</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${itemsList }" var="item">
				<tr>
					<td><input type="checkbox" name = "itemsId" value="${item.id }"></td>
					<td>${item.name}</td>
					<td>${item.price }</td>
					<td><fmt:formatDate value="${item.createtime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${item.detail }</td>

					<td>
					<!-- shiro权限管理有item:update权限的时候才显示修改链接，没有则不显示 -->
					<shiro:hasPermission name="item:update">
						<a href="${pageContext.request.contextPath }/items/editItems.action?id=${item.id}">修改</a>
					</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>

		</table>
	</form>
</body>

</html>