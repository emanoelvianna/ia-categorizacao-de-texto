package br.com.utilitario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang.math.NumberUtils;

public class DividirTexto {

	private static final String SEPARADOR = "TEXTO";
	// TODO: rever o melhor lugar para criar os arquivos de saida!
	private static final String CAMINHO_SAIDA = "textos/";
	// TODO: melhorar o nome, quem sabe deixar mais explicito a origem
	private static final String NOME_ARQUIVO = "texto";
	private static final String FORMATO = ".txt";

	/* método recebe como parametro uma lista de caminhos de arquivos */
	public void dividirTodos(ArrayList<String> arquivos) {
		StringBuffer texto = new StringBuffer();
		try {
			for (String caminhoParaAquivo : arquivos) {
				int contador = 1;
				BufferedReader info = new BufferedReader(new FileReader(caminhoParaAquivo));
				String linha = info.readLine();
				while (linha != null) {
					if (linha.contains(SEPARADOR + " " + contador)) {
						File arquivo = new File(CAMINHO_SAIDA + NOME_ARQUIVO + contador + FORMATO);
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
			}
		} catch (IOException e) {
			System.out.println("Erro ao dividir a lista de arquivos!\n");
		}
	}	

	// TODO: exemplo de como utilizar a classe, remover mais tarde!
	public static void main(String[] args) {
		ArrayList<String> arquivos = new ArrayList<String>();
		arquivos.add("textos/ParaTeste.txt");

		DividirTexto dividirTexto = new DividirTexto();
		dividirTexto.dividirTodos(arquivos);
	}

}
