package br.com.utilitario;

public class TermoRelevante {
	private String termo;
	private Integer repeticao;
	private Integer classificacao;

	public TermoRelevante() {
		this.repeticao = 1;
	}
	
	public TermoRelevante(String termo) {
		this.termo = termo;
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
	
	public int equals(TermoRelevante obj) {
		return this.termo.compareToIgnoreCase(obj.getTermo());
	}
}
