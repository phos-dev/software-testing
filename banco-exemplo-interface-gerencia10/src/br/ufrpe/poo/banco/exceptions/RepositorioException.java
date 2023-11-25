package br.ufrpe.poo.banco.exceptions;

/**
 * Excecao que representa erros de acesso a um meio de armazenamento
 * persistente.
 * 
 * @author
 * 
 */
public class RepositorioException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepositorioException(String mensagem) {
		super(mensagem);
	}

	public RepositorioException(Throwable exception) {
		super(exception);
	}

}
