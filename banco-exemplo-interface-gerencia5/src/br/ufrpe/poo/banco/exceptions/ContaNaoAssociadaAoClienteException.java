package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na operacao de uma conta associada ao
 * cliente.
 * 
 * @author
 * 
 */
public class ContaNaoAssociadaAoClienteException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContaNaoAssociadaAoClienteException() {
		super("O cliente nao possui essa conta!");
	}
}
