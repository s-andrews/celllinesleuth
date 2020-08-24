package uk.ac.babraham.CellLineSleuth.CellLines;

import java.util.HashMap;

public class SNP {

	private CellLine cell;
	private String id;
	private String chr;
	private int pos;
	private String strand;
	private char REF;
	private char ALT;
	private float penetrance;
	
	private static HashMap<Character, Character> revcomp = SNP.makeRevcomp();

	
	public SNP (CellLine cell, String id, String chr, int pos, String strand, char REF, char ALT, float penetrance) {
		this.cell = cell;
		this.id = id;
		this.chr = chr;
		this.strand = strand;
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
		if (strand.equals("-")) {
			return revcomp.get(REF);
		}
		return REF;
	}
	
	public char ALT () {
		if (strand.equals("-")) {
			return revcomp.get(ALT);
		}
		return ALT;
	}
	
	public float penetrance () {
		return penetrance;
	}
	
	private static HashMap<Character, Character> makeRevcomp(){
		
		HashMap<Character, Character> revcomp = new HashMap<Character, Character>();
		
		revcomp.put('G','C');
		revcomp.put('C','G');
		revcomp.put('A','T');
		revcomp.put('T','A');
		
		return(revcomp);
		
	}	
	
}
