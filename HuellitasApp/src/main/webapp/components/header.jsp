<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="modelo.Usuario"%>

<%
Usuario u = (Usuario) session.getAttribute("usuario");
%>


<header class="navbar">

	<div class="logo-container">
		<img src="<%=request.getContextPath()%>/img/logoTransBco.png" alt="Logo Huellitas" class="logo">
		<span class="brand-name">Clínica Veterinaria</span>
	</div>

	<!-- Menu desktop -->
	<nav class="desktop-menu">

		<%
		if (u != null && u.getRolUsuario() != null && u.getRolUsuario().getRolNombre() != null
				&& u.getRolUsuario().getRolNombre().trim().equalsIgnoreCase("admin")) {
		%>

		<a href="<%=request.getContextPath()%>/admin/ListarUsuariosServlet">
			Usuarios </a> <a
			href="<%=request.getContextPath()%>/admin/ListarMascotasServlet">
			Mascotas </a> <a
			href="<%=request.getContextPath()%>/admin/ListarCitasServlet">
			Citas </a>

		<%
		}
		%>

		<%
		if (u != null && u.getRolUsuario() != null && u.getRolUsuario().getRolNombre() != null
				&& u.getRolUsuario().getRolNombre().trim().equalsIgnoreCase("usuario")) {
		%>

		<a href="<%=request.getContextPath()%>/usuario/MisMascotasServlet"> Mis Mascotas </a> 
		<a href="<%=request.getContextPath()%>/usuario/MisCitasServlet"> Mis Citas </a>
		<a href="<%=request.getContextPath()%>/usuario/MisDatosServlet"> Mis Datos </a>

		<%
		}
		%>

			<!-- Desktop común -->
		<a href="<%=request.getContextPath()%>/index.jsp#servicios">Servicios</a>

		<%
		if (u == null) {
		%>

		<a href="<%=request.getContextPath()%>/index.jsp#contacto">Contacto</a> <a
			href="<%=request.getContextPath()%>/login.jsp"> Iniciar sesión </a>

		<%
		}
		%>


		<%
		if (u != null) {
		%>

		<a href="<%=request.getContextPath()%>/LogoutServlet"> Salir </a>

		<%
		}
		%>

	</nav>

	<%
	if (u != null) {
	%>

	<div class="user-info" title="<%=u.getPersona().getNombre()%>">

		<span class="material-icons">person</span> <span class="user-name">
			<%=u.getPersona().getNombre()%>
		</span>

	</div>
	<%
	}
	%>


	<div class="hamburger" id="hamburger">
		<span class="material-icons">menu</span>
	</div>

	<!--  MÓVIL-->
	<div class="mobile-menu" id="mobileMenu">

		<!-- SOLO ADMIN -->
		<%
		if (u != null && u.getRolUsuario() != null && u.getRolUsuario().getRolNombre() != null
				&& u.getRolUsuario().getRolNombre().trim().equalsIgnoreCase("admin")) {
		%>
		<a href="<%= request.getContextPath() %>/admin/ListarUsuariosServlet"> Gestión Usuarios</a>
		<a href="<%= request.getContextPath() %>/admin/ListarMascotasServlet">Gestión Mascotas</a> 
		<a href="<%= request.getContextPath() %>/admin/ListarCitasServlet">Gestión Citas</a>
		<%
		}
		%>


		<!-- SOLO USUARIO -->
		<%
		if (u != null && u.getRolUsuario() != null && u.getRolUsuario().getRolNombre() != null
				&& u.getRolUsuario().getRolNombre().trim().equalsIgnoreCase("usuario")) {
		%>
		<a href="<%=request.getContextPath()%>/usuario/MisMascotasServlet">Mis Mascotas</a> 
		<a href="<%=request.getContextPath()%>/usuario/MisCitasServlet">Mis Citas</a>
		<a href="<%=request.getContextPath()%>/usuario/MisDatosServlet">Mis Datos</a>
		<%
		}
		%>


		<!-- COMUN -->
		
		<a href="<%=request.getContextPath()%>/index.jsp#servicios">Servicios</a> <a href="<%=request.getContextPath()%>/index.jsp#contacto">Contacto</a>

		<%
		if (u == null) {
		%>

		<a href="<%=request.getContextPath()%>/login.jsp"> Iniciar sesión
		</a>

		<%
		} else {
		%>

		<a href="<%=request.getContextPath()%>/LogoutServlet"> Cerrar
			sesión </a>

		<%
		}
		%>		

	</div>

</header>