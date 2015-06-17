package lan.bd;

import lan.caixa.RepositorioTransacoesLista;
import lan.caixa.Transacao;

public class RepositorioLista extends Tabela{
	private Registro registro;
	private RepositorioLista proximo;
	
	public RepositorioLista(String nome, String[] campos) {
		super(nome, campos);
		this.registro = null;
		this.proximo = null;
		this.campos = campos;
	}
	
	public void inserir(Registro registro) {
		registro.setTabela(this);
		if (this.registro==null) {
			this.registro = registro;
		} else if (this.proximo == null ){
			this.proximo = new RepositorioLista(this.getNome(), this.getCampos());
			this.proximo.inserir(registro);
		} else {
			this.proximo.inserir(registro);
		}
	}
	
	public Registro getRegistro(int indice, int saltos) {
		Registro registro = null;
		if (indice == saltos) {
			registro = this.registro;
		} else if (this.proximo != null) {
			registro = this.proximo.getRegistro(indice, saltos+1);
		}
		return registro;
	}
	
	public void removeporid(int id) {
		if (this.registro.getId() == id) {
			if (this.proximo != null) {
				this.registro = this.proximo.getRegistro();
				this.proximo = this.proximo.getProximo();
			} else {
				this.registro = null;
			}
		} else {
			this.proximo.removeporid(id);
		}
	}
	
	public Registro[] toArray() {
		int qtdregistros = (this.registro == null) ? 0 : 1;
		RepositorioLista proximo = this.proximo;
		while (proximo != null) {
			qtdregistros++;
			proximo = proximo.getProximo();
		}
		Registro[] listaregistros = new Registro[qtdregistros];
		proximo = this.proximo;
		if(this.registro != null) {
			listaregistros[0] = this.registro;
			qtdregistros = 1;
		}
		while (proximo != null) {
			listaregistros[qtdregistros] = proximo.getRegistro();
			proximo = proximo.getProximo();
			qtdregistros++;
		}
		return listaregistros;
	}
	
	public Registro getRegistro() {
		return this.registro;
	}
	
	public RepositorioLista getProximo() {
		return this.proximo;
	}
}