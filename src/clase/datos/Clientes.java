package clase.datos;

import java.util.ArrayList;

public class Clientes {
	
	private ArrayList<ListaClientes> clientes;
	
	public Clientes() {
		this.clientes = new ArrayList<ListaClientes>();		
	}

	public ArrayList<ListaClientes> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<ListaClientes> clientes) {
		this.clientes = clientes;
	}
}
