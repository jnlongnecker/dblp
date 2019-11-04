# dblp

This program takes from standard input 3 things:
  1. The file path of the file to parse
  2. The keyword to filter initial results by
  3. The depth as an integer of citations to search
  
It will then go through the file and find the papers with the given keyword in the title and display them as Tier 0, sorted by year.

Then it will go through each previous tier and display the papers cited in the previous tier, also sorted by year.
