package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.painel.Lan;
import lan.server.produtos.CategoriaProdutos;
import lan.server.produtos.Produto;
import lan.server.util.Iterator;

public class ProdutosFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Iterator<CategoriaProdutos> categorias;
	private JTextField textField;
	private DefaultTableModel model;
	private JTable table;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JButton btnEditar;
	private JButton btnExcluir;
	private int linhaselecionada;

	public ProdutosFrame(Lan lan, Iterator<CategoriaProdutos> categorias) {
		this.categorias = categorias;
		this.lan = lan;
		this.linhaselecionada = 0;
		setTitle("Produtos");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostraprodutos();
			}
		});
		comboBox.setBounds(37, 24, 366, 20);
		contentPane.add(comboBox);
		
		JLabel lblNomeDoProduto = new JLabel("Nome do produto");
		lblNomeDoProduto.setBounds(37, 67, 100, 14);
		contentPane.add(lblNomeDoProduto);
		
		textField = new JTextField();
		textField.setBounds(145, 64, 106, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cadastrarproduto();
			}
		});
		btnCadastrar.setBounds(303, 117, 100, 34);
		contentPane.add(btnCadastrar);
		
		JPanel panel = new JPanel();
		panel.setBounds(37, 174, 366, 188);
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
		model.addColumn("Tipo"); 
		model.addColumn("Nome"); 
		model.addColumn("Alugado"); 
		
		table = new JTable(this.model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane, BorderLayout.CENTER);
		
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
		        		editaproduto();
		        	}
		        }
		    }
		});
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluiproduto();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(210, 373, 89, 23);
		contentPane.add(btnExcluir);
		
		this.comboBox_1 = new JComboBox();
		comboBox_1.setBounds(303, 64, 100, 20);
		contentPane.add(comboBox_1);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(261, 67, 32, 14);
		contentPane.add(lblTipo);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editaproduto();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(314, 373, 89, 23);
		contentPane.add(btnEditar);
		this.setModal(true);
		this.mostracategorias();
		this.mostraprodutos();
		this.setVisible(true);
	}
	
	public void mostracategorias() {
		comboBox_1.addItem(new TipoProdutoCombo("Console", "Console"));
		comboBox_1.addItem(new TipoProdutoCombo("PC", "PC"));
		while (this.categorias.hasNext()) {
			CategoriaProdutos categoria = this.categorias.next();
			comboBox.addItem(new CategoriasCombo(categoria.getDescricao(), categoria));
		}
	}
	
	public void cadastrarproduto() {
		String idcategoria = String.valueOf(((CategoriasCombo)comboBox.getSelectedItem()).getValue().getId());
		String nome = textField.getText();
		String tipo = ((TipoProdutoCombo)comboBox_1.getSelectedItem()).getValue();
		if (nome.length() > 0) {
			this.lan.novoProduto(idcategoria, nome, tipo);
			new JOptionPane().showMessageDialog(null, "Produto cadastrado");
			this.mostraprodutos();
		}
	}
	
	public void mostraprodutos() {
		btnExcluir.setEnabled(false);
		btnEditar.setEnabled(false);
		this.linhaselecionada = 0;
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		String idcategoria = String.valueOf(((CategoriasCombo)comboBox.getSelectedItem()).getValue().getId());
		Iterator<Produto> produtos = this.lan.iteratorProduto(idcategoria);
		while (produtos.hasNext()) {
			Produto produto = produtos.next();
			this.model.addRow(new Object[]{String.valueOf(produto.getId()), produto.getTipo(),produto.getNome(), produto.alugado() ? "Sim": "Não"});
		}
	}
	
	public void selecionalinha(int linha) {
		this.linhaselecionada = linha;
		btnExcluir.setEnabled(true);
		btnEditar.setEnabled(true);
	}
	
	public void excluiproduto() {
		String id = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
		this.lan.deletaProduto(id);
		this.mostraprodutos();
		new JOptionPane().showMessageDialog(null, "Produto excluído");
	}
	
	public void editaproduto() {
		String id = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
		String nome = String.valueOf(this.model.getValueAt(this.linhaselecionada, 2));
		String tipo = String.valueOf(this.model.getValueAt(this.linhaselecionada, 1));
		new EditarProdutoFrame(this, this.lan, id, nome, tipo);
	}
	
	public void callbackprodutoeditado() { //deve ser chamado pelo frame de edição de produtos após atualização
		mostraprodutos();
	}
}
