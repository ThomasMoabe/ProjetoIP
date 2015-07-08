package lan.server.relatorios;

import java.io.Serializable;

public class LogRelatoriosGerados implements Serializable { //repositório de relatórios gerados que será serializado
	private RelatorioGerado[] relatorios;
	private int qtdrelatorios;
	
	public LogRelatoriosGerados() {
		this.relatorios = new RelatorioGerado[2]; //esse array dobra a capacidade sempre que lotar
		this.qtdrelatorios = 0;
	}
	
	public void inserir(RelatorioGerado relatorio) {
		if (this.qtdrelatorios == relatorios.length) { //aumenta o array
			RelatorioGerado[] logmaior = new RelatorioGerado[this.relatorios.length * 2];
			for (int i = 0; i < this.relatorios.length; i++) {
				logmaior[i] = this.relatorios[i];
			}
			this.relatorios = logmaior;
		}
		this.relatorios[this.qtdrelatorios] = relatorio;
		this.qtdrelatorios++;
	}
	
	public RelatoriosGeradosIterator iterator() {
		return new RelatoriosGeradosIterator(this.relatorios, this.qtdrelatorios);
	}
}
