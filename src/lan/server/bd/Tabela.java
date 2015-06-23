package lan.server.bd;

public abstract class Tabela { //um reposit�rio gen�rico que � tratado da mesma forma independente de ser array, lista ou excell
	private String nome;
	protected String[] tipos; //int, double, string, data, hora
	protected String[] campos; //id, nome,bendere�o, etc
	protected String[] campostipos;
	protected String classe;
	protected int idatual;
	
	public Tabela(String nome, String[] campos) {
		this.nome = nome;
		//this.campos = campos;
		this.classe = "!semclasse";
		this.idatual = 1;
		this.campostipos = campos;
		
		this.tipos = new String[campos.length];
		this.campos = new String [campos.length];
		for (int i = 0; i < campos.length; i++) {
			campos[i] = campos[i].replaceAll("\\s+",""); //remove os espa�os
			String[] tiposcampos = campos[i].split("\\)");
			this.tipos[i] = tiposcampos[0].toLowerCase().substring(1);
			this.campos[i] = tiposcampos[1];
		}
	}
	
	public String[] getCampos() {
		return this.campos;
	}
	
	public String[] getCamposTipos() {
		return this.campostipos;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public int getIdAtual() {
		return this.idatual;
	}
	
	private Registro[] procuraInterno(String parametros, boolean ignorecase) {
		parametros = parametros.replaceAll("}\\s+","}");
		parametros = parametros.replaceAll("\\s+\\{","{");
		
		//Cl�usula ORDER BY	
		boolean ordena = false;
		String[] listacamposordena = new String[1];
		boolean ordemcrescente = true;
		{
			if (parametros.matches(".*}\\s*(?i)order\\s+by{1}.*")) {
				ordena = true;
				String[] quebraordem = parametros.split("(?i)order\\s+by{1}\\s+");
				if (quebraordem.length > 1) {
					parametros = quebraordem[0];
					String camposordena;
					if (quebraordem[1].length() > (camposordena = quebraordem[1].replaceAll("\\s+(?i)desc$", "")).length()) {
						ordemcrescente = false;
					} else {
						camposordena =  quebraordem[1].replaceAll("\\s+(?i)asc$", "");
					}
					if (camposordena.indexOf(",") < 0) { //s� tem um campo para ordena��o
						listacamposordena[0] = camposordena;
					} else {
						listacamposordena = camposordena.split("\\s*,\\s*");
					}
				}
			}
		}//fim do tratamento ORDER BY
		parametros = parametros.replaceAll("}OR\\{","}or{"); 
		String[] conjuntoparametros = new String[1];
		if (parametros.indexOf("}or{") > 0) {
			conjuntoparametros = parametros.split("}or\\{");
			for (int i = 0; i < conjuntoparametros.length; i++) {
				if (i%2 == 0) {
					conjuntoparametros[i] = conjuntoparametros[i] + "}";
				} else {
					conjuntoparametros[i] = "{" + conjuntoparametros[i];
				}
			}
		} else {
			conjuntoparametros[0] = parametros;
		}
		RepositorioLista encontrados = new RepositorioLista("encontrados", this.campostipos);
		int indice = 0;
		Registro atual;
		while ((atual = this.getRegistro(indice, 0, this)) != null) { //varre todos os registros de forma gen�rica na tabela, independente do seu tipo
			boolean insere = false;
			for (int i = 0; i < conjuntoparametros.length && insere == false; i++) {
				String[] parametrosarray = this.ExplodeParametros(conjuntoparametros[i]);
				insere = true;
				String[] atuais = this.ExplodeParametros(atual.toString());
				for (int j = 0; j < parametrosarray.length && insere == true; j++) {  //enquanto n�o encontrar um par�metro que n�o satisfa�a..
					String campo = "";
					if (parametrosarray[j].indexOf(">") > 0) {
						campo = parametrosarray[j].substring(1, parametrosarray[j].indexOf(">"));
					} else if (parametrosarray[j].indexOf("<") > 0) {
						campo = parametrosarray[j].substring(1, parametrosarray[j].indexOf("<"));
					} else if (parametrosarray[j].indexOf("=") > 0) {
						campo = parametrosarray[j].substring(1, parametrosarray[j].indexOf("="));
					}
					int posicaocampo = this.getPosicaoCampo(campo);
					insere = this.compara(atuais[posicaocampo], parametrosarray[j], ignorecase);
				}
				if (insere) {
					encontrados.inserir(atual);
				}
			}
			indice++;
		}
		return ordena ? this.ordena(encontrados.toArray(), listacamposordena, ordemcrescente) : encontrados.toArray();
	}
	
	public Registro[] procura(String parametros) {
		return this.procuraInterno(parametros, false);
	}
	
	public Registro[] procuraIgnoreCase(String parametros) {
		return this.procuraInterno(parametros, true);
	}
	
	private Registro[] ordena(Registro[] registros, String[] campos, boolean crescente) {
		for (int i = campos.length-1; i >= 0; i--) { //ordena por todos os campos come�ando pelo ultimo que tem menos prioridade
			for (int j = 0; j < registros.length; j++) { //testa todos os registros
				for (int k = 0; k < registros.length-1; k++) { //em todas as posi��es
					int posicaocampo = this.getPosicaoCampo(campos[i]);
					String valorregistro = this.ExplodeParametros(registros[k].toString())[posicaocampo];
					String valorcompara = this.ExplodeParametros(registros[k+1].toString())[posicaocampo];
					valorcompara = valorcompara.replaceAll("^\\{" + campos[i] + "=", "{" + campos[i] + (crescente ? ">" : "<"));
					boolean muda = this.compara(valorregistro, valorcompara, true);
					if (muda) {
						Registro temp = registros[k];
						registros[k] = registros[k+1];
						registros[k+1] = temp; 
					}
				}
			}
		}
		System.out.println("ordenando");
		return registros;
	}
	
	private boolean compara(String atual, String condicao, boolean ignorecase) {
		boolean insere = false;
		String valoratual = "";
		String campo;
		int posicaocampo = -1;
		String tipo;
		String condicaovalor;
		String separador = "=";
		
		atual = atual.substring(1, atual.length()-1);
		String[] atualexplode = atual.split("=");
		campo = atualexplode[0];
		for (int i = 1; i < atualexplode.length; i++) { //resolvido problema de operator = na string, s� o primeiro � separador, os outros fazem parte da string
			valoratual += atualexplode[i];
		}
		posicaocampo = this.getPosicaoCampo(campo);
		tipo = this.tipos[posicaocampo];
		
		condicao = condicao.substring(1, condicao.length()-1);
		String[] condicaoexplode;
		if (condicao.indexOf(">=") > 0) {
			separador = ">=";
		} else if (condicao.indexOf(">") > 0) {
			separador = ">";
		} else if (condicao.indexOf("<=") > 0) {
			separador = "<=";
		} else if (condicao.indexOf("<") > 0) {
			separador = "<";
		}
		condicaoexplode = condicao.split(separador);
		condicaovalor = condicaoexplode[1];
		
		boolean inverte;
		
		switch (tipo) {
		case "string":
			if ((condicao.indexOf(campo + "=") > -1) && (condicao.indexOf("=") == campo.length())) { //na string � preciso ter mais cuidado, pois os operadores podem estar na pr�pria palavra, dessa forma fica garantida a posi��o do sinal
				String valoratualstring = ignorecase ? valoratual.toLowerCase() : valoratual;
				String valorcomparastring = ignorecase ? condicaovalor.toLowerCase() : condicaovalor;
				insere = valoratualstring.matches(valorcomparastring);
			} else if (((condicao.indexOf(campo + ">") > -1) && (condicao.indexOf(">") == campo.length()) || (condicao.indexOf(campo + ">=") > -1) && (condicao.indexOf(">=") == campo.length())) & !(inverte = false) ||
						((condicao.indexOf(campo + "<") > -1) && (condicao.indexOf("<") == campo.length()) || (condicao.indexOf(campo + "<=") > -1) && (condicao.indexOf("<=") == campo.length())) & (inverte = true)) {
				String stringmaior;
				String stringmenor;
				if (valoratual.length() >= condicaovalor.length()) {
					stringmaior = valoratual;
					stringmenor = condicaovalor;
				} else {
					stringmaior = condicaovalor;
					stringmenor = valoratual;
					inverte = !inverte;
				}
				boolean definicao = false;
				for (int i = 0; i < stringmenor.length() && definicao == false; i++) {
					if (stringmenor.toLowerCase().charAt(i) != stringmaior.toLowerCase().charAt(i)) {
						definicao = true; System.out.println(inverte);
						insere = stringmenor.toLowerCase().charAt(i) < stringmaior.toLowerCase().charAt(i) ? (!inverte) : (inverte);
					} else {
						insere = true;
					}
				}
			}
			break;
		case "int":
			int valoratualinteiro = Integer.parseInt(valoratual);
			int valorcomparainteiro = Integer.parseInt(condicaovalor);
			if ((separador.equals("=") && valoratualinteiro == valorcomparainteiro) || 
				(separador.equals(">") && valoratualinteiro > valorcomparainteiro) ||
				(separador.equals(">=") && valoratualinteiro >= valorcomparainteiro) ||
				(separador.equals("<") && valoratualinteiro < valorcomparainteiro) ||
				(separador.equals("<=") && valoratualinteiro <= valorcomparainteiro)) {
				insere = true;
			}
			break;
		case "double":
			double valoratualdouble = Double.parseDouble(valoratual);
			double valorcomparadouble = Double.parseDouble(condicaovalor);
			if ((separador.equals("=") && valoratualdouble == valorcomparadouble) || 
				(separador.equals(">") &&  valoratualdouble > valorcomparadouble) ||
				(separador.equals(">=") &&  valoratualdouble >= valorcomparadouble) ||
				(separador.equals("<") &&  valoratualdouble < valorcomparadouble) ||
				(separador.equals("<=") &&  valoratualdouble <= valorcomparadouble)) {
				insere = true;
			}
			break;
		case "data":
			String[] separadataatual = valoratual.split("/");
			String dataatualcompleta = "";//separadataatual[2] + separadataatual[1] + separadataatual[0]; //de tr�s pra frente, pois o ano tem mais importancia que o m�s e o m�s que o dia
			String datacomparacompleta = "";
			if (condicaovalor.indexOf("/") > 0) {
				String[] separadatacompara = condicaovalor.split("/");
				for (int i = separadatacompara.length-1; i >= 0; i--) {
					datacomparacompleta += separadatacompara[i];
					dataatualcompleta += separadataatual[i];
				}
			} else {
				datacomparacompleta += condicaovalor;
				dataatualcompleta += separadataatual[0];
			}
			//String datacomparacompleta = separadatacompara[2] + separadatacompara[1] + separadatacompara[0];
			int dataatualinteiro = Integer.parseInt(dataatualcompleta);
			int datacomparainteiro = Integer.parseInt(datacomparacompleta);
			if ((separador.equals("=") && dataatualinteiro == datacomparainteiro) || 
					(separador.equals(">") &&  dataatualinteiro > datacomparainteiro) ||
					(separador.equals(">=") &&  dataatualinteiro >= datacomparainteiro) ||
					(separador.equals("<") &&  dataatualinteiro < datacomparainteiro) ||
					(separador.equals("<=") &&  dataatualinteiro <= datacomparainteiro)) {
					insere = true;
			}
			break;
		case "hora":
			String[] separahoraatual = valoratual.split(":");
			String horaatualcompleta = "";//separahoraatual[0] + separahoraatual[1] + separahoraatual[2];
			String horacomparacompleta = "";
			if (condicaovalor.indexOf(":") > 0) {
				String[] separahoracompara = condicaovalor.split(":");
				for (int i = 0; i < separahoracompara.length; i++) {
					horacomparacompleta += separahoracompara[i];
					horaatualcompleta += separahoraatual[i];
				}
			} else {
				horaatualcompleta = separahoraatual[0];
				horacomparacompleta = condicaovalor;
			}
			
			int horaatualinteiro = Integer.parseInt(horaatualcompleta);
			int horacomparainteiro = Integer.parseInt(horacomparacompleta);
			if ((separador.equals("=") && horaatualinteiro == horacomparainteiro) || 
					(separador.equals(">") &&  horaatualinteiro > horacomparainteiro) ||
					(separador.equals(">=") &&  horaatualinteiro >= horacomparainteiro) ||
					(separador.equals("<") &&  horaatualinteiro < horacomparainteiro) ||
					(separador.equals("<=") &&  horaatualinteiro <= horacomparainteiro)) {
					insere = true;
			}
		}
		return insere;
	}
	
	public void remove(String parametros) {
		Registro[] listaremocao = this.procura(parametros);
		for (int i = 0; i < listaremocao.length; i++) {
			this.removeporid(listaremocao[i].getId());
		}
	}
	
	public void atualiza(String query) {
		query = query.replaceAll("}\\s","}");
		query = query.replaceAll("\\s\\{","{");
		if(query.toLowerCase().indexOf("}where{") > 0) {
			query = query.replaceAll("}WHERE\\{","}where{");
			String[] separa = query.split("}where\\{");
			separa[0] = separa[0] + "}";
			separa[1] = "{" + separa[1];
			Registro[] registrosmuda = this.procura(separa[1]);
			String[][] valoresmudanca = this.ExplodeQuery(separa[0]);
			for(int i=0;i<registrosmuda.length;i++) { //percorre todos os registros que deve atualizar
				String[] valoresatuais = registrosmuda[i].getValores();
				int idanterior = registrosmuda[i].getId(); //previne caso seja uma mudan�a de ID que � usado para substituir, no caso do excel n�o funcionaria pelo fato do objeto n�o estar em mem�ria, por isso a tabela excel deve substituir sempre pelo id anterior
				for (int j = 0; j < valoresmudanca.length; j++) {
					int posicaovalor = this.getPosicaoCampo(valoresmudanca[j][0]);
					String novovalor = valoresmudanca[j][1];
					valoresatuais[posicaovalor] = novovalor;
				}
				registrosmuda[i].setValores(valoresatuais);
				this.substitui(registrosmuda[i].getId(), idanterior, registrosmuda[i]);
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
	
	private String[][] ExplodeQuery(String query) {
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
	
	private String[] ExplodeParametros(String query) {
		query = query.replaceAll("}\\s+","}");
		query = query.replaceAll("\\s+\\{","{");
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
	
	public abstract Registro getRegistro(int indice, int saltos, Tabela tabela); //procura indice x a partir do 0 OBS: passa a pr�pria tabela para o excel que n�o tem como recuperar a refer�ncia para um objeto da planilha
	public abstract void removeporid(int id);
	public abstract void inserir(Registro registro);
	public abstract void substitui(int id, int idanterior, Registro registro);
}