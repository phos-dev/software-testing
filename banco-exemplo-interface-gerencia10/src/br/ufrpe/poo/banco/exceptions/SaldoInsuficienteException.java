package br.ufrpe.poo.banco.exceptions;

/**
 * Excessao que representa um erro no debito de um valor no saldo da conta.
 * 
 * @author
 * 
 */
public class SaldoInsuficienteException extends Exception {

	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(String numero, double saldo) {
		super(String.format(
				"Saldo insuficiente! O saldo atual da conta %s eh R$%.2f",
				numero, saldo));
	}
}
