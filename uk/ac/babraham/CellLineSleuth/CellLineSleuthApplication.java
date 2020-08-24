package uk.ac.babraham.CellLineSleuth;

import java.io.File;
import java.io.IOException;

import uk.ac.babraham.CellLineSleuth.CellLines.CellLineCollection;
import uk.ac.babraham.CellLineSleuth.CellLines.SNP;
import uk.ac.babraham.CellLineSleuth.CellLines.SNPQuantitation;
import uk.ac.babraham.CellLineSleuth.Sample.SleuthSample;

public class CellLineSleuthApplication {

	CellLineCollection cells;
	
	public CellLineSleuthApplication (File snpFile, File bamFile) {
		System.err.println("Parsing SNPS from "+snpFile.getName());
		try {
			cells = new CellLineCollection(snpFile);
		}
		catch (IOException ioe) {
			System.err.println("Failed to parse SNPs from "+snpFile+" "+ioe.getLocalizedMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
		
		System.err.println("Parsed "+cells.allSNPs().length+" SNPs");
		
		System.err.println("Reading data from "+bamFile.getName());
		SleuthSample sample = new SleuthSample(bamFile);
		sample.quantitateSNPs(cells);
		
		
		// Report on the SNPS from the different lines
		for (SNP snp : cells.allSNPs()) {
			SNPQuantitation quant = sample.getQuantitationForSNP(snp);
			
			String [] lineData = new String [] {snp.cell().name(),snp.chr(),""+snp.pos(),""+snp.REF(),""+snp.ALT(),""+quant.REF(),""+quant.ALT(),""+quant.OTHER()};
			
			System.out.println(String.join("\t", lineData));
			
			
		}
		
		
	}
	
	public static void main(String[] args) {
		new CellLineSleuthApplication(new File(args[0]), new File(args[1]));
	}

}
