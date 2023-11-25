package br.ufrpe.poo.banco.negocio;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;
import br.ufrpe.poo.banco.exceptions.ValorInvalidoException;

/**
 * Interface que representa as operacoes do cliente no banco.
 * 
 * @author
 * 
 */
public interface ICliente {

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
	 * Credita um valor a conta de um cliente.
	 * 
	 * @param conta
	 *            Conta do cliente a ser creditada.
	 * @param valor
	 *            Valor do credito.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro na atualizacao do repositorio.
	 * @throws ValorInvalidoException
	 *             Lancada caso o valor do credito seja invalido.
	 */
	void creditar(ContaAbstrata conta, double valor)
			throws RepositorioException, ValorInvalidoException;

	/**
	 * Debita um valor da conta de um cliente.
	 * 
	 * @param conta
	 *            Conta do cliente a ser debitada.
	 * @param valor
	 *            Valor do debito.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro na atualizacao do repositorio.
	 * @throws SaldoInsuficienteException
	 *             Lancada caso o valor do debito ultrapasse o valor do saldo da
	 *             conta do cliente.
	 * @throws ValorInvalidoException
	 *             Lancada caso o valor do debito seja invalido.
	 */
	void debitar(ContaAbstrata conta, double valor)
			throws RepositorioException, SaldoInsuficienteException,
			ValorInvalidoException;

	/**
	 * Transfere um valor da conta de origem para uma conta de destino.
	 * 
	 * @param contaOrigem
	 *            Conta a ser debitada.
	 * @param contaDestino
	 *            Conta a ser creditada.
	 * @param valor
	 *            Valor da transferencia.
	 * @throws SaldoInsuficienteException
	 *             Lancada caso o valor do debito ultrapasse o valor do saldo da
	 *             conta do cliente.
	 * @throws RepositorioException
	 *             Lancada caso ocorra erro na atualizacao do repositorio.
	 * @throws ValorInvalidoException
	 *             Lancada caso o valor da transferencia seja invalido.
	 */
	void transferir(ContaAbstrata contaOrigem, ContaAbstrata contaDestino,
			double valor) throws SaldoInsuficienteException,
			RepositorioException, ValorInvalidoException;

}
