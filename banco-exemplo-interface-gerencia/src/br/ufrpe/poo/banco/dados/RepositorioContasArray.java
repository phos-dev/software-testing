package br.ufrpe.poo.banco.dados;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.iterator.IteratorContaAbstrata;
import br.ufrpe.poo.banco.iterator.IteratorContaAbstrataArray;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;

/**
 * Implementacao de repositorio que mantem as contas na memoria em um array.
 * 
 * Tamanho do array cresce sob demanda.
 */
public class RepositorioContasArray implements IRepositorioContas {

	/** Array que mantem as contas. */
	private ContaAbstrata[] contas;

	/** Proxima posicao livre no array. */
	private int indice;

	/**
	 * Constroi um repositorio com array.
	 * 
	 * Tamanho inicial do array sao 100 posicoes.
	 */
	public RepositorioContasArray() throws RepositorioException {
		contas = new ContaAbstrata[100];
		indice = 0;
	}

	/**
	 * Retorna o indice da conta no array.
	 * 
	 * @param numero
	 *            numero da conta cujo indice e retornado.
	 * @return indice da conta no array. Igual a this.indice caso a conta nao
	 *         exista.
	 */
	private int getIndice(String numero) {
		String n;
		boolean achou = false;
		int i = 0;
		while ((!achou) && (i < this.indice)) {
			n = contas[i].getNumero();
			if (n.equals(numero)) {
				achou = true;
			} else {
				i = i + 1;
			}
		}
		return i;
	}

	@Override
	public boolean inserir(ContaAbstrata conta) throws RepositorioException {
		if (this.existe(conta.getNumero())) {
			return false;
		}
		if (contas.length == indice) {
			ContaAbstrata[] aux = new ContaAbstrata[contas.length * 2];
			for (int i = 0; i < indice; i++) {
				aux[i] = contas[i];
			}
			this.contas = aux;
		}
		contas[indice] = conta;
		indice = indice + 1;
		return true;
	}

	@Override
	public ContaAbstrata procurar(String numero) {
		ContaAbstrata conta = null;
		int i = this.getIndice(numero);
		if (i < this.indice) {
			conta = this.contas[i];
		}
		return conta;
	}

	@Override
	public boolean remover(String numero) throws RepositorioException {
		int i = this.getIndice(numero);
		if (i < this.indice) {
			this.indice = this.indice - 1;
			this.contas[i] = this.contas[this.indice];
			this.contas[this.indice] = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean atualizar(ContaAbstrata conta) throws RepositorioException {
		int i = this.getIndice(conta.getNumero());
		if (i < this.indice) {
			this.contas[i] = conta;
			return true;
		}
		return false;
	}

	@Override
	public boolean existe(String numero) {
		int i = this.getIndice(numero);
		return (i != this.indice);
	}

	@Override
	public IteratorContaAbstrata getIterator() {
		return new IteratorContaAbstrataArray(this.contas);
	}
}