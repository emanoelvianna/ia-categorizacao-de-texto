package br.com.utilitario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DividirTexto {

	private static final String SEPARADOR = "TEXTO";
	// TODO: rever o melhor lugar para criar os arquivos de saida!
	private static final String CAMINHO_SAIDA = "textos/saida";
	// TODO: melhorar o nome, quem sabe deixar mais explicito a origem
	private static final String NOME_ARQUIVO = "texto";
	private static final String FORMATO = ".txt";

	/* método recebe como parametro uma lista de caminhos de arquivos */
	public void dividirTodos(ArrayList<String> arquivos) {
		StringBuffer texto = new StringBuffer();
		try {
			for (String caminhoParaAquivo : arquivos) {
				int contador = 0;
				BufferedReader info = new BufferedReader(new FileReader(caminhoParaAquivo));
				String linha = info.readLine();
				while (linha != null) {
					if (linha.contains(SEPARADOR)) {
						File arquivo = new File(CAMINHO_SAIDA + NOME_ARQUIVO + contador + FORMATO);
						contador++;
						while (!linha.contains(SEPARADOR)) {
							escrever(linha, arquivo);
							linha = info.readLine();
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Erro ao dividir a lista de arquivos!\n");
		}
	}

	/* escreve a linha em um novo arquivo separado */
	private void escrever(String linha, File arquivo) {
		try {
			FileWriter grava = new FileWriter(arquivo);
			PrintWriter escreve = new PrintWriter(grava);
			escreve.println(linha);
			escreve.close();
			grava.close();
		} catch (IOException e) {
			System.out.println("Erro ao tentar escrever no arquivo!\n");
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
