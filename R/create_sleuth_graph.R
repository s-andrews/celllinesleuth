library(tidyverse)
theme_set(theme_bw(base_size=14))

# We pick up the input file from ARGV
input_file <- commandArgs(trailingOnly = TRUE)[1]
str_replace(input_file,".txt$",".png") -> output_file

## For testing only
#input_file <- "e:/COSMIC/COLO205_sleuth.txt"
#output_file <- "e:/COSMIC/COLO205_sleuth.png"

# Read the input file
read_tsv(input_file) -> sleuth_data

# Get the total obervation count for each SNP
sleuth_data %>%
  mutate(TOTAL=REFCOUNT+ALTCOUNT+OTHERCOUNT) -> sleuth_data


# Plot the data as a stripchart
sleuth_data %>%
  ggplot(aes(x=LINE, y=100*ALTCOUNT/TOTAL, colour=ZYG, size=TOTAL)) +
  geom_jitter(height=0, width=0.3) +
  ggtitle(input_file) +
  ylab("ALT percentage") +
  scale_color_brewer(palette = "Set1") -> sleuth_plot

ggsave(output_file,plot=sleuth_plot)
