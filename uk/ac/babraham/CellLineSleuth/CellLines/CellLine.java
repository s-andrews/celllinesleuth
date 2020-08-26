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
