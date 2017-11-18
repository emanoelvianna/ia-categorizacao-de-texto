package br.com.principal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	public static List<TermoRelevante> listarTermos(Document document, int n) {
		List<TermoRelevante> termosRelevantes = new ArrayList<TermoRelevante>();
		boolean termoEncontrado;

		Sentence sentence = null;
		Token token = null;
		String termo = null;
		
		for(int s = 0; s < document.getSentences().size(); s++) {
			sentence = document.getSentences().get(s);
			
			for(int t = 0; t < sentence.getTokens().size(); t++) {
				token = sentence.getTokens().get(t);
				token.getLemmas(); // array com os possíveis lemas

				termo = "";
				if (token.getLemmas().length != 0) {
					if(n == 1) {
						termo = sentence.getTokens().get(t).getLexeme().toLowerCase();
					} else if(n == 2) {
						if((t + 2) < sentence.getTokens().size()) {
							termo = token.getLexeme().toLowerCase() + " " 
									+ sentence.getTokens().get(t+1).getLexeme().toLowerCase();
						}
					} else if(n == 3) {
						if((t + 3) < sentence.getTokens().size()) {
							termo = token.getLexeme().toLowerCase() + " " 
									+ sentence.getTokens().get(t+1).getLexeme().toLowerCase() + " "
									+ sentence.getTokens().get(t+2).getLexeme().toLowerCase();
						}
					}
					
					System.out.println("Token = " + termo + " -- [" + token.getPOSTag() + "]");
					
					termoEncontrado = false;

					for(int i = 0; i < termosRelevantes.size(); i++) {
						if(termosRelevantes.get(i).getTermo().compareToIgnoreCase(termo) == 0) {
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
						termosRelevantes.add(new TermoRelevante(termo, classificacao));
					}
				}
			}
		}
		return termosRelevantes;
	}
	
	
	public static void list2file(List<TermoRelevante> lista, String outfile) throws IOException {
		FileWriter outWriter = new FileWriter(outfile);
		
		PrintWriter out = new PrintWriter(outWriter);
		
		for(TermoRelevante t : lista) {
			out.println(t.getTermo() + " [" + t.getRepeticao() + " -- " + t.getClassificacao() + "]");
		}
		
		outWriter.close();
	}
	

	public static void main(String[] args) {
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

		List<TermoRelevante> termosEsportes_n1 = listarTermos(document, 1);
		List<TermoRelevante> termosEsportes_n2 = listarTermos(document, 2);
		List<TermoRelevante> termosEsportes_n3 = listarTermos(document, 3);
		
		Collections.sort(termosEsportes_n1);
		Collections.sort(termosEsportes_n2);
		Collections.sort(termosEsportes_n3);
		
		/* lista de sentenças */
		try {
			list2file(termosEsportes_n1, "output/esportes_n1.txt");
			list2file(termosEsportes_n2, "output/esportes_n2.txt");
			list2file(termosEsportes_n3, "output/esportes_n3.txt");
		} catch(IOException e) {
			System.out.println("Falha ao escrever arquivos de saida!");
		}
	}
}
