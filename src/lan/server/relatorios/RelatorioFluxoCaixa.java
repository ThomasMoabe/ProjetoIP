package lan.server.relatorios;

import lan.server.caixa.Transacao;
import lan.server.util.Iterator;

public class RelatorioFluxoCaixa extends Relatorio{
	private int quantidadelinhas;
	
	public RelatorioFluxoCaixa(Iterator<Transacao> transacoes) {
		String linhacabecalho = this.formatalinha(new String[] {"Id", "Tipo", "Descrição", "Valor", "Data", "Hora", "Administrador"}) + "\r\n";
		this.inserelinha(linhacabecalho);
		while (transacoes.hasNext()) {
			Transacao transacao = transacoes.next();
			String linha = "\r\n" + this.formatalinha(new String[] {String.valueOf(transacao.getId()), transacao.getTipo(), transacao.getDescricao(), String.valueOf(transacao.getValor()), transacao.getData(), transacao.getHora(), transacao.getAdministrador()});
			this.inserelinha(linha);
		}
	}
}
