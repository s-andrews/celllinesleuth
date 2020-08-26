/**
 * Copyright Copyright 2020 Simon Andrews
 *
 *    This file is part of Cell Line Sleuth.
 *
 *    Cell Line Sleuth is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    FastQC is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with Cell Line Sleuth; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
	private String penetrance;
	
	private static HashMap<Character, Character> revcomp = SNP.makeRevcomp();

	
	public SNP (CellLine cell, String id, String chr, int pos, String strand, char REF, char ALT, String penetrance) {
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
	
	public String penetrance () {
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
