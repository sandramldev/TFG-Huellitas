package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConexionOracle {

	/*Propiedades de conexión usuario VPS*/
	private static final String URL = "jdbc:oracle:thin:@//127.0.0.1:1522/ORCLPDB";	
	private static final String USUARIO = "huellitas";
	private static final String CLAVE = "Huellitas2026";
	
	
	/*Localhost
	private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";	
	private static final String USUARIO = "huellitas";
	private static final String CLAVE = "1503";
	*/

	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver Oracle no encontrado", e);
		}

		return DriverManager.getConnection(URL, USUARIO, CLAVE);
	}


}


