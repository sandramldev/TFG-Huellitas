<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="modelo.Mascota"%>
<%@ page import="java.time.LocalDateTime"%>

<%
/*Viene del forward*/
List<Mascota> listaMascotas = (List<Mascota>) request.getAttribute("listaMascotas");
%>

<%
List<LocalDateTime> horasDisponibles = (List<LocalDateTime>) request.getAttribute("horasDisponibles");
%>

<%
List<String> tiposCita = (List<String>) request.getAttribute("tiposCita");
%>



<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>Nueva cita</title>


<!-- Estilos externos -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
	
<!-- Estilos css -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/error.css">


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
				<span class="material-icons">event</span> Nueva cita
			</h1>
		</div>


		<!-- Formularios -->
		<form class="admin-form"
			action="<%=request.getContextPath()%>/admin/FormCrearCitaServlet"
			method="get">

			<!-- Mascota -->
			<label for="mascotaId"> <span class="material-icons">pets</span> Mascota </label> <select id="mascotaId"
				name="mascotaId" required>

				<option value="">-- Selecciona mascota --</option>

				<%
				if (listaMascotas != null) {

					for (Mascota m : listaMascotas) {
				%>

				<option value="<%=m.getMascotaId()%>"
					<%=request.getParameter("mascotaId") != null
					&& request.getParameter("mascotaId").equals(String.valueOf(m.getMascotaId()))

					? "selected"
					: ""%>>

					<%=m.getMascotaId()%> -
					<%=m.getNombre()%>

				</option>



				<%
				}
				}
				%>
			</select>


			<!-- Tipo cita -->
			<label for="tipo"> <span class="material-icons">medical_services</span>Tipo cita </label> <select id="tipo" name="tipo"
				required>

				<option value="">-- Selecciona --</option>

				<%
				if (tiposCita != null) {

					for (String tipo : tiposCita) {
				%>

				<!-- Persistencia de datos -->
				<option value="<%=tipo%>"
					<%=request.getParameter("tipo") != null && request.getParameter("tipo").equals(tipo)

							? "selected"
							: ""%>>
					
										<%=tipo%>

				</option>

				<%
				}
				}
				%>

			</select>

			<!-- Fecha -->
			<label for="fecha"><span class="material-icons">event</span> Fecha </label>

			<div class="fecha-disponibilidad">

				<input type="date" id="fecha" name="fecha" required
					value="<%=request.getParameter("fecha") != null ? request.getParameter("fecha") : ""%>">


			</div>

		</form>



	<form class="admin-form"
			action="<%=request.getContextPath()%>/admin/CrearCitaServlet"
			method="post">

			<!-- Persistencia de datos -->
			<input type="hidden" name="fecha"
				value="<%=request.getParameter("fecha") != null ? request.getParameter("fecha") : ""%>">

			<input type="hidden" name="mascotaId"
				value="<%=request.getParameter("mascotaId") != null ? request.getParameter("mascotaId") : ""%>">
			<input type="hidden" name="tipo"
				value="<%=request.getParameter("tipo") != null ? request.getParameter("tipo") : ""%>">


			<!-- Separación del bloque horas -->
			<div class="titulo-horas">

				<span class="linea"></span> <span class="texto-titulo"> <span
					class="material-icons">schedule</span> Horas disponibles
				</span> <span class="linea"></span>

			</div>


			<div class="horas-container">

				<%
				if (horasDisponibles != null) {

					for (LocalDateTime hora : horasDisponibles) {
				%>

				<label class="hora-radio"> <input type="radio" name="hora"
					value="<%=hora.toLocalTime()%>" required> <span> <%=hora.toLocalTime()%>

				</span>

				</label>

				<%
				}
				}
				%>

		

			<%
			if (horasDisponibles != null && horasDisponibles.isEmpty()) {
			%>

			<p class="mensaje-flotante error">No hay horas disponibles para esta fecha.</p>

			<%
			}
			%>
	</div>


			<div class="admin-actions">
				<button type="submit" class="btn btn-form">

					<span class="material-icons icon-btn"> save </span> Guardar

				</button>
			</div>

		</form>
		
		
		<div class="admin-navigation">
			<a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp"><span
				class="material-icons"> arrow_back </span> Inicio</a> 
				
			<a class="btn-secondary" href="<%=request.getContextPath()%>/admin/ListarCitasServlet"><span
				class="material-icons"> arrow_back </span> Listado </a>
		</div>


	</div>
	
	<!-- JS carga las horas disponibles al elegir la fecha -->
		<script src="<%=request.getContextPath()%>/js/fechaHora.js"></script>
	

</body>
</html>