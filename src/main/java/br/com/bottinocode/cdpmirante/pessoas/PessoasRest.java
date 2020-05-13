package br.com.bottinocode.cdpmirante.pessoas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

@Path("/pessoas")
@RequestScoped
public class PessoasRest {

    @Inject
    private PessoasRepository repositorioPessoas;

    @Inject
    private TelefonesRepository repositorioTelefones;

    @Inject
    private PessoasService servicoPessoas;

    @Inject
    private TelefonesService servicoTelefones;

    @Inject
    private Validador<Pessoa> validadorPessoa;

    @Inject
    private Validador<Telefone> validadorTelefone;

    @Login
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PessoaDao> listar() {
        return repositorioPessoas.buscarTodos().stream()
                .map(p -> new PessoaDao(p, repositorioTelefones.buscarPorPessoa(p))).collect(Collectors.toList());
    }

    @Login
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public PessoaDao recuperar(@PathParam("id") long id) {
        Pessoa pessoa = repositorioPessoas.buscarPorId(id);
        if (pessoa == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new PessoaDao(pessoa, this.repositorioTelefones.buscarPorPessoa(pessoa));
    }

    @Login
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exluir(@PathParam("id") Long id) {

        try {
            servicoPessoas.excluir(id);
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
    public Response salvar(PessoaDao pessoaDao) {

        Response.ResponseBuilder builder = null;

        Pessoa pessoa = Pessoa.com().id(pessoaDao.getId()).nome(pessoaDao.getNome()).documento(pessoaDao.getDocumento())
                .dataNascimento(pessoaDao.getDataNascimento()).nomeMae(pessoaDao.getNomeMae()).nomePai(pessoaDao.getNomePai())
                .dataCadastro(pessoaDao.getDataCadastro()).login(pessoaDao.getLogin()).tipo(pessoaDao.getTipo()).construir();

        try {
            List<Map<String, String>> violacoesTelefone = new ArrayList<>();
            pessoaDao.getTelefones().forEach(telefone -> {
                Map<String, String> violacoes = validadorTelefone.validarObjeto(telefone);
                if (!violacoes.isEmpty()) {
                    violacoesTelefone.add(violacoes);
                }
                if (telefone.getPessoa() == null) {
                    telefone.setPessoa(pessoaDao);
                }
            });

            Map<String, String> violacoes = validadorPessoa.validarObjeto(pessoa);
            final Pessoa pessoaAtualizada = servicoPessoas.salvar(pessoa);
            if (violacoes.isEmpty() && violacoesTelefone.isEmpty()) {

                if(pessoaDao.getTelefones() != null && !pessoaDao.getTelefones().isEmpty()) {
                    pessoaDao.getTelefones().forEach(telefone -> {
                        servicoTelefones.salvar(telefone, pessoaAtualizada);
                    });
                }
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

    @Login
    @DELETE
    @Path("/{idPessoa:[0-9][0-9]*}/telefones/{idTelefone:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exluirTelefone(@PathParam("idPessoa") Long idPessoa, @PathParam("idTelefone") Long idTelefone) {
        
        try {
        	servicoTelefones.excluir(idTelefone);
        } catch (NotFoundException e) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
        
        return Response.noContent().build();
    }

}
