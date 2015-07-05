package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import lan.server.clientes.TempoClienteIterator;
import lan.server.painel.Lan;
import lan.server.sessoes.SessaoListener;

public class IndexFrame extends JFrame implements SessaoListener { //o sess�o listener permite "escutar" o fim de sess�es e alertar o administrador
	
	private Lan lan;
	private JPanel contentPane;
	private GerenciadorSessoesFrame gerenciadorsessoes;
	
	public static void main(String[] args) {
		Lan lan = null;
		try {
			lan = new Lan();
			lan.cadastraAdministrador("Thomas", "Thomas", "casaca");
			lan.loginAdministrador("Thomas", "casaca");
			lan.cadastraAtualizaCliente("0", "TAfm", "Teste", "ookok", "@@@", "******", "00/00/00");
			lan.cadastraAtualizaCliente("0", "TAfX", "Teste", "ookok", "@@@", "******", "00/00/00");
			lan.novaCategoriaProdutos("PS2", 1);
			lan.novoProduto("1", "PS2-001", "Console");
			lan.inserirTempo(lan.getCliente("1"), lan.getCategoriaProdutos("1"), 30, 0);
			TempoClienteIterator iterator = lan.iteratorTempoCliente("1");
			/*while(iterator.hasNext()) {
				System.out.println(iterator.next());
			}*/
			TempoClienteIterator iteratortempo = lan.iteratorTempoCliente("1");
			/*while (iteratortempo.hasNext()) {
				System.out.println(iteratortempo.next());
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		IndexFrame frame = new IndexFrame(lan);
	}

	public IndexFrame(Lan lan) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				finalizaSessoes();
			}
		});
		this.lan = lan;
		this.lan.setSessaoListener(this);
		setTitle("JLan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menucadastro = new JMenu("Cadastro");
		menucadastro.setMnemonic(KeyEvent.VK_C);
		menucadastro.getAccessibleContext().setAccessibleDescription("Menu de cadastro");
		menuBar.add(menucadastro);
		
		{
			JMenuItem cadastroclientes = new JMenuItem(new AbstractAction("Clientes") {
			    public void actionPerformed(ActionEvent e) {
			    	abrepesquisaclientes();
			    }
			});
			cadastroclientes.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
			cadastroclientes.getAccessibleContext().setAccessibleDescription("Cadastro de clientes");
			menucadastro.add(cadastroclientes);
			
			JMenuItem cadastrocategorias = new JMenuItem(new AbstractAction("Categorias de produtos") {
			    public void actionPerformed(ActionEvent e) {
			    	abrecategorias();
			    }
			});
			cadastrocategorias.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_2, ActionEvent.ALT_MASK));
			cadastrocategorias.getAccessibleContext().setAccessibleDescription("Categorias de produtos");
			menucadastro.add(cadastrocategorias);
		}
		
		JMenu menufinanceiro = new JMenu("Financeiro");
		menufinanceiro.setMnemonic(KeyEvent.VK_F);
		menufinanceiro.getAccessibleContext().setAccessibleDescription("Menu de finan�as");
		menuBar.add(menufinanceiro);
		
		{
			JMenuItem caixa = new JMenuItem(new AbstractAction("Caixa") {
			    public void actionPerformed(ActionEvent e) {
			    	abrecaixa();
			    }
			});
			caixa.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_4, ActionEvent.ALT_MASK));
			caixa.getAccessibleContext().setAccessibleDescription("Caixa");
			menufinanceiro.add(caixa);
		}
		
		JMenu menusessoes = new JMenu("Sess�es");
		menusessoes.setMnemonic(KeyEvent.VK_S);
		menusessoes.getAccessibleContext().setAccessibleDescription("Menu de sess�es");
		menuBar.add(menusessoes);
		
		{
			JMenuItem gerenciadorsessoes = new JMenuItem(new AbstractAction("Gerenciador de sess�es") {
			    public void actionPerformed(ActionEvent e) {
			    	abregerenciadorsessoes();
			    }
			});
			gerenciadorsessoes.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_5, ActionEvent.ALT_MASK));
			gerenciadorsessoes.getAccessibleContext().setAccessibleDescription("Cadastro de clientes");
			menusessoes.add(gerenciadorsessoes);
		}
		
		JMenu menurelatorios = new JMenu("Relat�rios");
		menurelatorios.setMnemonic(KeyEvent.VK_R);
		menurelatorios.getAccessibleContext().setAccessibleDescription("Menu de sess�es");
		menuBar.add(menurelatorios);
		
		setJMenuBar(menuBar);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setMinimumSize(new Dimension(850, 600));
		this.setVisible(true);
	}

	public void abrepesquisaclientes() {
		new PesquisaClienteFrame(this.lan, this);
	}
	
	public void abrecategorias() {
		
	}
	
	public void abrecaixa() {
		new CaixaFrame(this.lan);
	}
	
	public void abregerenciadorsessoes() {
		if (this.gerenciadorsessoes == null) {
			this.gerenciadorsessoes = new GerenciadorSessoesFrame(this.lan);
		} else {
			this.gerenciadorsessoes.setVisible(true);
		}
	}
	
	public void finalizaSessoes() {
		this.lan.finalizaTodasSessoes();
	}
	
	public void sessaoFinalizada(String cliente, String produto) {
		new JOptionPane().showMessageDialog(this, "Sess�o finalizada para cliente: " + cliente + " no produto: " + produto);
	}
}
