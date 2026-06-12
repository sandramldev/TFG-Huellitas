<%@ page language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">

<meta name="viewport"
content="width=device-width, initial-scale=1.0">

<title>Login - Huellitas</title>


<!-- ICONS -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- CSS con rutas relativas para los .html -->
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/footer.css">
<link rel="stylesheet" href="css/boton.css">
<link rel="stylesheet" href="css/error.css">
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
	<div id="header" data-context-path="<%=request.getContextPath()%>">
	</div>

	<div class="app-container">

		<!-- LOGIN -->

		<main class="login-layout">

			<!-- COLUMNA IZQUIERDA -->
			<div class="login-image">
			  <img src="img/Portada.jpg" alt="Perro, gato, conejo y ave en una clínica veterinaria">
			</div>

			<!-- COLUMNA DERECHA -->
			<div class="login-container">

				<div class="login-card">

					<a href="index.jsp"> <img src="img/Logo.jpg"
						class="login-logo" alt="Huellitas">
					</a>


					<h1>Acceso a la plataforma</h1>

					<form action="LoginServlet" method="post">

						<div class="input-group">
							<span class="material-icons">email</span> <input type="email"
								name="email" placeholder="Correo electrónico" required>
						</div>

						<div class="input-group">

							<span class="material-icons">lock</span> <input type="password"
								id="password" name="password" placeholder="Contraseña" required>

							<span class="material-icons password-toggle" id="togglePassword">
								visibility </span>

						</div>

						<button class="btn btn-login">Entrar</button>

					</form>


					<div class="login-links">
						<a href="<%=request.getContextPath()%>/registro.jsp">Crear cuenta </a>
						 <a href="#">¿Olvidaste tu contraseña?</a>
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
