library(tidyverse)
theme_set(theme_bw(base_size=14))

# We pick up the input file from ARGV
input_file <- commandArgs(trailingOnly = TRUE)[1]
format <- commandArgs(trailingOnly = TRUE)[2]

## For testing only
# input_file <- "e:/COSMIC/MCF7_sleuth.txt"
# format <- "svg"


str_replace(input_file,".txt$",".png") -> output_file
height <- 5
width <- 8

if (format == "svg") {
  str_replace(input_file,".txt$",".svg") -> output_file
}


# Read the input file
read_tsv(input_file) -> sleuth_data

# Get the total observation count for each SNP
sleuth_data %>%
  mutate(TOTAL=REFCOUNT+ALTCOUNT+OTHERCOUNT) -> sleuth_data


# Plot the data as a stripchart
sleuth_data %>%
  ggplot(aes(x=LINE, y=100*ALTCOUNT/TOTAL, colour=ZYG, size=TOTAL)) +
  geom_jitter(height=0, width=0.3) +
  ggtitle(input_file) +
  ylab("ALT percentage") +
  scale_color_brewer(palette = "Set1") -> sleuth_plot

ggsave(output_file,device = format, plot=sleuth_plot,width = width, height = height, units = "in", dpi = 200)

