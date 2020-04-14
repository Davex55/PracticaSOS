package clase.datos;

public class Transferencia {
	
	private int cuentaOrigen;
	private int cuentaDestino;
	private double importe;
	private String orden;
	
	public Transferencia(int cuentaOrigen, int cuentaDestino,double importe, String orden) {
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
		this.importe = importe;
		this.orden = orden;
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

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
		
}
