<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="modelo.Mascota"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
List<Mascota> lista = (List<Mascota>) request.getAttribute("listaMascotas");
%>

<%
if (lista == null) {
%>

<p class="mensaje-info"> No hay mascotas para mostrar.</p>
<%

}
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Mascotas</title>

<!-- Librería icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tablas.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/error.css">

</head>


<body>
	<div class="page-header">
		<span class="material-icons">pets</span>
		<h1>Listado de Mascotas</h1>
	</div>

	<!-- Inicio contenedor -->
	<div class="admin-actions">

		<a class="btn"
			href="<%=request.getContextPath()%>/admin/FormCrearMascotaServlet">

			<span class="material-icons">add</span> Nueva mascota

		</a>
	</div>

	<div class="admin-table-wrapper">
	
		<%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");%>

		<div class="table-responsive">

			<table class="admin-table admin-table-wide">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>Cliente</th>
						<th>Fecha Nac.</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>

				<tbody>

					<%
				for (Mascota m : lista) {
				%>
					<tr>
						<td><%=m.getMascotaId()%></td>
						<td><%=m.getNombre()%></td>
						<td><%=m.getCliente().getPersona().getNombre()%></td>
						<td><%=sdf.format(m.getFechaNacimiento())%></td>
						<td>
						<span class="<%=(m.getFechaFallecimiento() == null) ? "estado-activa" : "estado-fallecida"%>">
						
							<%=(m.getFechaFallecimiento() == null) ? "Activa" : "Fallecida"%></span>
						</td>
						<td class="acciones"><a
							href="<%=request.getContextPath()%>/admin/EditarMascotaServlet?id=<%=m.getMascotaId()%>">
								<span class="material-icons">edit</span>
						</a>
							<form
								action="<%=request.getContextPath()%>/admin/BorrarMascotaServlet"
								method="post" style="display: inline;">

								<input type="hidden" name="id" value="<%=m.getMascotaId()%>">

								<button type="submit" class="btn-icon"
									onclick="return confirm('¿Eliminar esta mascota?')">
									<span class="material-icons">delete</span>
								</button>

							</form></td>
					</tr>
					<%
				}
				%>
				</tbody>
			</table>

		</div>
	</div>


	<!-- Volver a la pg ppal-->
	<div class="admin-navigation">

		<a class="btn-secondary"
			href="<%=request.getContextPath()%>/index.jsp"><span
			class="material-icons"> arrow_back </span>Inicio</a>
	</div>

</body>
</html>
