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

import clase.datos.*;

@Path("/clientes")
public class ClientesRecursos {

	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	public ClientesRecursos() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			NamingContext envCtx = (NamingContext) ctx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/GarajesyEmpleados");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getClientes() {
		return Response.status(Response.Status.OK).entity("hey bro").build();
	}

	@GET
	@Path("{Cliente_id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getCliente(@PathParam("Cliente_id") int id) {
		try {
			String sql = "SELECT * FROM BANCO.Clientes WHERE idClientes = " + id;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Cliente cliente =  clienteFromRS(rs);
				return Response.status(Response.Status.OK).entity(cliente).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}			
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addCliente() {
		return null;
	}

	@PUT
	@Path("{Cliente_id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateCliente(@PathParam("Cliente_id") int id) {
		return null;
	}

	@DELETE
	@Path("{Cliente_id}")
	public Response deleteCliente(@PathParam("Cliente_id") int id) {
		return null;
	}

	@GET
	@Path("{Cliente_id}/Cuentas")
	public Response getCuentasCliente(@PathParam("Cliente_id") int id) {
		return null;
	}

	@GET
	@Path("{Cliente_id}/Cuentas/{Cuenta_id}")
	public Response getCuentaCliente(@PathParam("Cliente_id") int id, @PathParam("Cuenta_id") int id_cuenta) {
		return null;
	}

	@GET
	@Path("{Cliente_id}/Retiradas")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasCliente(@PathParam("Cliente_id") int id) {
		return null;
	}

	@GET
	@Path("{Cliente_id}/RetiradasTransferencias")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasTransferenciasCliente(@PathParam("Cliente_id") int id) {
		return null;
	}
	
	private Cliente clienteFromRS(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("dni"), rs.getString("telefono"), rs.getString("direccion"));
		return cliente;
	}

}
