package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import br.ufrpe.poo.banco.dados.IRepositorioContas;
import br.ufrpe.poo.banco.exceptions.ContaJaCadastradaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.InicializacaoSistemaException;
import br.ufrpe.poo.banco.exceptions.RenderBonusContaEspecialException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;

/**
 * Testa a classe Banco independente da implementa��o dos repositorios.
 * 
 * @author sidneynogueira
 * 
 */
public class TesteBancoUnidade {

	public static Banco getBancoMock() {
		//IRepositorioContas contasMock = mock(IRepositorioContas.class);
		IRepositorioContas contasMock = mock();
		Banco bancoMock = new Banco(null, contasMock);
		return bancoMock;
	}

	@Test
	public void testCadastrarNovaConta() throws InicializacaoSistemaException,
			RepositorioException {

		Banco banco = getBancoMock();

		ContaAbstrata conta1 = new Conta("1", 0);

		// mocking para chamadas de metodos de repositorio de contas que sao
		// realizadas dentro do metodo procurarConta do banco
		when(banco.contas.inserir(conta1)).thenReturn(true);
		when(banco.contas.procurar("1")).thenReturn(conta1);

		try {
			banco.cadastrar(conta1);
			ContaAbstrata conta2 = banco.procurarConta("1");
			assertEquals(conta1, conta2);
		} catch (RepositorioException | ContaJaCadastradaException e) {
			fail("Excecao levantada quando nao deveria");
		}

	}

	@Test(expected = ContaJaCadastradaException.class)
	public void testCadastrarContaExiste()
			throws InicializacaoSistemaException, RepositorioException,
			ContaJaCadastradaException {

		Banco banco = getBancoMock();

		ContaAbstrata conta = new Conta("1", 0);

		// mocking para chamadas de metodos de repositorio de contas que sao
		// realizadas dentro do metodo cadastrar do banco
		banco.contas = mock(IRepositorioContas.class);
		when(banco.contas.inserir(conta)).thenReturn(false);

		try {
			banco.cadastrar(conta);
			fail("ContaJaCadastradaException nao foi lancada");
		} catch (RepositorioException e) {
			fail("Nao eh possivel erro de repositorio");
		}
	}

}