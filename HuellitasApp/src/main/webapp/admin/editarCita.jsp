<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="modelo.Cita"%>
<%@ page import="java.time.LocalDateTime"%>

<%
Cita cita = (Cita) request.getAttribute("cita");

List<String> tiposCita = (List<String>) request.getAttribute("tiposCita");
List<LocalDateTime> horasDisponibles =(List<LocalDateTime>)request.getAttribute("horasDisponibles");

if (cita == null) {

	response.sendRedirect(request.getContextPath() + "/admin/ListarCitasServlet");

	return;
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>Editar cita</title>

<!-- Estilos externos -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Estilos css -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/error.css">


</head>
<body>
	<!-- Mensaje de error para forward, redirect, null -->
	<%
	String error =

			request.getParameter("error");

	if (error == null) {

		error =

		(String) request.getAttribute("error");
	}

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

	<!-- Inicio contenedor -->

	<div class="admin-form-wrapper">
	
		<div class="page-header">
			<h1>
				<span class="material-icons"> edit </span> Editar cita

			</h1>
		</div>

		<form class="admin-form"
			action="<%=request.getContextPath()%>/admin/FormEditarCitaServlet"
			method="get">

			<!-- id oculto -->
			<input type="hidden" name="id" value="<%=cita.getCitaId()%>">
			<p>
				 <span class="material-icons">pets</span>
				<strong>Mascota:</strong>
				<%=cita.getMascota().getMascotaId()%>
				-
				<%=cita.getMascota().getNombre()%>

			</p>

			<!-- Fecha -->
			<label for="fecha"><span
				class="material-icons">event</span> Fecha </label>

			<div class="fecha-disponibilidad">

				<input type="date" id="fecha" name="fecha" required
					value="<%=request.getParameter("fecha") != null ? request.getParameter("fecha")
		: cita.getFechaHora().toLocalDateTime().toLocalDate()%>">


			</div>


			<!-- Separación del bloque horas -->
			<div class="titulo-horas">

				<span class="linea"></span> <span class="texto-titulo"> <span
					class="material-icons">schedule</span>
				</span> <span class="linea"></span>

			</div>

		</form>


		<form class="admin-form" method="post"
			action="<%=request.getContextPath()%>/admin/ActualizarCitaServlet">


			<input type="hidden" name="id" value="<%=cita.getCitaId()%>">

			<input type="hidden" name="fecha"
				value="<%=request.getParameter("fecha") != null

						? request.getParameter("fecha")
					
						: cita.getFechaHora()
							.toLocalDateTime()
							.toLocalDate()
					
						%>">


			<div class="horas-container">

				<%
				if (horasDisponibles != null) {

					for (LocalDateTime hora : horasDisponibles) {
				%>

				<label class="hora-radio"> <input type="radio" name="hora"
					value="<%=hora.toLocalTime()%>"
					<%=hora.toLocalTime().equals(

					cita.getFechaHora().toLocalDateTime().toLocalTime())

				? "checked"
				: ""%>
					required> <span> <%=hora.toLocalTime()%>
				</span>

				</label>

				<%
				}
				}
				%>

			</div>

			<%
			if (horasDisponibles != null && horasDisponibles.isEmpty()) {
			%>

			<p class="mensaje-flotante error">No hay horas disponibles para
				esta fecha.</p>

			<%
			}
			%>

			<label><span class="material-icons">medical_services</span>Tipo cita</label> <select name="tipo">
				<%
				for (String tipo : tiposCita) {
				%>

				<option value="<%=tipo%>"
					<%=cita.getCitaTipo().equals(tipo) ? "selected" : ""%>>

					<%=tipo%>

				</option>

				<%
				}
				%>

			</select> <label><span class="material-icons">health_and_safety</span>Estado</label> <select name="estado">

				<option value="PENDIENTE"
					<%=cita.getEstado().equals("PENDIENTE")	? "selected": ""%>>

					PENDIENTE</option>

				<option value="REALIZADA"
					<%=cita.getEstado().equals("REALIZADA")? "selected": ""%>>

					REALIZADA</option>

			</select>

			<div class="admin-actions">
				<button type="submit" class="btn btn-form">

					<span class="material-icons icon-btn"> save </span> Guardar

				</button>
			</div>


		</form>


		<div class="admin-navigation">

			<a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp"><span
				class="material-icons"> arrow_back </span> Inicio</a> 
				
				<a	class="btn-secondary" href="<%=request.getContextPath()%>/admin/ListarCitasServlet"><span
				class="material-icons"> arrow_back </span> Listado </a>
		</div>


	</div>

	<!-- JS carga las horas disponibles al elegir la fecha -->
		<script src="<%=request.getContextPath()%>/js/fechaHora.js"></script>
	
</body>
</html>