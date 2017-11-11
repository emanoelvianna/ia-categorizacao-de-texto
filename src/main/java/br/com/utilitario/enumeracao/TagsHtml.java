package br.com.utilitario.enumeracao;

public enum TagsHtml {
	HEAD("<head>"),
	HEAD_FIM("</head>"),
	EDIC("<edic>"),
	EDIC_FIM("</edic>"),
	META("<autor>"),
	META_FIM("</autor>"),
	BODY("<body>"),
	BODY_FIM("</body>"),
	SUBTITULO("<subtítulo>"),
	SUBTITULO_FIM("</subtítulo>"),
	TITULO("<título>"),
	TITULO_FIM("</título>");

	private String valor;

	private TagsHtml(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public static String removeHtml(String linha) {
		for (TagsHtml tag : TagsHtml.values()) {
			if (linha.toLowerCase().contains(tag.getValor())) {
				linha = linha.replace(tag.getValor(), " ");
			}
		}
		return linha;
	}
}
