package br.ufrpe.poo.banco.iterator;

import br.ufrpe.poo.banco.negocio.ContaAbstrata;

/**
 * Interface que representa um iterador para operacoes sequencias no array de
 * contas.
 * 
 * @author
 * 
 */
public interface IteratorContaAbstrata {

	/**
	 * Indica a existencia de conta.
	 * 
	 * @return se conta foi encontrada. Se for <code>null</code> ou fim do array
	 *         eh retornado <code>false</code>.
	 */
	boolean hasNext();

	/**
	 * Retorna uma conta da posicao atual no array.
	 * 
	 * @return conta encontrada. Eh retornado <code>null</code> se nao houver
	 *         mais contas no array.
	 */
	ContaAbstrata next();

}
