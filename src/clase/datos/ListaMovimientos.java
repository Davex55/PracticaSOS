package clase.datos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movimientos")
public class ListaMovimientos {

	private ArrayList <Movimientos> movimientos;
	
	public ListaMovimientos() {
		this.movimientos = new ArrayList<>();
	}	
	
	@XmlElement(name="transferencia")
	public ArrayList<Movimientos> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(ArrayList <Movimientos> movimientos) {
		this.movimientos = movimientos;
	}
	
	public void addListaMovimientos(Movimientos movimientos) {
		this.movimientos.add(movimientos);
	}
}
