/**
 * 
 */
package com.devpredator.msanime.exceptions;

/**
 * @author DevPredator
 * @since v1.0 30/09/2021
 * @version v1.0
 * 
 * Custom Exception to identify that an anime is null.
 */
public class AnimeNullException extends RuntimeException {

	/**
	 * Initialize the exception's message.
	 * @param message {@link String} the details of the error.
	 */
	public AnimeNullException(String message) {
		super(message);
	}
}
