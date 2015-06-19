package lan.caixa;

import lan.bd.BD;
import lan.caixa.*;
import lan.util.*;

public class testacaixa {
	public static void main(String[] args) {
		BD.banco = new BD();
		BD.tipobanco = "excel";
		Caixa caixa = new Caixa();
		System.out.println(caixa.getSaldo());
		try {
			caixa.novatransacao("entrada", "fundo de caixa", "10", "thomas");
			caixa.novatransacao("entrada", "venda", "5", "thomas");
			System.out.println(caixa.getSaldo());
			caixa.novatransacao("saida", "cerva", "20", "thomas");
			System.out.println(caixa.getSaldo());
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}
		
		TransacaoIterator iterator = caixa.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		System.out.println(DataHora.getHora());
	}
}