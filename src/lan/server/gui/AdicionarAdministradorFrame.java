package lan.server.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lan.server.administracao.AdministradorJaCadastradoException;
import lan.server.administracao.AdministradorLoginInvalidoException;
import lan.server.administracao.AdministradorSenhaFracaException;
import lan.server.painel.Lan;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdicionarAdministradorFrame extends JDialog {

	private JPanel contentPane;
	private AdministradoresFrame framepai;
	private Lan lan;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	public AdicionarAdministradorFrame(AdministradoresFrame framepai, Lan lan) {
		setTitle("Cadastro de administrador");
		this.framepai = framepai;
		this.lan = lan;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 328, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(27, 37, 86, 14);
		contentPane.add(lblLogin);
		
		textField = new JTextField();
		textField.setBounds(123, 34, 85, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(27, 80, 85, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(122, 77, 125, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(27, 121, 85, 14);
		contentPane.add(lblSenha);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, 118, 125, 20);
		contentPane.add(passwordField);
		
		JLabel lblConfirmeASenha = new JLabel("Confirme a senha");
		lblConfirmeASenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmeASenha.setBounds(10, 164, 102, 14);
		contentPane.add(lblConfirmeASenha);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(122, 161, 125, 20);
		contentPane.add(passwordField_1);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastraadministrador();
			}
		});
		btnCadastrar.setBounds(123, 215, 126, 23);
		contentPane.add(btnCadastrar);
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void cadastraadministrador() {
		String login = textField.getText();
		String nome = textField_1.getText();
		String senha = passwordField.getText();
		String confirmasenha = passwordField_1.getText();
		if (!senha.equals(confirmasenha)) {
			new JOptionPane().showMessageDialog(null, "Você deve inserir a mesma senha nos dois campos", "Senha não confere", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				this.lan.cadastraAdministrador(login, nome, senha);
				JOptionPane.showMessageDialog(null, "Administrador cadastrado com sucesso!");
				this.dispose();
				this.framepai.callbackadmcadastrado();
			} catch (AdministradorLoginInvalidoException e) {
				new JOptionPane().showMessageDialog(null, e.getMessage(), "Login inválido", JOptionPane.ERROR_MESSAGE);
			} catch (AdministradorSenhaFracaException e) {
				new JOptionPane().showMessageDialog(null, e.getMessage(), "Senha fraca", JOptionPane.ERROR_MESSAGE);
			} catch (AdministradorJaCadastradoException e) {
				new JOptionPane().showMessageDialog(null, e.getMessage(), "Administrador já cadastrado", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
