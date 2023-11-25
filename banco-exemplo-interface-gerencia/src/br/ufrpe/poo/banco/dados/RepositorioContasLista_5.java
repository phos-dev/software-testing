package br.ufrpe.poo.banco.dados;

import java.util.ArrayList;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.iterator.IteratorContaAbstrata;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;

public class RepositorioContasLista_5 implements IRepositorioContas {

	private ArrayList<ContaAbstrata> contas = new ArrayList<ContaAbstrata>();
	
	@Override
	public boolean inserir(ContaAbstrata conta) throws RepositorioException {
		if (!contas.contains(conta)) {
			this.contas.add(conta);
			return true;
		}
		return false;
	}

	@Override
	public ContaAbstrata procurar(String numero) {
		for (int i=0; i < contas.size(); i++) {
			if (contas.get(i).getNumero() == numero) {
				return contas.get(i);
			}
		}
		return null;
	}

	@Override
	public boolean remover(String numero) throws RepositorioException {
		for (int i=0; i < contas.size(); i++) {
			if (contas.get(i).getNumero() == numero) {
				contas.remove(contas.get(i));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean atualizar(ContaAbstrata conta) throws RepositorioException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(String numero) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IteratorContaAbstrata getIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
