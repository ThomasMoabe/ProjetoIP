package lan.server.gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import lan.server.painel.Lan;
import lan.server.servicos.Servico;
import lan.server.util.Iterator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServicosFrame extends JDialog {

	private JPanel contentPane;
	private IndexFrame framepai;
	private Lan lan;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JFormattedTextField formattedTextField;
	private int linhaselecionada;

	public ServicosFrame(IndexFrame framepai, Lan lan) {
		this.framepai = framepai;
		this.lan = lan;
		this.linhaselecionada = 0;
		setTitle("Servi\u00E7os");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 493, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Descri\u00E7\u00E3o");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setBounds(27, 38, 68, 14);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(96, 35, 121, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPreo.setBounds(221, 38, 46, 14);
		contentPane.add(lblPreo);
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);
		
		formattedTextField = new JFormattedTextField(valor);
		formattedTextField.setBounds(263, 35, 63, 20);
		contentPane.add(formattedTextField);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastraservico();
			}
		});
		btnCadastrar.setBounds(348, 34, 114, 23);
		contentPane.add(btnCadastrar);
		
		JPanel panel = new JPanel();
		panel.setBounds(28, 80, 434, 240);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		}; 
		
		model.addColumn("Cod"); 
		model.addColumn("Nome"); 
		model.addColumn("Preço"); 
		
		table = new JTable(this.model);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionalinha(row);
		        	}
		        } else if(evt.getClickCount() == 2) {
		        	if (row >= 0 && col >= 0) {
		        		selecionalinha(row);
		        		editaservico();
		        	}
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editaservico();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(373, 333, 89, 23);
		contentPane.add(btnEditar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluiservico();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(263, 333, 89, 23);
		contentPane.add(btnExcluir);
		this.setModal(true);
		this.mostrarservicos();
		this.setVisible(true);
	}
	
	public void mostrarservicos() {
		btnExcluir.setEnabled(false);
		btnEditar.setEnabled(false);
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		DecimalFormat df = new DecimalFormat("0000.00");
		Iterator<Servico> servicos = this.lan.iteratorServicos();
		while (servicos.hasNext()) {
			Servico servico = servicos.next();
			this.model.addRow(new Object[]{servico.getId(), servico.getDescricao(), df.format(servico.getPreco()).replaceAll("^0+", "").replaceAll("^,", "0,")});
		}
	}
	
	public void cadastraservico() {
		String descricao = textField.getText();
		double preco = Double.parseDouble(formattedTextField.getText().replace(",", ".").length() > 0 ? formattedTextField.getText().replace(",", ".") : "0");
		if (descricao.length() > 0) {
			this.lan.cadastraServico(descricao, preco);
			new JOptionPane().showMessageDialog(null, "Serviço cadastrado");
			this.mostrarservicos();
			this.framepai.callbackmodificaservicos();
		}
	}
	
	public void selecionalinha(int linha) {
		this.linhaselecionada = linha;
		btnExcluir.setEnabled(true);
		btnEditar.setEnabled(true);
	}
	
	public void editaservico() {
		String id = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
		String nome = String.valueOf(this.model.getValueAt(this.linhaselecionada, 1));
		String valor = String.valueOf(this.model.getValueAt(this.linhaselecionada, 2));
		new EditaServicoFrame(this, this.lan, id, nome, valor);
	}
	
	public void excluiservico() {
		String id = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
		this.lan.deletaServico(id);
		new JOptionPane().showMessageDialog(null, "Serviço excluido");
		this.mostrarservicos();
		this.framepai.callbackmodificaservicos();
	}
	
	public void callbackservicoeditado() {
		this.mostrarservicos();
		this.framepai.callbackmodificaservicos();
	}
}
