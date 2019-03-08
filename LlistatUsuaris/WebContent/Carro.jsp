<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Llista usuaris</title>
</head>
<body>
	<h2 style="color: green">
		Prova checkboxes...
	</h2>
	<s:form action="checkBoxArrayAction" namespace="/" method="post">
		<table cellspacing="0" cellpadding="0" border="1" width="500">
		
			<tr style="font-weight: bold">
				<td><s:checkbox name="Select All" id="select_all" theme="simple" /></td>
				<td><s:text name="nom" /></td>
				<td><s:text name="dni" /></td>
			</tr>
			
			<s:iterator value="userList" status="stat">
				<tr>
					<td><s:checkbox name="checkboxes[%{#stat.index}]" theme="simple" /></td>  	
					<td><s:property value="nom" /></td>
					<td><s:property value="dni" /></td>
					<s:hidden value="%{userList.nom}"/>
				</tr>
			</s:iterator>
		</table>
		</br>
		<s:submit align="left" />
	</s:form>
</body>
</html>