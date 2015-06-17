package lan.caixa;
import lan.bd.*;

public class Caixa2 {
	BD banco;
	public Caixa2() {
		banco = new BD();
		banco.novatabela("transacoes", new String[] {"id", "tipo", "descricao", "valor", "data", "administrador"}, "array");
		Tabela tabelatransacoes = banco.selecionatabela("transacoes");
		//Transacao exemplo = new Transacao("entrada", "fundo de caixa", 10, "thomas");
		Transacao exemplo = new Transacao(new String[] {String.valueOf(Transacao.idatual), "entrada", "fundo de caixa", "10.00", "10/10/2015" , "thomas"});
		exemplo.setTabela(tabelatransacoes);
		//((RepositorioArray) tabelatransacoes).inserir((Registro)exemplo);
		tabelatransacoes.inserir((Registro)exemplo);
		//Registro[] teste = ((RepositorioArray) tabelatransacoes).procura("{");
		Registro[] teste = tabelatransacoes.procura("{id:1} {administrador:thomas}");
		
		if(teste.length>0) {
			System.out.println(((Transacao)teste[0]).getDescricao());
			System.out.println(((Transacao)teste[0]).toString());
		}
		
		tabelatransacoes.remove("{id:4}");
		
		teste = tabelatransacoes.procura("{id:1} {administrador:thomas}");
		
		if(teste.length>0) {
			System.out.println(((Transacao)teste[0]).getDescricao());
			System.out.println(((Transacao)teste[0]).toString());
		}
	}
}