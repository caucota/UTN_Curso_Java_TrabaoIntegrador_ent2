package org.caucotafederico.conexion;

public class ConectorSQL {
	private String JDBC_DRIVER;
	private String DB_URL;
	private String USUARIO;
	private String CLAVE;
	
	public ConectorSQL() {
		JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		DB_URL = "jdbc:mysql://localhost:3306/apuesta";
		USUARIO = "root";
		CLAVE = "root";
	}
	
	public final String getJdbc_Driver() {
		return JDBC_DRIVER;
	}
	public final void setJdbc_Driver(String jdbc_Driver) {
		this.JDBC_DRIVER = jdbc_Driver;
	}
	public final String getDb_Url() {
		return this.DB_URL;
	}
	public final void setDB_URL(String db_Url) {
		this.DB_URL = db_Url;
	}
	public final String getUsuario() {
		return this.USUARIO;
	}
	public final void setUsuario(String usuario) {
		this.USUARIO = usuario;
	}
	public final String getClave() {
		return this.CLAVE;
	}
	public final void setClave(String clave) {
		this.CLAVE = clave;
	}
	
	

}
