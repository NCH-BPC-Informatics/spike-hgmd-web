select				
	am.acc_num as 'HGMD_Accession',			
	am.tag as 'HGMD_Class',			
	coalesce(am.omimid,'') as 'OMIM_ID',			
	ag.mut_total as 'Num_Reported_Mutations_per_Gene',			
	coalesce(ag.entrezid,'') as 'Entrez_ID',			
	concat('="',ag.gene,'"') as 'Gene_Symbol',			
	coalesce(ag.altsymbol,'') as 'Alternate_Symbols',			
	ag.genename as 'Gene_Name',			
	coalesce(ngd.gene_summary,' ') as 'NCBI_Gene_Summary',			
	coalesce(ag.refseq,' ') as 'RefSeq_Transcript_ID',			
	coalesce(concat('c.', am.hgvs),'') AS 'DNA_change',			
	coalesce(concat(mn.protCORE,'.', mn.protver),'') as 'RefSeq_Protein_ID',			
	coalesce(concat('p.', am.descr),'') as 'Protein_Change',           			
	coalesce(am.dbsnp,' ') as 'dbSNP',			
	coalesce(concat('chr', hg19.chromosome, ':', hg19.coordSTART, '_', hg19.coordEND),'') as 'hg19_Coordinates',              			
	coalesce(concat('chr', am.chromosome, ':', am.startcoord, '_', am.endcoord),'') as 'hg38_Coordinates',            			
	coalesce(am.disease,'') as 'Disease_Association',			
	coalesce((select group_concat(hp.phenotype separator ', ')			
		from hgmd_phenbase.hgmd_mutation as hm		
		inner join hgmd_phenbase.hgmd_phenotype as hp on hm.phen_id = hp.phen_id		
		where am.acc_num = hm.acc_num),'') as 'Phenotype_Associations',		
	coalesce(am.pmid,'') as 'PMID',			
	coalesce((select group_concat(er.pmid separator ', ')			
		from hgmd_pro.extrarefs as er		
		where am.acc_num = er.acc_num),'') as 'Additional_Pubmed',		
	coalesce(ag.comments,' ') as 'Gene_Table_Comments',			
	coalesce(am.comments,' ') as 'Mutation_Table_Comments',			
	coalesce(ac.full,' ') as 'Amino_Acid_Change', 			
	coalesce(ac.polarity,'') as 'Change_in_Polarity',			
	coalesce(concat(' ',ac.pH),'') as 'Change_in_pH',			
	coalesce(concat(' ',ac.weight),'') as 'Change_in_AA_weight',			
	coalesce(concat(' ',ac.hydrophob),'') as 'Change_in_AA_hydrophobicity',			
	coalesce(concat(' ',ac.hydrophil),'') as 'Change_in_AA_hydrophilicity',			
	coalesce(ac.sec,'') as 'Predicted_Consequence_on_Secondary_Strucure',			
	coalesce(dsfp.polyphen,'') as 'PolyPhen',			
	coalesce(dsfp.LRT,'') as 'LRT',			
	coalesce(dsfp.mutationtaster,'') as 'Mutation_Toaster',			
	coalesce(dsfp.mutationassessor,'') as 'Mutation_Assessor',			
	coalesce(dsfp.fathmm,'') as 'FATHMM',			
	coalesce(dsfp.phylop,'') as 'PhyloP',			
	coalesce(dsfp.gerp_rs,'') as 'GERP_RS',			
	coalesce((select group_concat(distinct fa.comments) from hgmd_pro.func_anotat as fa where fa.acc_num = am.acc_num),'') as 'Functional_Annotation_Comments',			
	coalesce((select group_concat(distinct fa.pmid) from hgmd_pro.func_anotat as fa where fa.acc_num = am.acc_num),'') as 'Functional_Annotation_PMID_Reference',			
	coalesce(sp.comments,' ') as 'Splicing_Table_Comments',			
	coalesce(sp.location,' ') as 'Splicing_Table_Location'			
				
From hgmd_pro.allmut as am				
	inner join hgmd_pro.allgenes as ag on ag.gene = am.gene			
		left outer join external_data.ncbi_gene_data as ngd on ag.entrezID = ngd.gene_ID		
		left outer join hgmd_pro.mutnomen as mn on am.acc_num = mn.acc_num		
		left outer join (		
			select	
				z.acc_num,
				group_concat(distinct z.id) as id,
				group_concat(distinct uniprot) as uniprot,
				group_concat(distinct polyphen) as polyphen,
				group_concat(distinct lrt) as lrt,
				group_concat(distinct mutationTaster) as mutationTaster,
				group_concat(distinct mutationAssessor) as mutationAssessor,
				group_concat(distinct FATHMM) as FATHMM,
				min(phyloP) as phyloP,
				min(GERP_RS) as GERP_RS
			from hgmd_pro.dbnsfp2 as z	
			group by z.acc_num	
                 /*				
    The dbNSFP table contains some duplicate rows for some acc_num records. 				
    This subquery nicely collapses those duplicated rows.				
    The following query will show you those raw duplicated rows.				
    ....				
    select * from dbnsfp2 as d				
    where d.acc_num in (				
     select z.acc_num 				
     from dbnsfp2 as z				
     group by z.acc_num 				
     having count(*) > 1)				
    order by acc_num;				
                 */				
                 ) as dsfp on dsfp.acc_num = am.acc_num          				
                left outer join hgmd_pro.splice as sp on am.acc_num = sp.acc_num        				
                left outer join hgmd_pro.hg19_coords as hg19 on am.acc_num = hg19.acc_num               				
                left outer join hgmd_nch.aminochange as ac on am.amino = ac.amino_change				
;				
