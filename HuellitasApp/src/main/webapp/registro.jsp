<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1.0">
<title>Registro - Clínica Veterinaria Huellitas</title>

<!-- ICONS -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- ESTILOS con rutas relativas-->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/registro.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/error.css">

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

	
		<!-- HEADER -->
		<div id="header" 
		 	data-context-path="<%=request.getContextPath()%>">
		</div>
		
	<!-- Inicio contenedor -->
	<div class="app-container">

		
		<!-- REGISTRO -->
		<main class="register-layout">

			<!-- COLUMNA IMAGEN -->
			<div class="register-image"></div>

			<!-- COLUMNA FORMULARIO -->
			<div class="register-container">

				<div class="register-card">

					<img src="<%=request.getContextPath()%>/img/Logo.jpg" class="register-logo" alt="Huellitas">

					<div class="page-header">
						<h1>Crear cuenta</h1>
					</div>

					<form action="<%=request.getContextPath()%>/admin/RegistroServlet"
						method="post">

				<!-- Nombre -->
						<div class="input-group">
							<span class="material-icons">person</span> <input type="text"
								name="nombre" placeholder="Nombre"
								value="<%= request.getAttribute("nombre") != null ? request.getAttribute("nombre") : "" %>"
								 required>
						</div>
				<!--Apellidos  -->
						<div class="input-group">
							<span class="material-icons">person_outline</span> <input
								type="text" name="apellidos" placeholder="Apellidos" 								
								value="<%= request.getAttribute("apellidos") != null ? request.getAttribute("apellidos") : "" %>"
								required>
						</div>
						
				<!--Teléfono  -->				
						<div class="input-group">
							<span class="material-icons">phone</span> <input type="text"
								name="telefono" placeholder="Teléfono"
								value="<%= request.getAttribute("telefono") != null ? request.getAttribute("telefono") : "" %>"
								 required>
						</div>
						<%
						String errorTelefono = (String) request.getAttribute("errorTelefono");

						if (errorTelefono != null) {
						%>

						<p class="error-campo">
							<%=errorTelefono%>
						</p>

						<%
						}
						%>

					<!-- Email -->
						<div class="input-group">
							<span class="material-icons">email</span> <input type="email"
								name="email" placeholder="Correo electrónico" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}"
								value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" 
								required>
						</div>
						<%
						String errorEmail = (String) request.getAttribute("errorEmail");

						if (errorEmail != null) {
						%>

						<p class="error-campo">
							<%=errorEmail%>
						</p>

						<%
						}
						%>

					<!-- Campo contraseña -->
						<div class="input-group">

							<span class="material-icons">lock</span> <input type="password"
								id="password" name="password" placeholder="Contraseña" required>

							<span class="material-icons password-toggle" id="togglePassword">
								visibility </span>

						</div>

						<div class="input-group">

							<span class="material-icons">lock</span> <input type="password"
								id="password2" name="password2" placeholder="Repetir contraseña"
								required> <span class="material-icons password-toggle"
								id="togglePassword2"> visibility </span>

						</div>

						<%
						String errorPassword = (String) request.getAttribute("errorPassword");

						if (errorPassword != null) {
						%>

						<p class="error-campo">
							<%=errorPassword%>
						</p>

						<%
						}
						%>

						<button class="btn btn-login"> <span class="material-icons">person_add</span>Registrarse</button>

					</form>

					<div class="login-links">
						<a href="login.jsp">Ya tengo cuenta</a> <a href="index.jsp">Volver
							al inicio</a>
					</div>

				</div>

			</div>

		</main>
</div>
		<!-- FOOTER DINÁMICO -->
		<div id="footer"></div>
		<!-- JS -->
		<script src="<%=request.getContextPath()%>/js/loadComponents.js"></script>
		<script src="<%=request.getContextPath()%>/js/passwd.js"></script>
		
		</body>
</html>
