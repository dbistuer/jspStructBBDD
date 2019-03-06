<%@ page
	import="cat.almata.daw.llistatUsuaris.Constants"%>
<table width="100%">
	<tr>
		<%
		
 	String menuActual=request.getParameter("menuActual");
	for(int i=0;i<Constants.menuCode.length;i++){
		out.print("<td>");
		if(menuActual!=null && !menuActual.equals(Constants.menuCode[i])){
			out.print("<a href=\""+Constants.menuCode[i]+"\">"+Constants.menuString[i]+"</a>");			
		}else{
			out.print(Constants.menuString[i]);
		}
		out.print("</td>");
	}
	%>
	</tr>
</table>