package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestConta3 {

	@Test
	public final void testConstrutor1() {
		Conta c = new Conta("1", 0);
		assertEquals("O saldo incorreto",1, c.getSaldo(),0);
		assertEquals("1", c.getNumero());
	}
	
	@Test
	public final void testConstrutor2() {
		Conta c = new Conta("1", 0);
		assertEquals("O saldo incorreto",1, c.getSaldo(),0);
		assertEquals("1", c.getNumero());
	}
	
	@BeforeClass
	public static void preparacaoGEral(){
		System.out.println("Preparacao geral");
	}
	
	@Before
	public void preparacao(){
		System.out.println("Preparacao antes de executar metodo de teste");
	}
	
	@After
	public void liberacao(){
		System.out.println("Limpeza antes de executar metodo de teste");
	}
	
	@AfterClass
	public static void limpezaGEral(){
		System.out.println("Limpeza geral");
	}



}
