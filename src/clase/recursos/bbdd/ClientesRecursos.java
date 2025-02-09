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

			ds = (DataSource) envCtx.lookup("jdbc/BANCO");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// NECESARIO
	// agregar el saldo de los clientes
	/**
	 * getClientes/0 Devuelve la lista de clientes con su Url y su saldo
	 * 
	 * @return XML de tipo ListaClientes
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getClientes(@QueryParam("intervalo") @DefaultValue("0") String intervalo) {
		try {
			String sql;
			if (intervalo.equals("0")) {
				sql = "select (select Clientes.idClientes from Clientes where Clientes.idClientes=Cuentas.IDCliente)as idClientes,"
						+ "(select Clientes.Nombre from Clientes where Clientes.idClientes=Cuentas.IDCliente)as Nombre,"
						+ " sum(Cuentas.balance)from Cuentas group by Cuentas.IDCliente;";
			} else {
				String intervalos[] = intervalo.split("-");
				String intervalo0 = intervalos[0];
				String intervalo1 = intervalos[1];
				int inter0 = Integer.parseInt(intervalo0);
				int inter1 = Integer.parseInt(intervalo1);
				int diferencia = inter1 - inter0;
				inter0--;
				sql = "select (select Clientes.idClientes from Clientes where Clientes.idClientes=Cuentas.IDCliente)as idClientes,"
						+ "(select Clientes.Nombre from Clientes where Clientes.idClientes=Cuentas.IDCliente)as Nombre,"
						+ " sum(Cuentas.balance)from Cuentas group by Cuentas.IDCliente LIMIT " + inter0 + " ,"
						+ diferencia + ";";
			}

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaClientes lista = new ListaClientes();
			rs.beforeFirst();
			while (rs.next()) {
				lista.addListaCliente(new Clientes(uriInfo.getAbsolutePath() + "/" + rs.getInt("idClientes"), 15));
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

	// NECESARIO
	/**
	 * getCliente/1 Devuelve el cliente con el id de {Cliente_id}
	 * 
	 * @param id
	 * @return XML de tipo Cliente
	 */
	@GET
	@Path("{Cliente_id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getCliente(@PathParam("Cliente_id") String id) {
		try {
			int int_id = Integer.parseInt(id);
			Cliente cliente = new Cliente();
			String sql = "SELECT * FROM BANCO.Clientes WHERE idClientes = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cliente.clienteFromRS(rs);
				return Response.status(Response.Status.OK).entity(cliente).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	// NECESARIO
	/**
	 * addCliente/1 Crea un nuevo cliente en la bbdd utilizando el objeto clientes
	 * que se le pasa como arg1
	 * 
	 * @param cliente
	 * @return Response con la Url del nuevo recurso creado
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response addCliente(Cliente cliente) {
		try {
			String sql = "INSERT INTO BANCO.Clientes (Nombre, DNI, Telefono, Direccion) VALUES ('" + cliente.getNombre()
					+ "', '" + cliente.getDni() + "', '" + cliente.getTelefono() + "', '" + cliente.getDireccion()
					+ "');";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) {
				cliente.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + cliente.getId();
				return Response.status(Response.Status.CREATED).entity(cliente).header("Location", location)
						.header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	// NECESARIO
	/**
	 * updateCliente/2 Actualiza un cliente con el id {Cliente_id}(arg1) de la bbdd
	 * con los atributos del objeto nuevo_cliente(arg2)
	 * 
	 * @param id
	 * @param nuevo_cliente
	 * @return Response
	 */
	@PUT
	@Path("{Cliente_id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateCliente(@PathParam("Cliente_id") String id, Cliente nuevo_cliente) {
		try {
			Cliente cliente = new Cliente();
			int int_id = Integer.parseInt(id);
			String sql = "SELECT * FROM BANCO.Clientes WHERE idClientes = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cliente.clienteFromRS(rs);
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
			cliente.setNombre(nuevo_cliente.getNombre());
			cliente.setDni(nuevo_cliente.getDni());
			cliente.setTelefono(nuevo_cliente.getTelefono());
			cliente.setDireccion(nuevo_cliente.getDireccion());

			sql = "UPDATE BANCO.Clientes SET Nombre='" + cliente.getNombre() + "', DNI='" + cliente.getDni()
					+ "', Telefono='" + cliente.getTelefono() + "', Direccion='" + cliente.getDireccion()
					+ "' WHERE idClientes=" + int_id + " ;";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			String location = uriInfo.getAbsolutePath() + "/" + cliente.getId();
			return Response.status(Response.Status.CREATED).entity(cliente).header("Location", location)
					.header("Content-Location", location).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	// NECESARIO
	/**
	 * deleteCliente/1 Elimina el cliente con el id {Cliente_id} de la bbdd
	 * 
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("{Cliente_id}")
	public Response deleteCliente(@PathParam("Cliente_id") String id) {

		try {
			int int_id = Integer.parseInt(id);
			String sql = "DELETE FROM BANCO.Clientes WHERE ( SELECT COUNT(idCuentas) FROM BANCO.Cuentas WHERE IDCliente="
					+ int_id + ")=0 AND idClientes =" + int_id + ";";
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

	/**
	 * getCuentasCliente/1 Devuelve las cuentas del cliente con el id {Cliente_id}
	 * 
	 * @param id
	 * @return XML de tipo ListaCuentas
	 */
	@GET
	@Path("{Cliente_id}/cuentas")
	public Response getCuentasCliente(@PathParam("Cliente_id") int id) {
		try {
			String sql = "SELECT * FROM BANCO.Cuentas WHERE IDCliente= " + id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaCuentas lista = new ListaCuentas();
			rs.beforeFirst();
			while (rs.next()) {
				Cuenta cuenta = new Cuenta();
				cuenta.cuentaFromRS(rs);
				lista.addListaCuenta(cuenta);
			}
			return Response.status(Response.Status.OK).entity(lista).build(); // No se puede devolver el ArrayList (para
																				// generar XML)
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números")
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	// NECESARIO
	/**
	 * getRetiradasCliente/1 Devuelve las retiradas de un cliente cuyo id es
	 * {Clientes_id}
	 * 
	 * @param id
	 * @return XML tipo ListaRetiradas
	 */
	@GET
	@Path("{Cliente_id}/retiradas")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasCliente(@QueryParam("intervalo") @DefaultValue("0") String intervalo,
			@QueryParam("intervaloDinero") @DefaultValue("0") String intervaloDinero, @PathParam("Cliente_id") int id) {
		try {
			String sql;
			String limit = "";
			String dinero = "";
			if (!intervalo.equals("0")) {
				String intervalos[] = intervalo.split("-");
				String intervalo0 = intervalos[0];
				String intervalo1 = intervalos[1];
				int inter0 = Integer.parseInt(intervalo0);
				int inter1 = Integer.parseInt(intervalo1);
				int diferencia = inter1 - inter0;
				inter0--;
				limit = "LIMIT " + inter0 + " ," + diferencia+ " ";
			}
			if (!intervaloDinero.equals("0")) {
				String intervalosD[] = intervaloDinero.split("-");
				String intervaloD0 = intervalosD[0];
				String intervaloD1 = intervalosD[1];
				int interD0 = Integer.parseInt(intervaloD0);
				int interD1 = Integer.parseInt(intervaloD1);
				dinero = "and Importe > " + interD0 + " , Importe < " + interD1+" ";
			}
			sql = "SELECT * FROM BANCO.Transacciones WHERE IDTipoTransf=2 and "
					+ "(IDCuenta IN (SELECT idCuentas FROM BANCO.Cuentas WHERE IDCliente = " + id + ")) " + dinero
					+ limit + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaMovimientos lista = new ListaMovimientos();
			rs.beforeFirst();
			while (rs.next()) {
				Movimientos retirada = new Movimientos();
				retirada.retiradaFromRS(rs);
				lista.addListaMovimientos(retirada);
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
	 * getRetiradasTransferenciasCliente/1 Devuelve todas la retiradas y
	 * transferencias de el cliente con id {Cliente_id}
	 * 
	 * @param id
	 * @return XML tipo ListaRetiradas y ListaTransferencias
	 */
	@GET
	@Path("{Cliente_id}/retiradasTransferencias")
	@Produces(MediaType.APPLICATION_XML)
	public Response getRetiradasTransferenciasCliente(
			@QueryParam("intervaloDinero") @DefaultValue("0") String intervaloDinero, @PathParam("Cliente_id") int id) {
		try {
			String sql;
			if (intervaloDinero.equals("0")) {
				sql = "SELECT * FROM BANCO.Transacciones WHERE IDCuenta IN (SELECT idCuentas FROM BANCO.Cuentas WHERE IDCliente = "
						+ id + ");";
			} else {
				String intervalos[] = intervaloDinero.split("_");
				String intervalo0 = intervalos[0];
				String intervalo1 = intervalos[1];
				int inter0 = Integer.parseInt(intervalo0) - 1;
				int inter1 = Integer.parseInt(intervalo1);
				// int diferencia = inter1 - inter0;
				// inter0--;
				sql = "SELECT * FROM BANCO.Transacciones WHERE IDCuenta IN (SELECT idCuentas FROM BANCO.Cuentas WHERE IDCliente = "
						+ id + ") and Importe > " + inter0 + " and Importe < " + inter1 + ";";
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ListaMovimientos lista = new ListaMovimientos();
			rs.beforeFirst();
			while (rs.next()) {
				Movimientos movimiento = new Movimientos();
				movimiento.retiradaFromRS(rs);
				lista.addListaMovimientos(movimiento);				
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
