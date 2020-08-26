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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CellLineCollection {

	private HashMap<String, CellLine> cellLines = new HashMap<String, CellLine>();
	private ArrayList<SNP> allSNPs = new ArrayList<SNP>();
	private HashMap<String, ArrayList<SNP>> snpsByChrom = new HashMap<String, ArrayList<SNP>>();
	
	public CellLineCollection (File snpFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(snpFile));
		
		br.readLine(); // Discard header line
		
		String line;
		while ((line = br.readLine())!= null) {
			
			String [] sections = line.split("\t");
			
			String cell = sections[0];
			String id = sections[1];
			String chr = sections[2];
			int pos = Integer.parseInt(sections[3]);
			String strand = sections[5];
			char ref = sections[6].charAt(0);
			char alt = sections[7].charAt(0);
			String penetrance = sections[8];
			
			// We will stick with using bare chromosome names
			if (chr.startsWith("chr")) {
				chr = chr.substring(3);
			}
			
			if (!cellLines.containsKey(cell)) {
				addCellLine(new CellLine(cell));
			}
			SNP snp = new SNP(getLine(cell),id,chr, pos, strand, ref, alt, penetrance);			
			getLine(cell).addSNP(snp);
			allSNPs.add(snp);
			if (!snpsByChrom.containsKey(chr)) {
				snpsByChrom.put(chr,new ArrayList<SNP>());
			}
			
			snpsByChrom.get(chr).add(snp);
			
		}
		
		br.close();
		
	}
	
	public SNP [] allSNPs () {
		
		allSNPs.sort(new Comparator<SNP>() {	
			@Override
			public int compare(SNP s1, SNP s2) {
				return s1.cell().name().compareTo(s2.cell().name());
			}
		});
		
		return allSNPs.toArray(new SNP[0]);
	}
	
	public SNP [] getSNPSbyChr(String chr) {
		if (snpsByChrom.containsKey(chr)) {
			return snpsByChrom.get(chr).toArray(new SNP[0]);
		}
		else {
			return new SNP[0];
		}
	}
	
	
	public void addCellLine (CellLine line) {
		if (cellLines.containsKey(line.name())) {
			throw new IllegalStateException("Duplicate cell line name "+line.name());
		}
		
		cellLines.put(line.name(), line);
	}
	
	public CellLine [] getCellLines() {
		return cellLines.values().toArray(new CellLine[0]);
	}
	
	public CellLine getLine(String name) {
		return cellLines.get(name);
	}
	
	

	
}
