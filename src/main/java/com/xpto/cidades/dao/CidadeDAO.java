package com.xpto.cidades.dao;

import com.xpto.cidades.dto.CidadesPorEstadoDTO;
import com.xpto.cidades.entity.Cidade;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CidadeDAO extends ExclusaoLogicaDAO<Cidade, Long> {
 
    public List<Cidade> getCapitais() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cidade FROM Cidade cidade ");
        sb.append("WHERE cidade.capital = TRUE ");
        sb.append("AND").append(filtroNaoExcluido());
        sb.append("ORDER BY cidade.name ");

        TypedQuery<Cidade> query = em.createQuery(sb.toString(), Cidade.class);
        return query.getResultList();
    }
    
    public List<CidadesPorEstadoDTO> getQuantidadeDeCidadesPorEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("new ").append(CidadesPorEstadoDTO.class.getName());
        sb.append("(cidade.uf, COUNT(cidade)) ");
        sb.append("FROM Cidade cidade ");
        sb.append("WHERE").append(filtroNaoExcluido());
        sb.append("GROUP BY cidade.uf ");

        TypedQuery<CidadesPorEstadoDTO> query = em.createQuery(sb.toString(), CidadesPorEstadoDTO.class);
        return query.getResultList();
    }

    public Cidade getCidadeIBGE(Integer ibge) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cidade FROM Cidade cidade ");
        sb.append("WHERE cidade.ibge = :ibge ");
        sb.append("AND").append(filtroNaoExcluido());

        TypedQuery<Cidade> query = em.createQuery(sb.toString(), Cidade.class);
        query.setParameter("ibge", ibge);
        query.setMaxResults(1);

        return query.getSingleResult();
    }

    public List<Cidade> getCidadesDoEstado(String state) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cidade FROM Cidade cidade ");
        sb.append("WHERE cidade.uf = :uf ");
        sb.append("AND").append(filtroNaoExcluido());

        TypedQuery<Cidade> query = em.createQuery(sb.toString(), Cidade.class);
        query.setParameter("uf", state);

        return query.getResultList();
    }

    public List<Cidade> getCidadesPorColuna(String coluna, String filtro) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cidade FROM Cidade cidade ");
        sb.append("WHERE UPPER(CAST(cidade.").append(coluna).append(" AS string)) = UPPER(:filter) ");
        sb.append("AND").append(filtroNaoExcluido());

        TypedQuery<Cidade> query = em.createQuery(sb.toString(), Cidade.class);
        query.setParameter("filter", filtro);

        return query.getResultList();
    }

    public Long getQuantidadeUnicaCidadesPorColuna(String coluna) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(DISTINCT cidade.").append(coluna).append(") FROM Cidade cidade ");
        sb.append("WHERE").append(filtroNaoExcluido());

        TypedQuery<Long> query = em.createQuery(sb.toString(), Long.class);
        query.setMaxResults(1);

        return query.getSingleResult();
    }
    
    public List<Cidade> getTodasCidades() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT FROM CIDADE cidade ");
        sb.append("WHERE").append(filtroNaoExcluido());

        TypedQuery<Cidade> query = em.createQuery(sb.toString(), Cidade.class);

        return query.getResultList();
    }
    
    private String filtroNaoExcluido() {
        return " cidade.excluido = FALSE ";
    }
}
