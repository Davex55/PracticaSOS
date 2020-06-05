package clase.datos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clientes")
public class ListaClientes {
	
	private ArrayList<Clientes> cliente;

	public ListaClientes() {
		this.cliente = new ArrayList<Clientes>();
	}

	@XmlElement(name="cliente")
	public ArrayList<Clientes> getClientes() {
		return cliente;
	}

	public void setClientes(ArrayList<Clientes> cliente) {
		this.cliente = cliente;
	}
	
	public void addListaCliente(Clientes cliente) {
		this.cliente.add(cliente);
	}
}

