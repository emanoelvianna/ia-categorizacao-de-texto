package br.com.utilitario;

import br.com.utilitario.enumeracao.Classificacao;

public class TermoRelevante implements Comparable {
	private String termo;
	private Integer repeticao;
	private Classificacao classificacao;

	public TermoRelevante() {
		this.repeticao = 1;
	}
	
	public TermoRelevante(String termo, Classificacao classificacao) {
		this.termo = termo;
		this.repeticao = 1;
		this.classificacao = classificacao;
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

	public Classificacao getClassificacao() {
		return this.classificacao;
	}
	
	public void repetiuTermo() {
		this.repeticao++;
	}
	
	public int equals(TermoRelevante obj) {
		return this.termo.compareToIgnoreCase(obj.getTermo());
	}

	@Override
	public int compareTo(Object o) {
		TermoRelevante t = (TermoRelevante)o;
		
		if(this.getRepeticao() > t.getRepeticao())
			return -1;
		else if(this.repeticao < t.getRepeticao())
			return 1;
		
		return 0;
	}
}