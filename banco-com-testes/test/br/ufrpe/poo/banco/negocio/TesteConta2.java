package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesteConta2 {

	@Test
	public final void testDebitar() {
		Conta c = new Conta("1", 100);
		assertEquals("Numero incorreto", "1", c.getNumero());
	}

	@Test
	public final void testCreditar() {
		fail("Not yet implemented"); // TODO
	}
	

}
