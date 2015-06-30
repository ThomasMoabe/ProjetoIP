package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;
import lan.server.util.ConfiguracaoInvalidaException;

public class JLan extends JFrame {
	private Lan lan;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JLan frame = null;
				try {
					frame = new JLan();
					frame.setLocationRelativeTo(null); //centraliza na tela
					frame.setVisible(true);
				} catch (ConfiguracaoInvalidaException e) {
					new JOptionPane().showMessageDialog(frame, e.getMessage(), "Erro de configuração",JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ConfiguracaoInvalidaException 
	 */
	public JLan() throws ConfiguracaoInvalidaException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.lan = new Lan();
	}

}
