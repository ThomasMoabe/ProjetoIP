package lan.bd;

public abstract class Tabela { //um repositório genérico que é tratado da mesma forma independente de ser array, lista ou excell
	private String nome;
	protected String[] campos; //id, nome,bendereço, etc
	
	public Tabela(String nome, String[] campos) {
		this.nome = nome;
		this.campos = campos;
	}
	
	public String[] getCampos() {
		return this.campos;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	/*pesquisas em tabelas seguem a seguinte formatação de filtragem {campo:valor}, pode ser ultilizado mais de um filtro separando por espaço, a única condição suportada é a iguadade*/
	public Registro[] procura(String parametros) {
		String[] parametrosarray = parametros.split(" ");
		RepositorioLista encontrados = new RepositorioLista("encontrados", this.campos);
		int indice = 0;
		while (this.getRegistro(indice, 0) != null) { //varre todos os registros de forma genérica na tabela, independente do seu tipo
			boolean insere = true;
			for (int i = 0; i < parametrosarray.length && insere == true; i++) { //enquanto não encontrar um parâmetro que não satisfaça..
				if (this.getRegistro(indice, 0).toString().indexOf(parametrosarray[i]) < 0) {
					insere = false;
				}
			}
			if (insere) {
				encontrados.inserir(this.getRegistro(indice, 0));
			}
			insere = true;
			indice++;
		}
		return encontrados.toArray();
	}
	
	public void remove(String parametros) {
		Registro[] listaremocao = this.procura(parametros);
		for (int i = 0; i < listaremocao.length; i++) {
			this.removeporid(listaremocao[i].getId());
		}
	}
	
	public abstract Registro getRegistro(int indice, int saltos); //procura indice x a partir do 0
	public abstract void removeporid(int id);
	public abstract void inserir(Registro registro);
}