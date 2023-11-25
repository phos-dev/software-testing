package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na associassao de uma conta que o cliente ja
 * possui a ele mesmo.
 * 
 * @author 
 * 
 */
public class ClienteJaPossuiContaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteJaPossuiContaException() {
		super("O cliente ja possui esta conta associada a ele!");
	}
}
