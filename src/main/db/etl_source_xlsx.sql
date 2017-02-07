select				
	am.acc_num as 'HGMD Accession',			
	am.tag as 'HGMD Class.',			
	coalesce(am.omimid,'') as 'OMIM ID',			
	ag.mut_total as '# of Reported Mutations per Gene',			
	coalesce(ag.entrezid,'') as 'Entrez ID',			
	concat('="',ag.gene,'"') as 'Gene Symbol',			
	coalesce(ag.altsymbol,'') as 'Alternate Symbol(s)',			
	ag.genename as 'Gene Name',			
	coalesce(ngd.gene_summary,' ') as 'NCBI Gene Summary',			
	coalesce(ag.refseq,' ') as 'RefSeq Transcript ID',			
	coalesce(concat('c.', am.hgvs),'') AS 'DNA change',			
	coalesce(concat(mn.protCORE,'.', mn.protver),'') as 'RefSeq Protein ID',			
	coalesce(concat('p.', am.descr),'') as 'Protein Change',           			
	coalesce(am.dbsnp,' ') as 'dbSNP',			
	coalesce(concat('chr', hg19.chromosome, ':', hg19.coordSTART, '_', hg19.coordEND),'') as 'hg19 Coordinates',              			
	coalesce(concat('chr', am.chromosome, ':', am.startcoord, '_', am.endcoord),'') as 'hg38 Coordinates',            			
	coalesce(am.disease,'') as 'Disease Association',			
	coalesce((select group_concat(hp.phenotype separator ', ')			
		from hgmd_phenbase.hgmd_mutation as hm		
		inner join hgmd_phenbase.hgmd_phenotype as hp on hm.phen_id = hp.phen_id		
		where am.acc_num = hm.acc_num),'') as 'Phenotype Associations',		
	coalesce(am.pmid,'') as 'PMID',			
	coalesce((select group_concat(er.pmid separator ', ')			
		from hgmd_pro.extrarefs as er		
		where am.acc_num = er.acc_num),'') as 'Additional Pubmed',		
	coalesce(ag.comments,' ') as 'Gene Table Comments',			
	coalesce(am.comments,' ') as 'Mutation Table Comments',			
	coalesce(ac.full,' ') as 'Amino Acid Change', 			
	coalesce(ac.polarity,'') as 'Change in Polarity',			
	coalesce(concat(' ',ac.pH),'') as 'Change in pH',			
	coalesce(concat(' ',ac.weight),'') as 'Change in AA weight',			
	coalesce(concat(' ',ac.hydrophob),'') as 'Change in AA hydrophobicity',			
	coalesce(concat(' ',ac.hydrophil),'') as 'Change in AA hydrophilicity',			
	coalesce(ac.sec,'') as 'Predicted Consequence on Secondary Strucure',			
	coalesce(dsfp.polyphen,'') as 'PolyPhen',			
	coalesce(dsfp.LRT,'') as 'LRT',			
	coalesce(dsfp.mutationtaster,'') as 'Mutation Toaster',			
	coalesce(dsfp.mutationassessor,'') as 'Mutation Assessor',			
	coalesce(dsfp.fathmm,'') as 'FATHMM',			
	coalesce(dsfp.phylop,'') as 'PhyloP',			
	coalesce(dsfp.gerp_rs,'') as 'GERP_RS',			
	coalesce((select group_concat(distinct fa.comments) from hgmd_pro.func_anotat as fa where fa.acc_num = am.acc_num),'') as 'Functional Annotation Comments',			
	coalesce((select group_concat(distinct fa.pmid) from hgmd_pro.func_anotat as fa where fa.acc_num = am.acc_num),'') as 'Functional Annotation PMID Reference',			
	coalesce(sp.comments,' ') as 'Splicing Table Comments',			
	coalesce(sp.location,' ') as 'Splicing Table Location'			
				
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
