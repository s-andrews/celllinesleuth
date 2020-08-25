package uk.ac.babraham.CellLineSleuth.CellLines;

public class SNPQuantitation {

	
	private SNP snp;
	private int REF = 0;
	private int ALT = 0;
	private int OTHER = 0;
	
	public SNPQuantitation (SNP snp) {
		this.snp = snp;
	}

	public SNP snp() {
		return snp;
	}
	
	public void addREF() {
		REF++;
	}
	
	public void addALT() {
		ALT++;
	}
	
	public void addOTHER() {
		OTHER++;
	}
	
	public int REF () {
		return REF;
	}
	
	public int ALT () {
		return ALT;
	}
	
	public int OTHER () {
		return OTHER;
	}
	
	public int total() {
		return REF+ALT+OTHER;
	}
}
