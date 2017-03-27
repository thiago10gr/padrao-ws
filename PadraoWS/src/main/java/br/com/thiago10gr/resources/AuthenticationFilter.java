package br.com.thiago10gr.resources;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.thiago10gr.util.Secured;
import br.com.thiago10gr.util.Util;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
       
		// Obtém o Header Authorization (cabecalho de autorizacao) do request 
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);  // ou getHeaderString("Authorization")
       
        //System.out.println("HOST: " + requestContext.getHeaderString(HttpHeaders.HOST));
            
        // Verificando se o Header Authorization existe e se esta com o Bearer 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Necessário fornecer um cabeçalho de autorização.");
        }

        // Obtendo o token do Header Authorization Bearer 
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validando o token
        	Util.verificarToken(token, Util.SECRET_KEY);

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}