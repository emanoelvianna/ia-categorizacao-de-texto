package br.com.utilitario;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;

import br.com.utilitario.enumeracao.Classificacao;

public class ProcessarTexto {
	
	/* Processa um texto e retorna lista ordenada de termos relevantes baseado em frequencia */
	@SuppressWarnings("unchecked")
	public static void listarTermos(Document document, Texto texto) {
		int n = 1; // Limpar depois 
		
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
						} else {
							break;
						}
					} else if(n == 3) {
						if((t + 3) < sentence.getTokens().size()) {
							termo = token.getLexeme().toLowerCase() + " " 
									+ sentence.getTokens().get(t+1).getLexeme().toLowerCase() + " "
									+ sentence.getTokens().get(t+2).getLexeme().toLowerCase();
						} else {
							break;
						}
					}
					
					termoEncontrado = false;

					for(int i = 0; i < texto.getTermos().size(); i++) {
						if(texto.getTermos().get(i).getTermo().compareToIgnoreCase(termo) == 0) {
							texto.getTermos().get(i).repetiuTermo();
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

						if(n == 1) {
							if(classificacao == Classificacao.VERBO 
									|| classificacao == Classificacao.SUBSTANTIVO
									|| classificacao == Classificacao.ADJETIVO
									|| classificacao == Classificacao.ADVERBIO) {
								texto.addTermo(new TermoRelevante(termo, classificacao));
							}
						} else if(n == 2 || n == 3) {
							if(classificacao == Classificacao.VERBO 
									|| classificacao == Classificacao.SUBSTANTIVO
									|| classificacao == Classificacao.ADJETIVO
									|| classificacao == Classificacao.ADVERBIO
									|| classificacao == Classificacao.PREPOSICAO) {
								texto.addTermo(new TermoRelevante(termo, classificacao));
							}
						}
					}
				}
			}
		}
		Collections.sort(texto.getTermos());
	}

	
	public static void list2file(List<TermoRelevante> lista, String outfile) throws IOException {
		FileWriter outWriter = new FileWriter(outfile);
		PrintWriter out = new PrintWriter(outWriter);
		
		for(TermoRelevante t : lista) {
			out.println(t.getTermo() + " [" + t.getRepeticao() + " -- " + t.getClassificacao() + "]");
		}
		outWriter.close();
	}

	/* Gera arquivo no formato do WEKA a partir da lista de textos */
	public static void gerarBOW(List<Texto> textos, int k, String outfile) throws IOException {
		List<String> termos = new ArrayList<String>();
		
		if(textos.size() == 0) {
			return;
		}
		
		/* Indice de deslocamento entre as listas de termos de cada texto */
		int[] textIdx = new int[textos.size()];
		int currIdx = 0;

		/* Inicializa indices na posicao zero */
		for(int i = 0; i < textIdx.length; i++) 
			textIdx[i] = 0;
		
		 
		
		while(termos.size() < k) {
			TermoRelevante termoAux = textos.get(0).getTermos().get(textIdx[0]);
			currIdx = 0;
			
			for(int i = 0; i < textos.size(); i++) {
				System.out.println("Processando termo " + textos.get(i).getTermos().get(textIdx[i]) + " [" + termos.size() + "]");

				if((textIdx[i] + 1) >= textos.get(i).getTermos().size()) {
				continue;
				}
				
				if(termos.contains(termoAux.getTermo())) {
					textIdx[i]++;
					continue;
				}
				
				if(textos.get(i).getTermos().get(textIdx[i]).getRepeticao() > termoAux.getRepeticao()) {
					if(termos.contains(termoAux.getTermo())) {
						textIdx[i]++;
						continue;
					}
			
					/* Substituiu por termo com maior recorrencia */
					termoAux = textos.get(i).getTermos().get(textIdx[i]);
					currIdx = i;
				}
				termos.add(termoAux.getTermo());
				textIdx[currIdx]++;
			}
		}
		
		
		/* Gera lista dos k termos para arquivo */
		FileWriter outWriter = new FileWriter(outfile);
		PrintWriter out = new PrintWriter(outWriter);
		
		
		System.out.println("Dentro do BOW!");
		
		out.println("@relation " + outfile);
		
		/* Imprime palavras */
		for(String t : termos) {
			// TODO: Processar espacos e outros caracteres especiais
			out.println("@attribute " + t.toUpperCase() + " integer");
		}
		
		out.println("@attribute classe {Esporte, Policia, Problema, Trabalhador}");
		out.println("@data");
		
		for(String t : termos) {
			System.out.println("Bla " + t);
			out.println(t);
		}
		
		outWriter.close();
	}
}
