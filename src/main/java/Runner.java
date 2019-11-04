
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author Jared - PC
 */
public class Runner 
{
    public static void main(String[] args)
    {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Input the path to the json file: ");
        String filePath = stdin.next();
        PapersHandler handler = new PapersHandler(filePath);
        
        System.out.println("Input the filter keyword: ");
        String keyword = stdin.next();
        
        System.out.println("Input the tier depth (n): ");
        int n = stdin.nextInt();

        //Get the list of papers that match the keyword
        List<Paper> filteredList = handler.getPapersMatchingKeyword(keyword);
        //Sort the list by year
        filteredList = filteredList.stream().sorted((p, s) -> s.getYear() - p.getYear()).collect(Collectors.toList());
        
        System.out.println("****************TIER 0****************");
        System.out.println(filteredList);
        //Track our previous tier for searching the next tier
        List<Paper> prevList = filteredList;
        for(int k = 0; k < n; k++)
        {
            System.out.println("****************TIER " + (k+1) + "****************");
            //Construct a regex consisting of all the references in the previous tier
            String regex = "";
            for(Paper p : prevList)
            {
                if(p.getReferences() == null)
                    continue;
                for(String ref : p.getReferences())
                    regex += (ref + "|"); 
            }
            //Get the papers matching the regex constructed
            List<Paper> tierK = handler.getNextCitationTier(regex);
            //Sort based off year
            tierK = tierK.stream().sorted((p, s) -> s.getYear() - p.getYear()).collect(Collectors.toList());
            prevList = tierK;
            if(tierK.isEmpty())
                System.out.println("No papers in this tier.");
            else
                System.out.println(tierK);
        }
    }
}

class Author
{
    private String name = null;
    private String id = null;
    private String org = null;
    
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    
    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    
    public String getOrg() {return this.org;}
    public void setOrg(String org) {this.org = org;}
    
    @Override
    public String toString()
    {
        if(name == null || name.isEmpty())
            return "Author unknown.";
        return name;
    }
}
class Venue
{
    private String raw = null;
    private String id = null;
    
    public String getRaw() {return this.raw;}
    public void setRaw(String raw) {this.raw = raw;}
    
    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    
    @Override
    public String toString()
    {
        if(raw == null || raw.isEmpty())
            return "Venue unknown.";
        else
            return "Venue: " + raw + ".";
    }
}
class FOS
{
    private String name = null;
    private String w = null;
    
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    
    public String getW() {return this.w;}
    public void getW(String w) {this.w = w;}
    
    @Override
    public String toString()
    {
        if(name == null || name.isEmpty())
            return "Unknown field of study.";
        return name;
    }
}
class Paper
{
    private String id = null;
    private String title = null;
    private Author[] authors = null;
    private Venue venue = null;
    private int year = 0;
    private int n_citation = 0;
    private String page_start = null;
    private String page_end = null;
    private String doc_type = null;
    private String publisher = null;
    private String volume = null;
    private String issue = null;
    private String doi = null;
    private String[] references = null;
    private FOS[] fos = null;
    
    
    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    
    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}
    
    public Author[] getAuthors() {return this.authors;}
    public void setAuthors(Author[] authors) {this.authors = authors;}
    
    public Venue getVenue() {return this.venue;}
    public void setVenue(Venue venue) {this.venue = venue;}
    
    public int getYear() {return this.year;}
    public void setYear(int year) {this.year = year;}
    
    public int getN_citation() {return this.n_citation;}
    public void setN_citation(int n_citation) {this.n_citation = n_citation;}
    
    public String getPage_start() {return this.page_start;}
    public void setPage_start(String page_start) {this.page_start = page_start;}
    
    public String getPage_end() {return this.page_end;}
    public void setPage_end(String page_end) {this.page_end = page_end;}
    
    public String getDoc_type() {return this.doc_type;}
    public void setDoc_type(String doc_type) {this.doc_type = doc_type;}
    
    public String getPublisher() {return this.publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    
    public String getVolume() {return this.volume;}
    public void setVolume(String volume) {this.volume = volume;}
    
    public String getIssue() {return this.issue;}
    public void setIssue(String issue) {this.issue = issue;}
    
    public String getDoi() {return this.doi;}
    public void setDoi(String doi) {this.doi = doi;}
    
    public String[] getReferences() {return this.references;}
    public void setReferences(String[] references) {this.references = references;}
    
    public FOS[] getFos() {return this.fos;}
    public void setFos(FOS[] fos) {this.fos = fos;}
    
    @Override
    public String toString()
    {
        String ret = title;
        if(authors == null)
            ret += ", written by Unknown Author(s)";
        else
            ret += ", written by: " + Arrays.toString(authors);
        if(year == 0)
            ret += ", Year Unknown.";
        else
            ret += ", Year " + year + ".\n";
        return ret;
    }
}
