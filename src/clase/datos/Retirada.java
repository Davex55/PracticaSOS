package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "retirada")
public class Retirada {
	
	private int idTransacciones;
	private int IDCuenta;
	private double Importe;
			
	public Retirada() {
	}

	@XmlAttribute(required=false)
	public int getidTransacciones() {
		return idTransacciones;
	}
	
	public void setidTransacciones(int idTransacciones) {
		this.idTransacciones = idTransacciones;
	}
	
	public int getIDCuenta() {
		return IDCuenta;
	}

	public void setIDCuenta(int IDCuenta) {
		this.IDCuenta = IDCuenta;
	}

	public double getImporte() {
		return Importe;
	}

	public void setImporte(double Importe) {
		this.Importe = Importe;
	}
	
	public void retiradaFromRS(ResultSet rs) throws SQLException {
		this.setidTransacciones(rs.getInt("idTransacciones"));
		this.setIDCuenta(rs.getInt("IDCuenta"));
		this.setImporte(rs.getDouble("Importe"));
	}
}
