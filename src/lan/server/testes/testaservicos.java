package lan.server.testes;

import lan.server.bd.*;
import lan.server.servicos.*;

public class testaservicos {
	public static void main(String[] args) {
		BD.banco = new BD();
		BD.tipobanco = "array";
		ServicosManager admsrv = new ServicosManager();
		admsrv.cadastrarservico("xerox", 0.15);
		ServicoIterator iterator = admsrv.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getPreco());
		}
	}
}