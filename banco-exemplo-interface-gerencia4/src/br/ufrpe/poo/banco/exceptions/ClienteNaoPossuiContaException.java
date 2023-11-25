package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro ao buscar uma conta nao inserida no banco de
 * dados.
 * 
 * @author
 * 
 */
public class ClienteNaoPossuiContaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteNaoPossuiContaException() {
		super("O cliente nao possui conta com este numero!");
	}

}
