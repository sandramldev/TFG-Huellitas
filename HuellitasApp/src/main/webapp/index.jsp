<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="modelo.Usuario"%>

<%
Usuario u = (Usuario) session.getAttribute("usuario");
%>

<%
String urlServicio;

if (u == null) {

	urlServicio = request.getContextPath() + "/login.jsp";

} else if ("admin".equalsIgnoreCase(u.getRolUsuario().getRolNombre())) {

	urlServicio = request.getContextPath() + "/admin/FormCrearCitaServlet";

} else {

	urlServicio = request.getContextPath() + "/usuario/FormCrearCitaUsuarioServlet";
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Clínica Veterinaria Huellitas</title>

<!-- Librería icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- ESTILOS con rutas absolutas-->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/index.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/boton.css">

</head>
<body>

	<!-- HEADER DINÁMICO -->
	<div id="header" data-context-path="<%=request.getContextPath()%>">
	</div>

	<!-- ================= HERO FULL WIDTH ================= -->
	<section class="hero">

		<h1>Cuidamos a quienes más quieres</h1>
		<p>Gestión de citas, mascotas y clientes desde una única
			plataforma.</p>


		<% if(u == null){ %>

		<div class="hero-actions">

			<a href="<%=request.getContextPath()%>/registro.jsp" class="btn">

				Crear cuenta </a>

		</div>

		<%
		}
		%>

	</section>

	<!-- ================= CONTENIDO CENTRADO ================= -->
	<div class="app-container">

		<section class="services" id="servicios">

			<h2>Nuestros servicios</h2>

			<div class="services-grid">

				<a href="<%=urlServicio%>" class="service-card">

					<article>

						<span class="material-icons">medical_services</span>

						<h3>Consultas</h3>

						<p>Revisiones y seguimiento veterinario.</p>

					</article>

				</a> <a href="<%=urlServicio%>" class="service-card">

					<article>

						<span class="material-icons">vaccines</span>

						<h3>Vacunación</h3>

						<p>Programas de prevención y protección.</p>

					</article>

				</a> <a href="<%=urlServicio%>" class="service-card">

					<article>

						<span class="material-icons">pets</span>

						<h3>Identificación</h3>

						<p>Microchip y documentación.</p>

					</article>

				</a> <a href="<%=urlServicio%>" class="service-card">

					<article>

						<span class="material-icons">emergency</span>

						<h3>Urgencias</h3>

						<p>Atención rápida cuando más lo necesitan.</p>

					</article>

				</a>

			</div>

		</section>

		<section class="about">

			<h2>¿Por qué elegir Huellitas?</h2>

			<p>En Huellitas creemos que cada mascota merece una atención
				cercana, profesional y personalizada.</p>

		</section>

		<section class="contacto" id="contacto">

			<h2>Contacto y ubicación</h2>

			<p>
				<span class="material-icons">call</span> 600 000 000
			</p>

			<p>
				<span class="material-icons">mail</span> contacto@huellitas.info
			</p>
			
			
			<p>
				<span class="material-icons">location_on</span> <a
					href="https://maps.google.com/?q=Parque+de+El+Retiro+Madrid">

					Parque de El Retiro, Retiro, 28009 Madrid </a>
			</p>
			

			<div class="mapa-preview">

				<a href="https://maps.google.com/?q=Parque+de+El+Retiro+Madrid">

					<img src="<%=request.getContextPath()%>/img/mapa-retiro.png"
					alt="Ubicación Clínica Huellitas">

				</a>

			</div>

		</section>
	</div>

	<!-- FOOTER DINÁMICO -->
	<div id="footer"></div>
	<!-- JS -->

	<script src="<%=request.getContextPath()%>/js/loadComponents.js"></script>

</body>
</html>