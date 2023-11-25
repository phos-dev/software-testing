package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na associassao de uma conta a um cliente que
 * ja possui a conta.
 * 
 * @author
 * 
 */
public class ContaJaAssociadaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContaJaAssociadaException() {
		super("Numero de conta ja associada a um cliente!");
	}

}
