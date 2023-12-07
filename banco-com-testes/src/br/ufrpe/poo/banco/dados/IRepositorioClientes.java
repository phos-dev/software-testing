package br.ufrpe.poo.banco.dados;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.iterator.IteratorCliente;
import br.ufrpe.poo.banco.negocio.Cliente;

/**
 * Interface que representa um repositorio de objetos do tipo
 * <code>Cliente</code>.
 * 
 * @author
 * 
 */
public interface IRepositorioClientes {

	/**
	 * Insere um cliente no repositorio.
	 * 
	 * @param cliente
	 *            Cliente a ser inserido.
	 * @return se cliente foi inserido no respositorio. Se o cliente ja existe
	 *         no repositorio nao sera inserido.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 * 
	 */
	boolean inserir(Cliente cliente) throws RepositorioException;

	/**
	 * Procurar por um cliente no repositorio a partir do seu cpf.
	 * 
	 * @param cpf
	 *            Numero do cpf do cliente a ser retornado.
	 * @return cliente encontrado. Retorna <code>null</code> se o cliente nao
	 *         foi encontrado.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 */
	Cliente procurar(String cpf);

	/**
	 * Remove um cliente do repositorio.
	 * 
	 * @param cpf
	 *            Numero do cpf do cliente a ser removido.
	 * @return se cliente foi removido. Se cliente nao existe eh retornado
	 *         <code>false<code/>.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 */
	boolean remover(String cpf) throws RepositorioException;

	/**
	 * Atualiza os dados de um cliente no repositorio.
	 * 
	 * @param cliente
	 *            Cliente a ser atualizado.
	 * @return se foi atualizada. Se cliente nao existe eh retornado
	 *         <code>false</code>.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 */
	boolean atualizar(Cliente cliente) throws RepositorioException;

	/**
	 * Indica se existe um cliente no repositorio.
	 * 
	 * @param cpf
	 *            Numero do cpf do cliente a ser localizado.
	 * @return se cliente existe.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 */
	boolean existe(String cpf);

	/**
	 * Retorna um iterador para acessar sequencialmente os clientes do
	 * repositorio.
	 * 
	 * @return iterador para clientes.
	 * @throws RepositorioException
	 *             Lancada quando ocorre erro no repositorio.
	 */
	IteratorCliente getIterator();

}
