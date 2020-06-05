package clase.datos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "retiradas")
public class ListaRetiradas {

	private ArrayList<Retirada> retirada;
	
	public ListaRetiradas() {
		this.retirada = new ArrayList<>();
	}	
	
	@XmlElement(name="retirada")
	public ArrayList<Retirada> getRetirada() {
		return retirada;
	}

	public void setRetirada(ArrayList<Retirada> retirada) {
		this.retirada = retirada;
	}
	
	public void addListaRetirada(Retirada retirada) {
		this.retirada.add(retirada);
	}
}
