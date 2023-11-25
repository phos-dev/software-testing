package br.ufrpe.poo.banco.negocio;

import java.io.Serializable;

import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;

/**
 * Classe abstrata de uma conta bancaria.
 * 
 * @author
 * 
 */
public abstract class ContaAbstrata implements Serializable {

	protected static final long serialVersionUID = 1L;

	/**
	 * Numero da conta bancaria.
	 */
	protected String numero;

	/**
	 * Valor do saldo da conta bancaria.
	 */
	protected double saldo;

	/**
	 * Inicializa um conta.
	 * 
	 * @param numero
	 *            Numero da conta.
	 * @param valor
	 *            Valor do saldo da conta.
	 */
	public ContaAbstrata(String numero, double valor) {
		this.numero = numero;
		this.saldo = valor;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return this.numero;
	}

	public double getSaldo() {
		return this.saldo;
	}

	/**
	 * Credita um valor a conta bancaria.
	 * 
	 * @param valor
	 *            Valor a ser creditado a conta.
	 */
	public void creditar(double valor) {
		if(valor < 0)
			this.saldo = this.saldo + valor;
	}

	/**
	 * Debita um valor da conta bancaria.
	 * 
	 * @param valor
	 *            Valor a ser debita da conta.
	 * @throws SaldoInsuficienteException
	 *             Lancada caso o valor a ser debitado exceda o valor do saldo
	 *             atual da conta.
	 * 
	 */
	public abstract void debitar(double valor)
			throws SaldoInsuficienteException;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContaAbstrata) {
			ContaAbstrata c = (ContaAbstrata) obj;
			return c.getNumero().equals(this.getNumero());
		}
		return false;
	}
}
