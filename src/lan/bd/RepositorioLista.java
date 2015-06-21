package lan.bd;

public class RepositorioLista extends Tabela{
	private Registro registro;
	private RepositorioLista proximo;
	private RepositorioLista primeiro;
	
	public RepositorioLista(String nome, String[] campos) {
		super(nome, campos);
		this.registro = null;
		this.proximo = null;
		this.primeiro = this;
		//this.campos = campos;
	}
	
	public RepositorioLista(String nome, String[] campos, RepositorioLista primeiro) {
		super(nome, campos);
		this.registro = null;
		this.proximo = null;
		this.primeiro = primeiro;
		//this.campos = campos;
	}
	
	public void inserir(Registro registro) {
		registro.setTabela(this);
		if (this.registro==null) {
			this.registro = registro;
			if (this.primeiro.classe.equals("!semclasse")) {
				this.primeiro.classe = registro.getClass().getName();
			}
			this.primeiro.idatual++;
		} else if (this.proximo == null ){
			this.proximo = new RepositorioLista(this.getNome(), this.primeiro.getCamposTipos(), this.primeiro); //getCampos()
			this.proximo.inserir(registro);
		} else {
			this.proximo.inserir(registro);
		}
	}
	
	public Registro getRegistro(int indice, int saltos, Tabela tabela) {
		Registro registro = null;
		if (indice == saltos) {
			registro = this.registro;
		} else if (this.proximo != null) {
			registro = this.proximo.getRegistro(indice, saltos+1, tabela);
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
	
	public void substitui(int id, int idanterior, Registro registro) {
		if (this.registro.getId() == id) {
			this.registro = registro;
		} else if(this.proximo != null) {
			this.proximo.substitui(id, idanterior, registro);
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