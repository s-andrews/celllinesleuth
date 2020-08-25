#!/usr/bin/env python3

#############################################################################
#    Copyright 2020 Simon Andrews
#
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <https://www.gnu.org/licenses/>.
#
############################################################################


VERSION = 1.0

import sys
import argparse

def process_file(options):

    file = options.cosmicfile
    outfile = options.outfile

    interesting_cell_lines = options.cells.split(",")

    mutations = {}
    mutation_details = {}

    line_count = 0
    with open(file) as fh:
        for line in fh:

            line_count += 1

            if line_count % 100000 == 0 and not options.quiet:
                print (f"Read {line_count} lines", flush=True)

            if line.startswith("Gene name"):
                continue

            sections = line.strip().split("\t")
            # Skip it if there's no position
            if len(sections[25]) == 0:
                continue

#            for i in range(len(sections)):
#                print(f"{i} {sections[i]}")
#            break


            cell_line = sections[4]
            mutation_type = sections[21]
            zygosity = sections[22]
            snp_id = sections[16]
            (chrom,position) = sections[25].split(":")
            (start,end) = position.split("-")
            strand = sections[26]
            snp = sections[19]
            ref = snp[-3]
            alt = snp[-1]

            if not cell_line in interesting_cell_lines:
                continue

            if not mutation_type.startswith("Substitution"):
                continue

            if not snp_id in mutations:
                mutations[snp_id] = []

            mutations[snp_id].append(cell_line)

            if not snp_id in mutation_details:
                mutation_details[snp_id] = {"id": snp_id, "chr":chrom, "start":start, "end":end, "strand": strand, "ref":ref, "alt":alt, "zygosity":zygosity}
    
    per_line_count = {}
    with open (outfile,"w") as out:
        headers = ["LINE","ID","CHR","START","END","STRAND","REF","ALT","ZYGOSITY"]
        out.write("\t".join(headers))
        out.write("\n")

        for snp in mutations.keys():
            if len(mutations[snp]) == 1:
                cell_line = mutations[snp][0]
                if cell_line not in per_line_count:
                    per_line_count[cell_line] = 0
                
                per_line_count[cell_line] += 1

                details = mutation_details[snp]

                out.write(cell_line)
                out.write("\t")
                out.write("\t".join([str(details[x]) for x in ["id","chr","start","end","strand","ref","alt","zygosity"]]))
                out.write("\n")

    for line in per_line_count.items():
        print("\t".join([str(x) for x in line]))

def read_options():
    parser = argparse.ArgumentParser(description="Prepare unique SNPS for cell line sleuth")

    parser.add_argument('--quiet', dest="quiet", action='store_true', default=False, help="Supress all but essential messages")
    parser.add_argument('--version', action='version', version=f"Cell Line Sleuth v{VERSION}")
    parser.add_argument('--cells', type=str, help="Comma delimited list of cells to identify", default="MCF7,HeLa,COLO-205,HCT-116,H9")
    parser.add_argument('cosmicfile', type=str, help="The COSMIC SNP file to process")
    parser.add_argument('outfile', type=str, help="Where to write the output")

    options = parser.parse_args()

    return options

def main():

    options = read_options()
    process_file(options)


if __name__ == "__main__":
    main()