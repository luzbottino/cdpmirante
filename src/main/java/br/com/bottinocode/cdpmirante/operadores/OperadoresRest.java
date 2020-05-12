package br.com.bottinocode.cdpmirante.operadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.bottinocode.cdpmirante.autenticacao.Login;
import br.com.bottinocode.cdpmirante.util.Validador;

@Path("/operadores")
@RequestScoped
public class OperadoresRest {

	@Inject
    private Logger log;
	
	@Inject
	private Validador<Operador> validador;

    @Inject
	private OperadoresRepository repositorio;
    
    @Inject
    private OperadoresService servico;
    
    @Login
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Operador> listar() {
        return repositorio.buscarTodos();
    }
    
    @Login
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Operador recuperar(@PathParam("id") long id) {
        Operador operador = repositorio.buscarPorId(id);
        if (operador == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return operador;
    }
    
    @Login
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exluir(@PathParam("id") Long id) {
        
        try {
        	servico.excluir(id);
        } catch (NotFoundException e) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
        
        return Response.noContent().build();
    }
    
    @Login
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response salvar(Operador operador) {

        Response.ResponseBuilder builder = null;

        try {
        	
        	Map<String, String> violacoes = validador.validarObjeto(operador);
        	
        	if(violacoes.isEmpty()) {
        		servico.salvar(operador);
        		builder = Response.ok();
        	} else {
        		builder = Response.status(Response.Status.BAD_REQUEST).entity(violacoes);	
        	}        	
            
        } catch (Exception e) {          
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
   
}
