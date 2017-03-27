package br.com.thiago10gr.teste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import br.com.thiago10gr.model.Usuario;
import br.com.thiago10gr.util.CalendarDateJsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TesteGson {
	public static void main(String[] args) {
		try {
	
			String dt1 = "25/07/1985";
			String dt2 = "26/06/1986";
						
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(sdf1.parse(dt1));
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(sdf1.parse(dt2));
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		
			Usuario u1 = new Usuario();
			u1.setDataNascimento(cal1);
			u1.setNome("Thiago dos Santos Grillo");
			
			
			Usuario u2 = new Usuario();
			u2.setDataNascimento(cal2);
			u2.setNome("Maria de Las Dores");
			
			
			List<Usuario> lista = new ArrayList<Usuario>();
			lista.add(u1);
			lista.add(u2);
			
			GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setPrettyPrinting();
			gsonBuilder.registerTypeAdapter(GregorianCalendar.class, new CalendarDateJsonSerializer());
//			gsonBuilder.registerTypeAdapter(Time.class, new CalendarTimeJsonSerializer());
			Gson gson = gsonBuilder.create();
			
			String jsonString = gson.toJson(lista);
			
			System.out.println(jsonString);
			
				
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	
	}
}
