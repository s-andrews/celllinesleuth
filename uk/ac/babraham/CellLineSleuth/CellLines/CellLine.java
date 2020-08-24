package uk.ac.babraham.CellLineSleuth.CellLines;

import java.util.ArrayList;

public class CellLine {

	private String name;
	
	private ArrayList<SNP> allSNPs = new ArrayList<SNP>();	
	
	public CellLine (String name) {
		this.name = name;
	}
	
	public void addSNP (SNP snp) {
		allSNPs.add(snp);		
	}
	
	public String name () {
		return name;
	}
	
	public SNP [] getAllSNPs () {
		return allSNPs.toArray(new SNP[0]);
	}
	
}
