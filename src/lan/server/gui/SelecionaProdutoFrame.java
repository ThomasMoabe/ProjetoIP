package lan.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lan.server.clientes.Cliente;
import lan.server.painel.Lan;
import lan.server.produtos.CategoriaProdutos;
import lan.server.produtos.Produto;
import lan.server.sessoes.ImpossivelCriarSessaoException;
import lan.server.util.Iterator;

public class SelecionaProdutoFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Cliente cliente;
	private CategoriaProdutos categoria;
	private JDialog framepai;
	private JComboBox comboBox;

	public SelecionaProdutoFrame(JDialog framepai, Lan lan, Cliente cliente, CategoriaProdutos categoria) {
		setTitle("Produto");
		this.framepai = framepai;
		this.lan = lan;
		this.cliente = cliente;
		this.categoria = categoria;
		setLocationRelativeTo(this.framepai);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 142, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 116, 20);
		contentPane.add(comboBox);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciaSessao();
			}
		});
		btnIniciar.setBounds(20, 42, 89, 40);
		contentPane.add(btnIniciar);
		this.setModal(true);
		this.carregaProdutos();
		this.setVisible(true);
	}
	
	public void carregaProdutos() {
		Iterator<Produto> iterator = this.lan.iteratorProdutosDisponiveis(String.valueOf(this.categoria.getId()));
		while (iterator.hasNext()) {
			Produto atual = iterator.next();
			comboBox.addItem(new ProdutosCombo(atual.getNome(), atual));
		}
	}
	
	public void iniciaSessao() {
		ProdutosCombo item = (ProdutosCombo)comboBox.getSelectedItem();
		Produto produto = item.getValue();
		try {
			this.lan.novaSessao(String.valueOf(this.cliente.getId()), this.cliente.getNome(), String.valueOf(produto.getCategoria()), produto);
			new JOptionPane().showMessageDialog(null, "Sessão iniciada para cliente " + this.cliente.getNome());
			this.dispose();
		} catch (ImpossivelCriarSessaoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível iniciar a sessão", JOptionPane.ERROR_MESSAGE);
		}
	}
}
