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
