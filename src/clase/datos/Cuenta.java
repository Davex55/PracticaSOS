package clase.datos;

import java.util.ArrayList;
import java.util.Date;

public class Cuenta {

	private int id;
	private int cliente_id;
	private double saldo;
	private Date fecha;
	private ArrayList<Transferencia> transferencias;
	private ArrayList<Retirada> retiradas_efectivas;
	
	public Cuenta() {
		this.transferencias = new ArrayList<>();
		this.retiradas_efectivas = new ArrayList<>();
	}
	
	public void realizarTransferencia(int cuentaDestino, double importe, String orden) {
		this.transferencias.add(new Transferencia(this.id, cuentaDestino, importe, orden));
	}
	
	public void retirarEfectivo(double importe, String orden) {
		this.retiradas_efectivas.add(new Retirada(this.id, importe, orden));
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void addSaldo(double cantidad) {
		this.saldo = this.saldo + cantidad;
	}
	
	public void retirarSaldo(double cantidad) {
		this.saldo = this.saldo - cantidad;
	}
		
}
