package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na entrada de dados de um campo.
 * 
 * @author
 * 
 */
public class EntradaInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	public EntradaInvalidaException() {
		super("Entrada invalida!");
	}

}
