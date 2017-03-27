package br.com.thiago10gr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CalendarDateJsonSerializer implements JsonSerializer<Calendar>, JsonDeserializer<Calendar>{

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Calendar deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
		Date date = null;
		try {
			date = dateFormat.parse(json.getAsJsonPrimitive().getAsString());
		} catch (ParseException ex) {
			throw new JsonParseException(ex);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return calendar;
	}

	@Override
	public JsonElement serialize(Calendar calendar, java.lang.reflect.Type type,JsonSerializationContext context) {
		return new JsonPrimitive(dateFormat.format(calendar.getTime()));
	}
}