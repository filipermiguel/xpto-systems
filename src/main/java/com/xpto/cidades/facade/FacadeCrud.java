package com.xpto.cidades.facade;

import com.xpto.cidades.dao.CrudDAO;
import com.xpto.cidades.entity.Identidade;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.List;

public class FacadeCrud<E extends Identidade<ID>, ID, D extends CrudDAO<E, ID>> {

    @Inject
    protected Instance<D> dao;

    public E insert(final E entity) {
        return dao.get().insert(entity);
    }

    public void insert(final List<E> entities) {
        entities.stream().forEach(entity -> dao.get().insert(entity));
    }

    public E update(final E entity) {
        return dao.get().update(entity);
    }

    public void remove(final ID id) {
        dao.get().remover(id);
    }

    public E getPorId(final ID id) {
        return dao.get().getPorId(id);
    }

    public List<E> selectAll() {
        return dao.get().selectAll();
    }
}
