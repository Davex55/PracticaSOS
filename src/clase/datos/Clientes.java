package clase.datos;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.annotation.XmlAttribute;

public class Clientes {

	private URL url;
	private double saldo;
	
	@XmlAttribute(name="herf")
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@XmlAttribute
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Clientes(String url, double saldo) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.saldo = saldo;
	}
}
