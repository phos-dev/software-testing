package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na instancia unica do comunicador (erros no
 * repositorio).
 * 
 * @author
 * 
 */
public class InicializacaoSistemaException extends Exception {

	private static final long serialVersionUID = 1L;

	public InicializacaoSistemaException() {
		super("N�o foi poss�vel inicializar o sistema!");
	}

}
