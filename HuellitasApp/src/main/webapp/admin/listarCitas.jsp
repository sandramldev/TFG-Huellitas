<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Cita"%>
<%@ page import="java.text.SimpleDateFormat"%>


<%
List<Cita> lista = (List<Cita>) request.getAttribute("listaCitas");

if (lista == null || lista.isEmpty()) {
	
	
%>

<p class="mensaje-info"> No hay citas para mostrar.</p>

<%

}
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Listar citas</title>

<!-- Librería icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tablas.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/error.css">

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

	<%
}
%>

	<!-- INCIO ACCIONES ADMIN -->
	<div class="page-header">
		<span class="material-icons">event</span>
		<h1>Listado citas</h1>
	</div>
	
	<!-- Crear nueva cita, separado de las acciones borrar, editar -->
	<div class="admin-actions">

		<a class="btn"
			href="<%=request.getContextPath()%>/admin/FormCrearCitaServlet">


			<span class="material-icons">add</span> Nueva cita

		</a>

	</div>

	<div class="admin-table-wrapper">
	
	<div class="table-responsive">

		<table class="admin-table admin-table-wide">

			<thead>
				<tr>
					<th>ID</th>
					<th>Fecha - Hora</th>
					<th class="th-mascota">Mascota <br> <span
						class="subtitulo-th"> ID - Nombre </span></th>
					<th>Tipo</th>
					<th>Estado</th>
					<th>Acciones</th>
				</tr>
			</thead>

			<tbody>

				<%
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					
				
				for (Cita c : lista) {
				%>

				<tr>

					<td><%=c.getCitaId()%></td>

					<td><%=sdf.format(c.getFechaHora())%></td>

					<td><%=c.getMascota().getMascotaId()%> - <%=c.getMascota().getNombre()%></td>

					<td><%=c.getCitaTipo()%></td>

					<td><%=c.getEstado()%></td>

					<td class="acciones">
					<a
						href="<%=request.getContextPath()%>/admin/FormEditarCitaServlet?id=<%=c.getCitaId()%>">

							<span class="material-icons"> edit </span>

					</a>
						<form
							action="<%=request.getContextPath()%>/admin/EliminarCitaServlet"
							method="post" style="display: inline;">

							<input type="hidden" name="id" value="<%=c.getCitaId()%>">

							<button type="submit" class="btn-icon"
								onclick="return confirm('¿Eliminar esta cita?')">

								<span class="material-icons"> delete </span>

							</button>

						</form></td>

				</tr>

				<%
				}
				%>

			</tbody>

		</table>
		
		</div>
		
		<!-- Volver a la pg ppal-->
	<div class="admin-navigation">
	
		<a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp"><span
			class="material-icons"> arrow_back </span> Inicio </a>
	</div>
	
	
	</div>

</body>
</html>