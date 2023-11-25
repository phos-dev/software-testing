package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro quando uma busca de um cliente nao cadastrado
 * eh feita.
 * 
 * @author
 * 
 */
public class ClienteNaoCadastradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteNaoCadastradoException() {
		super("Nenhum cliente foi cadastrado com esse cpf!");
	}

}
