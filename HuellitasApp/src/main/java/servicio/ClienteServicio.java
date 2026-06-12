package servicio;



import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAO;
import dao.PersonaDAO;
import modelo.Cliente;
import modelo.Persona;

public class ClienteServicio {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private PersonaDAO personaDAO = new PersonaDAO();

    // CREAR CLIENTE
    public boolean crearCliente(Cliente c) throws SQLException {

        if (c == null) return false;
        if (c.getPersona() == null) return false;

        int personaId = c.getPersona().getPersonaId();

        Persona personaBD = personaDAO.obtenerPersonaPorId(personaId);
        if (personaBD == null) return false;

        Cliente clienteExistente = clienteDAO.leerClientePorId(personaId);
        if (clienteExistente != null) return false;

        return clienteDAO.insertarCliente(c);
    }

    // LISTAR
    public List<Cliente> listarClientes() throws SQLException {
        return clienteDAO.listarClientes();
    }

    // BUSCAR POR ID
    public Cliente buscarClientePorId(int id) throws SQLException {
        return clienteDAO.leerClientePorId(id);
    }

    //BUSCAR CLIENTE POR ID
    public Cliente buscarClientePorPersonaId(int personaId)
            throws SQLException {

        return clienteDAO
                .leerClientePorPersonaId(personaId);
    }
    
    // MODIFICAR CLIENTE
    public boolean modificarCliente(Cliente c) throws SQLException {

        if (c == null) return false;

        Cliente clienteBD = clienteDAO.leerClientePorId(c.getClienteId());
        if (clienteBD == null) return false;

        int nuevaPersonaId = c.getPersona().getPersonaId();
        int personaActualId = clienteBD.getPersona().getPersonaId();

        if (nuevaPersonaId != personaActualId) {
            Cliente clienteConEsaPersona = clienteDAO.leerClientePorId(nuevaPersonaId);
            if (clienteConEsaPersona != null) return false;
        }

        boolean hayCambios = false;

        if (personaActualId != nuevaPersonaId) hayCambios = true;

        if (!hayCambios) return false;

        return clienteDAO.actualizarCliente(c);
    }

    // BORRAR CLIENTE
    public boolean borrarCliente(int clienteId) throws SQLException {

        Cliente clienteBD = clienteDAO.leerClientePorId(clienteId);
        if (clienteBD == null) return false;

        boolean tieneMascotas = clienteDAO.tieneMascotas(clienteId);

        if (tieneMascotas) return false;

        return clienteDAO.eliminarCliente(clienteId);
    }
}

