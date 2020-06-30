package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transferencia")
public class Transferencia {
	
	private int idTransacciones;
	private int IDCuenta;
	private int IDCuentaDest;
	private double Importe;
	
	public Transferencia() {
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

	public int getIDCuentaDest() {
		return IDCuentaDest;
	}

	public void setIDCuentaDest(int IDCuentaDest) {
		this.IDCuentaDest = IDCuentaDest;
	}

	public double getImporte() {
		return Importe;
	}

	public void setImporte(double Importe) {
		this.Importe = Importe;
	}	

	public void TransferenciaFromRS(ResultSet rs) throws SQLException {
		this.setidTransacciones(rs.getInt("idTransacciones"));
		this.setIDCuenta(rs.getInt("IDCuenta"));
		this.setIDCuentaDest(rs.getInt("IDCuentaDest"));
		this.setImporte(rs.getDouble("Importe"));
	}
		
}
