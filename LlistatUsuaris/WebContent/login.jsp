<%@page import="cat.almata.daw.llistatUsuaris.Constants"%>
<%@page import="java.util.Hashtable"%>
<%@page import="cat.almata.daw.llistatUsuaris.Usuari"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%
		Hashtable<String, Usuari> llistaUsuaris =(Hashtable<String,Usuari>)application.getAttribute(Constants.sessioLista);
		
		if(llistaUsuaris==null || llistaUsuaris.isEmpty() ){
			llistaUsuaris = new Hashtable<String,Usuari>();
			Usuari usuari = new Usuari("dani" , "dani","1234");
			llistaUsuaris.put( usuari.getPass() , usuari);
			application.setAttribute(Constants.sessioLista, llistaUsuaris);
		}
	%>
	<s:actionerror/>
	<s:form action="login.action" method="post">
		<s:textfield key="login.nom" name="usuari.login"/>
	  	<s:password key="login.login" name="usuari.pass"/>
		<s:submit name="submit" key="login.submit" align="center" />
	</s:form>
</body>
</html>