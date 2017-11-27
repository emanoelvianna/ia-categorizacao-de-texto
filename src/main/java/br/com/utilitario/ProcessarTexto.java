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
import br.com.utilitario.enumeracao.TipoTexto;

public class ProcessarTexto {
	
	private static Classificacao classificar(String termo) {
		Classificacao classificacao = Classificacao.NULO;
		
		if(termo.equalsIgnoreCase("adv"))
			classificacao = Classificacao.ADVERBIO;
		else if(termo.equals("v-fin") || (termo.equals("v-fin") 
				|| (termo.equals("v-pcp")) || termo.equals("v-inf")) 
				|| (termo.equals("v-ger")))
			classificacao = Classificacao.VERBO;
		else if(termo.equals("art"))
			classificacao = Classificacao.ARTIGO;
		else if(termo.equals("n"))
			classificacao = Classificacao.SUBSTANTIVO;
		else if(termo.equals("prp"))
			classificacao = Classificacao.PREPOSICAO;
		else if(termo.equals("adj") || (termo.equals("n-adj")))
			classificacao = Classificacao.ADJETIVO;
		else if(termo.equals(",") || termo.equals(":") || termo.equals("."))
			classificacao = Classificacao.PONTO;
		else if(termo.equals("pron-det") || (termo.equals("pron-indp")) || (termo.equals("pron-pers")))
			classificacao = Classificacao.PRONOME;
		else if(termo.equals("conj-c") || (termo.equals("conj-s")))
			classificacao = Classificacao.CONJUNCAO;
		else if(termo.equals("num"))
			classificacao = Classificacao.NUMERO;

		return classificacao;
	}

	private static boolean isValidClassificacao(int n, Classificacao classificacao) {
		if(n == 1) {
			if(classificacao == Classificacao.VERBO 
					|| classificacao == Classificacao.SUBSTANTIVO
					|| classificacao == Classificacao.ADJETIVO
					|| classificacao == Classificacao.ADVERBIO)
				return true;
		} else if(n == 2 || n == 3) {
			if(classificacao == Classificacao.VERBO 
				|| classificacao == Classificacao.SUBSTANTIVO
				|| classificacao == Classificacao.ADJETIVO
				|| classificacao == Classificacao.ADVERBIO
				|| classificacao == Classificacao.PREPOSICAO)
			return true;
		}
		
		return false;
	}

	
	private static boolean hasTermo(List<TermoRelevante> termos, String termo) {
		for(int i = 0; i < termos.size(); i++) {
			if(termos.get(i).getTermo().toLowerCase().equals(termo.toLowerCase())) {
				termos.get(i).repetiuTermo();
				return true;
			} 
		}
		
		return false;
	}
	
	
	/* Processa um texto e retorna lista ordenada de termos relevantes baseado em frequencia */
	@SuppressWarnings("unchecked")
	public static void listarTermos(Document document, Texto texto, int n) {
		Sentence sentence = null;
		Token token = null;
		String termo = null;
		Classificacao classificacao;
		
		for(int s = 0; s < document.getSentences().size(); s++) {
			sentence = document.getSentences().get(s);
			
			for(int t = 0; t < sentence.getTokens().size(); t++) {
				classificacao = Classificacao.NULO;
				
				token = sentence.getTokens().get(t);
				token.getLemmas(); // array com os possíveis lemas

				termo = "";
				if (token.getLemmas().length != 0) {
					termo = sentence.getTokens().get(t).getLemmas()[0].toLowerCase();
					classificacao = classificar(sentence.getTokens().get(t).getPOSTag());
					
					if(n == 1) {
						if(isValidClassificacao(1, classificacao)) {
							if(!hasTermo(texto.getTermos(), termo)) {
								texto.addTermo(new TermoRelevante(termo.toLowerCase(), classificacao, texto));
							}
						}
					} else if(n == 2) {
						/* Encontra primeiro token valido */
						int idx2 = t + 1;
					
						if(!isValidClassificacao(2, classificacao)) {
							/* Avanca procurando proximo primeiro termo valido */
							continue;
						}
						
						if(idx2 >= sentence.getTokens().size()) {
							break;
						}
						
						while(idx2 < sentence.getTokens().size()) {
							token = sentence.getTokens().get(idx2);
							token.getLemmas();
							
							if(token.getLemmas().length != 0) {
								classificacao = classificar(sentence.getTokens().get(idx2).getPOSTag());
								
								if(isValidClassificacao(2, classificacao)) {
									texto.addTermo(new TermoRelevante(termo.toLowerCase() + " " 
											+ sentence.getTokens().get(idx2).getLemmas()[0].toLowerCase(),
											Classificacao.NULO, texto));
									break;
								}
							}							
							idx2++;
						}
					} else if(n == 3) {
						/* Encontra primeiro token valido */
						int idx2 = t + 1;
					
						if(!isValidClassificacao(2, classificacao)) {
							/* Avanca procurando proximo primeiro termo valido */
							continue;
						}
						
						if(idx2 >= sentence.getTokens().size()) {
							break;
						}
						
						while(idx2 < sentence.getTokens().size()) {
							token = sentence.getTokens().get(idx2);
							token.getLemmas();
							
							if(token.getLemmas().length != 0) {
								classificacao = classificar(sentence.getTokens().get(idx2).getPOSTag());
								
								if(isValidClassificacao(3, classificacao)) {
									break;
								}
							}							
							idx2++;
						}
						
						if(isValidClassificacao(2, classificacao)) {
							int idx3 = idx2 + 1;
							
							if(idx3 >= sentence.getTokens().size()) {
								break;
							}
							
							while(idx3 < sentence.getTokens().size()) {
								token = sentence.getTokens().get(idx3);
								token.getLemmas();
								
								if(token.getLemmas().length != 0) {
									classificacao = classificar(sentence.getTokens().get(idx3).getPOSTag());
									
									if(isValidClassificacao(3, classificacao)) {
										texto.addTermo(new TermoRelevante(termo + " "
												+ sentence.getTokens().get(idx2).getLemmas()[0].toLowerCase() + " "
												+ sentence.getTokens().get(idx3).getLemmas()[0].toLowerCase(),
												Classificacao.NULO, texto));
										break;
									}
								}
								idx3++;
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
	public static void gerarBOW(List<Texto> textos, int k, String outfile_treino, String outfile_teste) throws IOException {
		List<TermoRelevante> termos = new ArrayList<TermoRelevante>();
		boolean adicionarTermo = true;
		
		if(textos.size() == 0) {
			return;
		}
		
		/* Indice de deslocamento entre as listas de termos de cada texto */
		int[] textIdx = new int[textos.size()];
		
		/* Inicializa indices na posicao zero */
		for(int i = 0; i < textIdx.length; i++) 
			textIdx[i] = 0;
	
		
		for(int texto1_idx = 0; texto1_idx < textos.size(); texto1_idx++) {
			if(textos.get(texto1_idx).getTipoText() != TipoTexto.TREINO) {
				continue;
			}
			

			for(int termo1_idx = 0; termo1_idx < textos.get(texto1_idx).getTermos().size(); termo1_idx++) {
				adicionarTermo = true;
				
				/* Verifica se nao existe na lista de termos */
				for(int termo2_idx = 0; termo2_idx < termos.size(); termo2_idx++) {
					if(termos.get(termo2_idx).getTermo().equals(textos.get(texto1_idx).getTermos().get(termo1_idx).getTermo())) {
						termos.get(termo2_idx).addRepeticao(textos.get(texto1_idx).getTermos().get(termo1_idx).getRepeticao());
						adicionarTermo = false;
						break;
					}
				}
				
				if(adicionarTermo) {
					termos.add(textos.get(texto1_idx).getTermos().get(termo1_idx));
				}
			}
		}

		Collections.sort(termos);
		list2file(termos, "output/debug/termos.txt");
				
		int termos_size = k > termos.size() ? termos.size() : k;  
		
		/* Gera lista dos k termos para arquivo */
		FileWriter outWriterTreino = new FileWriter(outfile_treino);
		PrintWriter outTreino = new PrintWriter(outWriterTreino);

		FileWriter outWriterTeste = new FileWriter(outfile_teste);
		PrintWriter outTeste = new PrintWriter(outWriterTeste);

		
		outTreino.println("@relation PROCESSAR_TEXTO");
		outTeste.println("@relation PROCESSAR_TEXTO");
		
		
		/* Imprime palavras */
		for(int j = 0; j < termos_size; j++) {
			if(termos.get(j).getTexto().getTipoText() != TipoTexto.TREINO) {
				/* Nao adiciona texto de teste e outros tipos nos atributos */
				continue;
			}
			
			outTreino.println("@attribute " + termos.get(j).getTermo().replaceAll("\\s+","_").toUpperCase() + " integer");
			outTeste.println("@attribute " + termos.get(j).getTermo().replaceAll("\\s+","_").toUpperCase() + " integer");
		}
		
		outTreino.println("@attribute classe {ESPORTE, POLICIA, PROBLEMA, TRABALHADOR}");
		outTeste.println("@attribute classe {ESPORTE, POLICIA, PROBLEMA, TRABALHADOR}");
		outTreino.println("@data");
		outTeste.println("@data");

		
		/* Percorre todos os textos e gera ARFF */
		for(Texto t : textos) {
			for(int i = 0; i < termos_size; i++) {
				if(t.temTermo(termos.get(i).getTermo())) {
					if(t.getTipoText() == TipoTexto.TREINO) {
						outTreino.print("1");
					} else if(t.getTipoText() == TipoTexto.TESTE) {
						outTeste.print("1");
					}
				} else {
					if(t.getTipoText() == TipoTexto.TREINO) {
						outTreino.print("0");
					} else if(t.getTipoText() == TipoTexto.TESTE) {
						outTeste.print("0");
					}					
				}				
				
				if(t.getTipoText() == TipoTexto.TREINO) {
					if(i < termos.size() - 1) {
						outTreino.print(",");
					}
				} else if(t.getTipoText() == TipoTexto.TESTE) {
					if(i < termos.size() - 1) {
						outTeste.print(",");
					}
				}
			}
			
			if(t.getTipoText() == TipoTexto.TREINO) {
				outTreino.println(" " + t.getCategoriaTexto());
			} else if(t.getTipoText() == TipoTexto.TESTE) {
				outTeste.println(" " + t.getCategoriaTexto());
			}
		}
		
		outWriterTreino.close();
		outWriterTeste.close();
	}
}
