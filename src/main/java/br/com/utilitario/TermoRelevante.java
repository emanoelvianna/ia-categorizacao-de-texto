package br.com.utilitario;

import br.com.utilitario.enumeracao.Classificacao;

public class TermoRelevante implements Comparable {
	private String termo;
	private Integer repeticao;
	private Classificacao classificacao;
	private Integer[] categorias;

	public TermoRelevante() {
		this.repeticao = 1;
	}
	
	public TermoRelevante(String termo, Classificacao classificacao) {
		this.termo = termo;
		this.repeticao = 1;
		this.classificacao = classificacao;
		this.categorias = new Integer[4];
		
		this.categorias[0] = 0;
		this.categorias[1] = 0;
		this.categorias[2] = 0;
		this.categorias[3] = 0;
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

	public void setCategoria(int idx, int valor) {
		this.categorias[idx] = valor;
	}
	
	public Integer[] getCategorias() {
		return this.categorias;
	}
	
	public int compareTo(Object o) {
		TermoRelevante t = (TermoRelevante)o;
		
		if(this.getRepeticao() > t.getRepeticao())
			return -1;
		else if(this.repeticao < t.getRepeticao())
			return 1;
		
		return 0;
	}
	
	public String toString() {
		return "[Termo: " + termo + " | Classificacao: " + classificacao + "]";
	}
}
