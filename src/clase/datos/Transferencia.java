package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Transferencia {
	
	private int orden;
	private int cuentaOrigen;
	private int cuentaDestino;
	private double importe;
	
	public Transferencia() {
	}

	public int getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(int cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public int getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(int cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public void TransferenciaFromRS(ResultSet rs) throws SQLException {
		this.setOrden(rs.getInt("Orden"));
		this.setCuentaOrigen(rs.getInt("cuentaOrigen"));
		this.setCuentaDestino(rs.getInt("cuentaDestino"));
		this.setImporte(rs.getDouble("importe"));
	}
		
}
