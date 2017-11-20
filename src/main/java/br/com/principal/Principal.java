package br.com.principal;

import java.io.File;
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
import br.com.utilitario.Texto;
import br.com.utilitario.enumeracao.CategoriaTexto;
import br.com.utilitario.enumeracao.Classificacao;
import br.com.utilitario.enumeracao.TipoTexto;

public class Principal {
	
	/* Processa um texto e retorna lista ordenada de termos relevantes baseado em frequencia */
	//public static List<TermoRelevante> listarTermos(Document document, int n) {
	public static List<TermoRelevante> listarTermos(Document document, Texto texto) {
		
		int n = 1; // Limpar depois 
		
		
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
						texto.addTermo(new TermoRelevante(termo, classificacao));
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
	
	/* Gera arquivo no formato do WEKA concatenando as listas */
	public static void gerarBOW(List<TermoRelevante> l1,
			List<TermoRelevante> l2,
			List<TermoRelevante> l3,
			List<TermoRelevante> l4, int k, String outfile) throws IOException {
		
		List<TermoRelevante> termos = new ArrayList<TermoRelevante>();
		FileWriter outWriter = new FileWriter(outfile);
		PrintWriter out = new PrintWriter(outWriter);

		int idx1 = 0, idx2 = 0, idx3 = 0, idx4 = 0, total = 0;
		
		while(total < k) {
			if((l1.get(idx1).getRepeticao() > l2.get(idx2).getRepeticao())
					&& (l1.get(idx1).getRepeticao() > l3.get(idx3).getRepeticao())
					&& (l1.get(idx1).getRepeticao() > l4.get(idx4).getRepeticao())) {
				
				l1.get(idx1).setCategoria(0, 1);
								
				/* Pesquisa outras listas pela palavra para categorizacao */
				for(TermoRelevante t : l2) {
					if(t.getTermo().equals((l1.get(idx1).getTermo()))) {
						l1.get(idx1).setCategoria(1, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l3) {
					if(t.getTermo().equals((l1.get(idx1).getTermo()))) {
						l1.get(idx1).setCategoria(2, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l4) {
					if(t.getTermo().equals((l1.get(idx1).getTermo()))) {
						l1.get(idx1).setCategoria(3, 1);
						break;
					}
				}
				termos.add(l1.get(idx1));
				idx1++;
			} else if((l2.get(idx2).getRepeticao() > l1.get(idx1).getRepeticao())
					&& (l2.get(idx2).getRepeticao() > l3.get(idx3).getRepeticao())
					&& (l2.get(idx2).getRepeticao() > l4.get(idx4).getRepeticao())) {
				
				l2.get(idx2).setCategoria(1, 1);
								
				/* Pesquisa outras listas pela palavra para categorizacao */
				for(TermoRelevante t : l1) {
					if(t.getTermo().equals((l2.get(idx2).getTermo()))) {
						l2.get(idx2).setCategoria(0, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l3) {
					if(t.getTermo().equals((l2.get(idx2).getTermo()))) {
						l2.get(idx2).setCategoria(2, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l4) {
					if(t.getTermo().equals((l2.get(idx2).getTermo()))) {
						l2.get(idx2).setCategoria(3, 1);
						break;
					}
				}
				termos.add(l2.get(idx2));
				idx2++;
			} else if((l3.get(idx3).getRepeticao() > l1.get(idx1).getRepeticao())
					&& (l3.get(idx3).getRepeticao() > l2.get(idx2).getRepeticao())
					&& (l3.get(idx3).getRepeticao() > l4.get(idx4).getRepeticao())) {
				
				l3.get(idx3).setCategoria(2, 1);
								
				/* Pesquisa outras listas pela palavra para categorizacao */
				for(TermoRelevante t : l1) {
					if(t.getTermo().equals((l3.get(idx3).getTermo()))) {
						l3.get(idx3).setCategoria(0, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l2) {
					if(t.getTermo().equals((l3.get(idx3).getTermo()))) {
						l3.get(idx3).setCategoria(1, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l4) {
					if(t.getTermo().equals((l3.get(idx3).getTermo()))) {
						l2.get(idx2).setCategoria(3, 1);
						break;
					}
				}
				termos.add(l3.get(idx3));
				idx3++;
			} else if((l4.get(idx4).getRepeticao() > l1.get(idx1).getRepeticao())
					&& (l4.get(idx4).getRepeticao() > l2.get(idx2).getRepeticao())
					&& (l4.get(idx4).getRepeticao() > l3.get(idx3).getRepeticao())) {
				
				l4.get(idx4).setCategoria(3, 1);
								
				/* Pesquisa outras listas pela palavra para categorizacao */
				for(TermoRelevante t : l1) {
					if(t.getTermo().equals((l4.get(idx4).getTermo()))) {
						l4.get(idx4).setCategoria(0, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l2) {
					if(t.getTermo().equals((l4.get(idx4).getTermo()))) {
						l4.get(idx4).setCategoria(1, 1);
						break;
					}
				}
				
				for(TermoRelevante t : l3) {
					if(t.getTermo().equals((l4.get(idx4).getTermo()))) {
						l4.get(idx4).setCategoria(2, 1);
						break;
					}
				}
				termos.add(l4.get(idx4));
				idx4++;
			}			
		}
		
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
	
	public static List<String> getFilesInFolder(String path) {
		File folder = new File(path);
		List<String> files = new ArrayList<String>();
		
		for(File file : folder.listFiles()) {
			if(file.isFile()) {
				files.add(file.getAbsolutePath());
			}
		}
		
		return files;
	}
	

	public static void main(String[] args) {
		List<Texto> textos = new ArrayList<Texto>();
		
		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		Analyzer cogroo = factory.createPipe();

		/* processar texto */
		LerArquivoTexto leitor = new LerArquivoTexto();
		Document document = new DocumentImpl();

		// TODO: adicionar os outros texto mais tarde!
		
		/* processando texto sobre esportes */
		//document.setText(leitor.LerArquivoDeTeste());
		//cogroo.analyze(document);

		/* Obtem lista de arquivos de treino */
		
		/* Esportes */
		List<String> files = getFilesInFolder("textos/esporte/treino");
		
		for(String filePath : files) {
			System.out.println("Carregando arquivo = " + filePath);
			document.setText(leitor.LerArquivoDeTexto(filePath));
			cogroo.analyze(document);
			
			// TODO: preprocessar termos e colocar na lista do respectivo objeto Texto
			Texto texto = new Texto(TipoTexto.TREINO, CategoriaTexto.ESPORTE);
			listarTermos(document, texto);
			textos.add(texto);
		}
		
		/* Policia */
		
		
		/* Problema */
		
		
		/* Trabalhador */
		
		
		
		
		/* Lista textos e termos */
		for(Texto t : textos) {
			System.out.println(t);
		}
		
		

		/*
		document.setText(leitor.LerArquivoDeTexto("textos/Esporte.txt"));
		cogroo.analyze(document);

		List<TermoRelevante> termosEsportes_n1 = listarTermos(document, 1);
		//List<TermoRelevante> termosEsportes_n2 = listarTermos(document, 2);
		//List<TermoRelevante> termosEsportes_n3 = listarTermos(document, 3);

		Collections.sort(termosEsportes_n1);
		//Collections.sort(termosEsportes_n2);
		//Collections.sort(termosEsportes_n3);

		
		document.setText(leitor.LerArquivoDeTexto("textos/Policia.txt"));
		cogroo.analyze(document);

		List<TermoRelevante> termosPolicia_n1 = listarTermos(document, 1);
		//List<TermoRelevante> termosPolicia_n2 = listarTermos(document, 2);
		//List<TermoRelevante> termosPolicia_n3 = listarTermos(document, 3);

		Collections.sort(termosPolicia_n1);
		//Collections.sort(termosPolicia_n2);
		//Collections.sort(termosPolicia_n3);
		

		document.setText(leitor.LerArquivoDeTexto("textos/Problema.txt"));
		cogroo.analyze(document);

		List<TermoRelevante> termosProblema_n1 = listarTermos(document, 1);
		//List<TermoRelevante> termosProblema_n2 = listarTermos(document, 2);
		//List<TermoRelevante> termosProblema_n3 = listarTermos(document, 3);

		Collections.sort(termosProblema_n1);
		//Collections.sort(termosProblema_n2);
		//Collections.sort(termosProblema_n3);


		document.setText(leitor.LerArquivoDeTexto("textos/Trabalhador.txt"));
		cogroo.analyze(document);

		List<TermoRelevante> termosTrabalhador_n1 = listarTermos(document, 1);
		//List<TermoRelevante> termosTrabalhador_n2 = listarTermos(document, 2);
		//List<TermoRelevante> termosTrabalhador_n3 = listarTermos(document, 3);

		Collections.sort(termosTrabalhador_n1);
		//Collections.sort(termosTrabalhador_n2);
		//Collections.sort(termosTrabalhador_n3);

		*/	
		
		/* lista de sentenças */
		/*
		try {
			list2file(termosEsportes_n1, "output/esportes_n1.txt");
			list2file(termosPolicia_n1, "output/policia_n1.txt");
			list2file(termosProblema_n1, "output/problema_n1.txt");
			list2file(termosTrabalhador_n1, "output/trabalhador_n1.txt");
			
			gerarBOW(termosEsportes_n1, termosPolicia_n1, 
						termosProblema_n1, termosTrabalhador_n1, 10, "output/bow_n1.arff");
		} catch(IOException e) {
			System.out.println("Falha ao escrever arquivos de saida!");
		}
		*/
	}
}
