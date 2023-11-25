package br.ufrpe.poo.banco.negocio;

import java.io.Serializable;

/**
 * Conta do tipo poupanca
 * 
 * @author
 * 
 */
public class Poupanca extends Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	public Poupanca(String numero, double valor) {
		super(numero, valor);
	}

	/**
	 * Credita valor dos juros a conta
	 * 
	 * @param taxa
	 */
	public void renderJuros(double taxa) {
		double juros = this.getSaldo() * taxa;
		this.creditar(juros);
	}

}
