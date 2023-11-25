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

import br.ufrpe.poo.banco.exceptions.CampoVazioException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.negocio.Cliente;

public class ConsultarClienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static ConsultarClienteFrame instanceConsultarClienteFrame;
	private JPanel panelConsultarCliente;
	private JTextField cpfClienteTextField;
	private JLabel cpfClienteLabel;
	private JButton procurarClienteButton;
	private JButton cancelarButton;
	private JTextArea formularioDadosClienteTextArea;
	private JScrollPane formularioDadosClienteScrollPane;

	public static ConsultarClienteFrame getInstanceConsultarClienteFrame() {
		if (ConsultarClienteFrame.instanceConsultarClienteFrame == null) {
			ConsultarClienteFrame.instanceConsultarClienteFrame = new ConsultarClienteFrame();
		}
		return ConsultarClienteFrame.instanceConsultarClienteFrame;
	}

	public ConsultarClienteFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setTitle("Consultar cliente");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 350, 300);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelConsultarCliente());
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

	private JPanel getPanelConsultarCliente() {
		if (this.panelConsultarCliente == null) {
			this.panelConsultarCliente = new JPanel();
			this.panelConsultarCliente.setLayout(null);
			this.panelConsultarCliente.add(getCpfClienteTextField());
			this.panelConsultarCliente.add(getCpfClienteLabel());
			this.panelConsultarCliente
					.add(getFormularioDadosClienteScrollPane());
			this.panelConsultarCliente.add(getProcurarClienteButton());
			this.panelConsultarCliente.add(getCancelarButton());
		}
		return this.panelConsultarCliente;
	}

	private JTextField getCpfClienteTextField() {
		if (this.cpfClienteTextField == null) {
			this.cpfClienteTextField = new JTextField();
			this.cpfClienteTextField.setColumns(10);
			this.cpfClienteTextField.setBounds(60, 180, 120, 20);
		}
		return this.cpfClienteTextField;
	}

	private JLabel getCpfClienteLabel() {
		if (this.cpfClienteLabel == null) {
			this.cpfClienteLabel = new JLabel("CPF:");
			this.cpfClienteLabel.setBounds(20, 180, 50, 20);
		}
		return this.cpfClienteLabel;
	}

	private JButton getProcurarClienteButton() {
		if (this.procurarClienteButton == null) {
			this.procurarClienteButton = new JButton("Procurar");
			this.procurarClienteButton.setBounds(190, 175, 100, 30);
			this.procurarClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						getFormularioDadosClienteTextArea().setText(
								"Dados:\n\n");

						String cpf = getCpfClienteTextField().getText();
						verificarErroCampoVazio(cpf, "cpf");

						Cliente achouCliente = AdminMenuFrame.banco
								.procurarCliente(cpf);

						if (achouCliente == null)
							throw new ClienteNaoCadastradoException();

						getFormularioDadosClienteTextArea().append(
								achouCliente.toString());

					} catch (ClienteNaoCadastradoException e) {
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
		return this.procurarClienteButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(115, 230, 100, 30);
			this.cancelarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					esvaziarCampos();
					getFormularioDadosClienteTextArea().setText("Dados:\n\n");
					instanceConsultarClienteFrame.setVisible(false);
				}
			});
		}
		return this.cancelarButton;
	}

	private JTextArea getFormularioDadosClienteTextArea() {
		if (this.formularioDadosClienteTextArea == null) {
			this.formularioDadosClienteTextArea = new JTextArea("Dados:\n\n");
			this.formularioDadosClienteTextArea.setBounds(50, 10, 250, 150);
			this.formularioDadosClienteTextArea.setEditable(false);
		}
		return this.formularioDadosClienteTextArea;
	}

	private JScrollPane getFormularioDadosClienteScrollPane() {
		if (this.formularioDadosClienteScrollPane == null) {
			this.formularioDadosClienteScrollPane = new JScrollPane(
					getFormularioDadosClienteTextArea());
			this.formularioDadosClienteScrollPane.setBounds(50, 10, 250, 150);
		}
		return this.formularioDadosClienteScrollPane;
	}

	private void esvaziarCampos() {
		getCpfClienteTextField().setText("");
		getFormularioDadosClienteTextArea().setText("Dados:\n\n");
	}

	private void verificarErroCampoVazio(String campo, String valor)
			throws CampoVazioException {
		if (campo.equals(""))
			throw new CampoVazioException(valor);
	}

}
