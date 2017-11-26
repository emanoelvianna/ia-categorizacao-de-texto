package br.com.utilitario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.impl.DocumentImpl;

import br.com.utilitario.enumeracao.CategoriaTexto;
import br.com.utilitario.enumeracao.TipoTexto;

public class LerArquivoTexto {

	private LimparArquivoTexto limparArquivoTexto;
	private Document document; 

	public LerArquivoTexto() {
		limparArquivoTexto = new LimparArquivoTexto();
		document = new DocumentImpl();
	}

	
	private String LerArquivoDeTexto(String arquivo) {
		StringBuffer texto = new StringBuffer();
		
		try {
			BufferedReader info = new BufferedReader(new FileReader(arquivo));
			String linha = info.readLine();
			// TODO: Tratar o arquivo de texto antes de retornar!
			while (linha != null) {
				texto.append(limparLinha(linha));
				linha = info.readLine();
			}
			info.close();
		} catch (IOException e) {
			System.out.println("Erro ao abrir arquivo " + arquivo);
		} finally {
			
		}
		return texto.toString();
	}
	
	
	private List<String> getFilesInFolder(String path) {
		File folder = new File(path);
		List<String> files = new ArrayList<String>();
		
		for(File file : folder.listFiles()) {
			if(file.isFile()) {
				files.add(file.getAbsolutePath());
			}
		}
		
		return files;
	}
	
	public List<Texto> carregarTextos(String path, TipoTexto tipoTexto, CategoriaTexto categoriaTexto, int n) {
		List<String> files = getFilesInFolder(path);
		List<Texto> textos = new ArrayList<Texto>();
		
		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		
		
		for(String filePath : files) {
			document = new DocumentImpl();
			Analyzer cogroo = factory.createPipe();
			
			System.out.println("Carregando arquivo = " + filePath);
			
			document.setText(LerArquivoDeTexto(filePath));
			cogroo.analyze(document);
			
			Texto texto = new Texto(tipoTexto, categoriaTexto);
			ProcessarTexto.listarTermos(document, texto, n);
			textos.add(texto);
		}
		return textos;
	}
	
	
	public String limparLinha(String linha) {
		linha = limparArquivoTexto.removerTagsHtml(linha);
		linha = limparArquivoTexto.removerCaracteres(linha);
		return linha;
	}

}
