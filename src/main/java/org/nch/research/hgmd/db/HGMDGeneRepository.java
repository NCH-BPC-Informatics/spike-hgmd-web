package org.nch.research.hgmd.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HGMDGeneRepository extends JpaRepository<HGMDGene, String> {
    @Query(value = "select h from HGMDGene h where " +
            "h.entrezId like :id or " +
            "h.omimId like :id or " +
            "h.geneSymbol like :id or " +
            "h.Gene_Name like :id or " +
            "h.alternateSymbols like :id " +
            "order by h.geneSymbol")
    public List<HGMDGene> findByIdsOrderByGeneSymbol(@Param("id") String id);

    public List<HGMDGene> findAllByOrderByGeneSymbol();
}