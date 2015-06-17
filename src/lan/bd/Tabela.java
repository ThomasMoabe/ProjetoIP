package lan.bd;

public abstract class Tabela { //um reposit�rio gen�rico que � tratado da mesma forma independente de ser array, lista ou excell
	private String nome;
	private String[] campos; //id, nome,bendere�o, etc
	
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
	
	/*pesquisas em tabelas seguem a seguinte formata��o de filtragem {campo:valor}, pode ser ultilizado mais de um filtro separando por espa�o, a �nica condi��o suportada � a iguadade*/
	public Registro[] procura(String parametros) {
		String[] parametrosarray = parametros.split(" ");
		RepositorioLista encontrados = new RepositorioLista("encontrados", new String[]{});
		int indice = 0;
		while (this.getRegistro(indice, 0) != null) { //varre todos os registros de forma gen�rica na tabela, independente do seu tipo
			boolean insere = true;
			for (int i = 0; i < parametrosarray.length && insere == true; i++) { //enquanto n�o encontrar um par�metro que n�o satisfa�a..
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