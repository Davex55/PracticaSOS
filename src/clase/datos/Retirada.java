package clase.datos;

public class Retirada {
	
	private int cuenta;
	private double importe;
	private String orden;
	
	public Retirada(int cuenta, double importe, String orden) {
		this.cuenta = cuenta;
		this.importe = importe;
		this.orden = orden;
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

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

}
