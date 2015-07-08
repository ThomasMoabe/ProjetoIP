package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.painel.Lan;
import lan.server.relatorios.Relatorio;
import lan.server.relatorios.RelatorioGerado;
import lan.server.relatorios.RelatorioNaoEncontradoException;
import lan.server.util.Iterator;

public class RelatoriosRecentesFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private JTable table;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private JPanel panel_2;
	private JPanel panel_3;
	private JButton btnNewButton;
	private DefaultTableModel model;
	private int linhaselecionada;

	public RelatoriosRecentesFrame(Lan lan) {
		this.lan = lan;
		this.linhaselecionada = 0;
		setTitle("Relat\u00F3rios gerados");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		model.addColumn("Relatorio"); 
		model.addColumn("Nome"); 
		model.addColumn("Caminho"); 
		model.addColumn("Data"); 
		
		table = new JTable(model);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionalinha(row);
		        	}
		        } else if (evt.getClickCount() == 2) {
		        	if (row >= 0 && col >= 0) {
		        		selecionalinha(row);
		        		abrerelatorio();
		        	}
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.setRowHeight(table.getRowHeight() + 10);
		table.removeColumn(table.getColumnModel().getColumn(0));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(15);
		panel.add(panel_1, BorderLayout.WEST);
		
		lblNewLabel = new JLabel("Relat\u00F3rios gerados");
		panel_1.add(lblNewLabel);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setVgap(10);
		panel_2.add(panel_3, BorderLayout.EAST);
		
		btnNewButton = new JButton("Abrir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrerelatorio();
			}
		});
		btnNewButton.setEnabled(false);
		panel_3.add(btnNewButton);
		this.setModal(true);
		setMinimumSize(new Dimension(600, 400));
		this.carregarelatorios();
		this.setVisible(true);
	}

	public void carregarelatorios() {
		btnNewButton.setEnabled(false);
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		
		Iterator<RelatorioGerado> relatorios = this.lan.iteratorRelatoriosGerados();
		while (relatorios.hasNext()) {
			RelatorioGerado relatorio = relatorios.next();
			this.model.addRow(new Object[]{relatorio, relatorio.getNomeRelatorio(), relatorio.getCaminho(), relatorio.getData() + " - " + relatorio.getHora()});
		}
	}
	
	public void selecionalinha(int linha) {
		this.linhaselecionada = linha;
		btnNewButton.setEnabled(true);
	}
	
	public void abrerelatorio() {
		RelatorioGerado relatoriogerado = (RelatorioGerado) this.model.getValueAt(this.linhaselecionada, 0);
		String caminho = relatoriogerado.getCaminho();
		Relatorio relatorio;
		try {
			relatorio = new Relatorio(caminho);
			new RelatorioFrame(relatoriogerado.getNomeRelatorio(), this.lan, relatorio, false);
		} catch (RelatorioNaoEncontradoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível abrir relatório", JOptionPane.ERROR_MESSAGE);
		}
	}
}
