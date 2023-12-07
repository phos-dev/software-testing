package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.ufrpe.poo.banco.exceptions.ClienteJaPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;

/**
 * Classe de teste respons�vel por testar as condi��es dos m�todos
 * adicionarConta e removerConta da classe Cliente.
 * 
 * @author Aluno
 * 
 */
public class TesteCliente {

	/**
	 * Testa a inser��o de uma nova conta vinculada ao cliente
	 */
	@Test
	public void adicionarContaTest() {
		Cliente c1 = new Cliente("nome", "123");
		try {
			c1.adicionarConta("1");
		} catch (ClienteJaPossuiContaException e) {
			fail();
		}
		assertEquals(c1.procurarConta("1"), 0);
	}

	@Test
	public void getNomeTest() {
		Cliente c1 = new Cliente("nome", "123");
		assertEquals(c1.getNome(), "nome");
	}
	
	@Test
	public void setNomeTest() {
		Cliente c1 = new Cliente("a", "123");
		c1.setNome("nome");
		assertEquals(c1.getNome(), "nome");
	}
	
	@Test
	public void getCpfTest() {
		Cliente c1 = new Cliente("a", "123");
		assertEquals(c1.getCpf(), "123");
	}
	
	@Test
	public void setCpfTest() {
		Cliente c1 = new Cliente("a", "123");
		c1.setCpf("1234");
		assertEquals(c1.getCpf(), "1234");
	}

	@Test
	public void getContasTest() throws ClienteJaPossuiContaException {
		Cliente c1 = new Cliente("a", "123");
		c1.adicionarConta("1");
		c1.adicionarConta("2");
		assertEquals(c1.getContas(), new ArrayList<String>(List.of("1", "2")));
	}
	
	@Test
	public void removeAllContasTest() throws ClienteJaPossuiContaException {
		Cliente c1 = new Cliente("a", "123");
		c1.adicionarConta("1");
		c1.adicionarConta("2");
		c1.removerTodasAsContas();
		assertEquals(c1.getContas(), null);
	}
	
	@Test
	public void consultarNumeroContaTest() throws ClienteJaPossuiContaException {
		Cliente c1 = new Cliente("a", "123");
		c1.adicionarConta("1");
		c1.adicionarConta("2");
		assertEquals(c1.consultarNumeroConta(1), "2");
	}

    @Test
    public void equalsSameClienteTest() {
        Cliente cliente = new Cliente("a", "123456789");
        assertTrue(cliente.equals(cliente));
    }

    @Test
    public void testEqualsEqualObjects() {
        Cliente c1 = new Cliente("a", "123456789");
        Cliente c2 = new Cliente("a", "123456789");
        assertTrue(c1.equals(c2));
    }

    @Test
    public void testEqualsDifferentObjects() {
        Cliente c1 = new Cliente("a", "123456789");
        Cliente c2 = new Cliente("b", "987654321"); 
        Cliente c3 = new Cliente("b", "123456789");
        assertFalse(c1.equals(c2));
        assertTrue(c1.equals(c3));
    }

    @Test
    public void testEqualsDifferentTypes() {
        Cliente c1 = new Cliente("a", "123456789");
        Object obj = new Object();
        assertFalse(c1.equals(obj));
    }

    @Test
    public void testEqualsNullObject() {
        Cliente c1 = new Cliente("a", "123456789");
        assertFalse(c1.equals(null));
    }

	@Test
	public void showClientAsStringTest() throws ClienteJaPossuiContaException {
		Cliente c1 = new Cliente("nome", "2345");
		c1.toString();
		assertEquals(c1.toString(), "Nome: nome\nCPF: 2345\nContas: []");
		c1.adicionarConta("1");
		assertEquals(c1.toString(), "Nome: nome\nCPF: 2345\nContas: [1]");
	}
	
	/**
	 * Testa a condi��o da tentativa de adicionar uma conta j� existente � lista
	 * de contas do cliente
	 * 
	 * @throws ClienteJaPossuiContaException
	 */
	@Test(expected = ClienteJaPossuiContaException.class)
	public void adicionarContaJaExistenteTest()
			throws ClienteJaPossuiContaException {
		Cliente c1 = new Cliente("nome", "123");
		c1.adicionarConta("1"); // adiciona a conta a 1� vez
		c1.adicionarConta("1"); // tentativa de adicionar a mesma conta
								// novamente
	}

	/**
	 * Teste a remo��o de uma conta da lista de contas do cliente
	 */
	@Test
	public void removerContaClienteTest() {
		Cliente c1 = new Cliente("nome", "123");
		try {
			c1.adicionarConta("1"); // adiciona conta com n�mero 1
			c1.removerConta("1"); // remove a conta de n�mero 1
		} catch (Exception e) {
			fail("Exce��o inesperada lancada!");
		}

		assertEquals(c1.procurarConta("1"), -1);
	}

	/**
	 * Testa a remo��o de uma determinada conta que n�o est� vinculada ao
	 * cliente
	 * 
	 * @throws ClienteNaoPossuiContaException
	 */
	@Test(expected = ClienteNaoPossuiContaException.class)
	public void removerContaClienteSemContaTest()
			throws ClienteNaoPossuiContaException {
		Cliente c1 = new Cliente("nome", "123");
		c1.removerConta("1"); // tenta remover a conta de um cliente sem contas
	}

}
