package br.com.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import br.com.utilitario.LerArquivoTexto;
import br.com.utilitario.TermoRelevante;

public class Principal {

	public static void main(String[] args) {
		List<TermoRelevante> maisRelevantes = new ArrayList<TermoRelevante>();

		/* configurações de idioma */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
		Analyzer cogroo = factory.createPipe();

		/* processar texto */
		LerArquivoTexto leitor = new LerArquivoTexto();
		Document document = new DocumentImpl();

		// TODO: adicionar os outros texto mais tarde!
		
		/* processando texto sobre esportes */
		// document.setText(leitor.LerArquivoDeEsportes());
		document.setText(leitor.LerArquivoDeTeste());
		cogroo.analyze(document);

		/* lista de sentenças */
		for (Sentence sentence : document.getSentences()) {
			for (Token token : sentence.getTokens()) {
				token.getLemmas(); // array com os possíveis lemas
				if (token.getLemmas().length != 0) {
					/* classificar os termos mais relevantes */
					if (!maisRelevantes.contains(token.getPOSTag())) {
						TermoRelevante termo = new TermoRelevante();
						termo.setTermo(token.getPOSTag());
						maisRelevantes.add(termo);
					} else {
						int indexOf = maisRelevantes.indexOf(token.getPOSTag());
						maisRelevantes.get(indexOf).repetiuTermo();
					}
				}
			}
		}
	}
}
