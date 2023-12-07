package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestConta2 {

	@Test
	public final void test() {
		Conta c = new Conta("1",0);
		assertEquals(0, c.getSaldo(),0);
	}

}
