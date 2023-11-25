package br.ufrpe.poo.banco.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.CampoVazioException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.RemocaoInvalidaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Cliente;

public class RemoverContaFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelRemoverConta;
	private JButton removerContaButton;
	private JButton cancelarButton;
	private JTextField numeroContaTextField;
	private JLabel numeroContaLabel;
	private Cliente cliente;

	public RemoverContaFrame() {
		super();
		try {
			String cpf = JOptionPane.showInputDialog(null,
					"Informe o CPF do cliente: ", "CPF",
					JOptionPane.PLAIN_MESSAGE);
			if (cpf == null)
				return;
			cliente = AdminMenuFrame.banco.procurarCliente(cpf);
			if (cliente == null)
				throw new ClienteNaoCadastradoException();

			if (cliente.getContas().size() == 1)
				throw new RemocaoInvalidaException();
		} catch (ClienteNaoCadastradoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} catch (RemocaoInvalidaException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"O cliente nao pode ter contas removidas! Ele so possui 1 conta ativa!",
							"Alerta", JOptionPane.WARNING_MESSAGE);
			return;
		}
		initialize();
	}

	private void initialize() {
		this.setVisible(true);
		this.setTitle("Remover Conta");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 350, 150);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelRemoverConta());
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				esvaziarCampos();
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	private JPanel getPanelRemoverConta() {
		if (this.panelRemoverConta == null) {
			this.panelRemoverConta = new JPanel();
			this.panelRemoverConta.setLayout(null);
			this.panelRemoverConta.add(getNumeroContaLabel());
			this.panelRemoverConta.add(getNumeroContaTextField());
			this.panelRemoverConta.add(getRemoverContaButton());
			this.panelRemoverConta.add(getCancelarButton());
		}
		return this.panelRemoverConta;
	}

	private JButton getRemoverContaButton() {
		if (this.removerContaButton == null) {
			this.removerContaButton = new JButton("Excluir");
			this.removerContaButton.setBounds(60, 60, 100, 40);
			this.removerContaButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String numeroConta = getNumeroContaTextField()
								.getText();
						if (numeroConta.equals(""))
							throw new CampoVazioException("numero da conta");

						if (cliente.getContas().size() == 1)
							throw new RemocaoInvalidaException();

						AdminMenuFrame.banco.removerConta(cliente, numeroConta);
						JOptionPane.showMessageDialog(null,
								"Conta removida com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (ContaNaoEncontradaException
							| RemocaoInvalidaException
							| ClienteNaoPossuiContaException
							| RepositorioException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
						esvaziarCampos();
					} catch (CampoVazioException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return this.removerContaButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(180, 60, 100, 40);
			this.cancelarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					esvaziarCampos();
					setVisible(false);
				}
			});
		}
		return this.cancelarButton;
	}

	private JTextField getNumeroContaTextField() {
		if (this.numeroContaTextField == null) {
			this.numeroContaTextField = new JTextField();
			this.numeroContaTextField.setColumns(10);
			this.numeroContaTextField.setBounds(100, 20, 100, 20);
		}
		return this.numeroContaTextField;
	}

	private JLabel getNumeroContaLabel() {
		if (this.numeroContaLabel == null) {
			this.numeroContaLabel = new JLabel("Numero: ");
			this.numeroContaLabel.setBounds(20, 20, 70, 20);
		}
		return this.numeroContaLabel;
	}

	private void esvaziarCampos() {
		getNumeroContaTextField().setText("");
	}
}
