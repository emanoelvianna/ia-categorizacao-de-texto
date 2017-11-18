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
	
	/* Processa um texto e retorna lista ordenada de termos relevantes baseado em frequencia */
	public static List<TermoRelevante> listarTermos(Document document) {
		List<TermoRelevante> termosRelevantes = new ArrayList<TermoRelevante>();
		boolean termoEncontrado;

		for (Sentence sentence : document.getSentences()) {
			
			for (Token token : sentence.getTokens()) {
				token.getLemmas(); // array com os possíveis lemas

				if (token.getLemmas().length != 0) {
					//System.out.println("Token = " + token.getLemmas()[0] + " -- [" + token.getPOSTag() + "]");
					
					termoEncontrado = false;

					for(int i = 0; i < termosRelevantes.size(); i++) {
						if(termosRelevantes.get(i).getTermo().compareToIgnoreCase(token.getLemmas()[0]) == 0) {
							termosRelevantes.get(i).repetiuTermo();
							termoEncontrado = true;
							break;
						} 
					}

					if(!termoEncontrado) {
						Classificacao classificacao = Classificacao.NULO;
						
						String aux = token.getPOSTag();
						
						if(aux.equalsIgnoreCase("adv"))
							classificacao = Classificacao.ADVERBIO;
						else if(aux.equals("v-fin") || (aux.equals("v-fin") 
								|| (aux.equals("v-pcp")) || aux.equals("v-inf")) 
								|| (aux.equals("v-ger")))
							classificacao = Classificacao.VERBO;
						else if(aux.equals("art"))
							classificacao = Classificacao.ARTIGO;
						else if(aux.equals("n"))
							classificacao = Classificacao.SUBSTANTIVO;
						else if(aux.equals("prp"))
							classificacao = Classificacao.PREPOSICAO;
						else if(aux.equals("adj") || (aux.equals("n-adj")))
							classificacao = Classificacao.ADJETIVO;
						else if(aux.equals(",") || aux.equals(":") || aux.equals("."))
							classificacao = Classificacao.PONTO;
						else if(aux.equals("pron-det") || (aux.equals("pron-indp")) || (aux.equals("pron-pers")))
							classificacao = Classificacao.PRONOME;
						else if(aux.equals("conj-c") || (aux.equals("conj-s")))
							classificacao = Classificacao.CONJUNCAO;
						else if(aux.equals("num"))
							classificacao = Classificacao.NUMERO;
						
						//System.out.println("** Adicionando token " + token.getLemmas()[0] + "[" + classificacao + "]");
						termosRelevantes.add(new TermoRelevante(token.getLemmas()[0], classificacao));
					}
				}
			}
		}
		
		return termosRelevantes;
	}
	

	public static void main(String[] args) {
		List<TermoRelevante> maisRelevantes = new ArrayList<TermoRelevante>();
		
		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		Analyzer cogroo = factory.createPipe();

		/* processar texto */
		LerArquivoTexto leitor = new LerArquivoTexto();
		Document document = new DocumentImpl();

		// TODO: adicionar os outros texto mais tarde!
		
		/* processando texto sobre esportes */
		document.setText(leitor.LerArquivoDeEsportes());
		//document.setText(leitor.LerArquivoDeTeste());
		cogroo.analyze(document);

		List<TermoRelevante> termosEsportes = listarTermos(document);
		
		/* lista de sentenças */
		System.out.println("\n\nListagem de termos / repeticao");
		Collections.sort(termosEsportes);
		
		for(TermoRelevante t : termosEsportes) {
			System.out.println(t.getTermo() + " [" + t.getRepeticao() + " -- " + t.getClassificacao() + "]");
		}
	}
}
