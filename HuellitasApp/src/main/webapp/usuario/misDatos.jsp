<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="modelo.Usuario"%>

<%
Usuario u =
    (Usuario) request.getAttribute("usuario");

if (u == null) {

    response.sendRedirect(
        request.getContextPath()
        + "/index.jsp");

    return;
}

%>

<%
String error = request.getParameter("error");

String ok = request.getParameter("ok");
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0">

<title>Mis Datos</title>


<!-- Estilos externos-->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">

<!-- Estilos -->
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/admin-form.css">
      <link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/login.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/icons.css">
      <link rel="stylesheet"
      href="<%=request.getContextPath()%>/css/error.css">

</head>

<body>
	<body>
	
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
				<span class="material-icons"> account_circle </span> Mis Datos
			</h1>
		</div>


		<form class="admin-form"
        action="<%=request.getContextPath()%>/usuario/ActualizarMisDatosServlet" method="post">

        <input type="hidden" name="personaId" value="<%=u.getPersona().getPersonaId()%>">

	<!-- Nombre -->
        <label>  <span class="material-icons">person</span>Nombre</label>			  
        <input type="text"  name="nombre" maxlength="25" value="<%=u.getPersona().getNombre()%>" required>
		<small>Máximo 25 caracteres</small>
		
	<!-- Apellidos -->
        <label>  <span class="material-icons">badge</span> Apellidos</label>	
        <input type="text" name="apellidos" maxlength="50" value="<%=u.getPersona().getApellidos()%>" required>
		<small>Máximo 50 caracteres</small>
		
	<!-- Teléfono -->
        <label><span class="material-icons">phone</span>Teléfono</label>				 
        <input type="text" name="telefono" maxlength="9"
       pattern="[0-9]{9}" value="<%=u.getPersona().getTelefono()%>" required>
       <small>Máximo 9 números</small>
		
	<!--Email  -->
        <label> <span class="material-icons">email</span>Email</label>				  
        <input type="email" name="email" maxlength="100" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}" value="<%=u.getPersona().getEmail()%>" required>
    	
    	<!-- Validación -->
			<div class="admin-actions">
				<button type="submit" class="btn btn-form">
					<span class="material-icons"> save </span> Guardar datos
				</button>
			</div>
		</form>
   
    	
    <!--Cambiar contraseña  -->

		<h2>Cambiar contraseña</h2>

		<form class="admin-form"
			action="<%=request.getContextPath()%>/usuario/CambiarPasswordServlet"
			method="post">

			<!-- Contraseña actual -->
			<div class="input-group">

				<span class="material-icons">lock</span> <input type="password"
					id="passwordActual" name="passwordActual"
					placeholder="Contraseña actual" required> <span
					class="material-icons password-toggle" id="togglePasswordActual">
					visibility </span>

			</div>

			<!-- Nueva contaseña -->
			<div class="input-group">

				<span class="material-icons">lock_reset</span> <input
					type="password" id="passwordNueva" name="passwordNueva"
					placeholder="Nueva contraseña" required> <span
					class="material-icons password-toggle" id="togglePasswordNueva">
					visibility </span>

			</div>

			<!-- Repetir contraseña -->
			<div class="input-group">

				<span class="material-icons">lock_reset</span> <input
					type="password" id="passwordNueva2" name="passwordNueva2"
					placeholder="Repetir nueva contraseña" required> <span
					class="material-icons password-toggle" id="togglePasswordNueva2">
					visibility </span>

			</div>

			<!-- Validación -->
			<div class="admin-actions">
				<button type="submit" class="btn btn-form">
					<span class="material-icons"> save </span> Guardar contraseña
				</button>


			</div>
		</form>

		<!-- Navegar -->
    <div class="admin-navigation">
        <a class="btn-secondary" href="<%=request.getContextPath()%>/index.jsp">
            <span class="material-icons"> arrow_back </span>Inicio
        </a>
    </div>

</div>

<script src="<%=request.getContextPath()%>/js/passwd.js"></script>
</body>
</html>