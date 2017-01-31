package org.nch.research.hgmd.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hgmd")
public class HGMD {

    @Id
    @Column(name = "Entrez_ID")
    private String entrezId;
    @Column(name = "HGMD_Accession")
    private String accession;
    @Column(name = "HGMD_Classification")
    private String classification;
    @Column(name = "OMIM_ID")
    private String omimId;
    @Column(name = "Alternate_Symbol")
    private String alternateSymbols;

    public String getEntrezId() {
        return entrezId;
    }

    public void setEntrezId(String entrezId) {
        this.entrezId = entrezId;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
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
}

/*
SELECT `hgmd`.`HGMD_Accession`,
`hgmd`.`HGMD_Classification`,
`hgmd`.`OMIM_ID`,
`hgmd`.`Number_of_Reported_Mutations`,
`hgmd`.`Gene_Symbol`,
`hgmd`.`Alternate_Symbol`,
`hgmd`.`Entrez_ID`,
`hgmd`.`Gene_Name`,
`hgmd`.`RefSeq_Transcript_ID`,
`hgmd`.`DNA_change`,
`hgmd`.`Protein_ID`,
`hgmd`.`Protein_Change`,
`hgmd`.`dbSNP`,
`hgmd`.`hg19_Coordinates`,
`hgmd`.`hg38_Coordinates`,
`hgmd`.`Disease_Association`,
`hgmd`.`Phenotype_Associations`,
`hgmd`.`PMID`,
`hgmd`.`additional_pubmed`,
`hgmd`.`full`,
`hgmd`.`polarity`,
`hgmd`.`pH`,
`hgmd`.`weight`,
`hgmd`.`hydrophob`,
`hgmd`.`hydrophil`,
`hgmd`.`sec`,
`hgmd`.`polyphen`,
`hgmd`.`LRT`,
`hgmd`.`mutationtaster`,
`hgmd`.`mutationassessor`,
`hgmd`.`fathmm`,
`hgmd`.`phylop`,
`hgmd`.`gerp_rs`,
`hgmd`.`Functional_Annotation_Comments`,
`hgmd`.`Functional_Annotation_PMID_Reference`,
`hgmd`.`Splicing_Table_Comments`,
`hgmd`.`Splicing_Table_Location`,
`hgmd`.`Mutation_Table_Comments`,
`hgmd`.`Gene_Table_Comments`
FROM `hgmd`.`hgmd`;
*/