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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.naming.NamingContext;

import clase.datos.Cuenta;
import clase.datos.ListaCuentas;
import clase.datos.Movimientos;

@Path("/retiradas")
public class RetiradasRecursos {

	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	public RetiradasRecursos() {
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
	public Response getRetiradas() {
		return null;
	}

	/**
	 * getRetirada/1
	 * Devuelve la retirada con el id {Retirada_id}
	 * @param id
	 * @return XML de tipo Retirada
	 */
	@GET
	@Path("{Retirada_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetirada(@PathParam("Retirada_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			int tipoTransf = 2;
			String sql = "SELECT * FROM BANCO.Transacciones WHERE idTransacciones = "
					+ int_id + " AND IDTipoTransf = " + tipoTransf + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Movimientos movimientos = new Movimientos();
				movimientos.retiradaFromRS(rs);
				return Response.status(Response.Status.OK).entity(movimientos).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	//NECESARIO 
	//Revisar 
	/**
	 * addRetirada/1
	 * Crea una retirada en la bbdd
	 * @param retirada
	 * @return Response con la Url del recurso
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addRetirada(Movimientos movimiento) {
		try {

			String sql = "INSERT INTO Banco.Transacciones (Importe, IDCuenta, IDTipoTransf)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				movimiento.setidTransacciones(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + movimiento.getidTransacciones();
				return Response.status(Response.Status.CREATED).entity(movimiento).header("Location", location)
						.header("Content-Location", location).build();
			}
			sql = "UPDATE BANCO.Cuentas SET Balance = (Balance - " + movimiento.getImporte() + ") WHERE idCuentas = "
					+ movimiento.getIDCuenta() + ";";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}
}
