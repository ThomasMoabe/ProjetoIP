package lan.server.servicos;

import lan.server.bd.*;
import lan.server.util.Iterator;

import java.util.Arrays;

public class ServicoIterator implements Iterator{
	private int registroatual;
	private Servico[] servicos;
	
	public ServicoIterator(Registro[] registros) {
		this.registroatual = 0;
		this.servicos = Arrays.copyOf(registros, registros.length, Servico[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.servicos.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Servico next() {
		this.registroatual++;
		return this.servicos[this.registroatual-1];
	}
}