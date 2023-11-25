package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro na atualizacao de dados do cliente.
 * 
 * @author
 * 
 */
public class AtualizacaoNaoRealizadaException extends Exception {

	private static final long serialVersionUID = 1L;

	public AtualizacaoNaoRealizadaException() {
		super("Atualizacao nao realizada! Erro no repositorio!");
	}

}
