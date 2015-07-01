package lan.server.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import lan.server.administracao.AdministradorInvalidoException;
import lan.server.painel.Lan;

public class LoginFrame extends JFrame {
	private Lan lan;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public LoginFrame(Lan lan) {
		setTitle("Login");
		setResizable(false);
		this.lan = lan;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loga(textField.getText(), passwordField.getText());
			}
		});
		textField.setBounds(184, 79, 160, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(107, 88, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(107, 125, 46, 14);
		contentPane.add(lblSenha);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loga(textField.getText(), passwordField.getText());
			}
		});
		btnLogar.setBounds(255, 163, 89, 34);
		contentPane.add(btnLogar);
		
		JLabel lblLogin_1 = new JLabel("LOGIN");
		lblLogin_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLogin_1.setBounds(107, 38, 97, 14);
		contentPane.add(lblLogin_1);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loga(textField.getText(), passwordField.getText());
			}
		});
		passwordField.setBounds(184, 116, 160, 26);
		contentPane.add(passwordField);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void loga(String login, String senha) {
		try {
			this.lan.loginAdministrador(login, senha);
			this.dispose();
			new IndexFrame(this.lan);
		} catch (AdministradorInvalidoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível logar", JOptionPane.ERROR_MESSAGE);
		}
	}
}
