package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro no metodo render bonus de conta especial.
 * 
 * @author
 * 
 */
public class RenderBonusContaEspecialException extends Exception {

	private static final long serialVersionUID = 1L;

	public RenderBonusContaEspecialException() {
		super(
				"Erro ao render bonus, o numero informado nao eh de uma Conta Especial!");
	}
}
