package br.com.utilitario;

import java.util.List;

import br.com.utilitario.enumeracao.CategoriaTexto;

public class Texto {
	private CategoriaTexto categoria;
	private List<TermoRelevante> termos;
	
	
	public Texto(CategoriaTexto categoria) {
		this.categoria = categoria;
	}
	
	public CategoriaTexto getCategoriaTexto() {
		return this.categoria;
	}
	
	public void addTermo(TermoRelevante termo) {
		this.termos.add(termo);
	}
}
