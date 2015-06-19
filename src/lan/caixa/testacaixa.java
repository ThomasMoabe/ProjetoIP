package lan.caixa;

import lan.bd.BD;
import lan.caixa.*;

public class testacaixa {
	public static void main(String[] args) {
		BD.banco = new BD();
		BD.tipobanco = "lista";
		Caixa caixa = new Caixa();
		System.out.println(caixa.getSaldo());
		try {
			caixa.novatransacao("entrada", "fundo de caixa", "10", "01/01/2001", "thomas");
			System.out.println(caixa.getSaldo());
			caixa.novatransacao("saida", "cerva", "20", "01/01/2001", "thomas");
			System.out.println(caixa.getSaldo());
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}
		
	}
}