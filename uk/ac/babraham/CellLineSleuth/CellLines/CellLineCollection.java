package uk.ac.babraham.CellLineSleuth.CellLines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
			float penetrance = 1;
			
			if (sections[7].equals("het")) {
				penetrance = 0.5f;
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
