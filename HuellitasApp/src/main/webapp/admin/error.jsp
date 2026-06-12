<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/admin-form.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/error.css">

</head>

<body>

	<div class="admin-form-wrapper">

		<h1>Error</h1>

		<p class="mensaje-error">Ha ocurrido un error inesperado.</p>

		<div class="admin-navigation">

			<a class="btn-secondary"
				href="<%=request.getContextPath()%>/index.jsp"> <span
				class="material-icons"> arrow_back </span>Inicio
			</a>


		</div>

	</div>

</body>
</html>