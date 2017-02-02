package org.nch.research.hgmd.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hgmd_variant")
public class HGMDVariant {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getDnaChange() {
        return dnaChange;
    }

    public void setDnaChange(String dnaChange) {
        this.dnaChange = dnaChange;
    }

    public String getDbSnp() {
        return dbSnp;
    }

    public void setDbSnp(String dbSnp) {
        this.dbSnp = dbSnp;
    }

    public String getProteinChange() {
        return proteinChange;
    }

    public void setProteinChange(String proteinChange) {
        this.proteinChange = proteinChange;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "Gene_Symbol")
    private String geneSymbol;
    @Column(name = "DNA_change")
    private String dnaChange;
    @Column(name = "dbSNP")
    private String dbSnp;
    @Column(name = "Protein_Change")
    private String proteinChange;
    @Column(name = "weight")
    private String weight;
}