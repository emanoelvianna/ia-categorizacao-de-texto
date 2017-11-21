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
		
		/* Obtem lista de arquivos de treino */		
		/* Esportes */
		//textos.addAll(lerArquivo.carregarTextos("textos/teste", TipoTexto.TREINO, CategoriaTexto.TESTE));
		textos.addAll(lerArquivo.carregarTextos("textos/esporte/treino", TipoTexto.TREINO, CategoriaTexto.ESPORTE));
		textos.addAll(lerArquivo.carregarTextos("textos/policia/treino", TipoTexto.TREINO, CategoriaTexto.POLICIA));
		textos.addAll(lerArquivo.carregarTextos("textos/problema/treino", TipoTexto.TREINO, CategoriaTexto.PROBLEMA));
		//textos.addAll(lerArquivo.carregarTextos("textos/trabalhador/treino", TipoTexto.TREINO, CategoriaTexto.TRABALHADOR));
		
		System.out.println("Textos size = " + textos.size());
		
		try {
			ProcessarTexto.gerarBOW(textos, 80, "output/bow.txt");
		} catch(Exception e) {
			System.out.println("Falha ao criar arquivo de saida do BOW!");
		}
		
		
		//for(Texto t : textos)
		//	System.out.println(t);
	}
}
