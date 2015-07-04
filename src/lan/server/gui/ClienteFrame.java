package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import lan.server.clientes.Cliente;
import lan.server.clientes.ClienteJaCadastradoException;
import lan.server.clientes.ClienteLoginInvalidoException;
import lan.server.clientes.ClienteSenhaFracaException;
import lan.server.clientes.ClienteValorObrigatorioException;
import lan.server.clientes.TempoCliente;
import lan.server.clientes.TempoClienteIterator;
import lan.server.painel.Lan;
import lan.server.util.DataHora;

public class ClienteFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Cliente cliente;
	DefaultTableModel model;
	private JDialog paijanela;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	JFormattedTextField formattedTextField;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPasswordField passwordField;
	JButton btnSalvar;
	private JButton btnNewButton_1;
	JPanel panel;
	private JTable table;

	public ClienteFrame(Lan lan, Cliente cliente, JDialog frame) {
		super(frame);
		setLocationRelativeTo(frame);
		this.paijanela = frame;
		this.setModal(true);
		this.lan = lan;
		this.cliente = cliente;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(26, 237, 745, 284);
		panel.setVisible(false);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Excluir");
		btnNewButton.setBounds(521, 215, 94, 38);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Atualizar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastracliente();
			}
		});
		btnNewButton_1.setBounds(646, 215, 89, 38);
		panel.add(btnNewButton_1);
		
		JLabel lblHorasDeJogo = new JLabel("Horas de jogo restantes");
		lblHorasDeJogo.setBounds(24, 11, 169, 14);
		panel.add(lblHorasDeJogo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(24, 41, 457, 162);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		}; 
		this.table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		this.model.addColumn("Categoria"); 
		this.model.addColumn("Tempo");
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excluircadastro();
			}
		});
		
		JLabel lblId = new JLabel("Cod");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(38, 42, 43, 14);
		contentPane.add(lblId);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(98, 42, 95, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome*");
		lblNome.setBounds(253, 45, 46, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(309, 42, 444, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEndereo.setBounds(22, 92, 59, 14);
		contentPane.add(lblEndereo);
		
		textField_2 = new JTextField();
		textField_2.setBounds(98, 92, 655, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail*");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(38, 137, 43, 14);
		contentPane.add(lblEmail);
		
		textField_3 = new JTextField();
		textField_3.setBounds(97, 137, 218, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNascimento = new JLabel("Nascimento*");
		lblNascimento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNascimento.setBounds(309, 140, 100, 14);
		contentPane.add(lblNascimento);
		
		formattedTextField = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField.setBounds(419, 137, 95, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblDataDeCadastro = new JLabel("Data de cadastro");
		lblDataDeCadastro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataDeCadastro.setBounds(527, 137, 108, 14);
		contentPane.add(lblDataDeCadastro);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBounds(645, 134, 108, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Login*");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(38, 184, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField_5 = new JTextField();
		textField_5.setBounds(98, 181, 115, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha*");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setBounds(223, 184, 76, 14);
		contentPane.add(lblSenha);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(309, 181, 145, 20);
		contentPane.add(passwordField);
		
		btnSalvar = new JButton("Cadastrar");
		btnSalvar.setBounds(627, 307, 108, 39);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cadastracliente();
			}
		});
		if (this.cliente != null) {
			this.setTitle("Cliente - " + this.cliente.getNome());
		} else {
			this.setTitle("Cadatro de cliente");
		}
		this.preenchecaixas();
		this.mostratempocliente();
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
	
	public void preenchecaixas() {
		if (this.cliente == null) {
			this.textField_4.setText(DataHora.getData());
			setBounds(100, 100, 800, 400);
		} else {
			btnSalvar.setVisible(false);
			panel.setVisible(true);
			textField.setText(String.valueOf(this.cliente.getId()));
			textField_1.setText(this.cliente.getNome());
			textField_2.setText(this.cliente.getEndereco());
			textField_3.setText(this.cliente.getEmail());
			formattedTextField.setText(this.cliente.getDataNascimento());
			textField_4.setText(this.cliente.getDataCadastro());
			textField_5.setText(this.cliente.getLogin());
			passwordField.setText(this.cliente.getSenha());
		}
	}
	
	public void cadastracliente() {
		String nome = textField_1.getText();
		String endereco = textField_2.getText();
		String email = textField_3.getText();
		String datanascimento = formattedTextField.getText();
		String login = textField_5.getText();
		String senha = passwordField.getText();
		
		int id= 0; //sup�es-se que seja um cadastro
		if (this.cliente != null) {
			id = this.cliente.getId(); //� uma atualiza��o de cadastro na verdade...
		}
		try {
			this.lan.cadastraAtualizaCliente(String.valueOf(id), login, nome, endereco, email, senha, datanascimento);
			new JOptionPane().showMessageDialog(null, this.cliente == null ? "Cadastro realizado com sucesso" : "Cadastro atualizado com sucesso");
			if (this.cliente != null) {
				((PesquisaClienteFrame)this.paijanela).callbackmodificacao();
			}
			this.dispose();
		} catch (ClienteValorObrigatorioException e) {
			String erro = "Por favor preencha os seguintes campos obrigat�rios\n";
			for (int i = 0; i < e.getValoresNulos().length; i++) {
				erro+= "\n" + e.getValoresNulos()[i];
			}
			new JOptionPane().showMessageDialog(null, erro, "N�o foi poss�vel cadastrar cliente", JOptionPane.ERROR_MESSAGE);
		} catch (ClienteJaCadastradoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "N�o foi poss�vel cadastrar cliente", JOptionPane.ERROR_MESSAGE);
		} catch (ClienteLoginInvalidoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "N�o foi poss�vel cadastrar cliente", JOptionPane.ERROR_MESSAGE);
		} catch (ClienteSenhaFracaException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "N�o foi poss�vel cadastrar cliente", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	public void excluircadastro() {
		final JOptionPane optionPane = new JOptionPane();
		 int escolha = JOptionPane.showConfirmDialog(null, "O cadastro do cliente ser� exclu�do permanentemente assim como suas horas de jogo cadastradas\ndeseja continuar?", "Confirmar exclus�o", JOptionPane.YES_NO_OPTION);
		 if (escolha == JOptionPane.YES_OPTION) {
			 	this.lan.deletaCliente(String.valueOf(this.cliente.getId()));
			 	this.lan.deletaTempoCliente(String.valueOf(this.cliente.getId()));
				new JOptionPane().showMessageDialog(null, "Cliente excluido");
				this.dispose();
				((PesquisaClienteFrame)this.paijanela).callbackmodificacao();
		 }
	}
	
	public void mostratempocliente() {
		TempoClienteIterator tempocliente = this.lan.iteratorTempoCliente(String.valueOf(this.cliente.getId()));
		while (tempocliente.hasNext()) {
			TempoCliente tempo = tempocliente.next();System.out.println(tempo);
			this.model.addRow(new Object[]{tempo.getIdCategoriaProduto(), this.segundosToString(tempo.getSegundos())});
		}
	}
	
	private String segundosToString(int segundos) {
		int horastem = (int) Math.floor(segundos/3600);
		int minutostem = (int) Math.floor((segundos % 3600) / 60);
		int segundostem = (int) Math.floor(segundos % 60);
		String horaformatada = (horastem > 0 ? String.valueOf(horastem) + (horastem > 1 ? " horas" : " hora") : "") + ((horastem > 0 && minutostem > 0 && segundostem > 0) ? ", " : (horastem > 0 && minutostem > 0 && segundostem == 0) ? " e " : "") + ((minutostem > 0) ? String.valueOf(minutostem) + (minutostem > 1 ? " minutos" : " minuto") : "") + ((horastem > 0 || minutostem > 0) && (segundostem  >0) ? " e " : "") + (segundostem > 0 ? String.valueOf(segundostem) + (segundostem > 1 ? " segundos" : " segundo") : "");
		return horaformatada;
	}
}
