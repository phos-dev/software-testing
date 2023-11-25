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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.AtualizacaoNaoRealizadaException;
import br.ufrpe.poo.banco.exceptions.CampoVazioException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Cliente;

public class AtualizarClienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelAtualizarCliente;
	private Cliente cliente;
	private JTextArea formularioDadosClienteTextArea;
	private JScrollPane formularioDadosClienteScrollPane;
	private JButton submeterNovoClienteButton;
	private JButton cancelarButton;
	private JTextField novoNomeClienteTextField;
	private JLabel novoNomeClienteLabel;

	public AtualizarClienteFrame() {
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
		} catch (ClienteNaoCadastradoException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		initialize();
	}

	private void initialize() {
		this.setVisible(true);
		this.setTitle("Atualizar Cliente");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 350, 300);
		this.setLocationRelativeTo(null);
		this.getFormularioDadosClienteTextArea().setText(
				"Dados:\n\n" + cliente.toString());
		this.setContentPane(getPanelAtualizarCliente());
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

	public JPanel getPanelAtualizarCliente() {
		if (this.panelAtualizarCliente == null) {
			this.panelAtualizarCliente = new JPanel();
			this.panelAtualizarCliente.setLayout(null);
			this.panelAtualizarCliente
					.add(getFormularioDadosClienteScrollPane());
			this.panelAtualizarCliente.add(getSubmeterNovoClienteButton());
			this.panelAtualizarCliente.add(getCancelarButton());
			this.panelAtualizarCliente.add(getNovoNomeClienteTextField());
			this.panelAtualizarCliente.add(getNovoNomeClienteLabel());
		}
		return this.panelAtualizarCliente;
	}

	public JTextArea getFormularioDadosClienteTextArea() {
		if (this.formularioDadosClienteTextArea == null) {
			this.formularioDadosClienteTextArea = new JTextArea();
			this.formularioDadosClienteTextArea.setBounds(50, 10, 250, 150);
			this.formularioDadosClienteTextArea.setEditable(false);
		}
		return this.formularioDadosClienteTextArea;
	}

	public JScrollPane getFormularioDadosClienteScrollPane() {
		if (this.formularioDadosClienteScrollPane == null) {
			this.formularioDadosClienteScrollPane = new JScrollPane(
					getFormularioDadosClienteTextArea());
			this.formularioDadosClienteScrollPane.setBounds(50, 10, 250, 150);
		}
		return this.formularioDadosClienteScrollPane;
	}

	public JButton getSubmeterNovoClienteButton() {
		if (this.submeterNovoClienteButton == null) {
			this.submeterNovoClienteButton = new JButton("Atualizar");
			this.submeterNovoClienteButton.setBounds(65, 230, 100, 30);
			this.submeterNovoClienteButton
					.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								String novoNome = getNovoNomeClienteTextField()
										.getText();
								verificarCampoVazio(novoNome, "Novo nome");

								cliente.setNome(novoNome);

								AdminMenuFrame.banco.atualizarCliente(cliente);

								JOptionPane.showMessageDialog(null,
										"Cliente atualizado com suceso!");
								esvaziarCampos();
								getFormularioDadosClienteTextArea().setText(
										"Dados:\n\n" + cliente.toString());
							} catch (RepositorioException
									| AtualizacaoNaoRealizadaException e) {
								JOptionPane.showMessageDialog(null,
										e.getMessage(), "Erro",
										JOptionPane.ERROR_MESSAGE);
							} catch (CampoVazioException e) {
								JOptionPane.showMessageDialog(null,
										e.getMessage(), "Alerta",
										JOptionPane.WARNING_MESSAGE);
							}

						}
					});
		}
		return this.submeterNovoClienteButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(180, 230, 100, 30);
			this.cancelarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					esvaziarCampos();
					getFormularioDadosClienteTextArea().setText("Dados:\n\n");
					AtualizarClienteFrame.this.setVisible(false);
				}
			});
		}
		return this.cancelarButton;
	}

	public JTextField getNovoNomeClienteTextField() {
		if (this.novoNomeClienteTextField == null) {
			this.novoNomeClienteTextField = new JTextField();
			this.novoNomeClienteTextField.setColumns(10);
			this.novoNomeClienteTextField.setBounds(100, 180, 120, 20);
		}
		return this.novoNomeClienteTextField;
	}

	public JLabel getNovoNomeClienteLabel() {
		if (this.novoNomeClienteLabel == null) {
			this.novoNomeClienteLabel = new JLabel("Novo nome:");
			this.novoNomeClienteLabel.setBounds(20, 180, 70, 20);
		}
		return this.novoNomeClienteLabel;
	}

	private void verificarCampoVazio(String str, String valor)
			throws CampoVazioException {
		if (str.equals(""))
			throw new CampoVazioException(valor);
	}

	private void esvaziarCampos() {
		getNovoNomeClienteTextField().setText("");
		getFormularioDadosClienteTextArea().setText("");
	}

}
