package br.ufrpe.poo.banco.dados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.iterator.IteratorContaAbstrata;
import br.ufrpe.poo.banco.negocio.Conta;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;
import br.ufrpe.poo.banco.negocio.ContaEspecial;
import br.ufrpe.poo.banco.negocio.ContaImposto;
import br.ufrpe.poo.banco.negocio.Poupanca;

/**
 * Implementacao de repositorio de contas que persiste contas em arquivo texto.
 * 
 * Cada linha do arquivo representa uma conta e segue o padrao:
 * <p>
 * tipoConta numero saldo bonus
 * <p>
 * tipoConta e um valor inteiro para o tipo da conta: 0 - Conta, 1 - Poupanca, 2
 * - ContaImposto e 3 - ContaEspecial
 */
public class RepositorioContasArquivoTxt implements IRepositorioContas {

	/** Contas do arquivo sao mantidas em memoria. */
	private RepositorioContasArray contas;

	/** Arquivo que armazena as contas. */
	private File arquivo;

	/**
	 * Constroi um repositorio que mantem contas em arquivo texto.
	 * 
	 * @param arquivo
	 *            arquivo texto com informacoes sobre as contas. Se arquivo nao
	 *            existe, sera criado um vazio.
	 * @throws RepositorioException
	 *             lancada caso o arquivo nao existe e nao pode ser criado.
	 */
	public RepositorioContasArquivoTxt(File arquivo)
			throws RepositorioException {
		contas = new RepositorioContasArray();
		this.arquivo = arquivo;
		if (!arquivo.exists()) {
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RepositorioException("Arquivo de contas \""
						+ this.arquivo.getAbsolutePath()
						+ this.arquivo.getName() + "\" nao pode ser criado!");
			}
		}
		this.lerArquivo();
	}

	/**
	 * Le todas as contas do arquivo e guarda no repositorio de array.
	 * 
	 * @throws RepositorioException
	 *             lancada em caso de erro na leitura do arquivo.
	 */
	private void lerArquivo() throws RepositorioException {
		Scanner s = null;
		ContaAbstrata conta = null;
		try {
			s = new Scanner(arquivo);
			while (s.hasNext()) {
				int tipoConta = s.nextInt();
				String numero = s.next();
				double saldo = Double.parseDouble(s.next());
				switch (tipoConta) {
				case 0:
					conta = new Conta(numero, saldo);
					break;
				case 1:
					conta = new Poupanca(numero, saldo);
					break;
				case 2:
					conta = new ContaImposto(numero, saldo);
					break;
				case 3:
					conta = new ContaEspecial(numero, saldo);
					break;
				default:
					throw new RepositorioException("Tipo de conta inexistente!");
				}
				this.contas.inserir(conta);
			}
		} catch (FileNotFoundException e) {
			throw new RepositorioException(e);
		} finally {
			s.close();
		}
	}

	/**
	 * Concatena as informacoes da conta no fim do arquivo.
	 * 
	 * @param conta
	 *            conta a ser escrita.
	 * @throws RepositorioException
	 *             levantada no caso de erro com o arquivo.
	 */
	private void concatenarEmArquivo(ContaAbstrata conta)
			throws RepositorioException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(arquivo, true);
			if (conta instanceof Poupanca) {
				fw.write("1 " + conta.getNumero() + " " + conta.getSaldo());
			} else if (conta instanceof ContaImposto) {
				fw.write("2 " + conta.getNumero() + " " + conta.getSaldo());
			} else if (conta instanceof ContaEspecial) {
				fw.write("3 " + conta.getNumero() + " " + conta.getSaldo()
						+ " " + ((ContaEspecial) conta).getBonus());
			} else if (conta instanceof Conta) {
				fw.write("0 " + conta.getNumero() + " " + conta.getSaldo());
			} else {
				throw new RepositorioException(new Exception("Erro interno!"));
			}
			fw.write("\n");
		} catch (IOException e) {
			throw new RepositorioException(e);
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				throw new RepositorioException(e);
			}
		}
	}

	/**
	 * Escreve todas as contas do array em um arquivo texto.
	 * 
	 * @throws RepositorioException
	 *             levantada no caso de um erro com o arquivo.
	 */
	private void gravarArquivo() throws RepositorioException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(arquivo);
			IteratorContaAbstrata it = contas.getIterator();
			while (it.hasNext()) {
				ContaAbstrata c = it.next();
				if (c instanceof Conta) {
					fw.write("0 " + c.getNumero() + " " + c.getSaldo());
				} else if (c instanceof Poupanca) {
					fw.write("1 " + c.getNumero() + " " + c.getSaldo());
				} else if (c instanceof ContaImposto) {
					fw.write("2 " + c.getNumero() + " " + c.getSaldo());
				} else if (c instanceof ContaEspecial) {
					fw.write("3 " + c.getNumero() + " " + c.getSaldo() + " "
							+ ((ContaEspecial) c).getBonus());
				} else {
					throw new RepositorioException(new Exception(
							"Tipo de conta nao suportado!"));
				}
				fw.write("\n");
			}
		} catch (IOException e) {
			throw new RepositorioException(e);
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				throw new RepositorioException(e);
			}
		}
	}

	@Override
	public boolean inserir(ContaAbstrata conta) throws RepositorioException {
		boolean sucesso = contas.inserir(conta);
		if (sucesso) {
			this.concatenarEmArquivo(conta);
		}
		return sucesso;
	}

	@Override
	public ContaAbstrata procurar(String numero) {
		return contas.procurar(numero);
	}

	@Override
	public boolean remover(String numero) throws RepositorioException {
		boolean sucesso = contas.remover(numero);
		if (sucesso) {
			this.gravarArquivo();
		}
		return sucesso;
	}

	@Override
	public boolean atualizar(ContaAbstrata conta) throws RepositorioException {
		boolean sucesso = contas.atualizar(conta);
		if (sucesso) {
			this.gravarArquivo();
		}
		return sucesso;
	}

	@Override
	public boolean existe(String numero) {
		return contas.existe(numero);
	}

	@Override
	public IteratorContaAbstrata getIterator() {
		return contas.getIterator();
	}
}