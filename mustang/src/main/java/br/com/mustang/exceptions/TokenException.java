package br.com.mustang.exceptions;

public class TokenException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenException() {
		super();
	}

	public TokenException(String message) {
		message = "Token invalido";
	}
	
	

}
