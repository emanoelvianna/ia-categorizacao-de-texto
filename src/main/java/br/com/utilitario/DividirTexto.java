package br.com.utilitario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class DividirTexto {
	private static final String SEPARADOR = "TEXTO";
	// TODO: melhorar o nome, quem sabe deixar mais explicito a origem
	private static final String FORMATO = ".txt";

	/* método recebe como parametro uma lista de caminhos de arquivos */
	public void dividirTodos(String nomeArquivo, String caminhoSaida) {
//		StringBuffer texto = new StringBuffer();
		int contador = 1;
		try {			
			BufferedReader info = new BufferedReader(new FileReader(nomeArquivo));
			String linha = info.readLine();
			
			System.out.println("Lendo arquivo " + nomeArquivo);

			while (linha != null) {
				if (linha.contains(SEPARADOR + " " + contador)) {
					File fileArquivo = new File(nomeArquivo);
					
					nomeArquivo = caminhoSaida + fileArquivo.getName().toString().substring(0, fileArquivo.getName().toString().length() - 4) + contador + FORMATO;
					System.out.println("** Gerando arquivo " + nomeArquivo + "[" + contador + "]");
					
					File arquivo = new File(nomeArquivo);
					FileWriter grava = new FileWriter(arquivo);
					PrintWriter escreve = new PrintWriter(grava);
					contador++;
					/* ignorando a linha com o separador */
					linha = info.readLine();
					if (linha == null)
						break;
					while (!linha.contains(SEPARADOR + " " + contador)) {
						/* escreve a linha em um novo arquivo separado */
						escreve.println(linha);
						linha = info.readLine();
						if (linha == null)
							break;
					}
					escreve.close();
					grava.close();
				}
			}
			info.close();
		} catch (IOException e) {
			System.out.println("Erro ao dividir a lista de arquivos!\n");
			e.printStackTrace();
		}
	}	
}
