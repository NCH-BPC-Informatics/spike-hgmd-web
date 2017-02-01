package org.nch.research.hgmd.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hgmd_gene")
public class HGMDGene {

    public String getEntrezId() {
        return entrezId;
    }

    public void setEntrezId(String entrezId) {
        this.entrezId = entrezId;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String gene_Symbol) {
        geneSymbol = gene_Symbol;
    }

    public String getGene_Name() {
        return Gene_Name;
    }

    public void setGene_Name(String gene_Name) {
        Gene_Name = gene_Name;
    }

    public String getOmimId() {
        return omimId;
    }

    public void setOmimId(String omimId) {
        this.omimId = omimId;
    }

    public String getAlternateSymbols() {
        return alternateSymbols;
    }

    public void setAlternateSymbols(String alternateSymbols) {
        this.alternateSymbols = alternateSymbols;
    }

    public Integer getNumber_of_Reported_Mutations() {
        return Number_of_Reported_Mutations;
    }

    public void setNumber_of_Reported_Mutations(Integer number_of_Reported_Mutations) {
        Number_of_Reported_Mutations = number_of_Reported_Mutations;
    }

    public Integer getNumber_of_variants_avail() {
        return Number_of_variants_avail;
    }

    public void setNumber_of_variants_avail(Integer number_of_variants_avail) {
        Number_of_variants_avail = number_of_variants_avail;
    }

    @Id
    @Column(name = "Gene_Symbol")
    private String geneSymbol;
    @Column(name = "Entrez_ID")
    private String entrezId;
    @Column(name = "Gene_Name")
    private String Gene_Name;
    @Column(name = "OMIM_ID")
    private String omimId;
    @Column(name = "Alternate_Symbol")
    private String alternateSymbols;
    @Column(name = "Number_of_Reported_Mutations")
    private Integer Number_of_Reported_Mutations;
    @Column(name = "Number_of_variants_avail")
    private Integer Number_of_variants_avail;
}