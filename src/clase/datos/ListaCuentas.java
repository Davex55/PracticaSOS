package clase.datos;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cuentas")
public class ListaCuentas {

	private ArrayList<Cuenta> cuenta;
	
	public ListaCuentas() {
		this.cuenta = new ArrayList<>();
	}	
	
	@XmlElement(name="cuenta")
	public ArrayList<Cuenta> getCuenta() {
		return cuenta;
	}

	public void setCuenta(ArrayList<Cuenta> cuenta) {
		this.cuenta = cuenta;
	}
	
	public void addListaCuenta(Cuenta cuenta) {
		this.cuenta.add(cuenta);
	}
}
