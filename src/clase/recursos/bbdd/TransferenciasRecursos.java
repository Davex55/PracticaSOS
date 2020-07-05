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
import javax.ws.rs.DefaultValue;
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
import javax.ws.rs.QueryParam;

import org.apache.naming.NamingContext;

import clase.datos.Cuenta;
import clase.datos.Movimientos;

@Path("/transferencias")
public class TransferenciasRecursos {

	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	private int tipoTransf = 1; // 1->transferencia

	public TransferenciasRecursos() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			NamingContext envCtx = (NamingContext) ctx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/BANCO");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getTransferencia/1 Devuelve la transferencia con el id {Transferencia_id}
	 * 
	 * @param id
	 * @return XML de tipo Transferencia
	 */
	@GET
	@Path("{Transferencia_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTransferencia(@PathParam("Transferencia_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			String sql = "SELECT idTransacciones, Importe, IDCuenta, Fecha FROM BANCO.Transacciones WHERE idTransacciones = "
					+ int_id + " AND IDTipoTransf = " + tipoTransf + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Movimientos movimientos = new Movimientos();
				movimientos.transferenciaFromRS(rs);
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

	// NECESARIO
	// Revisar!!!
	/**
	 * addTransferencia/1 Crea una transferencia en la bbdd
	 * 
	 * @param transferencia
	 * @return Response con la Url del recurso
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addTransferencia(Movimientos transferencia) {
		try {
			int origen = transferencia.getIDCuenta();
			int destino = transferencia.getIDCuentaDest();
			double importe = transferencia.getImporte();
			Cuenta cuentaOr = new Cuenta();
			Cuenta cuentaDest = new Cuenta();
			System.out.println("hasta aqui1");
			String sql = "SELECT * FROM BANCO.Cuentas WHERE idCuentas = " + origen + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cuentaOr.cuentaFromRS(rs);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}

			if (cuentaOr.getSaldo() > importe) {
				// Se calcula el saldo final
				double balanceOrFin = cuentaOr.getSaldo() - importe;
				// Actualiza el Saldo de la cuesta origen
				sql = "UPDATE BANCO.Cuentas SET Balance = " + balanceOrFin + "WHERE idCuentas = " + cuentaOr.getId()
						+ ";";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				// Actualiza el saldo de la cuenta destino
				sql = "UPDATE BANCO.Cuentas SET Balance = (balance + " + importe + ") WHERE idCuentas = "
						+ cuentaDest.getId() + ";";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				// Se crea una nueva transferencia en el recurso /api/transferencia
				sql = "INSERT INTO BANCO.Transacciones (Importe, IDCuenta, IDTipoTransf, IDCuentaDest) VALUES "
						+ importe + ", " + origen + ", " + tipoTransf + ", " + destino + ";";
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("No se puede realizar la transaccion").build();
			}
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				transferencia.setidTransacciones(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + transferencia.getidTransacciones();
				return Response.status(Response.Status.CREATED).entity(transferencia).header("Location", location)
						.header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	// NECESARIO
	// Revisar
	/**
	 * deleteTransferenciasCuenta/1 Elimina la transferencia con el id
	 * {Transferencia_id}
	 * 
	 * @param Transferencia_id
	 * @return Response
	 */
	@DELETE
	@Path("{Transferencia_id}")
	public Response deleteTransferenciasCuenta(@QueryParam("administrador") @DefaultValue("no") String administrador,
			@PathParam("Transferencia_id") String Transferencia_id) {
		if (administrador == "yes") {
			try {
				int int_id = Integer.parseInt(Transferencia_id);
				String sql = "DELETE FROM BANCO.Transacciones WHERE idTransacciones = " + int_id
						+ " AND IDTripoTransf = " + tipoTransf + " ;";
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
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("No está autorizado a realizar esta acción")
					.build();
		}
	}
}
