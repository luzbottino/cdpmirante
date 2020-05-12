package br.com.bottinocode.cdpmirante.autenticacao;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.bottinocode.cdpmirante.operadores.Operador;
import br.com.bottinocode.cdpmirante.operadores.OperadoresRepository;
import br.com.bottinocode.cdpmirante.util.seguranca.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/login")
@RequestScoped
public class AutenticacaoRest {

	@Context
	private UriInfo uriInfo;

	@Inject
	private Logger logger;

	@Inject
	private KeyGenerator keyGenerator;
	
	@Inject
	private OperadoresRepository repositorio;

	@PersistenceContext
	private EntityManager em;

	@POST	
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Response autenticar(@FormParam("login") String login, @FormParam("senha") String password) {

		try {

			logger.info("Efetuando login do " + login);

			processar(login, password);
			String token = issueToken(login);
			
			return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

		} catch (Exception e) {
			return Response.status(UNAUTHORIZED).build();
		}
	}
	
	private void processar(String login, String senha) throws Exception {
		Operador operador = repositorio.buscarPorLoginSenha(login, senha);        

        if (operador == null)
            throw new SecurityException("Usuário ou senha incorreto");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("Gerando token: " + jwtToken + " - " + key);
        return jwtToken;

    }
    
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
