package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.clientes.Cliente;
import lan.server.painel.Lan;
import lan.server.util.Iterator;

public class PesquisaClienteFrame extends JDialog { //Jdialog
	private Lan lan;
	private JDialog janelaclientefilha;
	private DefaultTableModel model; //para uso em inser
	private Cliente[] clientesobjeto; //usados para manter os objetos de cliente que serão abertos na janela cliente
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JPanel panel_1;
	private JButton btnNewButton_2;
	private JPanel panel_2;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PesquisaClienteFrame frame = new PesquisaClienteFrame(null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PesquisaClienteFrame(Lan lan, JFrame frame) {
		super (frame);
		this.lan = lan;
		setModal(true);
		this.janelaclientefilha = null;
		
		setTitle("Clientes");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setBorder(new EmptyBorder(5, 0, 10, 0) );
		panel.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procuracliente();
			}
		});
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		panel_2.setBorder(new EmptyBorder(0, 10, 0, 0) );
		panel_2.setLayout(new BorderLayout(0, 0));
		
		btnNewButton = new JButton("Procurar cliente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procuracliente();
			}
		});
		panel_2.add(btnNewButton);

		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		}; 
		this.table = new JTable(model); 
		model.addColumn("Cod"); 
		model.addColumn("Nome"); 
		model.addColumn("Login"); 
		model.addColumn("Endereço");
		model.addColumn("E-mail");
		model.addColumn("Data de cadastro");
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(25);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        if (evt.getClickCount() == 2) {
		        	if (row >= 0 && col >= 0) {
		        		abrejanelacliente(String.valueOf(model.getValueAt(row, 0)));
		        	}
		        }
		    }
		});
		
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setBorder(new EmptyBorder(10, 0, 7, 0) );
		panel_1.setLayout(new BorderLayout(0, 0));
		btnNewButton_2 = new JButton("Cadastrar novo");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrejanelacliente("0");
			}
		});
		panel_1.add(btnNewButton_2, BorderLayout.EAST);
		
		setMinimumSize(new Dimension(800, 400));
		//setLocationRelativeTo(frame);
		this.setVisible(true);
	}
	
	public void procuracliente() {
		String parametros = textField.getText();
		Iterator<Cliente> encontrados = this.lan.procuraClientes(parametros);
		int linhastabela = this.table.getRowCount(); //remove tudo antes de adicionar
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		while (encontrados.hasNext()) {
			Cliente atual = encontrados.next();
			this.model.addRow(new Object[]{atual.getId(), atual.getNome(), atual.getLogin(), atual.getEndereco(), atual.getEmail(), atual.getDataCadastro()});
		}
		//this.model.addRow(new Object[]{parametros, "v2", "v2", "v2", "v2"});
	}
	
	public void abrejanelacliente(String id) {
		Cliente cliente = id.equals("0") ? null : this.lan.getCliente(id);
		if (this.janelaclientefilha == null) {
			this.janelaclientefilha = new ClienteFrame(this.lan, cliente, this);
		} else {
			this.janelaclientefilha.dispose();
			this.janelaclientefilha = new ClienteFrame(this.lan, cliente, this);
		}
	}
	
	public void callbackmodificacao() { //se excluir ou atualizar o cadastro do cliente a janela do cliente avisa pra atualizar na pesquisa
		this.procuracliente();
	}
}
