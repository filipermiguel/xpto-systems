package com.xpto.cidades.servicos;

import com.xpto.cidades.dao.CidadeDAO;
import com.xpto.cidades.entity.Cidade;
import com.xpto.cidades.facade.FacadeCidade;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/cidade")
public class ServicoCidade extends ServicoCRUD<Cidade, Long, CidadeDAO, FacadeCidade> {

    @GET
    @Path("/quantidade-cidades-por-coluna/{coluna}") 
    public Response getQuantidadeUnicaCidadesPorColuna(@PathParam("coluna") String coluna) {
        return ok(servico.get().getQuantidadeUnicaCidadesPorColuna(coluna));
    }
	
    @POST
    @Path("/importar-arquivo-csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCsv(@MultipartForm MultipartFormDataInput multipartFormData) throws IOException {
        Map<String, List<InputPart>> formDataMap = multipartFormData.getFormDataMap();
        InputPart inputPart = formDataMap.get("csv").get(0);
        InputStream inputStream = inputPart.getBody(InputStream.class, null);
        servico.get().importCsv(inputStream);
        return ok();
    }

    @GET
    @Path("/capitais")
    public Response getCapitais() {
        return ok(servico.get().getCapitais());
    }
    
    @Path("/cidade-ibge-id/{ibge}")
    public Response getCidadeIBGE(@PathParam("ibge") Integer ibge) {
        return ok(servico.get().getCidadeIBGE(ibge));
    }    
        
    @GET
    @Path("/estado-maior-menor-quantidade-cidades")
    public Response getEstadosMaiorMenorQuantidadeCidades() {
        return ok(servico.get().getEstadosMaiorMenorQuantidadeCidades());
    }

    @GET
    @Path("/quantidade-cidades-por-estado")
    public Response getQuantidadeCidadesPorEstado() {
        return ok(servico.get().getQuantidadeCidadesPorEstado());
    }

    @GET
    @Path("/cidades-por-coluna/{coluna}/{filtro}")
    public Response getCidadesPorColuna(@PathParam("coluna") String coluna, @PathParam("filtro") String filtro) {
        return ok(servico.get().getCidadesPorColuna(coluna, filtro));
    }

    @GET
    @Path("/quantidade-total")
    public Response selectAll() {
        return ok(servico.get().selectAll().size());
    }
    
    @GET
    @Path("/cidades-do-estado/{estado}")
    public Response getCidadesDoEstado(@PathParam("estado") String estado) {
        return ok(servico.get().getCidadesDoEstado(estado));
    }
    
    @GET
    @Path("/cidades-mais-distantes")
    public Response getCidadesMaisDistantes() {
        return ok(servico.get().getCidadesMaisDistantes());
    }

}
