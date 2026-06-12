package util;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordSecurity {

    // Genera el hash a partir del password del formulario
    public static String hashPassword(String passwordPlano) {
      
    	//Control de contraseñas null
    	  if (passwordPlano == null) {
    	        throw new IllegalArgumentException("La contraseña no puede ser null");
    	    }
    	
    	return BCrypt.hashpw(passwordPlano, BCrypt.gensalt());
    }

    // Comprueba password en login
    public static boolean checkPassword(String passwordPlano, String hashBD) {
       
    	//Control de null    	
        if (passwordPlano == null || hashBD == null) {
            return false;
        }
        
    	return BCrypt.checkpw(passwordPlano, hashBD);
    }
}
