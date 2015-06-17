package lan.caixa;
import lan.bd.*;

public class Caixa2 {
	BD banco;
	public Caixa2() {
		banco = new BD();
		banco.novatabela("transacoes", new String[] {"id", "tipo", "descricao", "valor", "data", "administrador"}, "lista");
		Tabela tabelatransacoes = banco.selecionatabela("transacoes");
		Transacao exemplo = new Transacao(new String[] {String.valueOf(Transacao.idatual), "entrada", "fundo de caixa", "10.00", "10/10/2015" , "thomas"});
		tabelatransacoes.inserir((Registro)exemplo);
		Registro[] teste = tabelatransacoes.procura("{id:1} {administrador:thomas}");
		
		RegistroIterator varre = new RegistroIterator(teste);
		
		//testando iterator e imprimindo todos os resultados encontrados
		while(varre.hasNext()) {
			System.out.println(((Transacao)varre.next()).toString());
		}
	}
}