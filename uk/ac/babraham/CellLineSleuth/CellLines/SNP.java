package uk.ac.babraham.CellLineSleuth.CellLines;

public class SNP {

	private CellLine cell;
	private String id;
	private String chr;
	private int pos;
	private char REF;
	private char ALT;
	private float penetrance;
	
	public SNP (CellLine cell, String id, String chr, int pos, char REF, char ALT, float penetrance) {
		this.cell = cell;
		this.id = id;
		this.chr = chr;
		this.pos = pos;
		this.REF = REF;
		this.ALT = ALT;
		this.penetrance = penetrance;
	}
	
	public CellLine cell () {
		return cell;
	}
	
	public String id () {
		return id;
	}
	
	public String chr () {
		return chr;
	}
	
	public int pos () {
		return pos;
	}
	
	public char REF () {
		return REF;
	}
	
	public char ALT () {
		return ALT;
	}
	
	public float penetrance () {
		return penetrance;
	}
	
}
