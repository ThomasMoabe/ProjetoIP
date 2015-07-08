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
import lan.server.produtos.CategoriaProdutos;
import lan.server.produtos.NenhumaCategoriaCadastradaException;
import lan.server.util.Iterator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoriasProdutosFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	private JFormattedTextField formattedTextField;
	private JButton btnExcluir;
	private JButton btnNewButton;
	private int linhaselecionada;

	public CategoriasProdutosFrame(IndexFrame framepai, Lan lan) {
		this.lan = lan;
		this.linhaselecionada = 0;
		setTitle("Categorias");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(10, 25, 41, 14);
		contentPane.add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(65, 22, 126, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPreo = new JLabel("Pre\u00E7o hora");
		lblPreo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreo.setBounds(187, 25, 77, 14);
		contentPane.add(lblPreo);
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);
		
		formattedTextField = new JFormattedTextField(valor);
		formattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		formattedTextField.setBounds(274, 22, 60, 20);
		contentPane.add(formattedTextField);
		
		JButton btnCriar = new JButton("Criar");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastracategoria();
			}
		});
		btnCriar.setBounds(344, 21, 81, 23);
		contentPane.add(btnCriar);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 64, 405, 256);
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
		model.addColumn("Preço por hora"); 
		
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
		        }
		        if (evt.getClickCount() == 2) {
		        	if (row >= 0 && col >= 0) {
		        		selecionalinha(row);
		        		abreedicaocategoria();
		        	}
		        }
		    }
		});
		
		btnNewButton = new JButton("Editar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abreedicaocategoria();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(336, 344, 89, 23);
		contentPane.add(btnNewButton);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				excluircategoria();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(228, 344, 89, 23);
		contentPane.add(btnExcluir);
		this.setModal(true);
		this.mostracategorias();
		this.setVisible(true);
	}
	
	public void mostracategorias() {
		this.linhaselecionada = 0;
		btnExcluir.setEnabled(false);
		btnNewButton.setEnabled(false);
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		DecimalFormat df = new DecimalFormat("0000.00");
		try {
			Iterator<CategoriaProdutos> categorias = this.lan.iteratorCategoriasProdutos();
			while (categorias.hasNext()) {
				CategoriaProdutos categoria = categorias.next();
				this.model.addRow(new Object[]{String.valueOf(categoria.getId()), categoria.getDescricao(), df.format(categoria.getPrecoHora()).replaceAll("^0+", "").replaceAll("^,", "0,")});
			}
		} catch (NenhumaCategoriaCadastradaException e) {}
	}
	
	public void cadastracategoria() {
		String descricao = textField.getText();
		String preco = formattedTextField.getText().replace(",", ".");
		if (preco.length() > 1) {
			double precohora = Double.parseDouble(preco);
			this.lan.novaCategoriaProdutos(descricao, precohora);
			new JOptionPane().showMessageDialog(null, "Categoria cadastrada");
			textField.setText("");
			formattedTextField.setText("");
			this.mostracategorias();
		}
	}
	
	public void selecionalinha(int linha) {
		this.linhaselecionada = linha;
		btnExcluir.setEnabled(true);
		btnNewButton.setEnabled(true);
	}
	
	public void excluircategoria() {
		int escolha = JOptionPane.showConfirmDialog(null, "Deletar uma categoria implica em deletar todos os seus produtos e todas as horas que os clientes possuem na mesma\ndeseja continuar?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
		if (escolha == JOptionPane.YES_OPTION) {
			String categoriaselecionada = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
			this.lan.deletaCategoriaProdutos(categoriaselecionada);
			new JOptionPane().showMessageDialog(null, "Categoria excluída");
			this.mostracategorias();
		}
	}
	
	public void abreedicaocategoria() {
		String idcategoria = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
		String nomecategoria = String.valueOf(this.model.getValueAt(this.linhaselecionada, 1));
		String valorcategoria = String.valueOf(this.model.getValueAt(this.linhaselecionada, 2));
		new EditaCategoriaFrame(this.lan, this, idcategoria, nomecategoria, valorcategoria);
	}
	
	public void callbackatualizacao() {
		this.mostracategorias();
	}
}
