package lan.server.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lan.server.administracao.Administrador;
import lan.server.administracao.ImpossivelDeletarAdministradorException;
import lan.server.painel.Lan;
import lan.server.util.Iterator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministradoresFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnExcluir;
	private int linhaselecionada;

	public AdministradoresFrame(Lan lan) {
		setTitle("Administradores");
		this.lan = lan;
		this.linhaselecionada = 0;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 75, 386, 223);
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
		model.addColumn("Login"); 
		model.addColumn("Nome"); 
		
		table = new JTable(this.model);
		
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
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnCadastrarNovo = new JButton("Cadastrar novo");
		btnCadastrarNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrecadastro();
			}
		});
		btnCadastrarNovo.setBounds(25, 38, 188, 23);
		contentPane.add(btnCadastrarNovo);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletaadministrador();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(322, 309, 89, 23);
		contentPane.add(btnExcluir);
		this.setModal(true);
		this.mostraadministradores();
		this.setVisible(true);
	}
	
	public void mostraadministradores() {
		btnExcluir.setEnabled(false);
		int linhastabela = this.table.getRowCount();
		for (int i = linhastabela - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
		Iterator<Administrador> administradores = this.lan.iteratorAdministradores();
		while (administradores.hasNext()) {
			Administrador administrador = administradores.next();
			this.model.addRow(new Object[]{administrador.getId(), administrador.getLogin(), administrador.getNome()});
		}
	}
	
	public void callbackadmcadastrado() {
		this.mostraadministradores();
	}
	
	public void abrecadastro() {
		new AdicionarAdministradorFrame(this, this.lan);
	}
	
	public void selecionalinha(int linha) {
		this.linhaselecionada = linha;
		btnExcluir.setEnabled(true);
	}
	
	public void deletaadministrador() {
		int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o administrador?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
		if (escolha == JOptionPane.YES_OPTION) {
			try {
				String id = String.valueOf(this.model.getValueAt(this.linhaselecionada, 0));
				this.lan.deletaAdministrador(id);
				new JOptionPane().showMessageDialog(null, "Administrador excluido");
				this.mostraadministradores();
			} catch (ImpossivelDeletarAdministradorException e) {
				new JOptionPane().showMessageDialog(null, e.getMessage(), "Falha ao excluir administrador", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
