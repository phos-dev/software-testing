package br.ufrpe.poo.banco.iterator;

import br.ufrpe.poo.banco.negocio.Cliente;

/**
 * Interface que representa um iterador para operacoes sequencias no array de
 * clientes.
 * 
 * @author
 * 
 */
public interface IteratorCliente {

	/**
	 * Indica a existencia de cliente.
	 * 
	 * @return se cliente foi encontrado. Se for <code>null</code> ou fim do
	 *         array eh retornado <code>false</code>.
	 */
	boolean hasNext();

	/**
	 * Retorna um cliente da posicao atual no array.
	 * 
	 * @return cliente encontrado. Eh retornado <code>null</code> se nao houver
	 *         mais clientes no array.
	 */
	Cliente next();
}
