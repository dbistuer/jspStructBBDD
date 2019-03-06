<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="cat.almata.daw.llistatUsuaris.Usuari"%>
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
 <%
 Usuari usuari = (Usuari)session.getAttribute(Constants.sessioUsuari); 
 out.println("Usuari:"+usuari.getLogin()+"<br/>");
 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
 out.println("Sessio:"+dateFormat.format(usuari.getIniciSessio()));
 
 %>
	<jsp:include page="menu.jsp">
		<jsp:param name="menuActual" value="nouUsuari.action"/>
	</jsp:include>
	 <s:actionerror/>
	 <s:form action="nouUsuari.action" method="post">
	  <s:textfield key="newUser.nom" name="usuari.nom"  />
	  <s:textfield key="newUser.login" name="usuari.login"  />
	  <s:password key="newUser.pass" name="usuari.pass"  />
	  <s:password key="newUser.password" name="usuari.password"  />
	  <s:textfield key="newUser.cognoms" name="usuari.cognoms"  />
      <s:textfield key="newUser.dataNaixement" name="usuari.dataNaixement"  value="%{getText('global.date',{usuari.dataNaixement})}" />
      <s:textfield key="newUser.email" name="usuari.email"  />
	  <s:textfield key="newUser.mobil" name="usuari.mobil"  />
      <s:submit name="submit" key="global.save" align="center" />
   </s:form>
	<jsp:include page="peu.jsp"/>
</body>
</html>
