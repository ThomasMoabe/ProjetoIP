package lan.server.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lan.server.administracao.AdministradorJaCadastradoException;
import lan.server.administracao.AdministradorLoginInvalidoException;
import lan.server.administracao.AdministradorSenhaFracaException;
import lan.server.painel.Lan;

public class CadastroAdministradorFrame extends JFrame {
	private Lan lan;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JLabel lblLogin;
	private JLabel lblNome;
	private JLabel lblSenha;
	private JPasswordField passwordField_1;

	public CadastroAdministradorFrame(Lan lan) {
		this.lan = lan;
		setResizable(false);
		setTitle("Cadastro de administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastroDeAdministrador = new JLabel("Cadastro de administrador");
		lblCadastroDeAdministrador.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCadastroDeAdministrador.setBounds(29, 28, 282, 14);
		contentPane.add(lblCadastroDeAdministrador);
		
		JLabel lblCaroUsurioPara = new JLabel("<html>Caro usu\u00E1rio, para utiliza\u00E7\u00E3o deste software \u00E9 necess\u00E1rio o cadastro de um administrador, por favor preencha os campos abaixo:</html>");
		lblCaroUsurioPara.setBounds(29, 67, 282, 45);
		contentPane.add(lblCaroUsurioPara);
		
		textField = new JTextField();
		textField.setBounds(160, 177, 151, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(160, 208, 151, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(160, 239, 151, 20);
		contentPane.add(passwordField);
		
		lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(104, 211, 41, 14);
		contentPane.add(lblLogin);
		
		lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(104, 180, 41, 14);
		contentPane.add(lblNome);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(104, 242, 41, 14);
		contentPane.add(lblSenha);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(29, 144, 282, 2);
		contentPane.add(separator);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(160, 271, 151, 20);
		contentPane.add(passwordField_1);
		
		JLabel lblConfirmaoDeSenha = new JLabel("Confirme a senha:");
		lblConfirmaoDeSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmaoDeSenha.setBounds(28, 274, 117, 14);
		contentPane.add(lblConfirmaoDeSenha);
		
		JButton btnContinuar = new JButton("Cadastrar");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (passwordField.getText().equals(passwordField_1.getText())) {
					cadastra(textField_1.getText() , textField.getText() ,passwordField.getText());
				} else {
					new JOptionPane().showMessageDialog(null, "Você deve inserir a mesma senha nos dois campos", "Senha não confere", JOptionPane.WARNING_MESSAGE);
				}
			} 
		});
		btnContinuar.setBounds(124, 327, 109, 33);
		contentPane.add(btnContinuar);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void cadastra(String login, String nome, String senha) {
		try {
			this.lan.cadastraAdministrador(login, nome, senha);
			JOptionPane.showMessageDialog(null, "Administrador cadastrado com sucesso!");
			this.dispose();
			new LoginFrame(this.lan);
		} catch (AdministradorLoginInvalidoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Login inválido", JOptionPane.WARNING_MESSAGE);
		} catch (AdministradorSenhaFracaException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Senha fraca", JOptionPane.WARNING_MESSAGE);
		} catch (AdministradorJaCadastradoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Administrador já cadastrado", JOptionPane.WARNING_MESSAGE);
		}
	}
}
