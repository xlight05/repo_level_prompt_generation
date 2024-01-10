package fr.epsi.epsi5.client.services;

/**
 * Classe permettant de faire remonter des exceptions
 * d'un service RPC vers son appellant côté client.
 * @author Wilhelm Peraud - Loïc Simon
 */
public class DTOException extends Exception {
	/**
	 * Attribut Message.
	 */
	private String message;
	
	/**
	 * Default Serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par défaut.
	 */
	public DTOException(){
		message = "";
	}
	
	/**
	 * Constructeur avec un paramètre.
	 * @param message Message de l'exception
	 */
	public DTOException(String message) {
		this.message = message;
	}
	
	/**
	 * Getter de l'attribut Message.
	 * @return Message de l'exception
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Setter de l'attribut Message.
	 * @param message Message de l'exception
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
