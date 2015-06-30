package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;
import lan.server.util.ConfiguracaoInvalidaException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
					frame.dispose();
					if (frame.getLan().liberalogin()) {
						new LoginFrame(frame.getLan());
					} else {
						new CadastroAdministradorFrame(frame.getLan());
					}
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
		setTitle("JLan - Carregando");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 174);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCarregandoArquivosDe = new JLabel("Carregando arquivos de configura\u00E7\u00E3o");
		lblCarregandoArquivosDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarregandoArquivosDe.setBounds(10, 120, 424, 14);
		contentPane.add(lblCarregandoArquivosDe);
		
		this.lan = new Lan();
	}
	
	public Lan getLan() {
		return this.lan;
	}
}
