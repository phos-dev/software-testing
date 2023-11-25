package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na tentantiva de transferir um valor de uma
 * conta de origem para uma conta de destino com o mesmo numero.
 * 
 * @author
 * 
 */
public class ContasOrigemDestinoIguaisException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContasOrigemDestinoIguaisException() {
		super("A conta de destino nao pode ser igual a conta de origem!");
	}
}
