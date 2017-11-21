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

	/* Gera arquivo no formato do WEKA concatenando as listas */
	public static void gerarBOW(List<Texto> textos, int k, String outfile) throws IOException {
		List<TermoRelevante> termos = new ArrayList<TermoRelevante>();

		FileWriter outWriter = new FileWriter(outfile);
		PrintWriter out = new PrintWriter(outWriter);

		/* Percorre todos os arquivos de texto procurando palavras com maior frequencia
		 * Listas de termos ja ordenadas em ordem decrescente		 * */
		
		
		/* Gera lista dos k termos para arquivo */
		out.println("@relation " + outfile);
		
		/* Imprime palavras */
		for(TermoRelevante t : termos) {
			// TODO: Processar espacos e outros caracteres especiais
			out.println("@attribute " + t.getTermo().toUpperCase() + " integer");
		}
		
		out.println("@attribute classe {Esporte, Policia, Problema, Trabalhador}");
		out.println("@data");
		
		for(TermoRelevante t : termos) {
			out.println(t.getTermo());
		}
		
		outWriter.close();
	}

	
	
}
