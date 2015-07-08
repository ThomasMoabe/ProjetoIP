package lan.server.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import lan.server.painel.Lan;
import lan.server.servicos.Servico;
import lan.server.servicos.ServicoNaoEncontradoException;
import lan.server.vendas.DescontoInvalidoException;

import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServicoFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Servico servico;
	private JFormattedTextField formattedTextField;
	private JFormattedTextField formattedTextField_1;
	private JFormattedTextField formattedTextField_2;
	private JLabel lblTotal;

	public ServicoFrame(Lan lan, Servico servico) {
		setTitle("Servi\u00E7o - " + servico.getDescricao());
		this.lan = lan;
		this.servico = servico;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 276, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreo.setBounds(29, 22, 93, 14);
		contentPane.add(lblPreo);
		
		DecimalFormat qtd = new DecimalFormat("#");  
		NumberFormatter formataQuantidade = new NumberFormatter(qtd);  
		formataQuantidade.setFormat(qtd);  
		formataQuantidade.setAllowsInvalid(false);  
		DefaultFormatterFactory quantidade = new DefaultFormatterFactory(formataQuantidade);
		
		formattedTextField = new JFormattedTextField(quantidade);
		formattedTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				calculavalor();
			}
		});
		formattedTextField.setBounds(132, 58, 71, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblN = new JLabel("Quantidade");
		lblN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblN.setBounds(53, 58, 69, 14);
		contentPane.add(lblN);
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValor.setBounds(10, 100, 112, 14);
		contentPane.add(lblValor);
		
		formattedTextField_1 = new JFormattedTextField(valor);
		formattedTextField_1.setEnabled(false);
		formattedTextField_1.setBounds(132, 97, 71, 20);
		contentPane.add(formattedTextField_1);
		
		JLabel lblDesconto = new JLabel("Desconto");
		lblDesconto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesconto.setBounds(29, 140, 93, 14);
		contentPane.add(lblDesconto);
		
		formattedTextField_2 = new JFormattedTextField(valor);
		formattedTextField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				calculavalor();
			}
		});
		formattedTextField_2.setBounds(132, 140, 71, 20);
		contentPane.add(formattedTextField_2);
		
		lblTotal = new JLabel("Total");
		lblTotal.setBounds(55, 182, 93, 14);
		contentPane.add(lblTotal);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contrataservico();
			}
		});
		btnConcluir.setBounds(117, 210, 89, 31);
		contentPane.add(btnConcluir);
		
		lblPreo.setText("Preço: R$ " + String.valueOf(servico.getPreco()));
		
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void calculavalor() {
		int quantidade = Integer.parseInt(formattedTextField.getText().length() > 0 ? formattedTextField.getText() : "0");
		double preco = this.servico.getPreco();
		double valor = quantidade * preco;
		DecimalFormat dfvalor = new DecimalFormat("0.00");
		formattedTextField_1.setText(dfvalor.format(valor));
		double desconto = Double.parseDouble(formattedTextField_2.getText().replace(",", ".").length() > 0 ? formattedTextField_2.getText().replace(",", ".") : "0");
		double total = valor - desconto;
		lblTotal.setText("Total R$ " + dfvalor.format(total));
	}
	
	public void contrataservico() {
		String id = String.valueOf(this.servico.getId());
		int quantidade = Integer.parseInt(formattedTextField.getText().length() > 0 ? formattedTextField.getText() : "0");
		double desconto = Double.parseDouble(formattedTextField_2.getText().replace(",", ".").length() > 0 ? formattedTextField_2.getText().replace(",", ".") : "0");
		try {
			if (quantidade > 0) {
				this.lan.contrataServico(id, quantidade, desconto);
				new JOptionPane().showMessageDialog(null, "Serviço cadastrado");
				formattedTextField.setText("0");
				formattedTextField_1.setText("");
				formattedTextField_2.setText("");
				lblTotal.setText("Total R$ 0,00");
			}
		} catch (ServicoNaoEncontradoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		} catch (DescontoInvalidoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
