package lan.server.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import lan.server.caixa.SaldoInsuficienteException;
import lan.server.painel.Lan;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntradaRetiradaFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private JTextField textField;
	JComboBox comboBox;
	JFormattedTextField formattedTextField;
	
	public EntradaRetiradaFrame(Lan lan) {
		setTitle("Entrada / Retirada");
		setResizable(false);
		this.lan = lan;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(190, 36, 104, 20);
		comboBox.addItem(new TipoTransacaoCombo("Entrada", "entrada"));
		comboBox.addItem(new TipoTransacaoCombo("Saída", "saida"));
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(40, 83, 380, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);
		
		formattedTextField = new JFormattedTextField(valor);
		formattedTextField.setBounds(316, 36, 104, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setHorizontalAlignment(SwingConstants.LEFT);
		lblValor.setBounds(316, 14, 66, 14);
		contentPane.add(lblValor);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescrio.setBounds(40, 58, 104, 14);
		contentPane.add(lblDescrio);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entradasaida();
			}
		});
		btnConcluir.setBounds(331, 144, 89, 32);
		contentPane.add(btnConcluir);
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void entradasaida() {
		String tipo = ((TipoTransacaoCombo)comboBox.getSelectedItem()).getValue();
		String descricao = textField.getText();
		double valor = Double.parseDouble(formattedTextField.getText().replace(",", "."));
		try {
			this.lan.novaTransacaoFinanceira(tipo, descricao, valor);
			new JOptionPane().showMessageDialog(null, tipo.equals("entrada") ? "Valor adicionado ao caixa" : "Retirada concluída" );
			this.dispose();
		} catch (SaldoInsuficienteException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível inserir nova transação de saída", JOptionPane.ERROR_MESSAGE);
		}
	}
}
