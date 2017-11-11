package br.com.principal;

import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import br.com.utilitario.LerArquivoTexto;

public class Principal {
	public static void main(String[] args) {
		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		Analyzer cogroo = factory.createPipe();

		/* processar texto */
		LerArquivoTexto leitor = new LerArquivoTexto();
		Document document = new DocumentImpl();

		/* processando texto sobre esportes */
		document.setText(leitor.LerArquivoDeEsportes());
		System.out.println(document.getText());
		cogroo.analyze(document);

		// TODO: adicionar os outros texto mais tarde!

		/* lista de sentenças */
		for (Sentence sentence : document.getSentences()) {
			for (Token token : sentence.getTokens()) {
				token.getLemmas(); // array com os possíveis lemas
				if (token.getLemmas().length != 0)
					token.getLemmas();
			}
		}
	}
}
