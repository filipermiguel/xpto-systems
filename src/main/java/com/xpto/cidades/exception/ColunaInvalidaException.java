package com.xpto.cidades.exception;

public class ColunaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String coluna;

    public ColunaInvalidaException(String coluna) {
        super();
        this.coluna = coluna;
    }

    public String getColuna() {
        return coluna;
    }
}
