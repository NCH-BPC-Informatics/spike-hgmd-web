package org.nch.research.hgmd.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "HGMD_VARIANT")
public class HGMDVariant {
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "GENE_SYMBOL")
    private String geneSymbol;
    @Column(name = "DNA_CHANGE")
    private String dnaChange;
    @Column(name = "DBSNP")
    private String dbSnp;
    @Column(name = "PROTEIN_CHANGE")
    private String proteinChange;
    @Column(name = "WEIGHT")
    private String weight;
}