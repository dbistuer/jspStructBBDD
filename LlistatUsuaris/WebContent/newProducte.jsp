<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="cat.almata.daw.llistatUsuaris.Producte"%>
<%@page import="cat.almata.daw.llistatUsuaris.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nou usuari</title>
</head>
<body>
	<jsp:include page="menu.jsp">
		<jsp:param name="menuActual" value="nouProducte.action"/>
	</jsp:include>
	 <s:actionerror labelposition="center" style="border: 1px solid red; background-color: #c23f0f"/>
	 <s:form action="nouProducte.action" method="post">
	  <s:textfield key="newP.nom" name="producte.nom"  />
	  <s:textfield key="newP.disponibilitat" name="producte.disponibilitat"  />
	  <s:textfield key="newP.descripcio" name="producte.descripcio"  />
	  <s:textfield key="newP.preu" name="producte.preu"  />
      <s:textfield key="newP.iniciVenda" name="producte.dataInici"  value="%{getText('global.date',{producte.dataInici})}" />
      <s:submit name="submit" key="global.save" align="center" />
   </s:form>
	<jsp:include page="peu.jsp"/>
</body>
</html>
