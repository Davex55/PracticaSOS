package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cliente")
public class Cliente {

	private int id;
	private String nombre;
	private String dni;
	private String telefono;
	private String direccion;
	
	public Cliente() {
	}

	@XmlAttribute(required=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public void clienteFromRS(ResultSet rs) throws SQLException {
		this.setId(rs.getInt("idClientes"));
		this.setNombre(rs.getString("Nombre"));
		this.setDni(rs.getString("DNI"));
		this.setTelefono(rs.getString("Telefono"));
		this.setDireccion(rs.getString("Direccion"));
	}
}
