package lan.server.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditaServicoFrame extends JDialog {

	private JPanel contentPane;
	private ServicosFrame framepai;
	private Lan lan;
	private JTextField textField;
	private JTextField textField_1;
	private JFormattedTextField formattedTextField;

	public EditaServicoFrame(ServicosFrame framepai, Lan lan, String id, String nome, String preco) {
		this.framepai = framepai;
		this.lan = lan;
		setTitle("Editar servi\u00E7o");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 522, 144);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCod = new JLabel("Cod");
		lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCod.setBounds(10, 32, 52, 14);
		contentPane.add(lblCod);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(72, 29, 69, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(170, 32, 45, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(225, 29, 109, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreo.setBounds(344, 32, 46, 14);
		contentPane.add(lblPreo);
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(400, 29, 62, 20);
		contentPane.add(formattedTextField);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizaservico();
			}
		});
		btnAtualizar.setBounds(374, 70, 89, 23);
		contentPane.add(btnAtualizar);
		
		textField.setText(id);
		textField_1.setText(nome);
		formattedTextField.setText(preco);
		
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void atualizaservico() {
		String id = textField.getText();
		String descricao = textField_1.getText();
		double valor = Double.parseDouble(formattedTextField.getText().replace(",", ".").length() > 0 ? formattedTextField.getText().replace(",", "."): "0");
		if (descricao.length() > 0) {
			this.lan.atualizaServico(id, descricao, valor);
			new JOptionPane().showMessageDialog(null, "Serviço atualizado");
			this.dispose();
			this.framepai.callbackservicoeditado();
		}
	}

}
