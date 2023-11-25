package br.ufrpe.poo.banco.iterator;

import br.ufrpe.poo.banco.negocio.Cliente;

/**
 * Implementacao do iterador do array de clientes que permite operacoes
 * sequenciais no array.
 * 
 * @author
 * 
 */
public class IteratorClienteArray implements IteratorCliente {

	/**
	 * Array que possui os clientes para as operacoes do iterador.
	 */
	private Cliente[] clientes;

	/**
	 * Indice da posicao atual do array de clientes.
	 */
	private int indice;

	public IteratorClienteArray(Cliente[] clientes) {
		this.clientes = clientes;
		this.indice = 0;
	}

	@Override
	public boolean hasNext() {

		return (this.indice < clientes.length && clientes[this.indice] != null);
	}

	@Override
	public Cliente next() {
		Cliente c = clientes[this.indice];
		this.indice += 1;
		return c;
	}

}
