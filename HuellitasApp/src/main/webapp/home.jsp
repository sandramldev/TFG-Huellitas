<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="modelo.Usuario"%>

<%
Usuario u = (Usuario) session.getAttribute("usuario");

if (u == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>

    <h1>
        Hola,
        <%= u.getPersona().getNombre() %>
    </h1>

    <a href="<%=request.getContextPath()%>/LogoutServlet">Cerrar sesión</a>

</body>
</html>
