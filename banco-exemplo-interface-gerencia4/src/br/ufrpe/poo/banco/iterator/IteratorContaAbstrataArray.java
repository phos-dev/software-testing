package br.ufrpe.poo.banco.iterator;

import br.ufrpe.poo.banco.negocio.ContaAbstrata;

/**
 * Implementacao do iterador do array de contas que permite operacoes
 * sequenciais no array.
 * 
 * @author
 * 
 */
public class IteratorContaAbstrataArray implements IteratorContaAbstrata {

	/**
	 * Array que possui as contas para as operacoes do iterador.
	 */
	private ContaAbstrata[] contas;

	/**
	 * Indice da posicao atual do array de contas.
	 */
	private int indice;

	public IteratorContaAbstrataArray(ContaAbstrata[] contas) {
		this.contas = contas;
		indice = 0;
	}

	@Override
	public boolean hasNext() {
		return indice < contas.length && contas[indice] != null;
	}

	@Override
	public ContaAbstrata next() {
		ContaAbstrata resposta = contas[indice];
		this.indice = this.indice + 1;
		return resposta;
	}

}
