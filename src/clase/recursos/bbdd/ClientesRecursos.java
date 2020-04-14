package clase.recursos.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.naming.NamingContext;

@Path("/clientes")
public class ClientesRecursos {
	
	public ClientesRecursos() {
		
	}
	
	@GET 
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getClientes() {
		return Response.status(Response.Status.OK).entity("No se pudo crear el garaje").build();
	}
	
	@GET
	@Path("{Cliente_id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getCliente(@PathParam("Cliente_id") int id) {
		return null;
	}
	
	
	
	
}
