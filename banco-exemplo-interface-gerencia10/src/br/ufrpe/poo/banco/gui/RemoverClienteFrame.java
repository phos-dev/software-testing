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

import br.ufrpe.poo.banco.exceptions.AtualizacaoNaoRealizadaException;
import br.ufrpe.poo.banco.exceptions.CampoVazioException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaNaoEncontradaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;

public class RemoverClienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static RemoverClienteFrame instanceRemoverClienteFrame;
	private JPanel panelRemoverCliente;
	private JButton removerClienteButton;
	private JButton cancelarButton;
	private JTextField cpfClienteTextField;
	private JLabel cpfClienteLabel;

	public static RemoverClienteFrame getInstanceRemoverClienteFrame() {
		if (RemoverClienteFrame.instanceRemoverClienteFrame == null) {
			RemoverClienteFrame.instanceRemoverClienteFrame = new RemoverClienteFrame();
		}
		return RemoverClienteFrame.instanceRemoverClienteFrame;
	}

	public RemoverClienteFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setTitle("Remover Cliente");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 350, 150);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelRemoverCliente());
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

	private JPanel getPanelRemoverCliente() {
		if (this.panelRemoverCliente == null) {
			this.panelRemoverCliente = new JPanel();
			this.panelRemoverCliente.setLayout(null);
			this.panelRemoverCliente.add(getCpfClienteLabel());
			this.panelRemoverCliente.add(getCpfClienteTextField());
			this.panelRemoverCliente.add(getRemoverClienteButton());
			this.panelRemoverCliente.add(getCancelarButton());
		}
		return this.panelRemoverCliente;
	}

	private JButton getRemoverClienteButton() {
		if (this.removerClienteButton == null) {
			this.removerClienteButton = new JButton("Excluir");
			this.removerClienteButton.setBounds(65, 60, 100, 40);
			this.removerClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String cpf = getCpfClienteTextField().getText();
						if (cpf.equals(""))
							throw new CampoVazioException("CPF");

						if (AdminMenuFrame.banco.procurarCliente(cpf) == null)
							throw new ClienteNaoCadastradoException();

						AdminMenuFrame.banco.removerCliente(cpf);

						JOptionPane
								.showMessageDialog(
										null,
										"Cliente removido com sucesso! Todas as contas associadas a ele tambem foram removidas!",
										"Sucesso",
										JOptionPane.INFORMATION_MESSAGE);
						esvaziarCampos();
					} catch (RepositorioException | ContaNaoEncontradaException
							| ClienteNaoPossuiContaException
							| AtualizacaoNaoRealizadaException
							| ClienteNaoCadastradoException e) {
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
		return this.removerClienteButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(185, 60, 100, 40);
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

	private JTextField getCpfClienteTextField() {
		if (this.cpfClienteTextField == null) {
			this.cpfClienteTextField = new JTextField();
			this.cpfClienteTextField.setColumns(10);
			this.cpfClienteTextField.setBounds(100, 20, 100, 20);
		}
		return this.cpfClienteTextField;
	}

	private JLabel getCpfClienteLabel() {
		if (this.cpfClienteLabel == null) {
			this.cpfClienteLabel = new JLabel("CPF: ");
			this.cpfClienteLabel.setBounds(20, 20, 30, 20);
		}
		return this.cpfClienteLabel;
	}

	private void esvaziarCampos() {
		getCpfClienteTextField().setText("");
	}

}
