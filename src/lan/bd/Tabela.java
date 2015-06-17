package lan.bd;

public abstract class Tabela { //um repositório genérico que é tratado da mesma forma independente de ser array, lista ou excell
	private String nome;
	protected String[] tipos; //int, double, string, data
	protected String[] campos; //id, nome,bendereço, etc
	protected String classe;
	
	public Tabela(String nome, String[] campos) {
		//this.nome = nome;
		//this.campos = campos;
		this.nome = nome;
		this.campos = new String[campos.length];
		this.tipos = new String[campos.length];
		this.classe = "!semclasse";
		/*for (int i = 0; i < campos.length; i++) {
			String[] tiposcampos = campos[i].split("-");
			this.tipos[i] = tiposcampos[0].toLowerCase();
			this.campos[i] = tiposcampos[1];
		}System.out.println(campos.length);*/
	}
	
	public String[] getCampos() {
		return this.campos;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	/*pesquisas em tabelas seguem a seguinte formatação de filtragem {campo:valor}, pode ser ultilizado mais de um filtro separando por espaço, a única condição suportada é a iguadade*/
	public Registro[] procura(String parametros) {
		String[] parametrosarray = this.ExplodeParametros(parametros);
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
	
	public void atualiza(String query) {
		query = query.replaceAll("\\s","");
		if(query.toLowerCase().indexOf("}where{") > 0) {
			query = query.replaceAll("WHERE","where");
			String[] separa = query.split("where");
			Registro[] registrosmuda = this.procura(separa[1]);
			String[][] valoresmudanca = this.ExplodeQuery(separa[0]);
			for(int i=0;i<registrosmuda.length;i++) { //percorre todos os registros que deve atualizar
				String[] valoresatuais = registrosmuda[i].getValores();
				for (int j = 0; j < valoresmudanca.length; j++) {
					int posicaovalor = this.getPosicaoCampo(valoresmudanca[j][0]);
					String novovalor = valoresmudanca[j][1];
					valoresatuais[posicaovalor] = novovalor;
				}
				registrosmuda[i].setValores(valoresatuais);
				this.substitui(registrosmuda[i].getId(), registrosmuda[i]);
			}
		}
	}
	
	public int getPosicaoCampo(String campo) {
		int posicao = -1;
		for (int i = 0; i < this.campos.length; i++) {
			if (this.campos[i].equals(campo)) {
				posicao = i;
			}
		}
		return posicao;
	}
	
	public String[][] ExplodeQuery(String query) {
		int count = query.length() - query.replace("{", "").length();
		String[][] explodida = new String[count][2];
		int posicaoatual = 0;
		while(query.indexOf("{")>=0) {
			query = query.substring(query.indexOf("{")+1);
			String[] explodevalor = query.substring(0, query.indexOf("}")).split("=");
			explodida[posicaoatual][0] = explodevalor[0];
			explodida[posicaoatual][1] = explodevalor[1];
			posicaoatual++;
		}
		return explodida;
	}
	
	public String[] ExplodeParametros(String query) {
		query = query.replaceAll("\\s","");
		int count = query.length() - query.replace("{", "").length();
		String[] parametros = new String[count];
		int posicaoatual = 0;
		while (query.indexOf("{")>=0) {
			query = query.substring(query.indexOf("{")+1);
			parametros[posicaoatual] = "{" + query.substring(0, query.indexOf("}")+1);
			posicaoatual++;
		}
		return parametros;
	}
	
	public abstract Registro getRegistro(int indice, int saltos); //procura indice x a partir do 0
	public abstract void removeporid(int id);
	public abstract void inserir(Registro registro);
	public abstract void substitui(int id, Registro registro);
}