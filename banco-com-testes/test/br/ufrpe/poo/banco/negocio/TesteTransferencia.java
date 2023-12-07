package br.ufrpe.poo.banco.negocio;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;

@RunWith(Parameterized.class)
public class TesteTransferencia {

	private double saldoOrigemAntes;
	private double saldoDestinoAntes;
	private double valorTransferir;
	private double saldoOrigemDepois;
	private double saldoDestinoDepois;

	/**
	 * 
	 * @param oa Saldo da conta de origem antes
	 * @param da Saldo da conta de destino antes
	 * @param v Valor a transferir
	 * @param od Saldo da conta de origem depois da transferência do valor
	 * @param dd Saldo da conta de destino depois da transferência do valor
	 */
	public TesteTransferencia(double oa, double da, double v, double od,
			double dd) {
		this.saldoOrigemAntes = oa;
		this.saldoDestinoAntes = da;
		this.valorTransferir = v;
		this.saldoOrigemDepois = od;
		this.saldoDestinoDepois = dd;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {{ 100, 100, 0, 100, 100 },  
			{ 100, 100, 50, 50, 150 },
			{ 100, 100, 100, 0, 200 } };
		return Arrays.asList(data);
	}

	@Test
	public void testeTransferenciaEntreContasCorrente() throws SaldoInsuficienteException {
		Conta origem = new Conta("1", this.saldoOrigemAntes);
		Conta destino = new Conta("2", this.saldoDestinoAntes);
		origem.debitar(this.valorTransferir);
		destino.creditar(this.valorTransferir);
		assertEquals(this.saldoOrigemDepois, origem.getSaldo(), 0);
		assertEquals(this.saldoDestinoDepois, destino.getSaldo(), 0);
	}

}
