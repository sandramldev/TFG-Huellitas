<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="modelo.Mascota"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%

List<Mascota> lista =(List<Mascota>)request.getAttribute("listaMascotas");

%>

<!DOCTYPE html>

<html>

<head>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>Mis mascotas</title>

<!-- Icons -->
<link href=
"https://fonts.googleapis.com/icon?family=Material+Icons"
rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/base.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/tablas.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/boton.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/icons.css">
<link rel="stylesheet"href="<%=request.getContextPath()%>/css/error.css">

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

	<div class="table-container">
	
	<div class="page-header">

	<h1>

		<span class="material-icons"> pets </span>

		Mis mascotas

	</h1>
	</div>

		<div class="admin-actions">
			<a class="btn"
				href="<%=request.getContextPath()%>/usuario/formCrearMascota.jsp">

				<span class="material-icons">add</span> Nueva mascota

			</a>

		</div>


		<div class="admin-table-wrapper">
			<%
				if (lista == null || lista.isEmpty()) {
				%>

			<div class="mensaje-info">Aún no tienes mascotas registradas.</div>

			<%
				} else {
				%>

	<div class="table-responsive">
	
			<table class="admin-table">

				<thead>

					<tr>
						<th>Nombre</th>
						<th>Nacimiento</th>
						<th>Estado</th>
					</tr>

				</thead>
				<tbody>

					<%
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					%>

					<%
					
					if (lista != null) {

						for (Mascota m : lista) {
					%>

					<tr>
						<td><%=m.getNombre()%></td>
						<td><%=sdf.format(m.getFechaNacimiento())%></td>
						<td><span
							class="<%=(m.getFechaFallecimiento() == null) ? "estado-activa" : "estado-fallecida"%>">

								<%=(m.getFechaFallecimiento() == null) ? "Activa" : "Fallecida"%>

						</span></td>

					</tr>

					<%
					}
					}
					%>

				</tbody>
				
			</table>
			
			</div>
	
	
				<%
				}
				%>
		</div>
		<div class="admin-navigation">
			<a class="btn-secondary"  href="<%=request.getContextPath()%>/index.jsp"> <span
				class="material-icons"> arrow_back </span> Inicio</a> 
		</div>

	</div>

</body>

</html>