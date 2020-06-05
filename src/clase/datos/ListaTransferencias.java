package clase.datos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transferencias")
public class ListaTransferencias {

	private ArrayList<Transferencia> transferencia;
	
	public ListaTransferencias() {
		this.transferencia = new ArrayList<>();
	}	
	
	@XmlElement(name="transferencia")
	public ArrayList<Transferencia> getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(ArrayList<Transferencia> transferencia) {
		this.transferencia = transferencia;
	}
	
	public void addListaTransferencia(Transferencia transferencia) {
		this.transferencia.add(transferencia);
	}
}
