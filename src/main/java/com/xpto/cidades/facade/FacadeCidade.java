package com.xpto.cidades.facade;

import com.xpto.cidades.dao.CidadeDAO;
import com.xpto.cidades.dto.CidadesPorEstadoDTO;
import com.xpto.cidades.entity.Cidade;
import com.xpto.cidades.exception.ColunaInvalidaException;
import com.xpto.cidades.utils.CSVUtils;

import javax.ejb.Stateless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class FacadeCidade extends FacadeCrud<Cidade, Long, CidadeDAO> {

	public Long getQuantidadeUnicaCidadesPorColuna(String column) {
		String attribute = CSVUtils.CSV_TO_ENTITY.get(column);
		if (attribute == null) {
			throw new ColunaInvalidaException(column);
		}
		return dao.get().getQuantidadeUnicaCidadesPorColuna(attribute);
	}

	public List<CidadesPorEstadoDTO> getQuantidadeCidadesPorEstado() {
		return dao.get().getQuantidadeDeCidadesPorEstado();
	}

	public void importCsv(InputStream inputStream) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
			List<Cidade> cidades = buffer.lines().skip(1).map((String linha) -> {
				String[] colunas = linha.split(",");
				Cidade cidade = new Cidade();
				cidade.setIbge(CSVUtils.getIntegerValue(colunas[0]));
				cidade.setUf(CSVUtils.getStringValue(colunas[1]));
				cidade.setName(CSVUtils.getStringValue(colunas[2]));
				cidade.setCapital(CSVUtils.getBooleanValue(colunas[3]));
				cidade.setLongitude(CSVUtils.getBigDecimalValue(colunas[4]));
				cidade.setLatitude(CSVUtils.getBigDecimalValue(colunas[5]));
				cidade.setNoAccents(CSVUtils.getStringValue(colunas[6]));
				cidade.setAlternativeNames(CSVUtils.getStringValue(colunas[7]));
				cidade.setMicroRegion(CSVUtils.getStringValue(colunas[8]));
				cidade.setMesoRegion(CSVUtils.getStringValue(colunas[9]));
				return cidade;
			}).collect(Collectors.toList());
			insert(cidades);
		}
	}

	public List<Cidade> getCidadesDoEstado(String estado) {
		return dao.get().getCidadesDoEstado(estado);
	}

	public List<Cidade> getCapitais() {
		return dao.get().getCapitais();
	}

	public List<CidadesPorEstadoDTO> getEstadosMaiorMenorQuantidadeCidades() {
		List<CidadesPorEstadoDTO> cidadesPorEstado = dao.get().getQuantidadeDeCidadesPorEstado();
		Optional<CidadesPorEstadoDTO> estadoMaiorQuantidadeCidade = cidadesPorEstado.stream()
				.max(Comparator.comparing(CidadesPorEstadoDTO::getId));
		Optional<CidadesPorEstadoDTO> estadoMenorQuantidadeCidade = cidadesPorEstado.stream()
				.min(Comparator.comparing(CidadesPorEstadoDTO::getId));
		return Arrays.asList(estadoMaiorQuantidadeCidade.get(), estadoMenorQuantidadeCidade.get());
	}

	public List<Cidade> getCidadesPorColuna(String coluna, String filtro) {
		String campo = CSVUtils.CSV_TO_ENTITY.get(coluna);
		if (campo == null) {
			throw new ColunaInvalidaException(coluna);
		}
		return dao.get().getCidadesPorColuna(campo, filtro);
	}
	
	public Cidade getCidadeIBGE(Integer ibge) {
		return dao.get().getCidadeIBGE(ibge);
	}

	public List<Cidade> getCidadesMaisDistantes() {
		List<Cidade> todasCidades = dao.get().getTodasCidades();

		long distanciaMaxima = 0;
		Cidade cidadeMaisDistante1 = null;
		Cidade cidadeMaisDistante2 = null;

		for (int i = 0; i < todasCidades.size() - 1; i++) {
			for (int j = i + 1; j < todasCidades.size(); j++) {
				double distancia = Math.acos(
						Math.cos(Math.toRadians(todasCidades.get(i).getLatitude().byteValue()))
								* Math.cos(Math.toRadians(todasCidades.get(j).getLatitude().byteValue()))
								* Math.cos(Math.toRadians(todasCidades.get(j).getLongitude().byteValue())
										- Math.toRadians(todasCidades.get(i).getLongitude().byteValue()))
								+ Math.sin(Math.toRadians(todasCidades.get(i).getLatitude().byteValue()))
										* Math.sin(Math.toRadians(todasCidades.get(j).getLatitude().byteValue())));
				
				if(distancia > distanciaMaxima) {
					distancia = distanciaMaxima;
					cidadeMaisDistante1 = todasCidades.get(i);
					cidadeMaisDistante1 = todasCidades.get(j);
				}
			}
		}
		
		List<Cidade> cidadesMaisDistantes = new ArrayList<Cidade>();
		cidadesMaisDistantes.add(cidadeMaisDistante1);
		cidadesMaisDistantes.add(cidadeMaisDistante2);
		
		return cidadesMaisDistantes;
	}

}
