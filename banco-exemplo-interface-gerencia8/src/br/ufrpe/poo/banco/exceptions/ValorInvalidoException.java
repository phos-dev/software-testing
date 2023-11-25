package br.ufrpe.poo.banco.exceptions;

public class ValorInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ValorInvalidoException(){
		super("O valor informado eh invalido!");
	}

}
