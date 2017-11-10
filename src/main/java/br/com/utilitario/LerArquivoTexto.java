package br.com.utilitario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LerArquivoTexto {

	public String LerArquivoDeEsportes() {
		StringBuffer texto = new StringBuffer();
		try {
			BufferedReader info = new BufferedReader(new FileReader("textos/Esporte.txt"));
			// TODO: Tratar o arquivo de texto antes de retornar!
			String linha = info.readLine();
			while (linha != null) {
				texto.append(linha);
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
				texto.append(linha);
				linha = info.readLine();
			}
		} catch (IOException e) {
			System.out.println("Erro ao abrir arquivo de teste");
		}
		return texto.toString();
	}

}
