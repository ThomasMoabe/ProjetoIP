package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import lan.server.caixa.SaldoInsuficienteException;
import lan.server.caixa.Transacao;
import lan.server.caixa.TransacaoNaoEncontradaException;
import lan.server.painel.Lan;
import lan.server.util.DataHora;
import lan.server.util.Iterator;

import javax.swing.JComboBox;

public class CaixaFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private String transacaoselecionada;
	DefaultTableModel model;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JTable table;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_5;
	private JLabel lblDe;
	private JPanel panel_4;
	private JFormattedTextField formattedTextField;
	private JPanel panel_6;
	private JLabel lblAt;
	private JPanel panel_7;
	private JFormattedTextField formattedTextField_1;
	private JPanel panel_8;
	private JButton btnFiltrar;
	private JPanel panel_9;
	private JPanel panel_10;
	private JButton btnNewButton;
	private JPanel panel_11;
	private JButton btnNewButton_1;
	private JPanel panel_12;
	private JComboBox comboBox;

	public CaixaFrame(Lan lan) {
		this.lan = lan;
		this.transacaoselecionada = "0";
		setTitle("Caixa");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
			
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		this.model.addColumn("Cod"); 
		this.model.addColumn("Data"); 
		this.model.addColumn("Hora");
		this.model.addColumn("Tipo"); 
		this.model.addColumn("Descrição");
		this.model.addColumn("Valor");
		
		//JScrollPane scrollPane = new JScrollPane();
		//contentPane.add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 0, 10, 0));
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("Saldo: 0,00");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblNewLabel, BorderLayout.EAST);
		
		panel_11 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_11.getLayout();
		flowLayout_2.setVgap(10);
		panel.add(panel_11, BorderLayout.WEST);
		
		btnNewButton_1 = new JButton("Estornar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				estornatransacao();
			}
		});
		btnNewButton_1.setEnabled(false);
		panel_11.add(btnNewButton_1);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(this.model);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionatransacao(String.valueOf(model.getValueAt(row, 0)));
		        	}
		        }
		    }
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(4).setPreferredWidth(250);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);

		JScrollPane scrollPane = new JScrollPane(table);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(0);
		panel_2.add(panel_3, BorderLayout.WEST);
		
		panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_3.add(panel_5);
		
		lblDe = new JLabel("De:");
		panel_5.add(lblDe);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 0, 0, 20));
		panel_3.add(panel_4);
		
		formattedTextField = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextField.setColumns(10);
		panel_4.add(formattedTextField);
		
		panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_3.add(panel_6);
		
		lblAt = new JLabel("At\u00E9");
		panel_6.add(lblAt);
		
		panel_7 = new JPanel();
		panel_7.setBorder(new EmptyBorder(0, 0, 0, 20));
		panel_3.add(panel_7);
		
		formattedTextField_1 = new JFormattedTextField(createFormatter("##/##/####"));
		formattedTextField_1.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextField_1.setColumns(10);
		panel_7.add(formattedTextField_1);
		
		panel_12 = new JPanel();
		panel_12.setBorder(new EmptyBorder(0, 0, 0, 20));
		panel_3.add(panel_12);
		
		comboBox = new JComboBox();
		comboBox.addItem(new TipoTransacaoCombo("Todas", "todas"));
		comboBox.addItem(new TipoTransacaoCombo("Entrada", "entrada"));
		comboBox.addItem(new TipoTransacaoCombo("Saída", "saida"));
		panel_12.add(comboBox);
		
		panel_8 = new JPanel();
		panel_8.setBorder(new EmptyBorder(0, 0, 0, 10));
		panel_3.add(panel_8);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregavalores();
			}
		});
		panel_8.add(btnFiltrar);
		
		panel_9 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_9.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setVgap(10);
		panel_2.add(panel_9, BorderLayout.EAST);
		
		panel_10 = new JPanel();
		panel_9.add(panel_10);
		
		btnNewButton = new JButton("Entrada / Retirada");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abreentradaretirada();
			}
		});
		panel_10.setLayout(new BorderLayout(0, 0));
		panel_10.add(btnNewButton);
		
		setMinimumSize(new Dimension(800, 500));
		this.setModal(true);
		this.mostrasaldo();
		this.carregavalores();
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
	
	public void selecionatransacao(String id) {
		this.transacaoselecionada = id;
		btnNewButton_1.setEnabled(true);
	}
	
	public void estornatransacao() {
		try {
			this.lan.removeTransacaoFinanceira(this.transacaoselecionada);
			new JOptionPane().showMessageDialog(null, "Valor estornado");
		} catch (TransacaoNaoEncontradaException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Erro ao estornar transação", JOptionPane.ERROR_MESSAGE);
		} catch (SaldoInsuficienteException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Erro ao estornar transação", JOptionPane.ERROR_MESSAGE);
		}
		this.mostrasaldo();
	}
	
	public void mostrasaldo() {
		double saldo = this.lan.getSaldoCaixa();
		DecimalFormat df = new DecimalFormat("0000.00");
		lblNewLabel.setText("Saldo: " + df.format(saldo).replaceAll("^0+", "").replaceAll("^,", "0,"));
	}
	
	public void carregavalores() {
		btnNewButton_1.setEnabled(false);
		this.transacaoselecionada = "0";
		
		DecimalFormat df = new DecimalFormat("0000.00");
		
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}

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
		int linha = 0;
		while (transacoes.hasNext()) {
			Transacao transacao = transacoes.next();
			this.model.addRow(new Object[]{String.valueOf(transacao.getId()), transacao.getData(), transacao.getHora(), transacao.getTipo(),  transacao.getDescricao(), df.format(transacao.getValor()).replaceAll("^0+", "").replaceAll("^,", "0,")});
			table.setRowHeight(linha, 25);
			linha++;
		}
	}
	
	public void abreentradaretirada() {
		new EntradaRetiradaFrame(this, this.lan);
		this.mostrasaldo();
	}
	
	public void callbackentradaretirada() {
		this.carregavalores();
	}
}
