package br.com.utilitario;

import br.com.utilitario.enumeracao.Caracteres;
import br.com.utilitario.enumeracao.TagsHtml;

public class LimparArquivoTexto {

	public String removerTagsHtml(String linha) {
		return TagsHtml.removeHtml(linha);
	}

	public String removerCaracteres(String linha) {
		StringBuffer novaLinha = new StringBuffer(linha);
		int tamanho = linha.length();

		for (int i = 0; i < tamanho; i++) {
			/* verificar se é um número */
			if (!(novaLinha.charAt(i) >= 48 && novaLinha.charAt(i) <= 57)) {
				/* verificar se é um caractere minusculo */
				if (!(novaLinha.charAt(i) >= 97 && novaLinha.charAt(i) <= 122)) {
					/* verifica se é um caractere maiusculo */
					if (!(novaLinha.charAt(i) >= 65 && novaLinha.charAt(i) <= 90)) {
						/* aceitando algumas acentuações mais utilizadas */
						if (!(novaLinha.charAt(i) >= 192 && novaLinha.charAt(i) <= 197
								|| novaLinha.charAt(i) >= 199 && novaLinha.charAt(i) <= 207
								|| novaLinha.charAt(i) >= 209 && novaLinha.charAt(i) <= 214
								|| novaLinha.charAt(i) >= 217 && novaLinha.charAt(i) <= 221
								|| novaLinha.charAt(i) >= 224 && novaLinha.charAt(i) <= 228
								|| novaLinha.charAt(i) >= 231 && novaLinha.charAt(i) <= 239
								|| novaLinha.charAt(i) >= 241 && novaLinha.charAt(i) <= 246
								|| novaLinha.charAt(i) >= 249 && novaLinha.charAt(i) <= 253
								|| novaLinha.charAt(i) == 255 || novaLinha.charAt(i) == 256)) {

							/* substitui caractere por um espaço em branco */
							novaLinha.setCharAt(i, ' ');

						}
					}
				}
			}
		}
		// return Caracteres.removeCaracteres(linha);
		return novaLinha.toString();
	}

}
