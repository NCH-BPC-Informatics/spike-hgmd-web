package org.nch.research.hgmd.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HGMDVariantRepository extends JpaRepository<HGMDVariant, Long> {
    @Query(value = "select h from HGMDVariant h where h.geneSymbol = :id order by h.geneSymbol, h.dnaChange")
    public List<HGMDVariant> findById(@Param("id") String id);
}