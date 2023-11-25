package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro ao tentar acessar o sistema relacionado ao
 * cliente do banco.
 * 
 * @author
 * 
 */
public class AcessoMenuClienteBloqueadoExeception extends Exception {

	private static final long serialVersionUID = 1L;

	public AcessoMenuClienteBloqueadoExeception() {
		super(
				"Numero maximo de tentativas de acessar o sistema alcancado. Sistema encerrado!");
	}

}
