package br.com.thiago10gr.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

// JAX-RS 2.0
// anotacao de ligacao 
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured { 
	
}
