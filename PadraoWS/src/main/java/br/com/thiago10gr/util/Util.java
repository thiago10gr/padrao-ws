package br.com.thiago10gr.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

public class Util implements Serializable {

	private static final long serialVersionUID = -4333328849310093213L;
	
	public static final String SECRET_KEY = "pa158iu2UiyPnAyRq";
	public static final String URL = "thiago10gr.com.br";

	public static boolean isNumeric (String s) {
	    try {
	        Long.parseLong (s); 
	        return true;
	    } catch (NumberFormatException ex) {
	        return false;
	    }
	}
	
	public static String getHash(String txt, String hashType) {
	     
		try {
			
	        MessageDigest md = MessageDigest.getInstance(hashType);
	        byte[] array = md.digest(txt.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	       	  sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        }
	              
	        return sb.toString();
	     } catch (NoSuchAlgorithmException e) {
	          
	     }
	     return null;
	  }

	  public static String md5(String txt) {
	    return Util.getHash(txt, "MD5");
	  }

	  public static String sha1(String txt) {
	    return Util.getHash(txt, "SHA1");
	  }
	 
	  /**
	   * 
	   * @param claims parametros para a criação do token
	   * @param algorithm tipo do algorítimo utilizado
	   * @param secret chave secreta
	   * @return token válido
	   */
	  public static String gerarToken(HashMap<String, Object> claims, Algorithm algorithm, String secret) {
		  
		  String token = null;
		  
		  JWTSigner signer = new JWTSigner(secret);
		  JWTSigner.Options options = new JWTSigner.Options();
		  options.setAlgorithm(algorithm);
		  signer.sign(claims, options);
		  token = signer.sign(claims);
		  
		  return token;
	  }
	  
	  /**
	   * 
	   * @param token
	   * @param secret
	   * @return Informaçõees de acesso
	   * @throws Exception
	   */
	  public static Map<String, Object> verificarToken(String token, String secret) throws Exception {
		  
		  Map<String,Object> claims = null;
		  
		  try { 
		      
			  JWTVerifier verifier = new JWTVerifier(secret);
		      claims = verifier.verify(token);
		      
		  } catch (JWTVerifyException e) {
			  e.printStackTrace();
			  throw new Exception("Token inválido.");
		  } catch (InvalidKeyException e) {
			  e.printStackTrace();
			  throw new Exception("Token inválido.");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new Exception("Token inválido.");
			} catch (IllegalStateException e) {
				e.printStackTrace();
				throw new Exception("Token inválido.");
			} catch (SignatureException e) {
				e.printStackTrace();
				throw new Exception("Token inválido.");
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("Token inválido.");
			}
	  
		 return  claims;
	  }
	 
	public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

			try {
				OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	
	
	/**
	 * Converte org.joda.time.DateTime para java.sql.Date  
	 * @param data
	 * @return java.sql.Date 
	 */
	public static java.sql.Date toDate(Calendar data){
		return new java.sql.Date(data.getTimeInMillis());
	}
	
	/**
	 * Converte  java.sql.Date para org.joda.time.DateTime
	 * @param data
	 * @return org.joda.time.DateTime 
	 */
	public static Calendar toCalendar(java.sql.Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return cal;
	}
	
	/**
	 * Converte org.joda.time.DateTime para java.sql.Date  
	 * @param data
	 * @return java.sql.Date 
	 */
//	public static java.sql.Date toDate(DateTime data){
//		return new java.sql.Date(data.getMillis());
//	}
	
	/**
	 * Converte  java.sql.Date para org.joda.time.DateTime
	 * @param data
	 * @return org.joda.time.DateTime 
	 */
//	public static DateTime toDateTime(java.sql.Date data){
//		return new DateTime(data);
//	}
		
	
	public static Calendar toCalendar(String formato, String data) throws ParseException {
		SimpleDateFormat curFormater = new SimpleDateFormat(formato); 
		Date dateObj = curFormater.parse(data); 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateObj);
		return calendar;
	}
	
	
	
	
}
