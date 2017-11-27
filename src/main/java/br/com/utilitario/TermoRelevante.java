package br.com.utilitario;

import br.com.utilitario.enumeracao.Classificacao;

@SuppressWarnings("rawtypes")
public class TermoRelevante implements Comparable {
	private String termo;
	private Integer repeticao;
	private Classificacao classificacao;
	private Texto texto;

	public TermoRelevante() {
		this.repeticao = 1;
	}
	
	public TermoRelevante(String termo, Classificacao classificacao, Texto texto) {
		this.termo = termo;
		this.repeticao = 1;
		this.classificacao = classificacao;
		this.texto = texto;
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

	public void addRepeticao(int repeticao) {
		this.repeticao += repeticao;
	}	
	
	public int equals(TermoRelevante obj) {
		return this.termo.compareToIgnoreCase(obj.getTermo());
	}
	
	public Texto getTexto() {
		return this.texto;
	}
	
	public int compareTo(Object o) {
		TermoRelevante t = (TermoRelevante)o;
		
		if(this.getRepeticao() > t.getRepeticao())
			return -1;
		else if(this.repeticao < t.getRepeticao())
			return 1;
		
		return 0;
	}
	
	public boolean equals(Object o) {
		TermoRelevante t = (TermoRelevante)o;
		
		return t.getTermo().equals(this.termo);
	}
	
	public String toString() {
		return "[Termo: " + termo + " | Classificacao: " + classificacao 
				+ " | Repeticao: " + this.repeticao + " | Texto: " + this.texto + "]";
	}
}
