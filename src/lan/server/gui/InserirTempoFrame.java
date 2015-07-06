package lan.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import lan.server.clientes.Cliente;
import lan.server.clientes.SessaoIniciadaException;
import lan.server.painel.Lan;
import lan.server.produtos.CategoriaProdutos;
import lan.server.util.Iterator;
import lan.server.vendas.DescontoInvalidoException;

public class InserirTempoFrame extends JDialog {

	private JPanel contentPane;
	private Lan lan;
	private Cliente cliente;
	private ClienteFrame janelapai;
	private JComboBox comboBox;
	private JLabel lblValorDaHora;
	private Iterator<CategoriaProdutos> categorias;
	private JFormattedTextField formattedTextField_3; //horas
	private JFormattedTextField formattedTextField; //minutos
	private JFormattedTextField formattedTextField_2; //valor
	private JFormattedTextField formattedTextField_1; //desconto
	private JLabel lblTotal; //total
	
	int minutostotal;
	double desconto;
	double valoranterior;
	int minutosanterior;

	public InserirTempoFrame(ClienteFrame janelapai, Lan lan, Cliente cliente, Iterator<CategoriaProdutos> categorias) {
		setTitle("Inserir tempo");
		this.lan = lan;
		this.cliente = cliente;
		this.janelapai = janelapai;
		this.categorias = categorias;
		this.minutostotal = 0;
		this.desconto = 0;
		this.valoranterior = 0;
		this.minutosanterior = 0;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBox = new JComboBox();
		comboBox.setBounds(109, 41, 151, 20);
		contentPane.add(comboBox);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategoria.setBounds(10, 44, 89, 14);
		contentPane.add(lblCategoria);
		
		this.lblValorDaHora = new JLabel("Valor da hora");
		lblValorDaHora.setBounds(280, 44, 132, 14);
		contentPane.add(lblValorDaHora);
		
		JLabel lblNewLabel = new JLabel("Minutos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(186, 89, 59, 14);
		contentPane.add(lblNewLabel);
		
		DecimalFormat horas = new DecimalFormat("#");  
		NumberFormatter formataHora = new NumberFormatter(horas);  
		formataHora.setFormat(horas);  
		formataHora.setAllowsInvalid(false);  
		DefaultFormatterFactory hora = new DefaultFormatterFactory(formataHora);  //formatação de horas 00
		
		DecimalFormat decimal = new DecimalFormat("#,###,##0.00");  
		NumberFormatter formatavalor = new NumberFormatter(decimal);  
		formatavalor.setFormat(decimal);  
		formatavalor.setAllowsInvalid(false);  
		DefaultFormatterFactory valor = new DefaultFormatterFactory(formatavalor);  //formatação de valor $$.00
		
		formattedTextField = new JFormattedTextField(hora);
		formattedTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				executacalculo();
			}
		});
		formattedTextField.setBounds(255, 86, 80, 20);
		contentPane.add(formattedTextField);
		
		JLabel lblDesconto = new JLabel("Desconto");
		lblDesconto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDesconto.setBounds(34, 158, 65, 14);
		contentPane.add(lblDesconto);
		
		formattedTextField_1 = new JFormattedTextField(valor);
		formattedTextField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				executacalculo();
			}
		});
		formattedTextField_1.setBounds(109, 155, 80, 20);
		contentPane.add(formattedTextField_1);
		
		lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotal.setBounds(53, 197, 239, 14);
		contentPane.add(lblTotal);
		
		JButton btnNewButton = new JButton("Inserir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executacalculo();
				inserirtempo();
			}
		});
		btnNewButton.setBounds(345, 223, 89, 38);
		contentPane.add(btnNewButton);
		
		formattedTextField_2 = new JFormattedTextField(valor);
		formattedTextField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				executacalculo();
			}
		});
		formattedTextField_2.setBounds(109, 124, 80, 20);
		contentPane.add(formattedTextField_2);
		
		JLabel lblR = new JLabel("R$");
		lblR.setHorizontalAlignment(SwingConstants.RIGHT);
		lblR.setBounds(66, 127, 33, 14);
		contentPane.add(lblR);
		
		JLabel lblHoras = new JLabel("Horas");
		lblHoras.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoras.setBounds(53, 92, 46, 14);
		contentPane.add(lblHoras);
		
		formattedTextField_3 = new JFormattedTextField(hora);
		formattedTextField_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				executacalculo();
			}
		});
		formattedTextField_3.setBounds(109, 86, 80, 20);
		contentPane.add(formattedTextField_3);
		this.setModal(true);
		this.carregacategorias();
		this.executacalculo();
		this.setVisible(true);
	}
	
	public void carregacategorias() {
		while (this.categorias.hasNext()) {
			CategoriaProdutos categoria = categorias.next();
			comboBox.addItem(new CategoriasCombo(categoria.getDescricao(), categoria));
		}		
	}
	
	public void executacalculo() {
		DecimalFormat df = new DecimalFormat("0.00"); 
		double valorhora = ((CategoriasCombo)this.comboBox.getSelectedItem()).getValue().getPrecoHora();
		String precohora = (df.format(valorhora));
		lblValorDaHora.setText("Valor da hora R$ " + String.valueOf(precohora));
		
		int horas = Integer.valueOf(formattedTextField_3.getText().replace(" ", "0").length() > 0 ? formattedTextField_3.getText().replace(" ", "0") : "0");
		int minutos = Integer.valueOf(formattedTextField.getText().replace(" ", "0").length() > 0 ? formattedTextField.getText().replace(" ", "0") : "0");
		int minutostotal = this.minutostotal = (horas * 60) + minutos;		
		
		DecimalFormat dfvalor = new DecimalFormat("0.00");
		double valor = 0;
		double valorcampovalor = Double.parseDouble(formattedTextField_2.getText().replace(",", ".").length() > 0 ? formattedTextField_2.getText().replace(",", ".") : "0");
		
		if (minutostotal > 0 || valorcampovalor != this.valoranterior) {
			valor = ((double)minutostotal/60) * valorhora;
			valor = Double.valueOf(dfvalor.format(valor).replace(",", "."));
			if (minutostotal != minutosanterior) {
				formattedTextField_2.setText(String.valueOf(valor).replace(".", ","));
			} else if(valorcampovalor != this.valoranterior) { //o usuário alterou o valor manualmente...
				valor = valorcampovalor;
				double tempo = ((double) valorcampovalor / valorhora) * 60; //conversão de $$ para minutos
				horas = (int) Math.floor(tempo / 60);
				minutos = (int) tempo % 60;
				minutostotal = this.minutostotal = (horas * 60) + minutos;
				formattedTextField_3.setText(String.valueOf(horas));
				formattedTextField.setText(String.valueOf(minutos));
			}
			this.minutosanterior = minutostotal;
			this.valoranterior = valor;
			
		}
		
		double desconto = Double.parseDouble(formattedTextField_1.getText().replace(",", ".").length() > 0 ? formattedTextField_1.getText().replace(",", ".") : "0");
		desconto = Double.valueOf(dfvalor.format(desconto).replace(",", "."));
		this.desconto = desconto;
		double total = valor - desconto;
		lblTotal.setText("Total: R$ " + df.format(total));
	}
	
	public void inserirtempo() {
		try {
			if (this.minutostotal > 0) {
				this.lan.inserirTempo(this.cliente, ((CategoriasCombo)this.comboBox.getSelectedItem()).getValue(), this.minutostotal, this.desconto);
				new JOptionPane().showMessageDialog(null, "Tempo inserido");
				this.dispose();
			}
			this.janelapai.callbacktempoinserido();
		} catch (DescontoInvalidoException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível inserir tempo", JOptionPane.ERROR_MESSAGE);
		} catch (SessaoIniciadaException e) {
			new JOptionPane().showMessageDialog(null, e.getMessage(), "Não foi possível inserir tempo", JOptionPane.ERROR_MESSAGE);
		}
	}
}
