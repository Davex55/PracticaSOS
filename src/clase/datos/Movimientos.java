package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movimiento")
public class Movimientos {
	
	private int idTransacciones;
	private int IDCuenta;
	private int IDCuentaDest;
	private double Importe;
	private int tipo;
		
	public Movimientos() {
	}

	@XmlAttribute(required=false)
	public int getidTransacciones() {
		return idTransacciones;
	}
	
	public void setidTransacciones(int idTransacciones) {
		this.idTransacciones = idTransacciones;
	}
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
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

	public void transferenciaFromRS(ResultSet rs) throws SQLException {
		this.setidTransacciones(rs.getInt("idTransacciones"));
		this.setIDCuenta(rs.getInt("IDCuenta"));
		this.setIDCuentaDest(rs.getInt("IDCuentaDest"));
		this.setImporte(rs.getDouble("Importe"));
		this.setTipo(rs.getInt("IDTipoTransf"));		
	}
	
	public void retiradaFromRS(ResultSet rs) throws SQLException {
		this.setidTransacciones(rs.getInt("idTransacciones"));
		this.setIDCuenta(rs.getInt("IDCuenta"));
		this.setImporte(rs.getDouble("Importe"));
		this.setTipo(rs.getInt("IDTipoTransf"));
	}		
}
