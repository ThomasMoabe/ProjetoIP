package lan.server.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarProdutoFrame extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private ProdutosFrame framepai;
	private Lan lan;
	private JComboBox comboBox;

	public EditarProdutoFrame(ProdutosFrame framepai, Lan lan, String id, String nome, String tipo) {
		this.framepai = framepai;
		this.lan = lan;
		setTitle("Editar produto");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCod = new JLabel("Cod");
		lblCod.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCod.setBounds(10, 36, 41, 14);
		contentPane.add(lblCod);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(69, 33, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(165, 36, 41, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setBounds(216, 33, 104, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipo.setBounds(330, 36, 46, 14);
		contentPane.add(lblTipo);
		
		this.comboBox = new JComboBox();
		comboBox.addItem(new TipoProdutoCombo("Console", "Console"));
		comboBox.addItem(new TipoProdutoCombo("PC", "PC"));
		if (tipo.equals("Console")) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(1);
		}
		
		comboBox.setBounds(374, 33, 86, 20);
		contentPane.add(comboBox);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		btnAtualizar.setBounds(374, 82, 89, 23);
		contentPane.add(btnAtualizar);
		
		textField.setText(id);
		textField_1.setText(nome);
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void atualizar() {
		String id = textField.getText();
		String nome = textField_1.getText();
		String tipo = ((TipoProdutoCombo)comboBox.getSelectedItem()).getValue();
		if (nome.length() > 0) {
			this.lan.atualizaProduto(id, nome, tipo);
			new JOptionPane().showMessageDialog(null, "Produto atualizado");
			this.dispose();
			this.framepai.callbackprodutoeditado();
		}
	}
}
