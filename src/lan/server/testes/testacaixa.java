package lan.server.testes;

import lan.server.bd.BD;
import lan.server.caixa.*;
import lan.server.util.*;

public class testacaixa {
	public static void main(String[] args) {
		BD.banco = new BD();
		BD.tipobanco = "lista";
		Caixa caixa = new Caixa();
		System.out.println(caixa.getSaldo());
		try {
			caixa.novatransacao("entrada", "fundo de caixa", "10", "thomas");
			caixa.novatransacao("entrada", "venda", "5", "thomas");
			caixa.novatransacao("entrada", "vendaSS", "5", "thomas");
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
		
		try {
			caixa.removetransacao("1", "THOMAS");
			caixa.removetransacao("1", "THOMAS");
		} catch (TransacaoNaoEncontradaException e) {
			e.printStackTrace();
		}
		
		/*iterator = caixa.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}*/
		
		TransacaoIterator procuraiterator = caixa.procuratransacao("19/06/2015", "21/06/2015", "todas");
		//while (procuraiterator.hasNext()) {
			//System.out.println(procuraiterator.next());
		//}
	}
}