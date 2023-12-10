package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
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
	public void apagarArquivos() throws IOException, RepositorioException, InicializacaoSistemaException {

		BufferedWriter bw = new BufferedWriter(new FileWriter("clientes.dat"));
		bw.close();
		bw = new BufferedWriter(new FileWriter("contas.dat"));
		bw.close();

		Banco.instance = null;
		TesteBanco.banco = Banco.getInstance();
	}

	@Test
	public void testeCadastarNovaConta() throws RepositorioException, ContaJaCadastradaException,
			ContaNaoEncontradaException, InicializacaoSistemaException {

		Banco banco = new Banco(null, new RepositorioContasArquivoBin());
		ContaAbstrata conta1 = new Conta("1", 100);
		banco.cadastrar(conta1);
		ContaAbstrata conta2 = banco.procurarConta("1");
		assertEquals(conta1.getNumero(), conta2.getNumero());
		assertEquals(conta1.getSaldo(), conta2.getSaldo(), 0);
	}

	@Test(expected = ContaJaCadastradaException.class)
	public void testeCadastrarContaExistente() throws RepositorioException, ContaJaCadastradaException,
			ContaNaoEncontradaException, InicializacaoSistemaException {

		Conta c1 = new Conta("1", 200);
		Conta c2 = new Conta("1", 300);
		banco.cadastrar(c1);
		banco.cadastrar(c2);
		fail("Excecao ContaJaCadastradaException nao levantada");
	}

	@Test
	public void testeCreditarContaExistente() throws RepositorioException, ContaNaoEncontradaException,
			InicializacaoSistemaException, ContaJaCadastradaException, ValorInvalidoException {

		ContaAbstrata conta = new Conta("1", 100);
		banco.cadastrar(conta);
		banco.creditar(conta, 100);
		conta = banco.procurarConta("1");
		assertEquals(200, conta.getSaldo(), 0);
	}

	@Test(expected = ValorInvalidoException.class)
	public void testeCreditarSaldoNegativo() throws RepositorioException, ContaNaoEncontradaException,
			InicializacaoSistemaException, ContaJaCadastradaException, ValorInvalidoException {

		ContaAbstrata conta = new Conta("1", 100);
		banco.cadastrar(conta);
		banco.creditar(conta, -20);
	}

	@Test(expected = ContaNaoEncontradaException.class)
	public void testeCreditarContaInexistente() throws RepositorioException, ContaNaoEncontradaException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {

		ContaAbstrata conta = new Conta("123", 300);
		banco.cadastrar(conta);
		banco.creditar(conta, 200);
		assertEquals((long) banco.procurarConta("123").saldo, 500);
	}

	@Test
	public void testeCreditarValorNegativo() throws RepositorioException, ContaNaoEncontradaException,
			InicializacaoSistemaException, ValorInvalidoException {
		try {
			banco.creditar(new Conta("", 0), 200);
			fail("Excecao ValorInvalidoException nao levantada");
		} catch (Exception ex) {
			fail("Excecao errada levantada");
		}
	}

	@Test
	public void testeDebitarContaExistente()
			throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ContaJaCadastradaException, ValorInvalidoException {

		ContaAbstrata conta = new Conta("1", 50);
		banco.cadastrar(conta);
		banco.debitar(conta, 50);
		conta = banco.procurarConta("1");
		assertEquals(0, conta.getSaldo(), 0);
	}

	@Test
	public void testeDebitarContaInexistente()
			throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ContaJaCadastradaException, ValorInvalidoException {
		banco.debitar(new Conta("John", 123), 20);
	}

	@Test(expected = ValorInvalidoException.class)
	public void testeDebitarValorInvalido() throws RepositorioException, ContaNaoEncontradaException,
			SaldoInsuficienteException, InicializacaoSistemaException, ValorInvalidoException {

		banco.debitar(new Conta("", 0), -10);
		fail("Excecao ValorInvalidoException nao levantada");
	}

	@Test(expected = ValorInvalidoException.class)
	public void testeDebitarContaValorInvalido() throws RepositorioException, ContaNaoEncontradaException,
			SaldoInsuficienteException, InicializacaoSistemaException, ValorInvalidoException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException,
			ContaJaCadastradaException, ClienteJaCadastradoException {
		Cliente cliente = new Cliente("john", "123");
		ContaAbstrata conta1 = new Conta("1", 100);
		banco.cadastrarCliente(cliente);
		banco.associarConta("123", "1");
		banco.cadastrar(conta1);
		banco.debitar(conta1, -20);
		fail("Excecao ValorInvalidoException nao levantada)");
	}

	@Test
	public void testeTransferirContaExistente()
			throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ContaJaCadastradaException, ValorInvalidoException {

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

	@Test
	public void testeTransferirContaInexistente() throws RepositorioException, ContaNaoEncontradaException,
			SaldoInsuficienteException, InicializacaoSistemaException, ValorInvalidoException {
		try {
			banco.transferir(new Conta("", 0), new Conta("", 0), 50);
			fail("Excecao ContaNaoEncontradaException nao levantada)");
		} catch (Exception ex) {
			fail("Excecao errada levantada");
		}
	}

	@Test
	public void testeTransferirContaDestinoInexistente()
			throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {

		try {
			ContaAbstrata conta1 = new Conta("1", 100);
			banco.cadastrar(conta1);
			banco.transferir(conta1, new Conta("", 0), 50);
			fail("Excecao ContaNaoEncontradaException nao levantada)");
		} catch (Exception ex) {
			fail("Excecao errada levantada");
		}
	}

	@Test
	public void testeTransferirContaOrigemInexistente()
			throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
			InicializacaoSistemaException, ValorInvalidoException, ContaJaCadastradaException {
		try {
			ContaAbstrata conta1 = new Conta("1", 100);
			banco.cadastrar(conta1);
			banco.transferir(new Conta("", 0), conta1, 50);
			fail("Excecao ContaNaoEncontradaException nao levantada");
		} catch (Exception ex) {
			fail("Excecao errada levantada");
		}
	}

	@Test
	public void testeTransferir() throws RepositorioException, ContaNaoEncontradaException, SaldoInsuficienteException,
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
	public void testeTransferirContaValorInvalido() throws RepositorioException, ContaNaoEncontradaException,
			SaldoInsuficienteException, InicializacaoSistemaException, ValorInvalidoException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException,
			ContaJaCadastradaException, ClienteJaCadastradoException {
		Cliente cliente = new Cliente("john", "123");
		ContaAbstrata conta1 = new Conta("1", 100);
		ContaAbstrata conta2 = new Conta("2", 100);
		banco.cadastrarCliente(cliente);
		banco.associarConta("123", "1");
		banco.associarConta("123", "2");
		banco.cadastrar(conta1);
		banco.cadastrar(conta2);
		banco.transferir(conta1, conta2, -20);
		fail("Excecao ValorInvalidoException nao levantada)");
	}

	@Test
	public void testeRenderJurosContaExistente() throws RepositorioException, ContaNaoEncontradaException,
			RenderJurosPoupancaException, InicializacaoSistemaException, ContaJaCadastradaException {

		Poupanca poupanca = new Poupanca("20", 100);
		banco.cadastrar(poupanca);
		double saldoSemJuros = poupanca.getSaldo();
		double saldoComJuros = saldoSemJuros * 1.5;
		banco.renderJuros(poupanca);
		assertEquals(saldoComJuros, banco.procurarConta("20").getSaldo(), 0);
	}

	@Test
	public void testeRenderBonusContaExistente()
			throws RepositorioException, ContaNaoEncontradaException, RenderJurosPoupancaException,
			InicializacaoSistemaException, ContaJaCadastradaException, RenderBonusContaEspecialException {

		ContaEspecial conta = new ContaEspecial("20", 100);
		double saldoSemBonus = conta.getSaldo();
		conta.creditar(1);
		banco.cadastrar(conta);
		double saldoComBonus = saldoSemBonus + 1.01;
		banco.renderBonus(conta);
		assertEquals(saldoComBonus, banco.procurarConta("20").getSaldo(), 0);
	}

	@Test(expected = ContaNaoEncontradaException.class)
	public void testeRenderBonusContaNaoExistente()
			throws RepositorioException, ContaNaoEncontradaException, RenderJurosPoupancaException,
			InicializacaoSistemaException, ContaJaCadastradaException, RenderBonusContaEspecialException {

		ContaEspecial conta = new ContaEspecial("20", 100);
		banco.renderBonus(conta);
	}

	@Test(expected = RenderBonusContaEspecialException.class)
	public void testeRenderBonusContaNaoEspecial()
			throws RepositorioException, ContaNaoEncontradaException, RenderJurosPoupancaException,
			InicializacaoSistemaException, ContaJaCadastradaException, RenderBonusContaEspecialException {

		ContaAbstrata conta = new Conta("20", 100);
		banco.renderBonus(conta);
	}

	@Test(expected = RenderJurosPoupancaException.class)
	public void testeRenderJurosContaNaoPoupanca() throws RepositorioException, ContaNaoEncontradaException,
			RenderJurosPoupancaException, InicializacaoSistemaException, ContaJaCadastradaException {

		ContaAbstrata conta = new Conta("20", 100);
		banco.renderJuros(conta);
	}

	@Test(expected = ContaNaoEncontradaException.class)
	public void testeRenderJurosContaNaoExistente() throws RepositorioException, ContaNaoEncontradaException,
			RenderJurosPoupancaException, InicializacaoSistemaException, ContaJaCadastradaException {

		Poupanca poupanca = new Poupanca("20", 100);
		banco.renderJuros(poupanca);
	}

	@Test
	public void testeRemoverClienteInexistente() throws RepositorioException, ClienteNaoCadastradoException,
			ContaNaoEncontradaException, ClienteNaoPossuiContaException {
		try {
			banco.removerCliente("12345678901");
			fail("Excecao ClienteNaoCadastradoException nao levantada");
		} catch (ClienteNaoCadastradoException ex) {
			throw ex;
		} catch (Exception ex) {
			fail("Excecao errada levantada");
		}
	}

	@Test(expected = ClienteJaCadastradoException.class)
	public void testeCadastroClienteErroExiste() throws RepositorioException, ClienteNaoCadastradoException,
			ContaNaoEncontradaException, ClienteJaCadastradoException, ClienteNaoPossuiContaException {
		Cliente cliente = new Cliente("john", "123");
		banco.cadastrarCliente(cliente);
		banco.cadastrarCliente(cliente);
		fail("Excecao ClienteJaCadastradoException nao levantada");
	}

	@Test(expected = ContaJaAssociadaException.class)
	public void testeAssociarContaNaoExiste()
			throws RepositorioException, ClienteJaCadastradoException, ContaJaCadastradaException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException {
		Cliente cliente = new Cliente("john", "123");
		ContaAbstrata conta = new Conta("abc123", 100);
		banco.cadastrarCliente(cliente);
		banco.cadastrar(conta);
		banco.associarConta("123", "abc123");
		fail("Excecao ContaJaAssociadaException nao levantada");
	}

	@Test(expected = ClienteNaoCadastradoException.class)
	public void testeAssociarContaClienteNaoExiste()
			throws RepositorioException, ClienteJaCadastradoException, ContaJaCadastradaException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, ClienteNaoCadastradoException {
		banco.associarConta("", "");
		fail("Excecao ClienteNaoCadastradoException nao levantada");
	}

	@Test
	public void removerClienteComContaTest() throws RepositorioException, ClienteNaoCadastradoException,
			ContaNaoEncontradaException, ClienteNaoPossuiContaException, ContaJaCadastradaException,
			ClienteJaCadastradoException, ClienteJaPossuiContaException, ContaJaAssociadaException {
		Cliente cliente = new Cliente("john", "123");
		ContaAbstrata conta = new Conta("abc123", 100);
		banco.cadastrarCliente(cliente);
		banco.associarConta("123", "abc123");
		banco.cadastrar(conta);

		banco.removerCliente("123");
		assertNull(banco.procurarCliente("123"));
		assertNull(banco.procurarConta("1"));
	}

	@Test
	public void removerClienteComContasTest() throws RepositorioException, ClienteNaoCadastradoException,
			ContaNaoEncontradaException, ClienteNaoPossuiContaException, ContaJaCadastradaException,
			ClienteJaCadastradoException, ClienteJaPossuiContaException, ContaJaAssociadaException {
		try {
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
		} catch (Exception exp) {
			fail("Excessão não esperada ao associar mais de uma conta");
		}
	}

	@Test(expected = AtualizacaoNaoRealizadaException.class)
	public void testClienteErroAtualizar()
			throws RepositorioException, ClienteNaoCadastradoException, ContaNaoEncontradaException,
			ClienteNaoPossuiContaException, ContaJaCadastradaException, ClienteJaCadastradoException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, AtualizacaoNaoRealizadaException {
		banco.atualizarCliente(new Cliente("john", "123"));
		fail("Excecao ValorInvalidoException nao levantada)");
	}

	@Test
	public void testClienteAtualizar()
			throws RepositorioException, ClienteNaoCadastradoException, ContaNaoEncontradaException,
			ClienteNaoPossuiContaException, ContaJaCadastradaException, ClienteJaCadastradoException,
			ClienteJaPossuiContaException, ContaJaAssociadaException, AtualizacaoNaoRealizadaException {
		Cliente cliente = new Cliente("john", "123");
		banco.cadastrarCliente(cliente);
		cliente.setNome("abc");
		banco.atualizarCliente(cliente);
		assertEquals(banco.clientes.procurar(cliente.getCpf()).nome, "abc");
	}

}
