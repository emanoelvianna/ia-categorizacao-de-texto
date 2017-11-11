package br.com.utilitario.enumeracao;

public enum Caracteres {
	// TODO: adicionar os Caracteres que devem ser removido!
	ABRE("<"),
	FECHA(">"),
	BARRA_1("|"),
	BARRA_2("/"),
	BARRA_3("\\"), 
	TRACO("-"),
	ABRE_PARENTESES("("),
	FECHA_PARENTESES(")"),
	ACENTUACAO_1("'");

	private String valor;

	private Caracteres(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static String removeCaracteres(String linha) {
		for (Caracteres caractere : Caracteres.values()) {
			if (linha.toLowerCase().contains(caractere.getValor())) {
				linha = linha.replace(caractere.getValor(), " ");
			}
		}
		return linha;
	}
}
