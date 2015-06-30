package lan.server.testes;

import lan.server.bd.BD;
import lan.server.util.*;
import lan.server.vendas.*;

public class testavendas {
	public static void main(String[] args) {
		try {
			BD.banco = new BD();
			Config config = new Config();
			BD.banco.tipobanco = config.getTipoBanco();
		} catch (ConfiguracaoInvalidaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		VendasManager vendas = new VendasManager();
		try {
			vendas.novaVenda("merda", "fulano", 10, 5);
			VendaIterator iterator = vendas.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		} catch (DescontoInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
