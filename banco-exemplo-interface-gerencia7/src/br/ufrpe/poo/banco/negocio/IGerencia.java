package br.ufrpe.poo.banco.negocio;

import br.ufrpe.poo.banco.exceptions.AtualizacaoNaoRealizadaException;
import br.ufrpe.poo.banco.exceptions.ClienteJaCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteJaPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaJaAssociadaException;
import br.ufrpe.poo.banco.exceptions.ContaJaCadastradaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;

/**
 * Interface que representa as operacoes do administrador (gerencia) do banco.
 * 
 * @author
 * 
 */
public interface IGerencia extends ICliente {

	/**
	 * Procurar por um cliente no repositorio a partir de seu cpf.
	 * 
	 * @param cpf
	 *            Valor do cpf do cliente a ser encontrado.
	 * @return Cliente encontrado. Retorna null se cliente nao for encontrado.
	 */
	Cliente procurarCliente(String cpf);

	/**
	 * Procurar por uma conta no repositorio a partir do seu numero.
	 * 
	 * @param numero
	 *            numero da conta a ser enconrada.
	 * @return Conta encontrada. Retorna null se a conta nao for encontrada.
	 */
	ContaAbstrata procurarConta(String numero);

	/**
	 * Insere um cliente no repositorio de clientes.
	 * 
	 * @param cliente
	 *            Cliente a ser inserido.
	 * @throws RepositorioException
	 *             Lancada caso ocorra um erro de gravacao do repositorio de
	 *             clientes.
	 * @throws ClienteJaCadastradoException
	 *             Lancada caso o cliente ja tenha sido cadastrado com os mesmos
	 *             dados.
	 */
	void cadastrarCliente(Cliente cliente) throws RepositorioException,
			ClienteJaCadastradoException;

	/**
	 * Insere uma conta no repositorio de contas.
	 * 
	 * @param conta
	 *            Conta a ser inserida.
	 * @throws RepositorioException
	 *             Lancada caso ocorra um erro de gravacao do repositorio de
	 *             contas.
	 * @throws ContaJaCadastradaException
	 */
	void cadastrar(ContaAbstrata conta) throws RepositorioException,
			ContaJaCadastradaException;

	/**
	 * Associa um numero de uma conta a um cliente.
	 * 
	 * @param cpf
	 *            Cpf do cliente que a conta deve ser associada.
	 * @param numeroConta
	 *            Numero da conta a ser associada com o cliente.
	 * @throws ClienteJaPossuiContaException
	 *             Lancada caso o cliente ja tenha uma conta com o numero
	 *             passado a associada a ele.
	 * @throws ContaJaAssociadaException
	 *             Lancada caso o numero da conta ja esteja associada a outro
	 *             cliente.
	 * @throws ClienteNaoCadastradoException
	 *             Lancada caso nao exista um cliente com o cpf passado.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro no repositorio.
	 */
	void associarConta(String cpf, String numeroConta)
			throws ClienteJaPossuiContaException, ContaJaAssociadaException,
			ClienteNaoCadastradoException, RepositorioException;

	/**
	 * Remove um cliente do repositorio.
	 * 
	 * @param cpf
	 *            Cpf do cliente a ser removido.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro de gravacao no repositorio.
	 * @throws ClienteNaoCadastradoException
	 *             Lancada caso nao exista um cliente com o cpf passado.
	 * @throws AtualizacaoNaoRealizadaException
	 *             Lancada caso ocorra erro da atualizacao do cliente no
	 *             repositorio.
	 * @throws ContaNaoEncontradaException
	 * @throws ClienteNaoPossuiContaException
	 */
	void removerCliente(String cpf) throws RepositorioException,
			ClienteNaoCadastradoException, AtualizacaoNaoRealizadaException,
			ContaNaoEncontradaException, ClienteNaoPossuiContaException;

	/**
	 * Remove uma conta do repositorio de contas e o numero da conta associada a
	 * um cliente.
	 * 
	 * @param cliente
	 *            Cliente que tera o numero da conta removida.
	 * @param numeroConta
	 *            Numero da conta a ser removida no repositorio de contas.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro na gravacao do repositorio.
	 * @throws ContaNaoEncontradaException
	 *             Lancada caso a conta nao exista.
	 * @throws ClienteNaoPossuiContaException
	 */
	void removerConta(Cliente cliente, String numeroConta)
			throws RepositorioException, ContaNaoEncontradaException,
			ClienteNaoPossuiContaException;

	/**
	 * Atualiza os dados cadastrais do cliente.
	 * 
	 * @param cliente
	 *            Cliente com os novos dados cadastrais.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro de gravacao do repositorio.
	 * @throws AtualizacaoNaoRealizadaException
	 *             Lancada caso ocorra algum erro na atualizacao.
	 */
	void atualizarCliente(Cliente cliente) throws RepositorioException,
			AtualizacaoNaoRealizadaException;

}
