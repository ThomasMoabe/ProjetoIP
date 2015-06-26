package lan.server.testes;

import lan.server.bd.BD;
import lan.server.clientes.ClienteIterator;
import lan.server.clientes.ClienteJaCadastradoException;
import lan.server.clientes.ClienteLoginInvalidoException;
import lan.server.clientes.ClienteSenhaFracaException;
import lan.server.clientes.ClienteValorObrigatorioException;
import lan.server.clientes.ClientesManager;
import lan.server.util.Config;
import lan.server.util.ConfiguracaoInvalidaException;

public class testaclientes {
	public static void main(String[] args) {
		BD.banco = new BD();
		try {
			Config conf = new Config();
			BD.tipobanco = conf.getTipoBanco();
			ClientesManager clientes = new ClientesManager();
			try {
				try {
					clientes.cadastraAtualizaCliente("0", "tafm", "Thomas", "Rua x", "thomas@qqcoisa", "1234", "03/05/1992");
					ClienteIterator iterator = clientes.iteratorCliente();
					while (iterator.hasNext()) {
						System.out.println(iterator.next());
					}
					clientes.cadastraAtualizaCliente("1", "tafm", "ThomasX", "Rua j", "thomas@qqcoiss", "1235", "03/05/1992");
					iterator = clientes.procuraClientes("th");
					while (iterator.hasNext()) {
						System.out.println(iterator.next());
					}
				} catch (ClienteValorObrigatorioException e) {
					// TODO Auto-generated catch block
					System.out.println("Campo obrigat�rio em branco");
					String[] campos = e.getValoresNulos();
					for (int i = 0; i < campos.length; i++) {
						System.out.println(campos[i]);
					}
				}
			} catch (ClienteJaCadastradoException
					| ClienteLoginInvalidoException
					| ClienteSenhaFracaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ConfiguracaoInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}