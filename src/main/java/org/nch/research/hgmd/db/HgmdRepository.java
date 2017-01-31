package org.nch.research.hgmd.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HgmdRepository extends JpaRepository<HGMD, String> {
    @Query(value = "select h from HGMD h where h.entrezId like :id or h.omimId like :id or h.accession like :id or h.alternateSymbols like :id")
    public List<HGMD> findByIds(@Param("id") String id);
}