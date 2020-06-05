package clase.recursos.bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import org.apache.naming.NamingContext;

import clase.datos.Cuenta;
import clase.datos.Transferencia;

@Path("/transferencias")
public class TransferenciasRecursos {

	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	public TransferenciasRecursos() {
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
	@Produces(MediaType.APPLICATION_XML)
	public Response getTransferencias() {
		return null;
	}
	
	@GET
	@Path("{Transferencia_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTransferencia(@PathParam("Transferencia_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			String sql = "";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Transferencia transferencia = new Transferencia();
				transferencia.TransferenciaFromRS(rs);
				return Response.status(Response.Status.OK).entity(transferencia).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addTransferencia(Transferencia transferencia) {
		try {
			String sql = "";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				transferencia.setOrden(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + transferencia.getOrden();
				return Response.status(Response.Status.CREATED).entity(transferencia).header("Location", location)
						.header("Content-Location", location).build();
			}	
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();						
		}catch(SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	@DELETE
	@Path("{Transferencia_id}")
	public Response deleteTransferenciasCuenta(@PathParam("Transferencia_id") String Transferencia_id) {
		try {
			int int_id = Integer.parseInt(Transferencia_id);
			String sql = "";
			PreparedStatement ps = conn.prepareStatement(sql);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 1)
				return Response.status(Response.Status.NO_CONTENT).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("No se pudo eliminar el cliente\n" + e.getStackTrace()).build();
		}
	}
}
