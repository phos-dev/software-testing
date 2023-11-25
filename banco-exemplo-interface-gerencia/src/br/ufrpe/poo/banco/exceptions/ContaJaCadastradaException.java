package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro caso o sistema tente cadastrar os dados de um
 * cliente ja cadastrado.
 * 
 * @author
 * 
 */
public class ContaJaCadastradaException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContaJaCadastradaException() {
		super("Ja existe uma conta cadastrada com este numero!");
	}
}
