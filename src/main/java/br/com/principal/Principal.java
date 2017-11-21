package br.com.principal;

import java.util.ArrayList;
import java.util.List;

import br.com.utilitario.LerArquivoTexto;
import br.com.utilitario.ProcessarTexto;
import br.com.utilitario.Texto;
import br.com.utilitario.enumeracao.CategoriaTexto;
import br.com.utilitario.enumeracao.TipoTexto;


public class Principal {
	
	public static void main(String[] args) {
		List<Texto> textos = new ArrayList<Texto>();
		LerArquivoTexto lerArquivo = new LerArquivoTexto();
		
		/* Obtem lista de arquivos por aplicacao (treino/teste) */
		//textos.addAll(lerArquivo.carregarTextos("textos/teste/treino", TipoTexto.TREINO, CategoriaTexto.POLICIA));
		textos.addAll(lerArquivo.carregarTextos("textos/esporte/treino", TipoTexto.TREINO, CategoriaTexto.ESPORTE));
		textos.addAll(lerArquivo.carregarTextos("textos/policia/treino", TipoTexto.TREINO, CategoriaTexto.POLICIA));
		textos.addAll(lerArquivo.carregarTextos("textos/problema/treino", TipoTexto.TREINO, CategoriaTexto.PROBLEMA));
		//textos.addAll(lerArquivo.carregarTextos("textos/trabalhador/treino", TipoTexto.TREINO, CategoriaTexto.TRABALHADOR));

		/* Textos de testes */
		//textos.addAll(lerArquivo.carregarTextos("textos/teste/teste", TipoTexto.TESTE, CategoriaTexto.POLICIA));
		textos.addAll(lerArquivo.carregarTextos("textos/esporte/teste", TipoTexto.TESTE, CategoriaTexto.ESPORTE));
		textos.addAll(lerArquivo.carregarTextos("textos/policia/teste", TipoTexto.TESTE, CategoriaTexto.POLICIA));
		textos.addAll(lerArquivo.carregarTextos("textos/problema/teste", TipoTexto.TESTE, CategoriaTexto.PROBLEMA));
		//textos.addAll(lerArquivo.carregarTextos("textos/trabalhador/teste", TipoTexto.TESTE, CategoriaTexto.TRABALHADOR));

		try {
			ProcessarTexto.gerarBOW(textos, 120, "output/treino.arff", "output/teste.arff");
		} catch(Exception e) {
			System.out.println("Falha ao criar arquivo de saida do BOW!");
		}
	}
}
