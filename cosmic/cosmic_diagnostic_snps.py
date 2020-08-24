#!python
import sys

interesting_cell_lines = ["MCF7","HeLa","COLO-205","HCT-116","H9"]
def process_file(file):

    mutations = {}
    mutation_details = {}

    line_count = 0
    with open(file) as fh:
        for line in fh:

            line_count += 1

            if line_count % 100000 == 0:
                print (f"Read {line_count} lines")

            if line.startswith("Gene name"):
                continue

            sections = line.strip().split("\t")
            # Skip it if there's no position
            if len(sections[25]) == 0:
                continue

            cell_line = sections[4]
            mutation_type = sections[21]
            zygosity = sections[22]
            snp_id = sections[16]
            (chrom,position) = sections[25].split(":")
            (start,end) = position.split("-")
            snp = sections[19]
            ref = snp[-3]
            alt = snp[-1]

            if not cell_line in interesting_cell_lines:
                continue

            if not mutation_type.startswith("Substitution"):
                continue

#            for i in range(len(sections)):
#                print(f"{i} {sections[i]}")
#
#            break


            if not snp_id in mutations:
                mutations[snp_id] = []

            mutations[snp_id].append(cell_line)

            if not snp_id in mutation_details:
                mutation_details[snp_id] = {"id": snp_id, "chr":chrom, "start":start, "end":end, "ref":ref, "alt":alt, "zygosity":zygosity}
    
    per_line_count = {}
    with open ("cosmic_diagnostic_snps.txt","w") as out:
        headers = ["LINE","ID","CHR","START","END","REF","ALT","ZYGOSITY"]
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
                out.write("\t".join([str(details[x]) for x in ["id","chr","start","end","ref","alt","zygosity"]]))
                out.write("\n")

    for line in per_line_count.items():
        print("\t".join([str(x) for x in line]))

def main():
    if len(sys.argv) > 1:
        process_file(sys.argv[1])

    else:
        process_file("CosmicCLP_MutantExport.tsv")


if __name__ == "__main__":
    main()