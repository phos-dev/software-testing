package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesteContaEspecial {

	/**
	 * Testa se os parametros do construtor sao passados corretamente
	 */
	@Test
	public void testeConstrutor() {
		ContaEspecial c = new ContaEspecial("1",100);
		assertEquals("Numero incorreto", "1", c.getNumero());
		assertEquals("Saldo incorreto", 100,  c.getSaldo(), 0);
		assertEquals("Bonus incorreto", 50,  c.getBonus(), 0);
	}
	
	/**
	 * Testa que nao e permitido criar uma conta com bonus negativo
	 */
	@Test 
	public void testeConstrutorBonusNegativo() {
		ContaEspecial c = new ContaEspecial("1",1000);
		assertEquals(0,c.getBonus(), 0);
	}
	
	/**
	 * Testa que a operação de credito modifica o bonus
	 */
	@Test
	public void testeCreditarBonusOk() {
		ContaEspecial c = new ContaEspecial("1",900);
		c.creditar(100);
		assertEquals(101, c.getBonus(), 0);
	}

	/**
	 * Testa que a operação de render bonus credita na conta o valor de bonus e zera o bonus
	 */
	@Test
	public void testeRenderBonusSaldoOk() {
		ContaEspecial c = new ContaEspecial("6564",2000);
		c.renderBonus();
		assertEquals(2100, c.getSaldo(), 0);
		assertEquals(0, c.getBonus(), 0);
	}	

}
