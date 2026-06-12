<%@ page import="modelo.Usuario"%>

<%
Usuario u = (Usuario) request.getAttribute("usuario");

if (u == null) {
	response.sendRedirect(request.getContextPath() + "/admin/ListarUsuariosServlet");
	return;

}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Editar Usuario</title>

<!-- Librería icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- ESTILOS TABLAS con rutas absolutas-->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/icons.css">

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

	<div class="admin-form-wrapper">


		<div class="page-header">

			<div class="page-header">
				<h1>
					<span class="material-icons">group</span> Rol Usuario
				</h1>
			</div>
		</div>

		<form class="admin-form"
			action="<%=request.getContextPath()%>/admin/ActualizarUsuarioServlet"
			method="post">

			<!-- ID real oculto -->
			<input type="hidden" name="id" value="<%=u.getUsuarioId()%>">

			<!-- ID visible -->
			<p><span class="material-icons">badge</span>
				<strong>ID Usuario:</strong>
				<%=u.getUsuarioId()%>
			</p>

			<p><span class="material-icons">person</span>
				<strong>Persona:</strong>
				<%=u.getPersona().getNombre()%>
			</p>

			<p><span class="material-icons">shield</span>
				<strong>Rol actual:</strong>
				<%=u.getRolUsuario().getRolNombre()%>
			</p>

			<!-- Rol -->
			<label> <span class="material-icons">admin_panel_settings</span>Nuevo Rol</label> <select name="rolId" required>
				<option value="1"
					<%=u.getRolUsuario().getRolUsuarioId() == 1 ? "selected" : ""%>>
					Administrador</option>

				<option value="2"
					<%=u.getRolUsuario().getRolUsuarioId() == 2 ? "selected" : ""%>>
					Usuario</option>

			</select>
			
			<!-- Contraseña -->
			 <label> <span class="material-icons">lock_reset</span> Nueva
				contraseña
			</label> <input type="password" name="passwordNueva" maxlength="50">

			<!-- Nueva contraseña -->
			<label> <span class="material-icons">lock_reset</span>
				Confirmar contraseña
			</label> <input type="password" name="passwordNueva2" maxlength="50">

			<small> <span class="material-icons">info</span>
			Dejar ambos campos vacíos para no modificar la
				contraseña. </small>

			<div class="admin-actions">
				<button type="submit" class="btn btn-form">

					<span class="material-icons icon-btn"> save </span> Guardar

				</button>
			</div>


		</form>


		<!-- Desde el update al listado-->
		<div class="admin-navigation">
			<a class="btn-secondary"
				href="<%=request.getContextPath()%>/index.jsp"> <span
				class="material-icons"> arrow_back </span> Inicio
			</a> <a class="btn-secondary"
				href="<%=request.getContextPath()%>/admin/ListarUsuariosServlet"><span
				class="material-icons"> arrow_back </span> Listado</a>

		</div>

	</div>


</body>
</html>
