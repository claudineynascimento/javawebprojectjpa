<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="br.eti.claudiney.curso.web.jpa.entities.Ocorrencia"%>
<%@page import="br.eti.claudiney.curso.web.jpa.factory.DBFactory"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Tipo de Ocorrências</title>
</head>
<body>
<h3>Lista de Tipo de Ocorrências</h3>
<table border="1" cellpadding="5" cellspacing="0">
	<thead>
		<tr>
			<th>Código</th>
			<th>Descrição</th>
			<th>Ação</th>
		</tr>
	</thead>
	<tbody> <%
	
	List<Ocorrencia> listaOcorrencias = null;
	
	String message = null;
	
	try {
		
		EntityManager entityManager = DBFactory.INSTANCE.getEntityManager();
		
		listaOcorrencias = entityManager.createQuery(
				"SELECT T FROM Ocorrencia T", Ocorrencia.class)
				.getResultList();
		
		entityManager.close();
		
	} catch(Throwable e) {
		message = e.getMessage();
		listaOcorrencias = new ArrayList<Ocorrencia>();
	}
	
	for(Ocorrencia ocorrencia: listaOcorrencias) { %>
		<tr>
			<td><%=ocorrencia.getId()%></td>
			<td><%=ocorrencia.getNomeOcorrencia()%></td>
			<td>
				<input type="button" value="Remover" onclick="location.href='./Ocorrencia?acao=remover&id=<%=ocorrencia.getId()%>'">
				<input type="button" value="Editar" onclick="location.href='./Ocorrencia?acao=editar&id=<%=ocorrencia.getId()%>'">
			</td>
		</tr><%
	}
	if( message != null ) {	%>
		<tr><td colspan="3"><%=message%></td></tr>
<%	} %>
	</tbody>
</table>
<br/>
<input type="button" onclick="location.href='formOcorrencia.jsp'" value="Incluir Ocorrência">
</body>
</html>