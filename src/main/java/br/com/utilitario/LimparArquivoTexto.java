package br.com.utilitario;

import br.com.utilitario.enumeracao.Caracteres;
import br.com.utilitario.enumeracao.TagsHtml;

public class LimparArquivoTexto {

	public String removerTagsHtml(String linha) {
		return TagsHtml.removeHtml(linha);
	}

	public String removerCaracteres(String linha) {
		return Caracteres.removeCaracteres(linha);
	}

}
