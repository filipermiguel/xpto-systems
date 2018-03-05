package com.xpto.cidades.dao;

import com.xpto.cidades.entity.Identidade;
import com.xpto.cidades.entity.ExclusaoLogica;

import java.io.Serializable;
import java.util.List;

public abstract class ExclusaoLogicaDAO<E extends Identidade<ID> & ExclusaoLogica, ID extends Serializable> extends CrudDAO<E, ID> {

    @Override
    public void remover(ID id) {
        E entity = getPorId(id);
        if (entity != null) {
            entity.setExcluido(true);
        }
    }

    @Override
    public List<E> selectAll() {
        return em.createQuery("SELECT e FROM " + getClassE().getSimpleName() + " e WHERE e.excluido = false ", getClassE())
                .getResultList();
    }
}
