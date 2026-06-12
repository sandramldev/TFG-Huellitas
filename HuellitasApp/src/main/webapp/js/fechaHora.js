/**

* Autoenvío del formulario al seleccionar fecha.
* Permite cargar automáticamente las horas disponibles
  */


document.addEventListener("DOMContentLoaded", () => {

    const fecha =
        document.getElementById("fecha");

    if(fecha){

        fecha.addEventListener("change", () => {

            fecha.form.submit();

        });
    }

});
