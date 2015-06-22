package lan.administracao;

import lan.bd.*;
import lan.util.Iterator;

import java.util.Arrays;

public class AdministradorIterator implements Iterator{
	private int registroatual;
	private Administrador[] administradores;
	
	public AdministradorIterator(Registro[] registros) {
		this.registroatual = 0;
		this.administradores = Arrays.copyOf(registros, registros.length, Administrador[].class);
	}
	
	public boolean hasNext() {
		boolean temproximo = true;
		if (registroatual == this.administradores.length) {
			temproximo = false;
		}
		return temproximo;
	}
	
	public Administrador next() {
		this.registroatual++;
		return this.administradores[this.registroatual-1];
	}
}