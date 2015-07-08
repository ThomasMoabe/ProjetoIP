package lan.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import lan.server.caixa.Transacao;
import lan.server.painel.Lan;
import lan.server.relatorios.RelatorioFluxoCaixa;
import lan.server.util.DataHora;
import lan.server.util.Iterator;

public class RelatorioFluxoCaixaFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private JFormattedTextField formattedTextField;
	private JFormattedTextField formattedTextField_1;
	private JComboBox comboBox;

	public RelatorioFluxoCaixaFrame(Lan lan) {
		setResizable(false);
		this.lan = lan;
		setTitle("Gerar relat\u00F3rio - fluxo de caixa");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDataInicial = new JLabel("Data inicial");
		lblDataInicial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataInicial.setBounds(22, 46, 68, 14);
		contentPane.add(lblDataInicial);
		
		formattedTextField = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextField.setBounds(100, 43, 83, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblDataFinal = new JLabel("Data final");
		lblDataFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDataFinal.setBounds(203, 46, 62, 14);
		contentPane.add(lblDataFinal);
		
		formattedTextField_1 = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField_1.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextField_1.setBounds(275, 43, 83, 20);
		contentPane.add(formattedTextField_1);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(368, 46, 46, 14);
		contentPane.add(lblTipo);
		
		comboBox = new JComboBox();
		comboBox.addItem(new TipoTransacaoCombo("Todas", "todas"));
		comboBox.addItem(new TipoTransacaoCombo("Entrada", "entrada"));
		comboBox.addItem(new TipoTransacaoCombo("Saída", "saida"));
		comboBox.setBounds(424, 43, 83, 20);
		contentPane.add(comboBox);
		
		JButton btnGerarRelatrio = new JButton("Gerar relat\u00F3rio");
		btnGerarRelatrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gerarrelatorio();
			}
		});
		btnGerarRelatrio.setBounds(352, 102, 155, 33);
		contentPane.add(btnGerarRelatrio);
		this.setModal(true);
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
	
	public void gerarrelatorio() {
		String datainicial = formattedTextField.getText();
		String datafinal = formattedTextField_1.getText();
		String tipotransacao = ((TipoTransacaoCombo)comboBox.getSelectedItem()).getValue();
		Iterator<Transacao> transacoes = null;
		if (datainicial.matches("[\\d]{2}/[\\d]{2}/[\\d]{4}") && datafinal.matches("[\\d]{2}/[\\d]{2}/[\\d]{4}")) {
			transacoes = this.lan.getTransacoesData(datainicial, datafinal, tipotransacao);	
		} else if(!tipotransacao.equals("todas")) {
			transacoes = this.lan.getTransacoesData("00/00/0000", DataHora.getData(), tipotransacao);
		} else {
			transacoes = this.lan.iteratorTransacoesFinanceiras();
		}
		RelatorioFluxoCaixa relatorio = new RelatorioFluxoCaixa(transacoes);
		new RelatorioFrame("Pré visualização de relatório - Fluxo de caixa", this.lan, relatorio, true);
	}
}
