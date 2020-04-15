package clase.datos;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {

	private int id;
	private String nombre;
	private String dni;
	private String telefono;
	private String direccion;
	
	public Cliente(int id, String nombre, String dni, String telefono, String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
	}

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

	public String getFecha() {
		return this.telefono;
	}

	public void setFecha(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
