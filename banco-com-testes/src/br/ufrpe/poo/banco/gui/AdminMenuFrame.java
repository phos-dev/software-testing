package br.ufrpe.poo.banco.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.InicializacaoSistemaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Banco;
import br.ufrpe.poo.banco.negocio.IGerencia;

public class AdminMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static IGerencia banco;
	private CadastrarClienteFrame cadastrarClienteFrame;
	private AssociarContaFrame associarContaFrame;
	private ConsultarClienteFrame consultarClienteFrame;
	private RemoverClienteFrame removerClienteFrame;
	private RemoverContaFrame removerContaFrame;

	public static IGerencia getBanco() {
		return banco;
	}

	private AtualizarClienteFrame atualizarClienteFrame;
	private JPanel panelGerenciaMenu;
	private JButton cadastrarClienteButton;
	private JButton associarContaButton;
	private JButton consultarClienteButton;
	private JButton atualizarClienteButton;
	private JButton removerClienteButton;
	private JButton removerContaButton;
	private GridLayout gridLayout;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					AdminMenuFrame adminMenuFrame = new AdminMenuFrame();
					adminMenuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminMenuFrame() {
		super();
		try {
			AdminMenuFrame.banco = Banco.getInstance();
			initialize();
		} catch (InicializacaoSistemaException | RepositorioException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private void initialize() {
		this.setTitle("Gerencia Banco");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 500, 200);
		this.setLocationRelativeTo(null);
		this.setContentPane(getPanelGerenciaMenu());
	}

	private JPanel getPanelGerenciaMenu() {
		if (this.panelGerenciaMenu == null) {
			this.panelGerenciaMenu = new JPanel();
			this.panelGerenciaMenu.setLayout(getGridLayout());
			this.panelGerenciaMenu.add(getCadastrarClienteButton());
			this.panelGerenciaMenu.add(getAssociarContaButton());
			this.panelGerenciaMenu.add(getConsultarClienteButton());
			this.panelGerenciaMenu.add(getAtualizarClienteButton());
			this.panelGerenciaMenu.add(getRemoverClienteButton());
			this.panelGerenciaMenu.add(getRemoverContaButton());
		}
		return this.panelGerenciaMenu;
	}

	private JButton getCadastrarClienteButton() {
		if (this.cadastrarClienteButton == null) {
			this.cadastrarClienteButton = new JButton();
			this.cadastrarClienteButton.setText("Cadastrar");
			this.cadastrarClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					cadastrarClienteFrame = CadastrarClienteFrame
							.getInstanceCadastroClienteFrame();
					cadastrarClienteFrame.setVisible(true);
				}
			});
		}
		return this.cadastrarClienteButton;
	}

	private JButton getAssociarContaButton() {
		if (this.associarContaButton == null) {
			this.associarContaButton = new JButton();
			this.associarContaButton.setText("Associar Conta");
			this.associarContaButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					associarContaFrame = new AssociarContaFrame();
				}
			});
		}
		return this.associarContaButton;
	}

	private JButton getConsultarClienteButton() {
		if (this.consultarClienteButton == null) {
			this.consultarClienteButton = new JButton();
			this.consultarClienteButton.setText("Consultar Cliente");
			this.consultarClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					consultarClienteFrame = ConsultarClienteFrame
							.getInstanceConsultarClienteFrame();
					consultarClienteFrame.setVisible(true);
				}
			});
		}
		return this.consultarClienteButton;
	}

	private JButton getAtualizarClienteButton() {
		if (this.atualizarClienteButton == null) {
			this.atualizarClienteButton = new JButton();
			this.atualizarClienteButton.setText("Atualizar Cliente");
			this.atualizarClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					atualizarClienteFrame = new AtualizarClienteFrame();
				}
			});
		}

		return this.atualizarClienteButton;
	}

	private JButton getRemoverClienteButton() {
		if (this.removerClienteButton == null) {
			this.removerClienteButton = new JButton();
			this.removerClienteButton.setText("Remover Cliente");
			this.removerClienteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					removerClienteFrame = RemoverClienteFrame
							.getInstanceRemoverClienteFrame();
					removerClienteFrame.setVisible(true);
				}
			});
		}
		return this.removerClienteButton;
	}

	private JButton getRemoverContaButton() {
		if (this.removerContaButton == null) {
			this.removerContaButton = new JButton();
			this.removerContaButton.setText("Remover Conta");
			this.removerContaButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					removerContaFrame = new RemoverContaFrame();
				}
			});
		}
		return this.removerContaButton;
	}

	private GridLayout getGridLayout() {
		if (this.gridLayout == null) {
			this.gridLayout = new GridLayout(2, 3, 5, 5);
		}
		return this.gridLayout;
	}

	public AtualizarClienteFrame getAtualizarClienteFrame() {
		return this.atualizarClienteFrame;
	}

	public CadastrarClienteFrame getCadastrarClienteFrame() {
		return cadastrarClienteFrame;
	}

	public AssociarContaFrame getAssociarContaFrame() {
		return associarContaFrame;
	}

	public ConsultarClienteFrame getConsultarClienteFrame() {
		return consultarClienteFrame;
	}

	public RemoverClienteFrame getRemoverClienteFrame() {
		return removerClienteFrame;
	}

	public RemoverContaFrame getRemoverContaFrame() {
		return removerContaFrame;
	}

}
