package br.ufrpe.poo.banco.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.AcessoMenuClienteBloqueadoExeception;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.ContasOrigemDestinoIguaisException;
import br.ufrpe.poo.banco.exceptions.EntradaInvalidaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.exceptions.SaldoInsuficienteException;
import br.ufrpe.poo.banco.exceptions.ValorInvalidoException;
import br.ufrpe.poo.banco.negocio.Cliente;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;

public class ClienteMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GridLayout gridLayout;
	private JButton getSaldoButton;
	private JButton transferirButton;
	private JButton creditarButton;
	private JButton saqueButton;
	private JButton exitButton;
	private Cliente cliente;
	private JTextArea formularioContasClienteTextArea;
	private JScrollPane formularioContasClienteScrollPane;

	public ClienteMenuFrame() {
		super();
		try {
			int cont = 0;
			while (true) {
				String cpf = JOptionPane.showInputDialog(null,
						"Para acessar o menu informe o seu CPF (Tentativa = "
								+ cont + "): ", "Senha (CPF)",
						JOptionPane.PLAIN_MESSAGE);

				if (cpf == null)
					return;
				Cliente achouCliente = AppletClienteMenuFrame.banco
						.procurarCliente(cpf);

				if (achouCliente != null) {
					cliente = achouCliente;
					break;
				}
				cont++;
				if (cont == 3)
					throw new AcessoMenuClienteBloqueadoExeception();
			}
			initialize();
		} catch (AcessoMenuClienteBloqueadoExeception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	private void initialize() {
		this.setTitle("Cliente " + cliente.getNome() + " ativo!");
		this.setSize(500, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(getGridLayout());
		this.add(getGetSaldoButton());
		this.add(getCreditarButton());
		this.add(getSaqueButton());
		this.add(getTransferirButton());
		this.add(getExitButton());
		this.add(getFormularioContasClienteScrollPane());
		this.setVisible(true);
	}

	public GridLayout getGridLayout() {
		if (this.gridLayout == null) {
			this.gridLayout = new GridLayout(2, 3, 5, 5);
		}
		return this.gridLayout;
	}

	public JButton getGetSaldoButton() {
		if (this.getSaldoButton == null) {
			this.getSaldoButton = new JButton();
			this.getSaldoButton.setText("Saldo");
			this.getSaldoButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String numeroConta = JOptionPane.showInputDialog(null,
								"Informe o numero da conta: ", "Conta",
								JOptionPane.PLAIN_MESSAGE);

						if (numeroConta == null)
							return;

						ContaAbstrata achouConta = null;
						if (cliente.procurarConta(numeroConta) != -1)
							achouConta = AppletClienteMenuFrame.banco
									.procurarConta(numeroConta);
						else
							throw new ClienteNaoPossuiContaException();

						String saldo = String.format("R$%.2f",
								achouConta.getSaldo());

						JOptionPane.showMessageDialog(null, saldo, "Saldo",
								JOptionPane.PLAIN_MESSAGE);
					} catch (ClienteNaoPossuiContaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}

				}
			});
		}
		return this.getSaldoButton;
	}

	public JButton getTransferirButton() {
		if (this.transferirButton == null) {
			this.transferirButton = new JButton();
			this.transferirButton.setText("Transferir");
			this.transferirButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String numeroContaOrigem = JOptionPane.showInputDialog(
								null, "Informe o numero da conta origem:",
								"Conta Origem", JOptionPane.PLAIN_MESSAGE);

						if (numeroContaOrigem == null)
							return;

						ContaAbstrata contaOrigem = null;
						if (cliente.procurarConta(numeroContaOrigem) != -1)
							contaOrigem = AppletClienteMenuFrame.banco
									.procurarConta(numeroContaOrigem);
						else
							throw new ClienteNaoPossuiContaException();

						String numeroContaDestino = JOptionPane
								.showInputDialog(null,
										"Informe o numero da conta destino:",
										"Conta Destino",
										JOptionPane.PLAIN_MESSAGE);

						if (numeroContaDestino == null)
							return;

						if (numeroContaDestino
								.equalsIgnoreCase(numeroContaOrigem))
							throw new ContasOrigemDestinoIguaisException();

						ContaAbstrata contaDestino = AppletClienteMenuFrame.banco
								.procurarConta(numeroContaDestino);

						if (contaDestino == null)
							throw new ContaNaoEncontradaException();

						String valor = JOptionPane.showInputDialog(null,
								"Informe o valor da transferencia:",
								"Transferencia", JOptionPane.PLAIN_MESSAGE);

						if (!valor.matches("[0-9]*") || valor.equals(""))
							throw new EntradaInvalidaException();

						double valor2 = Double.parseDouble(valor);

						AppletClienteMenuFrame.banco.transferir(contaOrigem,
								contaDestino, valor2);

						JOptionPane.showMessageDialog(null,
								"Transferencia realizada com sucesso!",
								"Sucesso", JOptionPane.INFORMATION_MESSAGE);
					} catch (EntradaInvalidaException | RepositorioException
							| ContasOrigemDestinoIguaisException
							| ValorInvalidoException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
					} catch (ContaNaoEncontradaException
							| ClienteNaoPossuiContaException
							| SaldoInsuficienteException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}
				}
			});

		}
		return this.transferirButton;
	}

	public JButton getCreditarButton() {
		if (this.creditarButton == null) {
			this.creditarButton = new JButton();
			this.creditarButton.setText("Depositar");
			this.creditarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String numero = JOptionPane.showInputDialog(null,
								"Informe o numero da conta:", "Conta",
								JOptionPane.PLAIN_MESSAGE);

						if (numero == null)
							return;

						ContaAbstrata achouConta = null;
						if (cliente.procurarConta(numero) != -1)
							achouConta = AppletClienteMenuFrame.banco
									.procurarConta(numero);
						else
							throw new ClienteNaoPossuiContaException();

						String valor = JOptionPane.showInputDialog(null,
								"Informe o valor a ser depositado:",
								"Depositar", JOptionPane.PLAIN_MESSAGE);

						if (!valor.matches("[0-9]*") || valor.equals(""))
							throw new EntradaInvalidaException();

						double valor2 = Double.parseDouble(valor);

						AppletClienteMenuFrame.banco.creditar(achouConta,
								valor2);

						JOptionPane.showMessageDialog(null,
								"Deposito realizado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (EntradaInvalidaException | RepositorioException
							| ValorInvalidoException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
					} catch (ClienteNaoPossuiContaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return this.creditarButton;
	}

	public JButton getSaqueButton() {
		if (this.saqueButton == null) {
			this.saqueButton = new JButton();
			this.saqueButton.setText("Saque");
			this.saqueButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String numero = JOptionPane.showInputDialog(null,
								"Informe o numero da conta:", "Conta",
								JOptionPane.PLAIN_MESSAGE);

						if (numero == null)
							return;

						ContaAbstrata achouConta = null;
						if (cliente.procurarConta(numero) != -1)
							achouConta = AppletClienteMenuFrame.banco
									.procurarConta(numero);
						else
							throw new ClienteNaoPossuiContaException();

						String valor = JOptionPane.showInputDialog(null,
								"Informe o valor a ser sacado:", "Saque",
								JOptionPane.PLAIN_MESSAGE);

						if (!valor.matches("[0-9]*") || valor.equals(""))
							throw new EntradaInvalidaException();
						double valor2 = Double.parseDouble(valor);

						AppletClienteMenuFrame.banco
								.debitar(achouConta, valor2);

						JOptionPane.showMessageDialog(null,
								"Saque realizado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (EntradaInvalidaException | RepositorioException
							| ValorInvalidoException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
					} catch (ClienteNaoPossuiContaException
							| SaldoInsuficienteException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return this.saqueButton;
	}

	private JButton getExitButton() {
		if (this.exitButton == null) {
			this.exitButton = new JButton();
			this.exitButton.setText("Sair");
			this.exitButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					ClienteMenuFrame.this.setVisible(false);
				}
			});
		}
		return this.exitButton;
	}
	
	private JTextArea getFormularioContasClienteTextArea() {
		if (this.formularioContasClienteTextArea == null) {
			this.formularioContasClienteTextArea = new JTextArea("Contas:\n\n" + cliente.getContas());	
			this.formularioContasClienteTextArea.setEditable(false);
		}
		return this.formularioContasClienteTextArea;
	}

	private JScrollPane getFormularioContasClienteScrollPane() {
		if (this.formularioContasClienteScrollPane == null) {
			this.formularioContasClienteScrollPane = new JScrollPane(
					getFormularioContasClienteTextArea());
		}
		return this.formularioContasClienteScrollPane;
	}


}
