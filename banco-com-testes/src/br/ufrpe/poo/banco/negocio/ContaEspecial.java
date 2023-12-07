package br.ufrpe.poo.banco.negocio;

/**
 * Conta bancaria do tipo conta especial.
 * 
 * @author
 * 
 */
public class ContaEspecial extends Conta {

	private static final long serialVersionUID = 1L;

	/**
	 * Valor do bonus.
	 */
	protected double bonus;

	public ContaEspecial(String numero, double saldo) {
		super(numero, saldo);
		this.bonus = 0;
	}

	public double getBonus() {
		return this.bonus;
	}

	@Override
	public void creditar(double valor) {
		super.creditar(valor);
		this.bonus = this.bonus + (valor * 0.01);
	}

	/**
	 * Credita valor de um bonus a conta especial.
	 */
	public void renderBonus() {
		super.creditar(this.bonus);
		bonus = 0;
	}

}