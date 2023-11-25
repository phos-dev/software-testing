package br.ufrpe.poo.banco.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.CampoVazioException;
import br.ufrpe.poo.banco.exceptions.ClienteJaPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ClienteNaoCadastradoException;
import br.ufrpe.poo.banco.exceptions.ContaJaAssociadaException;
import br.ufrpe.poo.banco.exceptions.ContaJaCadastradaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Conta;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;
import br.ufrpe.poo.banco.negocio.ContaEspecial;
import br.ufrpe.poo.banco.negocio.ContaImposto;
import br.ufrpe.poo.banco.negocio.Poupanca;

public class AssociarContaFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panelAssociarConta;
	private JLabel cpfClienteLabel;
	private JLabel numeroContaClienteLabel;
	private JTextField cpfClienteTextField;
	private JTextField numeroContaTextField;
	private JButton associarContaButton;
	private JButton cancelarButton;
	private ButtonGroup tipoContaButtonGroup;
	private JRadioButton tipoCorrenteRadioButton;
	private JRadioButton tipoPoupancaRadioButton;
	private JRadioButton tipoEspecialRadioButton;
	private JRadioButton tipoImpostoRadioButton;
	private JLabel tipoContasLabel;
	private ContaAbstrata tipoConta = new Conta("", 0);

	public AssociarContaFrame() {
		super();
		initialiaze();
	}

	private void initialiaze() {
		this.setTitle("Associar Conta");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 400, 300);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelAssociarConta());
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
		this.setVisible(true);
	}

	private JPanel getPanelAssociarConta() {
		if (this.panelAssociarConta == null) {
			this.panelAssociarConta = new JPanel();
			this.panelAssociarConta.setLayout(null);
			this.panelAssociarConta.add(getCpfClienteLabel());
			this.panelAssociarConta.add(getCpfClienteTextField());
			this.panelAssociarConta.add(getNumeroContaClienteLabel());
			this.panelAssociarConta.add(getNumeroContaTextField());
			this.panelAssociarConta.add(getAssociarContaButton());
			this.panelAssociarConta.add(getCancelarButton());
			this.getTipoContaButtonGroup();
			this.panelAssociarConta.add(getTipoCorrenteRadioButton());
			this.panelAssociarConta.add(getTipoPoupancaRadioButton());
			this.panelAssociarConta.add(getTipoEspecialRadioButton());
			this.panelAssociarConta.add(getTipoImpostoRadioButton());
			this.panelAssociarConta.add(getContasLabel());
		}
		return this.panelAssociarConta;
	}

	private JLabel getCpfClienteLabel() {
		if (this.cpfClienteLabel == null) {
			this.cpfClienteLabel = new JLabel("CPF: ");
			this.cpfClienteLabel.setBounds(20, 20, 30, 20);
		}
		return this.cpfClienteLabel;
	}

	private JLabel getNumeroContaClienteLabel() {
		if (this.numeroContaClienteLabel == null) {
			this.numeroContaClienteLabel = new JLabel("Nova Conta: ");
			this.numeroContaClienteLabel.setBounds(20, 55, 100, 20);
		}
		return this.numeroContaClienteLabel;
	}

	private JTextField getCpfClienteTextField() {
		if (this.cpfClienteTextField == null) {
			this.cpfClienteTextField = new JTextField();
			this.cpfClienteTextField.setColumns(10);
			this.cpfClienteTextField.setBounds(100, 20, 100, 20);
		}
		return this.cpfClienteTextField;
	}

	private JTextField getNumeroContaTextField() {
		if (this.numeroContaTextField == null) {
			this.numeroContaTextField = new JTextField();
			this.numeroContaTextField.setColumns(10);
			this.numeroContaTextField.setBounds(100, 55, 100, 20);
		}
		return this.numeroContaTextField;
	}

	private JButton getAssociarContaButton() {
		if (this.associarContaButton == null) {
			this.associarContaButton = new JButton();
			this.associarContaButton.setText("Associar");
			this.associarContaButton.setBounds(85, 200, 100, 40);
			this.associarContaButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String cpf = getCpfClienteTextField().getText();
						if (cpf.equals(""))
							throw new CampoVazioException("CPF");

						String numeroConta = getNumeroContaTextField()
								.getText();
						if (numeroConta.equals(""))
							throw new CampoVazioException("numero da conta");

						AdminMenuFrame.banco.associarConta(cpf, numeroConta);

						tipoConta.setNumero(numeroConta);
						tipoConta.setSaldo(0);

						AdminMenuFrame.banco.cadastrar(tipoConta);

						JOptionPane.showMessageDialog(null,
								"Conta associada ao cliente com sucesso!",
								"Sucesso", JOptionPane.INFORMATION_MESSAGE);
						System.out.println(tipoConta.getClass());
						esvaziarCampos();
					} catch (ClienteNaoCadastradoException
							| ClienteJaPossuiContaException
							| ContaJaAssociadaException | RepositorioException
							| ContaJaCadastradaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
					} catch (CampoVazioException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}

				}
			});
		}
		return this.associarContaButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(205, 200, 100, 40);
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

	private ButtonGroup getTipoContaButtonGroup() {
		if (this.tipoContaButtonGroup == null) {
			this.tipoContaButtonGroup = new ButtonGroup();
			this.tipoContaButtonGroup.add(getTipoCorrenteRadioButton());
			this.tipoContaButtonGroup.add(getTipoPoupancaRadioButton());
			this.tipoContaButtonGroup.add(getTipoEspecialRadioButton());
			this.tipoContaButtonGroup.add(getTipoImpostoRadioButton());
		}
		return this.tipoContaButtonGroup;
	}

	private JRadioButton getTipoCorrenteRadioButton() {
		if (this.tipoCorrenteRadioButton == null) {
			this.tipoCorrenteRadioButton = new JRadioButton("Corrente", true);
			this.tipoCorrenteRadioButton.setBounds(50, 150, 90, 20);
			this.tipoCorrenteRadioButton
					.addItemListener(new ContasRadioButtonHandler(new Conta("",
							0)));
		}
		return this.tipoCorrenteRadioButton;
	}

	private JRadioButton getTipoPoupancaRadioButton() {
		if (this.tipoPoupancaRadioButton == null) {
			this.tipoPoupancaRadioButton = new JRadioButton("Poupanca", false);
			this.tipoPoupancaRadioButton.setBounds(140, 150, 90, 20);
			this.tipoPoupancaRadioButton
					.addItemListener(new ContasRadioButtonHandler(new Poupanca(
							"", 0)));
		}
		return this.tipoPoupancaRadioButton;
	}

	private JRadioButton getTipoEspecialRadioButton() {
		if (this.tipoEspecialRadioButton == null) {
			this.tipoEspecialRadioButton = new JRadioButton("Especial", false);
			this.tipoEspecialRadioButton.setBounds(229, 150, 85, 20);
			this.tipoEspecialRadioButton
					.addItemListener(new ContasRadioButtonHandler(
							new ContaEspecial("", 0)));
		}
		return this.tipoEspecialRadioButton;
	}

	private JRadioButton getTipoImpostoRadioButton() {
		if (this.tipoImpostoRadioButton == null) {
			this.tipoImpostoRadioButton = new JRadioButton("Imposto", false);
			this.tipoImpostoRadioButton.setBounds(310, 150, 100, 20);
			this.tipoImpostoRadioButton
					.addItemListener(new ContasRadioButtonHandler(
							new ContaImposto("", 0)));
		}
		return this.tipoImpostoRadioButton;
	}

	private JLabel getContasLabel() {
		if (this.tipoContasLabel == null) {
			this.tipoContasLabel = new JLabel("Tipo da Conta:");
			this.tipoContasLabel.setBounds(15, 120, 150, 20);
		}
		return this.tipoContasLabel;
	}

	private class ContasRadioButtonHandler implements ItemListener {

		private ContaAbstrata conta;

		public ContasRadioButtonHandler(ContaAbstrata conta) {
			this.conta = conta;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (conta instanceof Conta) {
				if (conta instanceof Poupanca)
					tipoConta = new Poupanca("", 0);
				else if (conta instanceof ContaEspecial)
					tipoConta = new ContaEspecial("", 0);
				else
					tipoConta = new Conta("", 0);
			} else
				tipoConta = new ContaImposto("", 0);
		}
	}

	private void esvaziarCampos() {
		getCpfClienteTextField().setText("");
		getNumeroContaTextField().setText("");
		getTipoContaButtonGroup();
		getTipoCorrenteRadioButton().setSelected(true);
		tipoConta = new Conta("", 0);
	}
}
