package br.com.principal;

import java.util.ArrayList;
import java.util.List;

import br.com.utilitario.LerArquivoTexto;
import br.com.utilitario.ProcessarTexto;
import br.com.utilitario.Texto;
import br.com.utilitario.enumeracao.CategoriaTexto;
import br.com.utilitario.enumeracao.TipoTexto;


public class Principal {
	private static int k;
	private static int n;
	
	public static void main(String[] args) {
		List<Texto> textos = new ArrayList<Texto>();
		LerArquivoTexto lerArquivo = new LerArquivoTexto();
		
		for(int i = 2; i < 4; i++) {
			n = i;
			
			for(int j = 25; j < 401; j *= 2) {
				k = j;

				textos.clear();
				
				/* Obtem lista de arquivos por aplicacao (treino/teste) */
//				textos.addAll(lerArquivo.carregarTextos("textos/teste/treino", TipoTexto.TREINO, CategoriaTexto.ESPORTE, n));
				textos.addAll(lerArquivo.carregarTextos("textos/esporte/treino", TipoTexto.TREINO, CategoriaTexto.ESPORTE, n));
				textos.addAll(lerArquivo.carregarTextos("textos/policia/treino", TipoTexto.TREINO, CategoriaTexto.POLICIA, n));
				textos.addAll(lerArquivo.carregarTextos("textos/problema/treino", TipoTexto.TREINO, CategoriaTexto.PROBLEMA, n));
				textos.addAll(lerArquivo.carregarTextos("textos/trabalhador/treino", TipoTexto.TREINO, CategoriaTexto.TRABALHADOR, n));
							
				/* Textos de testes */
				textos.addAll(lerArquivo.carregarTextos("textos/esporte/teste", TipoTexto.TESTE, CategoriaTexto.ESPORTE, n));
				textos.addAll(lerArquivo.carregarTextos("textos/policia/teste", TipoTexto.TESTE, CategoriaTexto.POLICIA, n));
				textos.addAll(lerArquivo.carregarTextos("textos/problema/teste", TipoTexto.TESTE, CategoriaTexto.PROBLEMA, n));
				textos.addAll(lerArquivo.carregarTextos("textos/trabalhador/teste", TipoTexto.TESTE, CategoriaTexto.TRABALHADOR, n));
			
				try {
					ProcessarTexto.gerarBOW(textos, k, "output/treino_n" + n + "_k" + k + ".arff", "output/teste_n" + n + "_k" + k + ".arff");
				} catch(Exception e) {
					System.out.println("Falha ao criar arquivo de saida do BOW!");
					e.printStackTrace();
				}
			}
		}
	}
}
