package br.com.thiago10gr.service;

import java.io.Serializable;

public class ServiceException extends Exception implements Serializable{
	  
	private static final long serialVersionUID = -8520030999651550003L;

	public ServiceException(String message) {
		super(message);
	}
}
