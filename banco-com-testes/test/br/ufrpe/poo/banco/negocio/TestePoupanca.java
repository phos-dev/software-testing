package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestePoupanca {

	@Test
	public final void testRenderJurosZero() {
		Poupanca p = new Poupanca("4432",1000);
		double saldoAntes = p.getSaldo();
		p.renderJuros(0);
		assertEquals("Saldo nao deveria mudar", saldoAntes, p.getSaldo(), 0);
	}
	
	@Test
	public final void testRenderJurosCinquentaPorcento() {
		Poupanca p = new Poupanca("4432",1000);
		p.renderJuros(0.5);
		assertEquals(1500, p.getSaldo(), 0);
	}


}
