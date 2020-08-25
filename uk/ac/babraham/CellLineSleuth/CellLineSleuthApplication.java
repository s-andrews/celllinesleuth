package uk.ac.babraham.CellLineSleuth;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import uk.ac.babraham.CellLineSleuth.CellLines.CellLineCollection;
import uk.ac.babraham.CellLineSleuth.CellLines.SNP;
import uk.ac.babraham.CellLineSleuth.CellLines.SNPQuantitation;
import uk.ac.babraham.CellLineSleuth.Sample.SleuthSample;

public class CellLineSleuthApplication {

	public static boolean QUIET = false;
	
	private CellLineCollection cells;
	
	public CellLineSleuthApplication (File snpFile, File bamFile, File outFile) {
		
		if (System.getProperty("quiet").equals("true")) {
			CellLineSleuthApplication.QUIET = true;
		}
		
		if (!CellLineSleuthApplication.QUIET) {
			System.err.println("Parsing SNPS from "+snpFile.getName());
		}
		
		try {
			cells = new CellLineCollection(snpFile);
		}
		catch (IOException ioe) {
			System.err.println("Failed to parse SNPs from "+snpFile+" "+ioe.getLocalizedMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
		
		if (!CellLineSleuthApplication.QUIET) {
			System.err.println("Parsed "+cells.allSNPs().length+" SNPs");
		}
		
		if (!CellLineSleuthApplication.QUIET) {
			System.err.println("Reading data from "+bamFile.getName());
		}
		
		SleuthSample sample = new SleuthSample(bamFile);
		sample.quantitateSNPs(cells);

		if (!CellLineSleuthApplication.QUIET) {
			System.err.println("Writing output to "+outFile.getName());
		}
		
		try {
			PrintWriter pr = new PrintWriter(outFile);

			pr.println(String.join("\t", new String [] {"LINE","ID","CHR","POS","ZYG","REF","ALT","REFCOUNT","ALTCOUNT","OTHERCOUNT"}));

			// Report on the SNPS from the different lines
			for (SNP snp : cells.allSNPs()) {
				SNPQuantitation quant = sample.getQuantitationForSNP(snp);
				
				if (quant.total() == 0) continue;
				
				String [] lineData = new String [] {snp.cell().name(),snp.id(),snp.chr(),""+snp.pos(),snp.penetrance(),""+snp.REF(),""+snp.ALT(),""+quant.REF(),""+quant.ALT(),""+quant.OTHER()};
				
				pr.println(String.join("\t", lineData));
				
				
			}	
			
			pr.close();
		}
		catch (IOException ioe) {
			System.err.println("Failed to write to "+outFile.getName()+": "+ioe.getLocalizedMessage());
			
			ioe.printStackTrace();
			System.exit(2);
		}
		
	}
	
	public static void main(String[] args) {
		new CellLineSleuthApplication(new File(args[0]), new File(args[1]), new File(args[2]));
	}

}
