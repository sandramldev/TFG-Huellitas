<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="modelo.Mascota"%>


<%

List<Mascota> mascotas = (List<Mascota>) request.getAttribute("mascotas");

%>

<%
List<LocalDateTime> horasDisponibles = (List<LocalDateTime>) request.getAttribute("horasDisponibles");
%>

<!DOCTYPE html>

<html>

<head>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>Pedir cita</title>

<!-- Icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" 	href="<%=request.getContextPath()%>/css/error.css">

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

		<h1>

			<span class="material-icons"> event </span> Pedir cita

		</h1>

		<!-- FORM CONSULTAR HORAS -->

		<form class="admin-form"
			action="<%=request.getContextPath()%>/usuario/FormCrearCitaUsuarioServlet"
			method="get">

			<!-- Mascota -->
			<label>Mascota</label>
			
		 <select name="mascotaId" required>
	
				<option value="">-- Selecciona --</option>

				<%
				if (mascotas != null) {

					for (Mascota m : mascotas) {
				%>

				<%
				String mascotaSeleccionada = request.getParameter("mascotaId");
				%>

				<option value="<%=m.getMascotaId()%>"
					<%=String.valueOf(m.getMascotaId()).equals(mascotaSeleccionada) ? "selected" : ""%>>
					<%=m.getNombre()%>
				</option>

				<%
			}
		}
		%>

			</select>

			<!-- Tipo -->
			<label>Tipo cita</label>
			
			<%
				String tipoSeleccionado = request.getParameter("tipo");
				%>
			
			 <select name="tipo" required>

				<option value="">-- Selecciona --</option>
				
				<option value="REVISION"
					<%="REVISION".equals(tipoSeleccionado) ? "selected" : ""%>>
					REVISION</option>

				<option value="VACUNA"
					<%="VACUNA".equals(tipoSeleccionado) ? "selected" : ""%>>
					VACUNA</option>


				<option value="URGENCIA"
					<%="URGENCIA".equals(tipoSeleccionado) ? "selected" : ""%>>
					URGENCIA</option>

			</select>

			<!-- Fecha -->
			<label for="fecha"> Fecha </label>

			<div class="fecha-disponibilidad">

				<%
				String fechaSeleccionada = request.getParameter("fecha");
				%>
				<input type="date" id="fecha" name="fecha" value="<%= fechaSeleccionada != null ? fechaSeleccionada : "" %>" required>

			</div>

		</form>


		<!-- FORM GUARDAR CITA -->

		<form class="admin-form"
			action="<%=request.getContextPath()%>/usuario/CrearCitaUsuarioServlet"
			method="post">

			<!-- Persistencia -->
			<input type="hidden" name="fecha"
				value="<%=request.getParameter("fecha") != null

			? request.getParameter("fecha")

			: ""%>">

			<input type="hidden" name="mascotaId"
				value="<%=request.getParameter("mascotaId") != null

			? request.getParameter("mascotaId")

			: ""%>">

			<input type="hidden" name="tipo"
				value="<%=request.getParameter("tipo") != null

			? request.getParameter("tipo")

			: ""%>">

			<!-- Separación del bloque horas -->
			<div class="titulo-horas">

				<span class="linea"></span> <span class="texto-titulo"> <span
					class="material-icons">schedule</span> Horas disponibles
				</span> <span class="linea"></span>

			</div>
			
			<!-- Horas -->
			<div class="horas-container">

				<%
		if(horasDisponibles != null){

			for(LocalDateTime hora
					: horasDisponibles){
		%>

				<label class="hora-radio"> 
				<input type="radio" name="hora"
					value="<%=hora.toLocalTime()%>" required> 
					<span> <%=hora.toLocalTime()%></span>
				</label>

				<%
			}
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
		
		<a class="btn-secondary" href="<%=request.getContextPath()%>/usuario/MisCitasServlet">
				<span class="material-icons"> arrow_back </span>Listado </a>
			<a class="btn-secondary"  href="<%=request.getContextPath()%>/index.jsp"> <span
				class="material-icons"> arrow_back </span> Inicio</a> 
		</div>

		

	</div>
	
	<!-- JS carga las horas disponibles al elegir la fecha -->
		<script src="<%=request.getContextPath()%>/js/fechaHora.js"></script>

</body>

</html>
