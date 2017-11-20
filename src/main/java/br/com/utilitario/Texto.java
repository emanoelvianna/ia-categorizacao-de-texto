package br.com.utilitario;

import java.util.ArrayList;
import java.util.List;

import br.com.utilitario.enumeracao.CategoriaTexto;
import br.com.utilitario.enumeracao.TipoTexto;

public class Texto {
	private CategoriaTexto categoria; // esporte | policia etc
	private TipoTexto tipoTexto;	// treino | teste
	private List<TermoRelevante> termos;
	
	
	public Texto(TipoTexto tipoTexto, CategoriaTexto categoria) {
		this.tipoTexto = tipoTexto;
		this.categoria = categoria;
		termos = new ArrayList<TermoRelevante>();
	}
	
	public CategoriaTexto getCategoriaTexto() {
		return this.categoria;
	}
	
	public TipoTexto getTipoText() {
		return this.tipoTexto;
	}
	
	public void addTermo(TermoRelevante termo) {
		this.termos.add(termo);
	}
	
	public String toString() {
		String res = "Texto [Categoria: " + categoria + " | Tipo: " + tipoTexto + "]";
		
		for(TermoRelevante termo : termos) {
			res += termo;
		}
		return res;
	}
}
