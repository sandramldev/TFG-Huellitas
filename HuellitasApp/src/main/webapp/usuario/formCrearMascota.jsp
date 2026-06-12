<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Cliente"%>

<%
List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Nueva mascota</title>


<!-- Estilos externos -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Estilos css -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/error.css">

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

	<div class="admin-form-wrapper">

		<div class="page-header">
			<h1>
				<span class="material-icons">pets</span> Nueva mascota
			</h1>
		</div>


		<!-- Mensaje error -->

		<%
		if (error != null) {
		%>

		<p class="mensaje-error">No se pudo crear la mascota</p>

		<%
		}
		%>

		<!-- Formulario -->
		<form class="admin-form"
				action="<%=request.getContextPath()%>/usuario/CrearMascotaUsuarioServlet"
			method="post">

			


			<label><span class="material-icons">pets</span>Nombre
				mascota</label> <input type="text" name="nombre" required> <label><span
				class="material-icons">cake</span>Fecha nacimiento</label> <input
				type="date" name="fechaNacimiento" required>

			<div class="admin-actions">
				<button type="submit" class="btn btn-form">

					<span class="material-icons icon-btn"> save </span> Guardar

				</button>
			</div>
		</form>

		<div class="admin-navigation">

			<a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp"><span
				class="material-icons"> arrow_back </span>Inicio</a> <a
				class="btn-secondary"
				href="<%=request.getContextPath()%>/usuario/MisMascotasServlet">
				<span class="material-icons"> arrow_back </span> listado
			</a>
		</div>
	</div>

</body>
</html>