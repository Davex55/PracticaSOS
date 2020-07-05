package clase.tests;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import clase.datos.Cliente;
import clase.datos.Clientes;
import clase.datos.Cuenta;
import clase.datos.ListaClientes;
import clase.datos.ListaMovimientos;
import clase.datos.Movimientos;


public class ClienteRest {

	public static void main(String[] args) throws InterruptedException {
		
		
		/*----------------------CONFIGURACION-INICIAL----------------------*/
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseUri());
		
		/*----------------------PRUEBA1----------------------*/
		
//		System.out.println("1-Añadir un cliente");
//		Cliente cliente = new Cliente();
//		cliente.setNombre("Juan");
//		cliente.setDni("06659105V");
//		cliente.setDireccion("calle Perez");
//		cliente.setTelefono("601335466");		
//		addCliente(target, cliente);
//		
//		/*----------------------PRUEBA2----------------------*/
//		
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 2: ver los datos basicos de un cliente");
//		getCliente(target, "1");
//		
//		/*----------------------PRUEBA3----------------------*/
//		
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 3: actualizar los datos de un cliente");
//		cliente.setNombre("Juan");
//		cliente.setDni("06659105V");
//		cliente.setDireccion("calle Ramirez");
//		cliente.setTelefono("603333333");
//		updateCliente(target, "5", cliente);
//			
//		/*----------------------PRUEBA4----------------------*/
//
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 4: Eliminar un cliente");
//		deleteCliente(target, "5");
//
//		/*----------------------PRUEBA5----------------------*/
//
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 5");
//		Cuenta cuenta = new Cuenta();
//		cuenta.setCliente_id(2);
//		cuenta.setSaldo(0);
//		createCuenta(target, cuenta);
//
//		/*----------------------PRUEBA6----------------------*/
//
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 6");
//		deleteCuenta(target, "9");

		/*----------------------PRUEBA7----------------------*/

//		System.out.println("Prueba 7");
//		Movimientos transferencia = new Movimientos();
//		transferencia.setIDCuenta(1);
//		transferencia.setIDCuentaDest(3);
//		transferencia.setImporte(50);
//		transferencia.setTipo(1);
//		createTransferencia(target, transferencia);
//
//		/*----------------------PRUEBA8----------------------*/
//
//		System.out.println("Prueba 8");
//		deleteTransferencia(target, "1");
//
//		/*----------------------PRUEBA9----------------------*/
//
//		System.out.println("Prueba 9");
//		Movimientos retirada = new Movimientos();
//		retirada.setIDCuenta(4);
//		retirada.setImporte(10);
//		retirada.setTipo(2);
//		createRetirada(target, retirada);

		/*----------------------PRUEBA10----------------------*/

		//System.out.println("Prueba 10");
		//getTransferenciaCuenta(target, "1");

		/*----------------------PRUEBA11----------------------*/

//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 11");
//		getListaClientes(target);
//
//		/*----------------------PRUEBA12----------------------*/
//
//		TimeUnit.SECONDS.sleep(2);
//		System.out.println("Prueba 12");
//		getRetiradasCliente(target, "4");

		/*----------------------PRUEBA13----------------------*/

		TimeUnit.SECONDS.sleep(2);
		System.out.println("Prueba 13");
		getTransferenciasRetiradasCliente(target, "4");
	}
		
	//GET
	private static void getCliente(WebTarget target, String id) {
		Cliente cliente = target.path("api/clientes/" + id).request().accept(MediaType.APPLICATION_XML).get(Cliente.class);
		System.out.println("Nombre: " + cliente.getNombre());
		System.out.println("Id: " + cliente.getId());
		System.out.println("DNI: " + cliente.getDni());
		System.out.println("Telefono: " + cliente.getTelefono());
		System.out.println("Direccion: " + cliente.getDireccion());
	}
	
	//POST
	private static void addCliente(WebTarget target, Cliente cliente) {
		 Response response = target.path("api/clientes").request().post(Entity.xml(cliente));
		 System.out.println("La Uri del nuevo recurso es:" + response.getLocation());
		 System.out.println(response.getStatus());
		 response.close();
	}
	
	//PUT
	private static void updateCliente(WebTarget target, String id, Cliente cliente) {
		Response response = target.path("api/clientes/" + id).request().put(Entity.xml(cliente));
		System.out.println("La Uri del nuevo recurso es:" + response.getLocation());
		System.out.println(response.getStatus());
		response.close();
	}
	
	
	//DELETE
	private static void deleteCliente(WebTarget target, String id) {
		Response response = target.path("api/clientes/" + id).request().delete();
		System.out.println(response.getStatus());
		response.close();
	}
	
	//POST
	private static void createCuenta(WebTarget target, Cuenta cuenta) {
		Response response = target.path("api/cuentas").request().post(Entity.xml(cuenta));
		System.out.println("La Uri del nuevo recurso es:" + response.getHeaderString("Location"));
		System.out.println(response.getStatus());
		response.close();
	}

	//DELETE
	private static void deleteCuenta(WebTarget target, String id) {
		Response response = target.path("api/cuentas/" + id).request().delete();
		System.out.println(response.getStatus());
		response.close();
	}
	
	//POST
	private static void createTransferencia(WebTarget target, Movimientos movimiento) {
		Response response = target.path("api/transferencias").request().post(Entity.xml(movimiento));
		System.out.println("La Uri del nuevo recurso es:" + response.getHeaderString("Location"));
		System.out.println(response.getStatus());
		response.close();
	}
	
	//DELETE
	private static void deleteTransferencia(WebTarget target, String id) {
		Response response = target.path("api/transferencias/" + id).request().delete();
		System.out.println(response.getStatus());
		response.close();
	}
	
	//POST
	private static void createRetirada(WebTarget target, Movimientos movimiento) {
		Response response = target.path("api/retiradas").request().post(Entity.xml(movimiento));
		System.out.println("La Uri del nuevo recurso es:" + response.getHeaderString("Location"));
		System.out.println(response.getStatus());
		response.close();
	}

	//GET
	private static void getTransferenciaCuenta(WebTarget target, String id) {
		ListaMovimientos lista = target.path("api/cuenta/" + id + "/transferencia").request().accept(MediaType.APPLICATION_XML).get(ListaMovimientos.class);
		ArrayList <Movimientos> movimientos = lista.getMovimientos();
		for (int i = 0; i < movimientos.size(); i++) {
			System.out.println(movimientos.get(i).getIDCuenta() + "-" + movimientos.get(i).getIDCuentaDest() + "-" + movimientos.get(i).getImporte());			
		}
	}
	
	//GET
	private static void getListaClientes(WebTarget target) {
		ListaClientes lista = target.path("api/clientes").request().accept(MediaType.APPLICATION_XML).get(ListaClientes.class);
		ArrayList <Clientes> clientes = lista.getClientes();
		for (int i = 0; i < clientes.size(); i++) {
			System.out.println("URL: " + clientes.get(i).getUrl() + " saldo: " + clientes.get(i).getSaldo());			
		}
	}
	
	//GET
	private static void getRetiradasCliente(WebTarget target, String id) {
		ListaMovimientos lista = target.path("api/clientes/" + id + "/retiradas").request().accept(MediaType.APPLICATION_XML).get(ListaMovimientos.class);
		ArrayList<Movimientos> movimientos = lista.getMovimientos();
		for (int i = 0; i < movimientos.size(); i++) {
			System.out.println(movimientos.get(i).getIDCuenta() + "-" + movimientos.get(i).getImporte());			
		}
	}
	
	//GET
	private static void getTransferenciasRetiradasCliente(WebTarget target, String id) {
		ListaMovimientos lista = target.path("api/clientes/" + id + "/retiradasTransferencias").request().accept(MediaType.APPLICATION_XML).get(ListaMovimientos.class);
		ArrayList<Movimientos> movimientos = lista.getMovimientos();
		for (int i = 0; i < movimientos.size(); i++) {
			System.out.println(movimientos.get(i).getIDCuenta() + "-" + movimientos.get(i).getImporte());			
		}
	}
	
	// Devuelve la Uri de la api
	private static URI getBaseUri() {
		return UriBuilder.fromUri("http://localhost:8080/PracticaRESTful").build();
	}
}
