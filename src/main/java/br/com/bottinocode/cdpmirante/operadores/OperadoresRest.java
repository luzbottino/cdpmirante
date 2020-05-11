package br.com.bottinocode.cdpmirante.operadores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
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

@Path("/operadores")
@RequestScoped
public class OperadoresRest {

	@Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
	private OperadoresRepository repositorio;
    
    @Inject
    private OperadoresService servico;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Operador> listar() {
        return repositorio.buscarTodos();
    }
    
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Response salvar(Operador operador) {

        Response.ResponseBuilder builder = null;

        try {
            validarOperador(operador);
            servico.salvar(operador);

            builder = Response.ok();
        } catch (ConstraintViolationException ce) { 
            builder = criarViolacoes(ce.getConstraintViolations());
        } catch (Exception e) {          
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    
    private void validarOperador(Operador operador) throws ConstraintViolationException, ValidationException {        // 
        Set<ConstraintViolation<Operador>> violations = validator.validate(operador);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }        
    }
    
    private Response.ResponseBuilder criarViolacoes(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
