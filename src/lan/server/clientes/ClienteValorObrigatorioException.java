package lan.server.clientes;

public class ClienteValorObrigatorioException extends Exception {
	private String valoresnulos;
	
	public ClienteValorObrigatorioException() {
		super("Valor obrigatório nulo");
		this.valoresnulos = "";
	}
	
	public boolean adicionarValorNulo(String valor) {
		this.valoresnulos += "," + valor;
		return true;
	}
	
	public String[] getValoresNulos() {
		String[] valoresnulos = {this.valoresnulos.substring(1)};
		return valoresnulos[0].indexOf(",") == 0 ? valoresnulos : this.valoresnulos.substring(1).split(",");
	}
}