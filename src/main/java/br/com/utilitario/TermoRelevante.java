package br.com.utilitario;

public class TermoRelevante {
	private String termo;
	private Integer repeticao;

	public TermoRelevante() {
		this.repeticao = 1;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Integer getRepeticao() {
		return this.repeticao;
	}

	public void repetiuTermo() {
		this.repeticao++;
	}
}
