package lan.server.relatorios;

import java.util.Arrays;
import lan.server.util.Iterator;

public class RelatoriosGeradosIterator implements Iterator<RelatorioGerado>{
	private int registroatual;
	private RelatorioGerado[] relatorios;
	
	public RelatoriosGeradosIterator(RelatorioGerado[] relatorios, int quantidade) {
		this.registroatual = 0;
		this.relatorios = Arrays.copyOf(relatorios, quantidade, RelatorioGerado[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.relatorios.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public RelatorioGerado next() {
		this.registroatual++;
		return this.relatorios[this.registroatual-1];
	}
}
