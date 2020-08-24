package uk.ac.babraham.CellLineSleuth.Sample;

import java.io.File;
import java.util.HashMap;

import htsjdk.samtools.AlignmentBlock;
import htsjdk.samtools.SAMRecord;
import htsjdk.samtools.SAMRecordIterator;
import htsjdk.samtools.SamReader;
import htsjdk.samtools.SamReaderFactory;
import htsjdk.samtools.ValidationStringency;
import uk.ac.babraham.CellLineSleuth.CellLines.CellLineCollection;
import uk.ac.babraham.CellLineSleuth.CellLines.SNP;
import uk.ac.babraham.CellLineSleuth.CellLines.SNPQuantitation;

public class SleuthSample {
	
	private File bamFile;
	private HashMap<SNP, SNPQuantitation> quantitation = new HashMap<SNP, SNPQuantitation>();
	
	public SleuthSample (File bamFile) {
		this.bamFile = bamFile;
	}
	
	public void quantitateSNPs (CellLineCollection cells) {

		// Fill out the quantitation with blank entries
		for (SNP s : cells.allSNPs()) {
			quantitation.put(s, new SNPQuantitation(s));
		}
		
		SamReader sam = SamReaderFactory.makeDefault().validationStringency(ValidationStringency.SILENT).open(bamFile);
		SAMRecordIterator sam_it = sam.iterator();
		
		int count = 0;
		
		while (sam_it.hasNext()) {
		
			++count;
			
			if (count % 1000000 == 0) {
				System.err.println("Read "+count+" alignments");
			}
			
			SAMRecord r = sam_it.next();
			int start = r.getAlignmentStart();
			int end = r.getAlignmentEnd();
			
			String chr = r.getReferenceName();
			
			SNP [] snps = cells.getSNPSbyChr(chr);
			
			for (SNP s : snps) {
				if (s.pos() < start || s.pos() > end) continue;
				
				// Find the base where the SNP overlaps
				for (AlignmentBlock b : r.getAlignmentBlocks()) {
					if (b.getReferenceStart() > s.pos() || b.getReferenceStart()+(b.getLength()-1) < s.pos()) continue;
					
//					System.out.println("Block start="+b.getReferenceStart()+" length="+b.getLength()+" Read start="+b.getReadStart()+" Pos="+s.pos());
					
					// We're in the right block, so get the base
					int basePosition = (b.getReadStart() + (s.pos()-b.getReferenceStart()))-1;
					
					// We can get away with this type of casting because we're only ever going to 
					// hit ASCII text in the base calls so we don't have to care about encoding
					char actual = (char)(r.getReadBases()[basePosition]);
					
					if (actual == s.REF()) {
						quantitation.get(s).addREF();
					}
					else if (actual == s.ALT()) {
						quantitation.get(s).addALT();
					}
					else {
						quantitation.get(s).addOTHER();
					}
				}
			}			
		}
	}
	
	public SNPQuantitation getQuantitationForSNP (SNP snp) {
		return quantitation.get(snp);
	}
	
	
}
