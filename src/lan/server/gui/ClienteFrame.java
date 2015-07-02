package lan.server.gui;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import lan.server.clientes.Cliente;
import javax.swing.JButton;

public class ClienteFrame extends JDialog {

	private JPanel contentPane;
	private Cliente cliente;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField;

	public ClienteFrame(Cliente cliente, JDialog frame) {
		super(frame);
		setLocationRelativeTo(frame);
		this.setModal(true);
		this.cliente = cliente;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblId = new JLabel("Cod");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(26, 45, 43, 14);
		contentPane.add(lblId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(86, 45, 95, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome*");
		lblNome.setBounds(241, 48, 46, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(297, 45, 444, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereo.setBounds(10, 95, 59, 14);
		contentPane.add(lblEndereo);
		
		textField_2 = new JTextField();
		textField_2.setBounds(86, 95, 655, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail*");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(26, 140, 43, 14);
		contentPane.add(lblEmail);
		
		textField_3 = new JTextField();
		textField_3.setBounds(85, 140, 218, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNascimento = new JLabel("Nascimento*");
		lblNascimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNascimento.setBounds(297, 143, 100, 14);
		contentPane.add(lblNascimento);
		
		JFormattedTextField formattedTextField = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField.setBounds(407, 140, 95, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblDataDeCadastro = new JLabel("Data de cadastro");
		lblDataDeCadastro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeCadastro.setBounds(515, 140, 108, 14);
		contentPane.add(lblDataDeCadastro);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(633, 137, 108, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login*");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(26, 187, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField_5 = new JTextField();
		textField_5.setBounds(86, 184, 115, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha*");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(211, 187, 76, 14);
		contentPane.add(lblSenha);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(297, 184, 145, 20);
		contentPane.add(passwordField);
		
		JButton btnSalvar = new JButton("Gravar");
		btnSalvar.setBounds(652, 304, 89, 39);
		contentPane.add(btnSalvar);
		if (this.cliente != null) {
			this.setTitle("Cliente - " + this.cliente.getNome());
		} else {
			this.setTitle("Cadatro de cliente");
		}
		this.setVisible(true);
	}
	
	MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
}
