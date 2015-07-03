package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;

public class IndexFrame extends JFrame {
	
	private Lan lan;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		Lan lan = null;
		try {
			lan = new Lan();
			lan.cadastraAdministrador("Thomas", "Thomas", "casaca");
			lan.loginAdministrador("Thomas", "casaca");
			lan.cadastraAtualizaCliente("0", "TAfm", "Teste", "ookok", "@@@", "******", "00/00/00");
			lan.cadastraAtualizaCliente("0", "TAfX", "Teste", "ookok", "@@@", "******", "00/00/00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		IndexFrame frame = new IndexFrame(lan);
	}

	public IndexFrame(Lan lan) {
		this.lan = lan;
		setTitle("JLan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Cadastro");
		menu.setMnemonic(KeyEvent.VK_C);
		menu.getAccessibleContext().setAccessibleDescription("Menu de cadastro");
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		JMenuItem cadastroclientes = new JMenuItem(new AbstractAction("Clientes") {
		    public void actionPerformed(ActionEvent e) {
		    	abrepesquisaclientes();
		    }
		});
		cadastroclientes.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
		cadastroclientes.getAccessibleContext().setAccessibleDescription("Cadastro de clientes");
		menu.add(cadastroclientes);
		
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
}
