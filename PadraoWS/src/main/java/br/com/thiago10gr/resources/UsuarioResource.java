package br.com.thiago10gr.resources;

import java.util.GregorianCalendar;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import br.com.thiago10gr.model.Usuario;
import br.com.thiago10gr.service.ServiceException;
import br.com.thiago10gr.service.UsuarioService;
import br.com.thiago10gr.util.CalendarDateJsonSerializer;
import br.com.thiago10gr.util.CalendarDateTimeJsonSerializer;
import br.com.thiago10gr.util.DateTime;
import br.com.thiago10gr.util.Secured;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("usuario")
public class UsuarioResource {
	
	@POST 
	@Path("/registrar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response cadastrarUsuario(@BeanParam Usuario usuario){
		JSONObject jo = new JSONObject();
		try {	
			new UsuarioService().cadastrar(usuario);
			jo.put("message", "Cadastrado com sucesso");
			return  Response.status(Response.Status.OK).entity(jo.toString()).build();
			
		} catch (ServiceException e) {
			//e.printStackTrace();
			jo.put("mensagem", e.getMessage());
			return  Response.status(Response.Status.BAD_REQUEST).entity(jo.toString()).build();
			
		} catch (Exception e) {
			//e.printStackTrace();
			jo.put("mensagem", "Não foi possível realizar o cadastro no momento.");
			return  Response.status(Response.Status.BAD_REQUEST).entity(jo.toString()).build();
		}
	}
	
	@POST
    @Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response realizarLogin(@BeanParam Usuario usuario){
    	JSONObject jo = new JSONObject();
    	String token = null;
    	try {
    		token = new UsuarioService().realizarLogin(usuario); 
    		jo.put("token", token);
    		jo.put("mensagem", "Login realizado com sucesso");
			return  Response.status(Response.Status.OK).entity(jo.toString()).build();	
    		
    	} catch (ServiceException e) {
    		//e.printStackTrace();
    		jo.put("mensagem", e.getMessage());
			return  Response.status(Response.Status.UNAUTHORIZED).entity(jo.toString()).build();
    		
    	} catch (Exception e) {
    		//e.printStackTrace();
			jo.put("mensagem", "Não foi possível realizar o login no momento.");
			return  Response.status(Response.Status.UNAUTHORIZED).entity(jo.toString()).build();
    	}
    }
	
	//Necessário fornecer o token no header para obter a lista de usuários
	@Secured 
	@POST 
    @Path("/buscarUsuarios")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response buscarUsuarios(){
    	try {
    		//Quando for obtida a lista realizará a serialização das datas 
    		//data de nascimento caso haja para yyyy-MM-dd e data de cadastro para yyyy-MM-dd HH:mm:ss
    		GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setPrettyPrinting();
			gsonBuilder.registerTypeAdapter(GregorianCalendar.class, new CalendarDateJsonSerializer());
			gsonBuilder.registerTypeAdapter(DateTime.class, new CalendarDateTimeJsonSerializer());
			Gson gson = gsonBuilder.create();
			
			List<Usuario> lista = new UsuarioService().buscarUsuarios();
			String jsonString = gson.toJson(lista);
			
			return  Response.status(Response.Status.OK).entity(jsonString).build();	
    
    	} catch (Exception e) {
    		//e.printStackTrace();
    		JSONObject jo = new JSONObject();
			jo.put("mensagem", "Não foi possível realizar a buscar momento.");
	
			return  Response.status(Response.Status.BAD_REQUEST).entity(jo.toString()).build();
    	}
    }
	
}
