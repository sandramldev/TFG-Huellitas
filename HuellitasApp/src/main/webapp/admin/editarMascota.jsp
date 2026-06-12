	<%@ page contentType="text/html;charset=UTF-8"%>
	<%@ page import="modelo.Mascota"%>
	
	<%
	Mascota m = (Mascota) request.getAttribute("mascota");
	
	if (m == null) {
	    response.sendRedirect(request.getContextPath() + "/admin/ListarMascotasServlet");
	    return;
	}
	%>
	
	<!DOCTYPE html>
	<html>
	<head>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Editar Mascota</title>
	
	<!-- Librería icons -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
		rel="stylesheet">
		
	<!-- Estilos css -->	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin-form.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icons.css">
	<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
	
	</head>
	
	<body>
	
	<div class="admin-form-wrapper">

		<div class="page-header">
			<h1>
				<span class="material-icons">pets</span> Editar mascota
			</h1>
		</div>

		<form class="admin-form"
			action="<%=request.getContextPath()%>/admin/ActualizarMascotaServlet"
			method="post">

			<!-- ID oculto -->
			<input type="hidden" name="id" value="<%=m.getMascotaId()%>">

			<input type="hidden" name="clienteId"
				value="<%=m.getCliente().getClienteId()%>">


			<!-- Datos formulario actualización -->
			<p><span class="material-icons">person</span> <strong>Cliente: <%=m.getCliente().getPersona().getNombre()%></strong>
			</p> <label><span class="material-icons">pets</span>Nombre
				mascota</label> <input type="text" name="nombre" value="<%=m.getNombre()%>"
				required> <label><span class="material-icons">cake</span>
				Fecha nacimiento</label> <input type="date" name="fechaNacimiento"
				value="<%=m.getFechaNacimiento()%>" required> <label>
				<span class="material-icons">health_and_safety</span> Estado
			</label> <select name="estado">
				<option value="activa"
					<%=(m.getFechaFallecimiento() == null) ? "selected" : ""%>>
					Activa</option>
				<option value="fallecida"
					<%=(m.getFechaFallecimiento() != null) ? "selected" : ""%>>
					Fallecida</option>
			</select>

			<div class="admin-actions">
				<button type="submit" class="btn btn-form">

					<span class="material-icons icon-btn"> save </span> Guardar

				</button>
			</div>

		</form>

		<div class="admin-navigation">
			<a class="btn-secondary"
				href="<%=request.getContextPath()%>/index.jsp">
				<span class="material-icons"> arrow_back </span>Inicio</a> <a
				class="btn-secondary"
				href="<%=request.getContextPath()%>/admin/ListarMascotasServlet">
				<span class="material-icons"> arrow_back </span>Listado
			</a>
		</div>

	</div>
	
	</body>
	</html>