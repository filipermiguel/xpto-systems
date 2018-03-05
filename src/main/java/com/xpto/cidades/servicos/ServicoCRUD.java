package com.xpto.cidades.servicos;

import com.xpto.cidades.dao.CrudDAO;
import com.xpto.cidades.entity.Identidade;
import com.xpto.cidades.facade.FacadeCrud;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class ServicoCRUD<E extends Identidade<ID>, ID, D extends CrudDAO<E, ID>, S extends FacadeCrud<E, ID, D>> extends Servico {

    @Inject
    Instance<S> servico;

    @POST
    public Response insert(E entity) {
        return ok(servico.get().insert(entity));
    }

    @PUT
    public Response update(E entity) {
        return ok(servico.get().update(entity));
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") ID id) {
        return ok(servico.get().getPorId(id));
    }

    @GET
    public Response selectAll() {
        return ok(servico.get().selectAll());
    }

    @DELETE
    @Path("/{id}")
    public Response removerPorId(@PathParam("id") ID id) {
        servico.get().remove(id);
        return ok();
    }
}
