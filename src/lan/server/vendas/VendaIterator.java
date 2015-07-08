package lan.server.vendas;

import lan.server.bd.*;
import lan.server.util.Iterator;

import java.util.Arrays;

public class VendaIterator implements Iterator{
	private int registroatual;
	private Venda[] vendas;
	
	public VendaIterator(Registro[] registros) {
		this.registroatual = 0;
		this.vendas = Arrays.copyOf(registros, registros.length, Venda[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.vendas.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Venda next() {
		this.registroatual++;
		return this.vendas[this.registroatual-1];
	}
}
