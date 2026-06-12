<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Usuario"%>

<%
List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
%>

<%
if (lista == null) {
%>

<p class="mensaje-info"> No hay usuarios para mostrar.</p>
<%
return;
}
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Usuarios</title>

<!-- Librería icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/tablas.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/error.css">


</head>


<body>

<!-- Mensaje flotante -->
	<%
	String error = request.getParameter("error");
	String ok = request.getParameter("ok");
	%>

	<%
	if (error != null) {
	%>
	<div class="mensaje-flotante error">
		<%=error%>
	</div>
	<%
	}
	%>

	<%
	if (ok != null) {
	%>
	<div class="mensaje-flotante ok">
		<%=ok%>
	</div>
	<% } %>

	<div class="page-header">
		<h1>
			<span class="material-icons">group</span> Listado de Usuarios
		</h1>
	</div>

	<div class="admin-table-wrapper">

		<div class="table-responsive">

			<table class="admin-table">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Apellidos</th>
						<th>Teléfono</th>
						<th>Email</th>
						<th>Rol</th>
						<th>Acciones</th>
					</tr>
				</thead>

				<tbody>
					<% for (Usuario u : lista) { 
					if (u == null)
					continue;%>
					<tr>
						<td><%=u.getUsuarioId()%></td>
						
						<td><%=u.getPersona().getNombre()%></td>

						<td><%=u.getPersona().getApellidos()%></td>

						<td><%=u.getPersona().getTelefono()%></td>

						<td><%=u.getPersona().getEmail()%></td>

						<td><%=u.getRolUsuario().getRolNombre()%></td>
						<td class="acciones"><a
							href="<%=request.getContextPath()%>/admin/EditarUsuarioServlet?id=<%=u.getUsuarioId()%>">
								<span class="material-icons">edit</span>
						</a> <a
							href="<%=request.getContextPath()%>/admin/BorrarUsuarioServlet?id=<%=u.getUsuarioId()%>">
								<span class="material-icons">delete</span>
						</a></td>
					</tr>
					<% } %>
				</tbody>
			</table>

		</div>

	</div>


	<!-- Volver a la pg ppal-->
	<div class="admin-navigation">
		<a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp"> 
			<span class="material-icons"> arrow_back </span> Inicio </a>
	</div>

</body>
</html>
