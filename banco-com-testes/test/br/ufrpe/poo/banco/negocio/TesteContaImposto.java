package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;

public class TesteContaImposto {
	/**
	 * Testa se os parametros do construtor sao passados corretamente
	 */
	@Test
	public void testeConstrutor() {
		ContaImposto c = new ContaImposto("1",100);
		assertEquals("Numero incorreto", "1", c.getNumero());
		assertEquals("Saldo incorreto", 100,  c.getSaldo(), 0);
	}
	
	/**
	 * Testa debito com valor menor que o saldo
	 * @throws SaldoInsuficienteException 
	 */
	@Test 
	public void testeDebitar() throws SaldoInsuficienteException {
		ContaImposto c = new ContaImposto("2",1000);
		c.debitar(100);
		//String saldoDepoisDebito = String.valueOf(c.getSaldo());//confere valor do saldo
		assertTrue( c.getSaldo() == 899.62);
	}	
	
	@Test 
	public void testeDebitarSaldoInsuficiente() throws SaldoInsuficienteException {
		ContaImposto c = new ContaImposto("2",1000);
		c.debitar(1200);
		String saldoDepoisDebito = String.valueOf(c.getSaldo());//confere valor do saldo
		assertTrue( saldoDepoisDebito, c.getSaldo() == 899.62);
	}	

	
	/**
	 * Testa debito com valor maior que o saldo
	 * 
	 */
	/*@Test 
	public void testeDebitarAcimaSaldo() throws SaldoInsuficienteException {
		ContaImposto c = new ContaImposto("2",1000);
		c.debitar(1001);
		fail("Excecao correta(Saldo insuficiente");
		//assertTrue( saldoDepoisDebito, c.getSaldo() == 899.62);
	}*/		
	
}
