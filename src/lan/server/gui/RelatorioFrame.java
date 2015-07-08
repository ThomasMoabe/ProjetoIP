package lan.server.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import lan.server.painel.Lan;
import lan.server.relatorios.Relatorio;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RelatorioFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Relatorio relatorio;
	private JLabel lblrelatorio;
	private JButton btnNewButton;
	private JMenuBar menuBar;
	private JScrollPane scrollPane;
	private String largurarelatorio;

	public RelatorioFrame(String titulo, Lan lan, Relatorio relatorio, boolean preview) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				ajustalargura();
			}
		});
		this.lan = lan;
		this.largurarelatorio = "100%";
		this.relatorio = relatorio;
		setTitle("Relat\u00F3rio");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		
		menuBar = new JMenuBar();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.lblrelatorio = new JLabel("");
		this.lblrelatorio.setOpaque(true);
		lblrelatorio.setBackground(SystemColor.window);
		lblrelatorio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblrelatorio.setVerticalAlignment(SwingConstants.TOP);
		scrollPane = new JScrollPane(lblrelatorio, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(15);
		panel.add(panel_1, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setVgap(10);
		panel_2.add(panel_3, BorderLayout.EAST);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarrelatorio();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setVisible(false);
		panel_3.add(btnNewButton);
		
		lblNewLabel.setText(titulo);
		this.mostrarelatorio(relatorio, preview);
		setMinimumSize(new Dimension(700, 400));
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void mostrarelatorio(Relatorio relatorio, boolean preview) {
		lblrelatorio.setText("<html><table style=\"width:100%;\">");

		int linhasescritas = 0;
		for (String linha : relatorio) {
			lblrelatorio.setText(lblrelatorio.getText() + "<tr" + (linhasescritas==0? " bgcolor=\"#CCCCCC\"" : "") + ">");
			
			String linhaatual = linha;
			Pattern pat = Pattern.compile(".*?[^\\\\]\\t");
			Matcher buscacolunas = pat.matcher(linhaatual);
			while (buscacolunas.find()) {
				lblrelatorio.setText(lblrelatorio.getText() + "<td>" + buscacolunas.group().substring(0, buscacolunas.group().lastIndexOf("\t")) + "</td>");
			}
			linhasescritas++;
			lblrelatorio.setText(lblrelatorio.getText() + "</tr>");
		}
		lblrelatorio.setText(lblrelatorio.getText() + "</table></html>");
		if (linhasescritas == 1) {
			lblrelatorio.setText("<html></body>Nenhum registro encontrado</body></html>");
		} else if(preview) {
			btnNewButton.setEnabled(true);
			btnNewButton.setVisible(true);
			
			menuBar = new JMenuBar();
			JMenu menuarquivo = new JMenu("Arquivo");
			menuarquivo.setMnemonic(KeyEvent.VK_A);
			menuarquivo.getAccessibleContext().setAccessibleDescription("Arquivo");
			
			JMenuItem salvararquivo = new JMenuItem(new AbstractAction("Salvar") {
			    public void actionPerformed(ActionEvent e) {
			    	salvarrelatorio();
			    }
			});
			salvararquivo.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
			salvararquivo.getAccessibleContext().setAccessibleDescription("Salvar arquivo");
			menuarquivo.add(salvararquivo);
			
			menuBar.add(menuarquivo);
			setJMenuBar(menuBar);
		}
	}
	
	public void salvarrelatorio() {
		JFileChooser salvarArquivo = new JFileChooser();
		salvarArquivo.showSaveDialog(null);
		try {
			String caminhoarquivo = salvarArquivo.getSelectedFile().getAbsolutePath();
			caminhoarquivo += ".txt";
			String nome = caminhoarquivo.replaceAll(".*\\\\", "");
			this.lan.salvarRelatorio(nome, this.relatorio, caminhoarquivo);
		} catch (Exception e) {}
	}
	
	public void ajustalargura() {
		int novalargura = scrollPane.getSize().width - (scrollPane.getVerticalScrollBar().WIDTH + 16);
		lblrelatorio.setText(lblrelatorio.getText().replaceFirst(this.largurarelatorio, String.valueOf(novalargura)));
		this.largurarelatorio = String.valueOf(novalargura);
	}
}
