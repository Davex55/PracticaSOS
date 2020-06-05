package clase.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cuenta")
public class Cuenta {

	private int id;
	private int cliente_id;
	private double saldo;
	
	public Cuenta() {}	
	
	
	@XmlAttribute(required=false)
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

	public void addSaldo(double cantidad) {
		this.saldo = this.saldo + cantidad;
	}
	
	public void retirarSaldo(double cantidad) {
		this.saldo = this.saldo - cantidad;
	}
	
	public void cuentaFromRS(ResultSet rs) throws SQLException {
		this.setId(rs.getInt("idCuentas"));
		this.setCliente_id(rs.getInt("iDCliente"));
		this.setSaldo(rs.getDouble("Balance"));
	}
}
