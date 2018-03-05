package com.xpto.cidades;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

	private FileUtils() {

	}

	public static File lerArquivo(String nomeArquivo) {
		ClassLoader classLoader = new FileUtils().getClass().getClassLoader();
		return new File(classLoader.getResource(nomeArquivo).getFile());
	}

    public static String lerArquivoTexto(String nomeArquivo) {
        try {
            ClassLoader classLoader = new FileUtils().getClass().getClassLoader();
            File arquivo = new File(classLoader.getResource(nomeArquivo).getFile());
            return new String(Files.readAllBytes(arquivo.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}