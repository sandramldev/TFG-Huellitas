// ===============================
// CONTROL DE ERRORES
// ===============================

const headerContainer =
	document.getElementById("header");

let contextPath = "";

if (headerContainer) {
	contextPath =
		headerContainer.getAttribute("data-context-path");
}

// ===============================
// CARGA HEADER
// ===============================
		
fetch(contextPath + "/components/header.jsp")
  .then(res => res.text())
  .then(data => {

    document.getElementById("header").innerHTML = data;

    // Cuando el header ya está cargado:
    const hamburger = document.getElementById("hamburger");
    const mobileMenu = document.getElementById("mobileMenu");
	const logoutLink = document.querySelector(".logout-link");
	
    if (hamburger && mobileMenu) {

      // Abrir / cerrar menú
      hamburger.addEventListener("click", (e) => {
        e.stopPropagation(); // evita cierre inmediato
        mobileMenu.classList.toggle("active");
      });

	  //Cerrar Sesión
	  if (logoutLink) {
	      logoutLink.addEventListener("click", (e) => {
	        e.stopPropagation();
	      });
	    }
	  
      // Cerrar al pulsar fuera
      document.addEventListener("click", (e) => {

        if (
          !mobileMenu.contains(e.target) &&
          !hamburger.contains(e.target)
        ) {
          mobileMenu.classList.remove("active");
        }

      });

    }

  });
 

// ===============================
// CARGA FOOTER
// ===============================
fetch(contextPath + "/components/footer.html")
  .then(res => res.text())
  .then(data => {
    document.getElementById("footer").innerHTML = data;
  });
