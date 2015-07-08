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
import lan.server.produtos.CategoriaProdutos;
import lan.server.produtos.NenhumaCategoriaCadastradaException;
import lan.server.servicos.Servico;
import lan.server.sessoes.SessaoListener;
import lan.server.util.Iterator;

public class IndexFrame extends JFrame implements SessaoListener { //o sess�o listener permite "escutar" o fim de sess�es e alertar o administrador
	
	private Lan lan;
	private JPanel contentPane;
	private GerenciadorSessoesFrame gerenciadorsessoes;
	private JMenu servicos;
	private JMenuBar menuBar;

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
		
		menuBar = new JMenuBar();
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
			
			JMenuItem cadastroprodutos = new JMenuItem(new AbstractAction("Produtos") {
			    public void actionPerformed(ActionEvent e) {
			    	abreprodutos();
			    }
			});
			cadastroprodutos.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_3, ActionEvent.ALT_MASK));
			cadastroprodutos.getAccessibleContext().setAccessibleDescription("Categorias de produtos");
			menucadastro.add(cadastroprodutos);
			
			JMenuItem cadastroservicos = new JMenuItem(new AbstractAction("Servi�os") {
			    public void actionPerformed(ActionEvent e) {
			    	abreservicos();
			    }
			});
			cadastroservicos.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_4, ActionEvent.ALT_MASK));
			cadastroservicos.getAccessibleContext().setAccessibleDescription("Cadastro de servi�os");
			menucadastro.add(cadastroservicos);
			
			JMenuItem cadastroadministradores = new JMenuItem(new AbstractAction("Administradores") {
			    public void actionPerformed(ActionEvent e) {
			    	abreadministradores();
			    }
			});
			cadastroadministradores.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_5, ActionEvent.ALT_MASK));
			cadastroadministradores.getAccessibleContext().setAccessibleDescription("Cadastro de administradores");
			menucadastro.add(cadastroadministradores);
		}
		
		this.servicos = new JMenu("Servi�os");
		this.servicos.setMnemonic(KeyEvent.VK_S);
		this.servicos.getAccessibleContext().setAccessibleDescription("Menu de servi�os");
		menuBar.add(this.servicos);
		
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
			caixa.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_6, ActionEvent.ALT_MASK));
			caixa.getAccessibleContext().setAccessibleDescription("Caixa");
			menufinanceiro.add(caixa);
		}
		
		JMenu menusessoes = new JMenu("Sess�es");
		menusessoes.setMnemonic(KeyEvent.VK_E);
		menusessoes.getAccessibleContext().setAccessibleDescription("Menu de sess�es");
		menuBar.add(menusessoes);
		
		{
			JMenuItem gerenciadorsessoes = new JMenuItem(new AbstractAction("Gerenciador de sess�es") {
			    public void actionPerformed(ActionEvent e) {
			    	abregerenciadorsessoes();
			    }
			});
			gerenciadorsessoes.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_7, ActionEvent.ALT_MASK));
			gerenciadorsessoes.getAccessibleContext().setAccessibleDescription("Cadastro de clientes");
			menusessoes.add(gerenciadorsessoes);
		}
		
		JMenu menurelatorios = new JMenu("Relat�rios");
		menurelatorios.setMnemonic(KeyEvent.VK_R);
		menurelatorios.getAccessibleContext().setAccessibleDescription("Menu de sess�es");
		menuBar.add(menurelatorios);
		
		{
			JMenu novorelatorio = new JMenu("Novo relat�rio");
			novorelatorio.getAccessibleContext().setAccessibleDescription("Gerar novo relat�rio");
			menurelatorios.add(novorelatorio);
			{
				JMenuItem fluxocaixa = new JMenuItem(new AbstractAction("Fluxo de caixa") {
				    public void actionPerformed(ActionEvent e) {
				    	abrerelatoriofluxocaixa();
				    }
				});
				fluxocaixa.getAccessibleContext().setAccessibleDescription("Gerar relat�rio de fluxo de caixa");
				novorelatorio.add(fluxocaixa);
			}
			
			JMenuItem relatoriosrecentes = new JMenuItem(new AbstractAction("Relat�rios recentes") {
			    public void actionPerformed(ActionEvent e) {
			    	abrerelatoriosrecentes();
			    }
			});
			relatoriosrecentes.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_8, ActionEvent.ALT_MASK));
			relatoriosrecentes.getAccessibleContext().setAccessibleDescription("Visualizar relat�rios recentes");
			menurelatorios.add(relatoriosrecentes);
		}
		
		setJMenuBar(menuBar);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setMinimumSize(new Dimension(850, 600));
		this.mostramenuservicos();
		this.setVisible(true);
	}

	public void abrepesquisaclientes() {
		new PesquisaClienteFrame(this.lan, this);
	}
	
	public void abrecategorias() {
		new CategoriasProdutosFrame(this, this.lan);
	}
	
	public void abreprodutos() {
		try {
			Iterator<CategoriaProdutos> categorias = this.lan.iteratorCategoriasProdutos();
			new ProdutosFrame(this.lan, categorias);
		} catch (NenhumaCategoriaCadastradaException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Ops, nada cadastrado ainda", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void abreservicos() {
		new ServicosFrame(this, this.lan);
	}
	
	public void abreadministradores() {
		new AdministradoresFrame(this.lan);
	}
	
	public void abrecaixa() {
		new CaixaFrame(this.lan);
	}
	
	public void abregerenciadorsessoes() {
		if (this.gerenciadorsessoes == null) {
			this.gerenciadorsessoes = new GerenciadorSessoesFrame(this, this.lan);
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
	
	public void callbackmodificaservicos() {
		this.mostramenuservicos();
	}
	
	public void mostramenuservicos() {
		try {
			this.servicos.removeAll();
			
			Iterator<Servico> servicos = this.lan.iteratorServicos();
			while (servicos.hasNext()) {
				final Servico servico = servicos.next();
				
				JMenuItem servicoadiciona = new JMenuItem(new AbstractAction(servico.getDescricao()) {
				    public void actionPerformed(ActionEvent e) {
				    	abreservico(servico);
				    }
				});
				servicoadiciona.getAccessibleContext().setAccessibleDescription("Servi�o " + servico.getDescricao());
				this.servicos.add(servicoadiciona);
			}
		} catch (Exception e) {}
	}
	
	public void abreservico(Servico servico) {
		new ServicoFrame(this.lan, servico);
	}
	
	public void abrerelatoriosrecentes() {
		new RelatoriosRecentesFrame(this.lan);
	}
	
	public void abrerelatoriofluxocaixa() {
		new RelatorioFluxoCaixaFrame(this.lan);
	}
}
