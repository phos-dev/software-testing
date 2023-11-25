package br.ufrpe.poo.banco.dados;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.iterator.IteratorCliente;
import br.ufrpe.poo.banco.iterator.IteratorClienteArray;
import br.ufrpe.poo.banco.negocio.Cliente;

/**
 * Implementacao do repositorio de clientes que persiste os objetos dos clientes
 * em array.
 * 
 * Tamanho do array cresce sobre demanda.
 * 
 * @author
 * 
 */
public class RepositorioClientesArray implements IRepositorioClientes {

	/**
	 * Array que mantem os clientes.
	 */
	private Cliente[] clientes;

	/**
	 * Proxima posicao livre do array.
	 */
	private int indice;

	/**
	 * Constroi um repositorio com array.
	 * 
	 * Tamanho inicial sao 100 posicoes.
	 */
	public RepositorioClientesArray() {
		this.clientes = new Cliente[100];
		this.indice = 0;
	}

	/**
	 * Retorna o indice do cliente no array.
	 * 
	 * @param cpf
	 *            Numero do cpf cujo indice eh retornado.
	 * @return indice do cliente no array. Igual a this.indice caso a conta nao
	 *         exista.
	 */
	private int getIndice(String cpf) {
		String n;
		boolean achou = false;
		int i = 0;
		while ((!achou) && (i < this.indice)) {
			n = clientes[i].getCpf();
			if (n.equals(cpf)) {
				achou = true;
			} else {
				i = i + 1;
			}
		}
		return i;
	}

	@Override
	public boolean inserir(Cliente cliente) throws RepositorioException {
		if (this.existe(cliente.getCpf())) {
			return false;
		}

		if (clientes.length == indice) {
			Cliente[] aux = new Cliente[clientes.length * 2];
			for (int i = 0; i < indice; i++) {
				aux[i] = clientes[i];
			}
			this.clientes = aux;
		}
		clientes[indice] = cliente;
		indice = indice + 1;
		return true;
	}

	@Override
	public Cliente procurar(String cpf) {
		Cliente cliente = null;
		int i = this.getIndice(cpf);
		if (i < this.indice) {
			cliente = this.clientes[i];
		}
		return cliente;
	}

	@Override
	public boolean remover(String cpf) throws RepositorioException {

		int i = this.getIndice(cpf);
		if (i < this.indice) {
			this.indice = this.indice - 1;
			this.clientes[i] = this.clientes[this.indice];
			this.clientes[this.indice] = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean atualizar(Cliente cliente) throws RepositorioException {
		int i = this.getIndice(cliente.getCpf());
		if (i < this.indice) {
			this.clientes[i] = cliente;
			return true;
		}
		return false;
	}

	@Override
	public boolean existe(String cpf) {
		int i = this.getIndice(cpf);
		return (i != this.indice);
	}

	@Override
	public IteratorCliente getIterator() {
		return new IteratorClienteArray(this.clientes);
	}

}
