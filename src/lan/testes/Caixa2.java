package lan.testes;
import lan.bd.*;
import lan.caixa.Transacao;

public class Caixa2 {
	BD banco;
	public Caixa2() {
		banco = new BD();
		banco.novatabela("transacoes", new String[] {"id", "tipo", "descricao", "valor", "data", "hora", "administrador"}, "excel");
		Tabela tabelatransacoes = banco.selecionatabela("transacoes");
		Transacao exemplo = new Transacao(new String[] {String.valueOf(tabelatransacoes.getIdAtual()), "entrada", "fundo de caixa", "10.00", "10/10/2015", "10:10:00", "thomas"});
		tabelatransacoes.inserir((Registro)exemplo);
		Registro[] teste = tabelatransacoes.procura("{id=1} {administrador=thomas}");
		
		//tabelatransacoes.remove("{id=7}");
		teste = tabelatransacoes.procura("{id=1} {administrador=thomas}");
		
		RegistroIterator varreregistros = new RegistroIterator(teste);
		
		//testando iterator e imprimindo todos os resultados encontrados
		while(varreregistros.hasNext()) {
			System.out.println(((Transacao)varreregistros.next()));
		}
		
		//tabelatransacoes.atualiza("{id=1} WHERE {id=4}"); //atualiza os valores da esquerda esquerda em tudo o que encontrar com a pesquisa na direita do WHERE
		
		teste = tabelatransacoes.procura("{id=1} {administrador=thomas}");
		varreregistros = new RegistroIterator(teste);
		while(varreregistros.hasNext()) {
			System.out.println(((Transacao)varreregistros.next()));
		}
	}
}