package br.com.utilitario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivoTexto {

	private LimparArquivoTexto limparArquivoTexto;

	public LerArquivoTexto() {
		this.limparArquivoTexto = new LimparArquivoTexto();
	}

	
	public String LerArquivoDeTexto(String arquivo) {
		StringBuffer texto = new StringBuffer();
		
		try {
			BufferedReader info = new BufferedReader(new FileReader(arquivo));
			String linha = info.readLine();
			// TODO: Tratar o arquivo de texto antes de retornar!
			while (linha != null) {
				texto.append(this.limparLinha(linha));
				linha = info.readLine();
			}
			info.close();
		} catch (IOException e) {
			System.out.println("Erro ao abrir arquivo de esportes");
		} finally {
			
		}
		return texto.toString();
	}

	
	
	public String LerArquivoDeEsportes() {
		StringBuffer texto = new StringBuffer();
		try {
			BufferedReader info = new BufferedReader(new FileReader("textos/Esporte.txt"));
			String linha = info.readLine();
			// TODO: Tratar o arquivo de texto antes de retornar!
			while (linha != null) {
				texto.append(this.limparLinha(linha));
				linha = info.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir arquivo de esportes");
		}
		return texto.toString();
	}

	public String LerArquivoDeTeste() {
		StringBuffer texto = new StringBuffer();
		try {
			BufferedReader info = new BufferedReader(new FileReader("textos/ParaTeste.txt"));
			String linha = info.readLine();
			while (linha != null) {
				texto.append(this.limparLinha(linha));
				linha = info.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir arquivo de teste");
		}
		return texto.toString();
	}

	public String limparLinha(String linha) {
		linha = this.limparArquivoTexto.removerTagsHtml(linha);
		linha = this.limparArquivoTexto.removerCaracteres(linha);
		return linha;
	}

}
