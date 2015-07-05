package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.painel.Lan;
import lan.server.sessoes.Sessao;
import lan.server.util.DataHora;
import lan.server.util.Iterator;

public class GerenciadorSessoesFrame extends JDialog implements Runnable {

	private JPanel contentPane;
	private Lan lan;
	DefaultTableModel model;
	private JTable table;
	private int idsessaoselecionada;
	JButton btnPararSesso;

	public GerenciadorSessoesFrame(Lan lan) {
		setResizable(false);
		this.lan = lan;
		this.idsessaoselecionada = -1;
		setTitle("Gerenciador de sess\u00F5es");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 547, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 21, 500, 176);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		}; 
		
		this.model.addColumn("Id sessão"); 
		this.model.addColumn("Cliente"); 
		this.model.addColumn("Produto"); 
		this.model.addColumn("Tempo restante");
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(1).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.removeColumn(table.getColumnModel().getColumn(0));
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 1) {
		        	if (row >= 0 && col >= 0) {
		        		selecionasessao((int)(model.getValueAt(row, 0)));
		        	}
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		btnPararSesso = new JButton("Parar sess\u00E3o");
		btnPararSesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pararsessao();
			}
		});
		btnPararSesso.setEnabled(false);
		btnPararSesso.setBounds(405, 208, 115, 31);
		contentPane.add(btnPararSesso);
		this.setModal(true);
		new Thread(this).start();
		this.setVisible(true);
	}
	
	public void run() {
		while (true) {
			try {
				int linhastabela = this.table.getRowCount(); //remove tudo antes de adicionar
				for (int i = linhastabela - 1; i >= 0; i--) {
				    this.model.removeRow(i);
				}
				
				Iterator<Sessao> sessoes = this.lan.iteratorSessoes();
				int linhascriadas = 0;
				boolean sessaoencontrada = false;
				while (sessoes.hasNext()) {
					Sessao sessao = sessoes.next();
					this.model.addRow(new Object[]{sessao.getId(), sessao.getNomeCliente(), sessao.getNomeProduto(), DataHora.segundosToString(sessao.temporestante())});
					if (this.idsessaoselecionada == sessao.getId()) {
						table.setRowSelectionInterval(linhascriadas, linhascriadas);
						sessaoencontrada = true;
					}
					linhascriadas++;
				}
				if (!sessaoencontrada) {
					this.idsessaoselecionada = -1;
					btnPararSesso.setEnabled(false);
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selecionasessao(int sessao) {
		this.idsessaoselecionada = sessao;
		btnPararSesso.setEnabled(true);
	}
	
	public void pararsessao() {
		this.lan.finalizaSessao(String.valueOf(this.idsessaoselecionada));
	}
}
