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

import clase.datos.Cliente;
import clase.datos.Cuenta;
import clase.datos.ListaCuentas;
import clase.datos.ListaRetiradas;
import clase.datos.ListaTransferencias;
import clase.datos.Retirada;
import clase.datos.Transferencia;

@Path("/cuentas")
public class CuentasRecursos {

	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	public CuentasRecursos() {
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
	 * getCuentas/0
	 * Devuelve todas las cuentas
	 * @return XML de tipo ListaCuentas
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getCuentas() {
		try {
			String sql = "SELECT * FROM BANCO.Cuentas;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaCuentas lista = new ListaCuentas();
			rs.beforeFirst();
			while (rs.next()) {
				Cuenta cuenta = new Cuenta();
				cuenta.cuentaFromRS(rs);
				lista.addListaCuenta(cuenta);
			}
			return Response.status(Response.Status.OK).entity(lista).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
					.build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	/**
	 * getCuenta/1
	 * Devuelve la cuenta con el id {Cuenta_id}
	 * @param id
	 * @return XML de tipo Cuenta
	 */
	@GET
	@Path("{Cuenta_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCuenta(@PathParam("Cuenta_id") String id) {

		try {
			int int_id = Integer.parseInt(id);
			String sql = "SELECT * FROM BANCO.Cuentas WHERE idCuentas = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Cuenta cuenta = new Cuenta();
				cuenta.cuentaFromRS(rs);
				return Response.status(Response.Status.OK).entity(cuenta).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}

	}

	/**
	 * addCuenta/1
	 * Crea una cuenta nueva en la bbdd
	 * @param cuenta
	 * @return Response con la Url del recurso
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addCuenta(Cuenta cuenta) {
		try {
			double saldo = cuenta.getSaldo();
			int cliente = cuenta.getCliente_id();
			String sql = "INSERT INTO BANCO.Cuentas (Balance, IDCliente) VALUES (" + saldo + ", " + cliente + ");";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				cuenta.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + cuenta.getId();
				return Response.status(Response.Status.CREATED).entity(cuenta).header("Location", location)
						.header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	/**
	 * updateCuenta/2
	 * Actualiza una cuenta con el id {Cuenta_id}(arg1) de la bbdd con los atributos del objeto nueva_cuenta(arg2)
	 * @param id
	 * @param nueva_cuenta
	 * @return Response
	 */
	@PUT
	@Path("{Cuenta_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response updateCuenta(@PathParam("Cuenta_id") String id, Cuenta nueva_cuenta) {
		try {
			int int_id = Integer.parseInt(id);
			Cuenta cuenta = new Cuenta();
			String sql = "SELECT * FROM BANCO.Cuentas WHERE idCuentas = " + int_id + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cuenta.cuentaFromRS(rs);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
			// cuenta.setId(nueva_cuenta.getId());
			cuenta.setCliente_id(nueva_cuenta.getCliente_id());
			cuenta.setSaldo(nueva_cuenta.getSaldo());

			sql = "UPDATE BANCO.Cuentas SET Balance = '" + cuenta.getSaldo() + "', IDCliente = '"
					+ cuenta.getCliente_id() + "' WHERE idCuentas = " + int_id + " ;";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			String location = uriInfo.getAbsolutePath() + "/" + cuenta.getId();
			return Response.status(Response.Status.CREATED).entity(cuenta).header("Location", location)
					.header("Content-Location", location).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	/**
	 * deleteCuenta/1
	 * Elimina una cuenta con el id {Cuenta_id}
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("{Cuenta_id}")
	public Response deleteCuenta(@PathParam("Cuenta_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			Cuenta cuenta = new Cuenta();
			String sql = "SELECT * FROM BANCO.Cuentas WHERE idCuentas = " + int_id + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			cuenta.cuentaFromRS(rs);
			if (cuenta.getSaldo() <= 0) {
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("La cuenta no tiene saldo 0").build();
			}

			sql = "DELETE FROM BANCO.Cuentas WHERE idCuentas = " + int_id + " ;";
			ps = conn.prepareStatement(sql);
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

	/**
	 * getRetiradasCuenta/1
	 * Devuelve todas las retiradas de la cuenta con id {Cuenta_id}
	 * @param id
	 * @return XML de tipo ListaRetiradas
	 */
	@GET
	@Path("{Cuenta_id}/Retiradas")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasCuenta(@PathParam("Cuenta_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			int tipoTransf = 2; // 2->Retirada
			String sql = "SELECT idTransacciones, Importe, IDCuenta, Fecha FROM BANCO.Transacciones WHERE IDCuenta = "
					+ int_id + " AND IDTipoTransf = " + tipoTransf + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaRetiradas lista = new ListaRetiradas();
			rs.beforeFirst();
			while (rs.next()) {
				Retirada retirada = new Retirada();
				retirada.retiradaFromRS(rs);
				lista.addListaRetirada(retirada);
			}
			return Response.status(Response.Status.OK).entity(lista).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	/**
	 * getTransferenciasCuenta/1
	 * Devuelve todas las transferencias de la cuenta con id {Cuenta_id}
	 * @param id
	 * @return XML de tipo ListaTransferencias
	 */
	@GET
	@Path("{Cuenta_id}/Transferencias")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTransferenciasCuenta(@PathParam("Cuenta_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			int tipoTransf = 1; // 1->Transferencia
			String sql = "SELECT idTransacciones, Importe, IDCuenta, Fecha FROM BANCO.Transacciones WHERE IDCuenta = "
					+ int_id + " AND IDTipoTransf = " + tipoTransf + " ;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaTransferencias lista = new ListaTransferencias();
			rs.beforeFirst();
			while (rs.next()) {
				Transferencia transferencia = new Transferencia();
				transferencia.TransferenciaFromRS(rs);
				lista.addListaTransferencia(transferencia);
			}
			return Response.status(Response.Status.OK).entity(lista).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}
}
