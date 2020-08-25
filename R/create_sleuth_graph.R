library(tidyverse)
theme_set(theme_bw(base_size=14))

#input_file <- "e:/COSMIC/COLO205_sleuth.txt"
#output_file <- "e:/COSMIC/COLO205_sleuth.png"

input_file <- "e:/COSMIC/MCF7_sleuth.txt"
output_file <- "e:/COSMIC/MCF7_sleuth.png"


read_tsv(input_file) -> sleuth_data

# Get the total count
sleuth_data %>%
  mutate(TOTAL=REFCOUNT+ALTCOUNT+OTHERCOUNT) -> sleuth_data

# Calculate the expected ALT count
sleuth_data %>%
  mutate(EXPECTED_ALT=if_else(ZYG=="het", as.integer(TOTAL/2),as.integer(TOTAL))) -> sleuth_data


sleuth_data %>%
  ggplot(aes(x=LINE, y=100*ALTCOUNT/TOTAL, colour=ZYG, size=TOTAL)) +
  geom_jitter(height=0, width=0.3) +
  ggtitle(input_file) +
  ylab("ALT percentage") +
  scale_color_brewer(palette = "Set1") -> sleuth_plot

ggsave(output_file,plot=sleuth_plot)
