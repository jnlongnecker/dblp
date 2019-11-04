The way that I viewed the problem, the main issue was that of storing the objects that needed to be displayed. In order to quickly search,
each object had to be created to use streams to very quickly filter through the papers I wanted to choose.

Creating these objects was easy thanks to Google's Gson API. After a Paper object had been created, to stream through and apply a filter
to the objects, they were added to a list. This list, however, needs to stay small enough so that the program does not run out of memory
simply storing an enormous amount of objects. I created a return list that would hold the objects that needed to stay; those that matched
the keyword in the title. Then I also created a working list, one that would store each object until it was no longer needed. I decided
that 500 objects was plenty, and so every 500 iterations of creating Paper objects and adding them to the working list, that list was
filtered and the results added to the returning list. The working list was then cleared and the process would continue to the end of the
file. Once reaching the end of the file, the remaining Paper objects were once again filtered and added to the returning list.

The drawback to this approach is that because the contents of the file are never stored anywhere, it must be looped through for every 
tier in order to check each citation; however, I find this a necessary evil as mentioned before the memory cost would be too great to 
attempt to store every paper.

For each tier, the same approach as before is taken, only this time the results are filtered based on the ID of the Paper matching one of 
the ID's of a previous tier's reference. In order to quickly check if an ID matched, instead of looping every single time through all the
possible ID's, a regex was constructed using the ID's of the previous tier's references. Checking if a Paper was cited by the previous tier
was then as simple as comparing an ID against the regex.
