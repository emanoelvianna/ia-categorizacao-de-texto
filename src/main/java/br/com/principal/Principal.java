package br.com.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import br.com.utilitario.LerArquivoTexto;
import br.com.utilitario.TermoRelevante;
import br.com.utilitario.enumeracao.Classificacao;

public class Principal {
	

	public static void main(String[] args) {
		List<TermoRelevante> maisRelevantes = new ArrayList<TermoRelevante>();
		boolean termoEncontrado;
		
		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		Analyzer cogroo = factory.createPipe();

		/* processar texto */
		LerArquivoTexto leitor = new LerArquivoTexto();
		Document document = new DocumentImpl();

		// TODO: adicionar os outros texto mais tarde!
		
		/* processando texto sobre esportes */
		// document.setText(leitor.LerArquivoDeEsportes());
		document.setText(leitor.LerArquivoDeTeste());
		cogroo.analyze(document);

		/* lista de sentenças */
		for (Sentence sentence : document.getSentences()) {
			
			for (Token token : sentence.getTokens()) {
				token.getLemmas(); // array com os possíveis lemas

				if (token.getLemmas().length != 0) {
					System.out.println("Token = " + token.getLemmas()[0] + " -- [" + token.getPOSTag() + "]");
					
					termoEncontrado = false;

					for(int i = 0; i < maisRelevantes.size(); i++) {
						if(maisRelevantes.get(i).getTermo().compareToIgnoreCase(token.getLemmas()[0]) == 0) {
							maisRelevantes.get(i).repetiuTermo();
							termoEncontrado = true;
							break;
						} 
					}

					if(!termoEncontrado) {
						Classificacao classificacao = Classificacao.NULO;
						
						if(token.getPOSTag().equalsIgnoreCase("adv"))
							classificacao = Classificacao.ADVERBIO;
						else if(token.getPOSTag().equalsIgnoreCase("v-fin"))
							classificacao = Classificacao.VERBO;
						else if(token.getPOSTag().equalsIgnoreCase("art"))
							classificacao = Classificacao.ARTIGO;
						else if(token.getPOSTag().equalsIgnoreCase("n"))
							classificacao = Classificacao.SUBSTANTIVO;
						else if(token.getPOSTag().equalsIgnoreCase("prp"))
							classificacao = Classificacao.PREPOSICAO;
						else if(token.getPOSTag().equalsIgnoreCase("adj"))
							classificacao = Classificacao.ADJETIVO;
						else if(token.getPOSTag().equals("."))
							classificacao = Classificacao.PONTO;
						else if(token.getPOSTag().equalsIgnoreCase("pron-det"))
							classificacao = Classificacao.PRONOME_DETERMINATIVO;
						
						System.out.println("** Adicionando token " + token.getLemmas()[0] + "[" + classificacao + "]");
						maisRelevantes.add(new TermoRelevante(token.getLemmas()[0], classificacao));
					}
	
					/*
					if (!maisRelevantes.contains(token.getPOSTag())) {
						TermoRelevante termo = new TermoRelevante();
						termo.setTermo(token.getPOSTag());
						maisRelevantes.add(termo);
					} else {
						int indexOf = maisRelevantes.indexOf(token.getPOSTag());
						maisRelevantes.get(indexOf).repetiuTermo();
					}
					*/
				}
			}
		}
		System.out.println("Listagem de termos / repeticao");
		Collections.sort(maisRelevantes);
		for(TermoRelevante t : maisRelevantes) {
			System.out.println(t.getTermo() + "[" + t.getRepeticao() + " -- " + t.getClassificacao() + "]");
		}
	}
}
