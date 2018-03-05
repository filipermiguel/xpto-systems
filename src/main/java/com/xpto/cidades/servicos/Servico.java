package com.xpto.cidades.servicos;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class Servico {

    public Response ok() {
        return Response.ok().build();
    }

    public Response ok(Object o) {
        return Response.ok(o).build();
    }

    public Response naoEncontrado() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response criado(Object o) {
        return Response.status(Response.Status.CREATED).build();
    }
}
