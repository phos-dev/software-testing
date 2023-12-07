package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.ufrpe.poo.banco.dados.RepositorioContasArquivoBin;
import br.ufrpe.poo.banco.exceptions.AtualizacaoNaoRealizadaException;
import br.ufrpe.poo.banco.exceptions.ClienteJaCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteJaPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaJaAssociadaException;
import br.ufrpe.poo.banco.exceptions.ContaJaCadastradaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.InicializacaoSistemaException;
import br.ufrpe.poo.banco.exceptions.RenderBonusContaEspecialException;
import br.ufrpe.poo.banco.exceptions.RenderJurosPoupancaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;
import br.ufrpe.poo.banco.exceptions.ValorInvalidoException;

public class TesteBanco {

	private static Banco banco;

	@Before
	public void apagarArquivos() throws IOException, RepositorioException,
			InicializacaoSistemaException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.dat"));
		bw.close();
		bw = new BufferedWriter(new FileWriter("contas.dat"));
		bw.close();
		
		Banco.instance = null;
		TesteBanco.banco = Banco.getInstance();
	}

	/**
	 * Verifica o cadastramento de uma nova conta.
	 * 
	 */
	@Test
	public void testeCadastarNovaConta() throws RepositorioException,
			ContaJaCadastradaException, ContaNaoEncontradaException,
			InicializacaoSistemaException {

		Banco banco = new Banco(null, new RepositorioContasArquivoBin());
		ContaAbstrata conta1 = new Conta("1", 100);
		banco.cadastrar(conta1);
		ContaAbstrata conta2 = banco.procurarConta("1");
		assertEquals(conta1.getNumero(), conta2.getNumero());
		assertEquals(conta1.getSaldo(), conta2.getSaldo(), 0);
	}

	/**
	 * Verifica que nao e permitido cadastrar duas contas com o mesmo numero.
	 * 
	 */
	@Test(expected = ContaJaCadastradaException.class)
	public void testeCadastrarContaExistente() throws RepositorioException,
			ContaJaCadastradaException, ContaNaoEncontradaException,
			InicializacaoSistemaException {

		Conta c1 = new Conta("1", 200);
		Conta c2 = new Conta("1", 300);
		banco.cadastrar(c1);
		banco.cadastrar(c2);
		fail("Excecao ContaJaCadastradaException nao levantada");
	}

	/**
	 * Verifica se o credito esta sendo executado corretamente em uma conta
	 * corrente.
	 * 
	 */
	@Test
	public void testeCreditarContaExistente() throws RepositorioException,
			ContaNaoEncontradaException, InicializacaoSistemaException,
			ContaJaCadastradaException, ValorInvalidoException {

		ContaAbstrata conta = new Conta("1", 100);
		banco.cadastrar(conta);
		banco.creditar(conta, 100);
		conta = banco.procurarConta("1");
		assertEquals(200, conta.getSaldo(), 0);
	}

	/**
	 * Verifica a excecao levantada na tentativa de creditar em uma conta que
	 * nao existe.
	 * 
	 */
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeCreditarContaInexistente() throws RepositorioException,
			ContaNaoEncontradaException, InicializacaoSistemaException,
			ValorInvalidoException {

		banco.creditar(new Conta("", 0), 200);

		fail("Excecao ContaNaoEncontradaException nao levantada");
	}

	/**
	 * Verifica que a operacao de debito em conta corrente esta acontecendo
	 * corretamente.
	 * 
	 */
	@Test
	public void testeDebitarContaExistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ContaJaCadastradaException,
			ValorInvalidoException {

		ContaAbstrata conta = new Conta("1", 50);
		banco.cadastrar(conta);
		banco.debitar(conta, 50);
		conta = banco.procurarConta("1");
		assertEquals(0, conta.getSaldo(), 0);
	}

	/**
	 * Verifica que tentantiva de debitar em uma conta que nao existe levante
	 * excecao.
	 * 
	 */
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeDebitarContaInexistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException {

		banco.debitar(new Conta("", 0), 50);
		fail("Excecao ContaNaoEncontradaException nao levantada");
	}
	
	@Test(expected = ValorInvalidoException.class)
	public void testeDebitarContaValorInvalido() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException, ContaJaCadastradaException, ClienteJaCadastradoException {
		Cliente cliente = new Cliente("john", "123");
        ContaAbstrata conta1 = new Conta("1", 100);
        banco.cadastrarCliente(cliente);
        banco.cadastrar(conta1);
        banco.associarConta("123", "1");
		banco.debitar(conta1, -20);
		fail("Excecao ValorInvalidoException nao levantada)");
	}


	/**
	 * Verifica que a transferencia entre contas correntes e realizada com
	 * sucesso.
	 * 
	 */
	@Test
	public void testeTransferirContaExistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ContaJaCadastradaException,
			ValorInvalidoException {

		ContaAbstrata conta1 = new Conta("1", 100);
		ContaAbstrata conta2 = new Conta("2", 200);
		banco.cadastrar(conta1);
		banco.cadastrar(conta2);
		banco.transferir(conta1, conta2, 50);
		conta1 = banco.procurarConta("1");
		conta2 = banco.procurarConta("2");
		assertEquals(50, conta1.getSaldo(), 0);
		assertEquals(250, conta2.getSaldo(), 0);
	}

	/**
	 * Verifica que tentativa de transferir entre contas cujos numeros nao
	 * existe levanta excecao.
	 * 
	 */
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeTransferirContaInexistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException {

		banco.transferir(new Conta("", 0), new Conta("", 0), 50);
		fail("Excecao ContaNaoEncontradaException nao levantada)");
	}
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeTransferirContaDestinoInexistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {
		ContaAbstrata conta1 = new Conta("1", 100);
		banco.cadastrar(conta1);
		banco.transferir(conta1, new Conta("", 0), 50);
		fail("Excecao ContaNaoEncontradaException nao levantada)");
	}
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeTransferirContaOrigemInexistente() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {
		ContaAbstrata conta1 = new Conta("1", 100);
		banco.cadastrar(conta1);
		banco.transferir(new Conta("", 0),conta1, 50);
		fail("Excecao ContaNaoEncontradaException nao levantada)");
	}
	@Test
	public void testeTransferir() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {
		ContaAbstrata conta1 = new Conta("1", 100);
		ContaAbstrata conta2 = new Conta("2", 200);
		banco.cadastrar(conta1);
		banco.cadastrar(conta2);
		banco.transferir(conta1, conta2, 50);
		assertEquals((long) banco.contas.procurar(conta2.numero).saldo, 250);
		assertEquals((long) banco.contas.procurar(conta1.numero).saldo, 50);
	}
	
	@Test(expected = ValorInvalidoException.class)
	public void testeTransferirContaValorInvalido() throws RepositorioException,
			ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException, ContaJaCadastradaException, ClienteJaCadastradoException {
		Cliente cliente = new Cliente("john", "123");
        ContaAbstrata conta1 = new Conta("1", 100);
        ContaAbstrata conta2 = new Conta("2", 100);
        banco.cadastrarCliente(cliente);
        banco.cadastrar(conta1);
        banco.cadastrar(conta2);
        banco.associarConta("123", "1");
        banco.associarConta("123", "2");
		banco.transferir(conta1, conta2, -20);
		fail("Excecao ValorInvalidoException nao levantada)");
	}


	/**
	 * Verifica que render juros de uma conta poupanca funciona corretamente
	 * 
	 */
	@Ignore
	@Test
	public void testeRenderJurosContaExistente() throws RepositorioException,
			ContaNaoEncontradaException, RenderJurosPoupancaException,
			InicializacaoSistemaException, ContaJaCadastradaException {

		Poupanca poupanca = new Poupanca("20", 100);
		banco.cadastrar(poupanca);
		double saldoSemJuros = poupanca.getSaldo();
		double saldoComJuros = saldoSemJuros + (saldoSemJuros * 0.008);
		poupanca.renderJuros(0.008);
		assertEquals(saldoComJuros, poupanca.getSaldo(), 0);
	}

	/**
	 * Verifica que tentativa de render juros em conta inexistente levanta
	 * excecao.
	 * 
	 */
	@Ignore
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeRenderJurosContaInexistente() throws RepositorioException,
			ContaNaoEncontradaException, RenderJurosPoupancaException,
			InicializacaoSistemaException {

		fail("Nao implementado");
	}

	/**
	 * Verifica que tentativa de render juros em conta que nao e poupanca
	 * levanta excecao.
	 * 
	 */
	@Ignore
	@Test(expected = RenderJurosPoupancaException.class)
	public void testeRenderJurosContaNaoEhPoupanca()
			throws RepositorioException, ContaNaoEncontradaException,
			RenderJurosPoupancaException, InicializacaoSistemaException,
			ContaJaCadastradaException {

		fail("Nao implementado");
	}

	/**
	 * Verifica que render bonus de uma conta especial funciona corretamente.
	 * 
	 */
	@Ignore
	@Test
	public void testeRenderBonusContaEspecialExistente()
			throws RepositorioException, ContaNaoEncontradaException,
			RenderBonusContaEspecialException, InicializacaoSistemaException,
			RenderJurosPoupancaException, ContaJaCadastradaException {

		fail("Nao implementado");
	}

	/**
	 * Verifica que a tentativa de render bonus em inexistente levanta excecao.
	 * 
	 */
	@Ignore
	@Test(expected = ContaNaoEncontradaException.class)
	public void testeRenderBonusContaEspecialNaoInexistente()
			throws RepositorioException, ContaNaoEncontradaException,
			RenderBonusContaEspecialException, InicializacaoSistemaException,
			RenderJurosPoupancaException {

		fail("Nao implementado");
	}

	/**
	 * Verifica que tentativa de render bonus em conta que nao e especial
	 * levante excecao.
	 */
	@Ignore
	@Test(expected = RenderBonusContaEspecialException.class)
	public void testeRenderBonusContaNaoEspecial() throws RepositorioException,
			ContaNaoEncontradaException, RenderBonusContaEspecialException,
			InicializacaoSistemaException, RenderJurosPoupancaException,
			ContaJaCadastradaException {

		fail("Nao implementado");
	}
	
	@Test(expected = ClienteNaoCadastradoException.class)
    public void testeRemoverClienteInexistente() throws RepositorioException,
            ClienteNaoCadastradoException, ContaNaoEncontradaException,
            ClienteNaoPossuiContaException {
        banco.removerCliente("12345678901");
        fail("Excecao ClienteNaoCadastradoException nao levantada");
    }
	
	@Test(expected = ClienteJaCadastradoException.class)
    public void testeCadastroClienteErroExiste() throws RepositorioException,
            ClienteNaoCadastradoException, ContaNaoEncontradaException, ClienteJaCadastradoException,
            ClienteNaoPossuiContaException {
		Cliente cliente = new Cliente("john", "123");
        banco.cadastrarCliente(cliente);
        banco.cadastrarCliente(cliente);
        fail("Excecao ClienteJaCadastradoException nao levantada");
    }
	
	@Test(expected = ContaJaAssociadaException.class)
    public void testeAssociarContaNaoExiste() throws RepositorioException, ClienteJaCadastradoException, ContaJaCadastradaException, ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException {
		Cliente cliente = new Cliente("john", "123");
        banco.cadastrarCliente(cliente);
        banco.associarConta("123", "");
        fail("Excecao ContaJaAssociadaException nao levantada");
    }
	
	@Test(expected = ClienteNaoCadastradoException.class)
    public void testeAssociarContaClienteNaoExiste() throws RepositorioException, ClienteJaCadastradoException, ContaJaCadastradaException, ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException {
        banco.associarConta("", "");
        fail("Excecao ClienteNaoCadastradoException nao levantada");
    }

	@Test
    public void removerClienteComContasTest() throws RepositorioException,
            ClienteNaoCadastradoException, ContaNaoEncontradaException,
            ClienteNaoPossuiContaException, ContaJaCadastradaException, ClienteJaCadastradoException, ClienteJaPossuiContaException, ContaJaAssociadaException {
        Cliente cliente = new Cliente("john", "123");
        ContaAbstrata conta1 = new Conta("1", 100);
        ContaAbstrata conta2 = new Conta("2", 100);
        banco.cadastrarCliente(cliente);
        banco.cadastrar(conta1);
        banco.cadastrar(conta2);
        banco.associarConta("123", "1");
        banco.associarConta("123", "2");
        banco.removerCliente("123");
        assertNull(banco.procurarCliente("123"));
        assertNull(banco.procurarConta("1"));
        assertNull(banco.procurarConta("2"));
    }
	
	@Test(expected = AtualizacaoNaoRealizadaException.class)
    public void testClienteErroAtualizar() throws RepositorioException,
            ClienteNaoCadastradoException, ContaNaoEncontradaException,
            ClienteNaoPossuiContaException, ContaJaCadastradaException, ClienteJaCadastradoException, ClienteJaPossuiContaException, ContaJaAssociadaException, AtualizacaoNaoRealizadaException {
        banco.atualizarCliente(new Cliente("john", "123"));
		fail("Excecao ValorInvalidoException nao levantada)");
    }
	

	@Test
    public void testClienteAtualizar() throws RepositorioException,
            ClienteNaoCadastradoException, ContaNaoEncontradaException,
            ClienteNaoPossuiContaException, ContaJaCadastradaException, ClienteJaCadastradoException, ClienteJaPossuiContaException, ContaJaAssociadaException, AtualizacaoNaoRealizadaException {
        Cliente cliente = new Cliente("john", "123");
        banco.cadastrarCliente(cliente);
        cliente.setNome("abc");
		banco.atualizarCliente(cliente);
		assertEquals(banco.clientes.procurar(cliente.getCpf()).nome, "abc");
    }
	
}
