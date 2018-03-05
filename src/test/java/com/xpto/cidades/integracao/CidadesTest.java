package com.xpto.cidades.integracao;

import com.xpto.cidades.FileUtils;
import com.xpto.cidades.dto.CidadesPorEstadoDTO;
import com.xpto.cidades.entity.Cidade;

import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

public class CidadesTest extends AbstractTest {

    @Test
    @InSequence(1)
    public void testImportarArquivoCSV() {
		File arquivo = FileUtils.lerArquivo("cidades.csv");
		MultipartFormDataOutput dataOutput = new MultipartFormDataOutput();
		dataOutput.addFormData("csv", arquivo, MediaType.MULTIPART_FORM_DATA_TYPE);
		GenericEntity<MultipartFormDataOutput> entidade = new GenericEntity<MultipartFormDataOutput>(dataOutput) {
		};

		Response response = cliente.target(url.toString()).path("cidade/importar-arquivo-csv").request()
				.post(Entity.entity(entidade, MediaType.MULTIPART_FORM_DATA_TYPE));
		assertTrue(response.getStatus() == 200);
    }
    
    @Test
    @InSequence(2)
    public void testGetCidadesDoEstado() {
        Response response = cliente.target(url.toString())
                .path("cidade/cidades-do-estado/DF")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        List<Cidade> cidades = response.readEntity(new GenericType<List<Cidade>>() {
        });

        assertTrue(cidades.size() == 1);
        assertTrue(cidades.get(0).getUf().equals("DF"));
        assertTrue(cidades.get(0).getName().equals("Braslia"));
    }

    
    @Test
    @InSequence(3)
    public void testGetQuantidadeCidadesPorEstado() {
        Response response = cliente.target(url.toString())
                .path("cidade/quantidade-cidades-por-estado")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        List<CidadesPorEstadoDTO> cidades = response.readEntity(new GenericType<List<CidadesPorEstadoDTO>>() {
        });

        assertTrue(cidades.size() == 27);
        assertTrue(cidades.get(0).getEstado().equals("RS"));
        assertTrue(cidades.get(0).getId() == 496);
    }
    
    @Test
    @InSequence(4)
    public void testSelectAll() {
        Response response = cliente.target(url.toString())
                .path("cidade/quantidade-total")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        Long count = response.readEntity(Long.class);
        assertTrue(count == 5565);
    }

    @Test
    @InSequence(5)
    public void testGetCapitais() {
        Response response = cliente.target(url.toString())
                .path("cidade/capitais")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        List<Cidade> cidades = response.readEntity(new GenericType<List<Cidade>>() {
        });
        assertTrue(cidades.size() == 27);
    }

    @Test
    @InSequence(6)
    public void testGetEstadosMaiorMenorQuantidadeCidades() {
        Response response = cliente.target(url.toString())
                .path("cidade/estado-maior-menor-quantidade-cidades")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        List<CidadesPorEstadoDTO> cidades = response.readEntity(new GenericType<List<CidadesPorEstadoDTO>>() {
        });

        assertTrue(cidades.size() == 2);
        assertTrue(cidades.get(0).getEstado().equals("MG"));
        assertTrue(cidades.get(1).getEstado().equals("DF"));
    }

    @Test
    @InSequence(7)
    public void testGetCidadeIBGE() {
        Response response = cliente.target(url.toString())
                .path("cidade/cidade-ibge-id/2300309")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        Cidade cidade = response.readEntity(Cidade.class);
        assertTrue(cidade.getName().equals("Acopiara"));
    }

    @Test
    @InSequence(8)
    public void testGetCidadesPorColuna() {
        Response response = cliente.target(url.toString())
                .path("cidade/cidades-por-coluna/mesoregion/LesTe rondoniense")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        List<Cidade> cidades = response.readEntity(new GenericType<List<Cidade>>() {
        });
        assertTrue(cidades.get(0).getUf().equals("RO"));
        assertTrue(cidades.get(0).getName().equals("Alta Floresta D'Oeste"));
    }

    @Test
    @InSequence(9)
    public void testGetQuantidadeUnicaCidadesPorColuna() {
        Response response = cliente.target(url.toString())
                .path("cidade/quantidade-cidades-por-coluna/microregion")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertTrue(response.getStatus() == 200);

        Long count = response.readEntity(Long.class);
        assertTrue(count == 554);
    }
    
	@Test
	@InSequence(10)
	public void testGetCidadesMaisDistantes() {
		Response response = cliente.target(url.toString()).path("cidade/cidades-mais-distantes")
				.request(MediaType.APPLICATION_JSON).get();

		assertTrue(response.getStatus() == 200);

		List<Cidade> cidades = response.readEntity(new GenericType<List<Cidade>>() {
		});

		assertTrue(cidades.get(0).getUf().equals("RO"));
		assertTrue(cidades.get(0).getName().equals("Alta Floresta D'Oeste"));
	}

	@Test
	@InSequence(11)
	public void testInserirCidade() {
		Response response = cliente.target(url.toString()).path("cidade").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(FileUtils.lerArquivoTexto("json/cidade.json")));
		assertTrue(response.getStatus() == 200);
	}
    
}
