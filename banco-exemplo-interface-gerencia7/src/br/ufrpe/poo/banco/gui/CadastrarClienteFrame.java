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
import br.ufrpe.poo.banco.exceptions.ClienteJaCadastradoException;
import br.ufrpe.poo.banco.exceptions.ClienteJaPossuiContaException;
import br.ufrpe.poo.banco.exceptions.ContaJaCadastradaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Cliente;
import br.ufrpe.poo.banco.negocio.Conta;
import br.ufrpe.poo.banco.negocio.ContaAbstrata;
import br.ufrpe.poo.banco.negocio.ContaEspecial;
import br.ufrpe.poo.banco.negocio.ContaImposto;
import br.ufrpe.poo.banco.negocio.Poupanca;

public class CadastrarClienteFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static CadastrarClienteFrame instanceCadastroClienteFrame;
	private JPanel panelCadastroCliente;
	private JTextField cpfTextField;
	private JLabel cpfLabel;
	private JTextField nomeTextField;
	private JLabel nomeLabel;
	private JTextField contaTextField;
	private JLabel contaLabel;
	private JButton submeterCadastroButton;
	private JButton cancelarButton;
	private JButton apagarCamposButton;
	private ButtonGroup tipoContasButtonGroup;
	private JRadioButton tipoCorrenteRadioButton;
	private JRadioButton tipoPoupancaRadioButton;
	private JRadioButton tipoEspecialRadioButton;
	private JRadioButton tipoImpostoRadioButton;
	private JLabel tipoContaLabel;
	private ContaAbstrata tipoConta = new Conta("", 0);

	public static CadastrarClienteFrame getInstanceCadastroClienteFrame() {
		if (instanceCadastroClienteFrame == null) {
			CadastrarClienteFrame.instanceCadastroClienteFrame = new CadastrarClienteFrame();
		}
		return CadastrarClienteFrame.instanceCadastroClienteFrame;
	}

	public CadastrarClienteFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setTitle("Cadastrar");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(0, 0, 400, 300);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelCadastroCliente());
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

	private JPanel getPanelCadastroCliente() {
		if (this.panelCadastroCliente == null) {
			this.panelCadastroCliente = new JPanel();
			this.panelCadastroCliente.setLayout(null);
			this.panelCadastroCliente.add(getCpfLabel());
			this.panelCadastroCliente.add(getCpfTextField());
			this.panelCadastroCliente.add(getNomeLabel());
			this.panelCadastroCliente.add(getNomeTextField());
			this.panelCadastroCliente.add(getContaLabel());
			this.panelCadastroCliente.add(getContaTextField());
			this.panelCadastroCliente.add(getSubmeterCadastroButton());
			this.panelCadastroCliente.add(getApagarCamposButton());
			this.panelCadastroCliente.add(getCancelarButton());
			this.getTipoContaButtonGroup();
			this.panelCadastroCliente.add(getTipoCorrenteRadioButton());
			this.panelCadastroCliente.add(getTipoPoupancaRadioButton());
			this.panelCadastroCliente.add(getTipoEspecialRadioButton());
			this.panelCadastroCliente.add(getTipoImpostoRadioButton());
			this.panelCadastroCliente.add(getTipoContaLabel());
		}
		return this.panelCadastroCliente;
	}

	private JTextField getCpfTextField() {
		if (this.cpfTextField == null) {
			this.cpfTextField = new JTextField();
			this.cpfTextField.setColumns(10);
			this.cpfTextField.setBounds(130, 20, 100, 20);
		}
		return this.cpfTextField;
	}

	private JLabel getCpfLabel() {
		if (this.cpfLabel == null) {
			this.cpfLabel = new JLabel("CPF: ");
			this.cpfLabel.setBounds(20, 20, 30, 20);
		}
		return this.cpfLabel;
	}

	private JTextField getNomeTextField() {
		if (this.nomeTextField == null) {
			this.nomeTextField = new JTextField();
			this.nomeTextField.setColumns(10);
			this.nomeTextField.setBounds(130, 55, 130, 20);
		}
		return this.nomeTextField;
	}

	private JLabel getNomeLabel() {
		if (this.nomeLabel == null) {
			this.nomeLabel = new JLabel("Nome: ");
			this.nomeLabel.setBounds(20, 55, 50, 20);
		}
		return this.nomeLabel;
	}

	private JTextField getContaTextField() {
		if (this.contaTextField == null) {
			this.contaTextField = new JTextField();
			this.contaTextField.setColumns(10);
			this.contaTextField.setBounds(130, 90, 100, 20);
		}
		return this.contaTextField;
	}

	private JLabel getContaLabel() {
		if (this.contaLabel == null) {
			this.contaLabel = new JLabel("Numero da conta: ");
			this.contaLabel.setBounds(20, 90, 120, 20);
		}
		return this.contaLabel;
	}

	private JButton getSubmeterCadastroButton() {
		if (this.submeterCadastroButton == null) {
			this.submeterCadastroButton = new JButton("Cadastrar");
			this.submeterCadastroButton.setBounds(35, 200, 100, 40);
			this.submeterCadastroButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						String cpf = getCpfTextField().getText();
						verificarCampoVazio(cpf, "CPF");

						String nome = getNomeTextField().getText();
						verificarCampoVazio(nome, "Nome");

						String numeroConta = getContaTextField().getText();
						verificarCampoVazio(numeroConta, "Numero da Conta");

						if (AdminMenuFrame.banco.procurarCliente(cpf) != null)
							throw new ClienteJaCadastradoException();
						if (AdminMenuFrame.banco.procurarConta(numeroConta) != null)
							throw new ContaJaCadastradaException();

						tipoConta.setNumero(numeroConta);
						tipoConta.setSaldo(0);

						Cliente cliente = new Cliente(nome, cpf);

						cliente.adicionarConta(numeroConta);
						AdminMenuFrame.banco.cadastrar(tipoConta);
						AdminMenuFrame.banco.cadastrarCliente(cliente);

						JOptionPane.showMessageDialog(null,
								"Cliente cadastrado com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
						esvaziarCampos();
					} catch (ClienteJaCadastradoException
							| ContaJaCadastradaException | RepositorioException
							| ClienteJaPossuiContaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Erro", JOptionPane.ERROR_MESSAGE);
					} catch (CampoVazioException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Alerta", JOptionPane.WARNING_MESSAGE);
					}

				}
			});
		}
		return this.submeterCadastroButton;
	}

	private void verificarCampoVazio(String str, String valor)
			throws CampoVazioException {
		if (str.equals(""))
			throw new CampoVazioException(valor);
	}

	private JButton getApagarCamposButton() {
		if (this.apagarCamposButton == null) {
			this.apagarCamposButton = new JButton("Desfazer");
			this.apagarCamposButton.setBounds(147, 200, 100, 40);
			this.apagarCamposButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					esvaziarCampos();
				}
			});
		}
		return this.apagarCamposButton;
	}

	private JButton getCancelarButton() {
		if (this.cancelarButton == null) {
			this.cancelarButton = new JButton("Cancelar");
			this.cancelarButton.setBounds(260, 200, 100, 40);
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
		if (this.tipoContasButtonGroup == null) {
			this.tipoContasButtonGroup = new ButtonGroup();
			this.tipoContasButtonGroup.add(getTipoCorrenteRadioButton());
			this.tipoContasButtonGroup.add(getTipoPoupancaRadioButton());
			this.tipoContasButtonGroup.add(getTipoEspecialRadioButton());
			this.tipoContasButtonGroup.add(getTipoImpostoRadioButton());
		}
		return tipoContasButtonGroup;
	}

	private JRadioButton getTipoCorrenteRadioButton() {
		if (this.tipoCorrenteRadioButton == null) {
			this.tipoCorrenteRadioButton = new JRadioButton("Corrente", true);
			this.tipoCorrenteRadioButton.setBounds(50, 150, 90, 20);
			this.tipoCorrenteRadioButton
					.addItemListener(new TipoContasRadioButtonHandler(
							new Conta("", 0)));
		}
		return this.tipoCorrenteRadioButton;
	}

	private JRadioButton getTipoPoupancaRadioButton() {
		if (this.tipoPoupancaRadioButton == null) {
			this.tipoPoupancaRadioButton = new JRadioButton("Poupanca", false);
			this.tipoPoupancaRadioButton.setBounds(140, 150, 90, 20);
			this.tipoPoupancaRadioButton
					.addItemListener(new TipoContasRadioButtonHandler(
							new Poupanca("", 0)));
		}
		return this.tipoPoupancaRadioButton;
	}

	private JRadioButton getTipoEspecialRadioButton() {
		if (this.tipoEspecialRadioButton == null) {
			this.tipoEspecialRadioButton = new JRadioButton("Especial", false);
			this.tipoEspecialRadioButton.setBounds(229, 150, 85, 20);
			this.tipoEspecialRadioButton
					.addItemListener(new TipoContasRadioButtonHandler(
							new ContaEspecial("", 0)));
		}
		return this.tipoEspecialRadioButton;
	}

	private JRadioButton getTipoImpostoRadioButton() {
		if (this.tipoImpostoRadioButton == null) {
			this.tipoImpostoRadioButton = new JRadioButton("Imposto", false);
			this.tipoImpostoRadioButton.setBounds(310, 150, 100, 20);
			this.tipoImpostoRadioButton
					.addItemListener(new TipoContasRadioButtonHandler(
							new ContaImposto("", 0)));
		}
		return this.tipoImpostoRadioButton;
	}

	private JLabel getTipoContaLabel() {
		if (this.tipoContaLabel == null) {
			this.tipoContaLabel = new JLabel("Tipo da Conta:");
			this.tipoContaLabel.setBounds(20, 120, 150, 20);
		}
		return this.tipoContaLabel;
	}

	private class TipoContasRadioButtonHandler implements ItemListener {

		private ContaAbstrata conta;

		public TipoContasRadioButtonHandler(ContaAbstrata conta) {
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
		this.getNomeTextField().setText("");
		this.getCpfTextField().setText("");
		this.getContaTextField().setText("");
		getTipoCorrenteRadioButton().setSelected(true);
		tipoConta = new Conta("", 0);
	}

}
