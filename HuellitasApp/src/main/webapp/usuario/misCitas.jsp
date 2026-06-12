<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Cita"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
List<Cita> lista = (List<Cita>) request.getAttribute("listaCitas");
%>
<%
if (lista != null && lista.isEmpty()) {
%>

<p class="mensaje-info">No tienes citas registradas.</p>

<%
}
%>

<!DOCTYPE html>

<html>

<head>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>Mis citas</title>

<!-- Icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/tablas.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/error.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
</head>

<body>
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

	<div class="table-container">

		<div class="page-header">

			<h1>

				<span class="material-icons"> event </span> Mis citas

			</h1>
		</div>


		<div class="admin-actions">

			<a class="btn"
				href="<%=request.getContextPath()%>/usuario/FormCrearCitaUsuarioServlet">

				<span class="material-icons">add</span> Nueva cita

			</a>

		</div>

		<div class="admin-table-wrapper">

					<div class="table-responsive">
					
			<table class="admin-table">

				<thead>
					<tr>

						<th>Mascota</th>
						<th>Tipo</th>
						<th>Fecha</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>


				<%
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					%>
				<tbody>


					<%
					if (lista != null) {

						for (Cita c : lista) {
					%>



					<tr>


						<td><%=c.getMascota().getNombre()%></td>

						<td><%=c.getCitaTipo()%></td>

						<td><%=sdf.format(c.getFechaHora())%></td>

						<td><%=c.getEstado()%></td>

						<td class="acciones">
							<%
							if (c.getEstado().equalsIgnoreCase("PENDIENTE")) {
							%>

							<form
								action="<%=request.getContextPath()%>/usuario/CancelarCitaServlet"
								method="post" style="display: inline;">

								<input type="hidden" name="id" value="<%=c.getCitaId()%>">

								<button type="submit" class="btn-icon"
									onclick="return confirm('¿Cancelar esta cita?')">

									<span class="material-icons"> delete </span>

								</button>

							</form> <%
							 }
							 %>

						</td>

					</tr>

					<%
					}
					}
					%>

				</tbody>

			</table>
			
			</div>

		</div>
		<div class="admin-navigation">
			<a class="btn-secondary"  href="<%=request.getContextPath()%>/index.jsp"> <span
				class="material-icons"> arrow_back </span> Inicio</a> </a>
		</div>


	</div>

</body>

</html>