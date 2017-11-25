package br.com.utilitario;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LimparArquivoTextoTest {

	private LimparArquivoTexto limparArquivoTexto;

	@Before
	public void construtor() {
		limparArquivoTexto = new LimparArquivoTexto();
	}

	@Test
	public void deve_remover_somente_caracteres_diferentes_de_numeros() {
		String linha = "#34'";
		String resultado = limparArquivoTexto.removerCaracteres(linha);
		Assert.assertEquals(" 34 ", resultado);
	}

	@Test
	public void deve_remover_somente_caracteres_diferentes_de_letras() {
		String linha = "#hoje'";
		String resultado = limparArquivoTexto.removerCaracteres(linha);
		Assert.assertEquals(" hoje ", resultado);
	}

	@Test
	public void nao_deve_modificar_a_frase() {
		String frase = "Hoje o dia está lindo";
		String resultado = limparArquivoTexto.removerCaracteres(frase);
		Assert.assertEquals(frase, resultado);
	}
}
