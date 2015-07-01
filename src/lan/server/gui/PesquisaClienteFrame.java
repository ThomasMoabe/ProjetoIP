package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.clientes.Cliente;
import lan.server.clientes.ClienteIterator;
import lan.server.painel.Lan;

public class PesquisaClienteFrame extends JDialog { //Jdialog
	private Lan lan;
	DefaultTableModel model; //para uso em inser
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
					PesquisaClienteFrame frame = new PesquisaClienteFrame(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PesquisaClienteFrame(Lan lan) {
		this.lan = lan;
		this.setVisible(true);
		
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
		panel.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		panel_2.setBorder(new EmptyBorder(0, 10, 0, 0) );
		panel_2.setLayout(new BorderLayout(0, 0));
		
		btnNewButton = new JButton("Procurar cliente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procura(textField.getText());
			}
		});
		panel_2.add(btnNewButton);
		
		String[] columnNames = {"Nome",
                "Login",
                "Endereço",
                "E-mail",
                "Data de cadastro"};
		Object[][] data = {
			    /*{"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false), "casa"},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)}
			     ,
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)} */
			     
			};
		
		
		this.model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		}; 
		this.table = new JTable(model); 
		model.addColumn("Nome"); 
		model.addColumn("Login"); 
		model.addColumn("Endereço");
		model.addColumn("E-mail");
		model.addColumn("Data de cadastro");
		//table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setBorder(new EmptyBorder(10, 0, 7, 0) );
		panel_1.setLayout(new BorderLayout(0, 0));
		btnNewButton_2 = new JButton("Cadastrar novo");
		panel_1.add(btnNewButton_2, BorderLayout.EAST);
		   
		  
		
		//DefaultTableModel model = (DefaultTableModel) table.getModel();
		//model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
		
		setMinimumSize(new Dimension(800, 400));
		setModal(true);
	}
	
	public void procura(String parametros) {
		ClienteIterator encontrados = this.lan.procuraClientes(parametros);
		int linhastabela = this.table.getRowCount(); //remove tudo antes de adicionar
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		while (encontrados.hasNext()) {
			Cliente atual = encontrados.next();
			this.model.addRow(new Object[]{atual.getNome(), atual.getLogin(), atual.getEndereco(), atual.getEmail(), atual.getDataCadastro(), atual});
		}
		//this.model.addRow(new Object[]{parametros, "v2", "v2", "v2", "v2"});
	}
}
