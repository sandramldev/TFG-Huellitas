		/**
		 Visibilidad contraseña , mejora ux login y registro
		 */
		
		 
		 //Función reutilizable
		 function togglePassword(inputId, iconId) {
		
		 	const password = document.getElementById(inputId);
		 	const toggle = document.getElementById(iconId);
		
		 	if (password.type === "password") {
		
		 		password.type = "text";
		 		toggle.textContent = "visibility_off";
		
		 	} else {
		
		 		password.type = "password";
		 		toggle.textContent = "visibility";
		
		 	}
		 }
		
		 //ELogin
		 document.addEventListener("DOMContentLoaded", () => {
		
		 	document.getElementById("togglePassword")
		 		.addEventListener("click", () =>
		 			togglePassword("password", "togglePassword"));
		
		 	document.getElementById("togglePassword2")
		 		.addEventListener("click", () =>
		 			togglePassword("password2", "togglePassword2"));
		
		 });
		 
		 
		 //Mis datos
		 
		 document.addEventListener("DOMContentLoaded", () => {

		     const icon1 =
		         document.getElementById("togglePasswordActual");

		     if(icon1){
		         icon1.addEventListener("click", () =>
		             togglePassword(
		                 "passwordActual",
		                 "togglePasswordActual"));
		     }

		     const icon2 =
		         document.getElementById("togglePasswordNueva");

		     if(icon2){
		         icon2.addEventListener("click", () =>
		             togglePassword(
		                 "passwordNueva",
		                 "togglePasswordNueva"));
		     }

		     const icon3 =
		         document.getElementById("togglePasswordNueva2");

		     if(icon3){
		         icon3.addEventListener("click", () =>
		             togglePassword(
		                 "passwordNueva2",
		                 "togglePasswordNueva2"));
		     }

		 });