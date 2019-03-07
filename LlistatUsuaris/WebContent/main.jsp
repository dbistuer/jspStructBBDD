<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Llistat de productes</title>
</head>
<body>
 <!-- <p>Usuari: <s:property value="usuari.login"/></p>
 <p>Sessio: <s:property value="%{getText('global.date2',{usuari.iniciSessio})}"/></p>  -->
	<jsp:include page="menu.jsp">
		<jsp:param name="menuActual" value="llistar.action"/>
	</jsp:include>
	
	<table width="100%">
			<!-- capcalera -->
			<tr><th colspan="7"><s:text name="llistat.llistatUsuaris" /></th></tr>
			<tr bgcolor="grey"><th><b><s:text name="llistat.id" /></b></th><th><b><s:text name="llistat.idUsuari" /></b></th><th><b><s:text name="llistat.nom" /></b></th><th><b><s:text name="llistat.disponibilitat" /></b></th><th><b><s:text name="llistat.descripcio" /></b></th><th><b><s:text name="llistat.preu" /></b></th><th><b><s:text name="llistat.dataInici" /></b></th>
	
	<s:iterator value="productes" var="producte" status="rowstatus">
	 			<!-- Pintem els parells diferent dels imparells -->
	 			<s:if test="#rowstatus.odd == true"><tr bgcolor="Aqua"></s:if>
            	<s:else><tr></s:else>
            	<!-- Anem a llistar un PRODUCTE -->
            	<td align="center"><s:property value="id"/></td>
            	<td align="center"><s:property value="idUsuari"/></td>
            	<td align="center"><s:property value="nom"/></td>
            	<td align="center"><s:property value="disponibilitat"/></td>
            	<td align="center"><s:property value="descripcio"/></td>
            	<td align="center"><s:property value="preu"/></td>
            	<td align="center"><s:property value="dataInici"/></td> 
            	<!-- <td align="center"><s:property value="%{getText('global.date',{dataInici})}"/></td>  -->
    </s:iterator>
    
	<jsp:include page="peu.jsp"/>
</body>
</html>
