package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "retirada")
public class Retirada {
	
	private int orden;
	private int cuenta;
	private double importe;
		
	public Retirada() {
	}

	@XmlAttribute(required=false)
	public int getOrden() {
		return orden;
	}
	
	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public void retiradaFromRS(ResultSet rs) throws SQLException {
		this.setOrden(rs.getInt("Orden"));
		this.setCuenta(rs.getInt("Cuenta"));
		this.setImporte(rs.getDouble("importe"));
	}
}
