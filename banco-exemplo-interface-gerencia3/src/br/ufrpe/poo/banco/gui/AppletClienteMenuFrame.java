package br.ufrpe.poo.banco.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import br.ufrpe.poo.banco.exceptions.InicializacaoSistemaException;
import br.ufrpe.poo.banco.exceptions.RepositorioException;
import br.ufrpe.poo.banco.negocio.Banco;
import br.ufrpe.poo.banco.negocio.ICliente;

public class AppletClienteMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton iniciarButton;
	public static ICliente banco;
	private ClienteMenuFrame clienteMenuFrame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					AppletClienteMenuFrame appletClienteMenuFrame = new AppletClienteMenuFrame();
					appletClienteMenuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AppletClienteMenuFrame() {
		super();
		try {
			banco = Banco.getInstance();
		} catch (InicializacaoSistemaException | RepositorioException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			System.exit(0);
		}
		initialize();
	}

	public void initialize() {
		this.setSize(300, 200);
		this.setTitle("Iniciar Banco");
		this.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.add(getIniciarButton());
	}

	public JButton getIniciarButton() {
		if (this.iniciarButton == null) {
			this.iniciarButton = new JButton("Iniciar");
			this.iniciarButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					setClienteMenuFrame();
				}
			});
		}
		return this.iniciarButton;
	}

	public void setClienteMenuFrame() {
		this.clienteMenuFrame = new ClienteMenuFrame();
	}

	public ClienteMenuFrame getClienteMenuFrame() {
		return this.clienteMenuFrame;
	}

}
