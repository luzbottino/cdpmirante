package br.com.bottinocode.cdpmirante.autenticacao;

import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.bottinocode.cdpmirante.util.seguranca.KeyGenerator;
import io.jsonwebtoken.Jwts;

@Provider
@Login
@Priority(Priorities.AUTHENTICATION)
public class LoginFilter implements ContainerRequestFilter {

    @Inject
    private Logger logger;

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        logger.info("authorizationHeader : " + authorizationHeader);
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.severe("authorizationHeader inválido: " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header deve ser provido");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("token válido: " + token);

        } catch (Exception e) {
            logger.severe("token inválido: " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}