package lan.server.gui;

import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import lan.server.painel.Lan;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditaCategoriaFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private CategoriasProdutosFrame framepai;
	private JTextField textField;
	private JTextField textField_1;
	private JFormattedTextField formattedTextField;

	public EditaCategoriaFrame(Lan lan, CategoriasProdutosFrame framepai, String idcategoria, String nomecategoria, String preco) {
		this.lan = lan;
		this.framepai = framepai;
		setResizable(false);
		setTitle("Editar categoria");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 562, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(56, 37, 58, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblCod = new JLabel("Cod");
		lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCod.setBounds(0, 40, 46, 14);
		contentPane.add(lblCod);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(141, 40, 39, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(190, 37, 124, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPreoHora = new JLabel("Pre\u00E7o hora");
		lblPreoHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreoHora.setBounds(307, 40, 88, 14);
		contentPane.add(lblPreoHora);
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);
		
		formattedTextField = new JFormattedTextField(valor);
		formattedTextField.setBounds(405, 37, 102, 20);
		contentPane.add(formattedTextField);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarcategoria();
			}
		});
		btnAtualizar.setBounds(418, 82, 89, 36);
		contentPane.add(btnAtualizar);
		this.setModal(true);
		textField.setText(idcategoria);
		textField_1.setText(nomecategoria);
		formattedTextField.setText(preco);
		this.setVisible(true);
	}
	
	public void atualizarcategoria() {
		String id = textField.getText();
		String descricao = textField_1.getText();
		String preco = formattedTextField.getText();
		double precohora = Double.parseDouble(preco.replace(",", "."));
		this.lan.atualizaCategoriaProdutos(id, descricao, precohora);
		new JOptionPane().showMessageDialog(null, "Categoria atualizada");
		this.dispose();
		this.framepai.callbackatualizacao();
	}
}
