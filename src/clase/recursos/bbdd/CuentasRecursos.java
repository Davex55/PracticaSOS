package clase.recursos.bbdd;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;

@Path("/Cuentas")
public class CuentasRecursos {

	public CuentasRecursos() {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getCuentas() {
		return null;
	}
	
	@GET
	@Path("{Cuenta_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addCuenta() {
		return null;
	}
	
	@PUT
	@Path("{Cuenta_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response updateCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@DELETE
	@Path("{Cuenta_id}")
	public Response deleteCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@GET
	@Path("{Cuenta_id}/Retiradas")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@GET
	@Path("{Cuenta_id}/Transferencias")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTransferenciasCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@POST
	@Path("{Cuenta_id}/Retiradas")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addRetiradasCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@POST
	@Path("{Cuenta_id}/Transferencias")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addTransferenciasCuenta(@PathParam("Cuenta_id") int id) {
		return null;
	}
	
	@DELETE
	@Path("{Cuenta_id}/Transferencias/{Transferencia_id}")
	public Response deleteTransferenciasCuenta(@PathParam("Cuenta_id") int id, @PathParam("Transferencia_id") int Transferencia_id) {
		return null;
	}
	
	
}
