package lan.server.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class RelatorioFrame extends JDialog {

	private JPanel contentPane;

	public RelatorioFrame() {
		setTitle("Relat\u00F3rio");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblBlablalba = new JLabel("blablalba");
		contentPane.add(lblBlablalba, BorderLayout.CENTER);
		this.setModal(true);
		this.setVisible(true);
	}

}
