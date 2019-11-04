/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Jared - PC
 */
public class PapersHandler 
{
    private final String file;
    private Gson gson;
    public PapersHandler(String f)
    {
        file = f;
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }
    
    public List<Paper> getPapersMatchingKeyword(String keyword)
    {
        //our list of papers matching the keyword
        ArrayList<Paper> ret = new ArrayList<>();
        //work list, we can't store every single paper
        ArrayList<Paper> work = new ArrayList<>();
        //Set up file reader
        Scanner inFS = null;
        try
        {
           inFS = new Scanner(new File(file));
        }
        catch(FileNotFoundException e)
        {
            System.out.print("Error: ");
            System.out.println(e.getMessage());
        }
        
        //Counter to not store too many objects
        int count = 0;
        while(inFS.hasNextLine())
        {
            //Get the current line of the file
            String line = inFS.nextLine();
            //Create a paper object from the line and add it to our working list
            Paper paper = gson.fromJson(line, Paper.class);
            work.add(paper);
            //Every 500, filter the list and add it to ret, then clear our work list
            if(count%500 == 0)
            {
                ret.addAll(work.stream().
                        filter(p->p.getTitle().toLowerCase().contains(keyword.toLowerCase())).
                        collect(Collectors.toList()));
                work.clear();
            }
            count++;
        }
        //Filter and add one last time
        ret.addAll(work.stream().
                filter(p->p.getTitle().toLowerCase().contains(keyword.toLowerCase())).
                collect(Collectors.toList()));
        work.clear();
        
        return ret;
    }
    
    public List<Paper> getNextCitationTier(String regex)
    {
        //our list of papers matching the keyword
        ArrayList<Paper> ret = new ArrayList<>();
        //work list, we can't store every single paper
        ArrayList<Paper> work = new ArrayList<>();
        //Set up file reader
        Scanner inFS = null;
        try
        {
           inFS = new Scanner(new File(file));
        }
        catch(FileNotFoundException e)
        {
            System.out.print("Error: ");
            System.out.println(e.getMessage());
        }
        //Counter to not store too many objects
        int count = 0;
        while(inFS.hasNextLine())
        {
            //Get the current line of the file
            String line = inFS.nextLine();
            //Create a paper object from the line and add it to our working list
            Paper paper = gson.fromJson(line, Paper.class);
            work.add(paper);
            //Every 500, filter the list and add it to ret, then clear our work list
            if(count%500 == 0)
            {
                ret.addAll(work.stream().
                        filter((p->p.getId().matches(regex))).
                        collect(Collectors.toList()));
                work.clear();
            }
            count++;
        }
        ret.addAll(work.stream().
                        filter((p->p.getId().matches(regex))).
                        collect(Collectors.toList()));
                work.clear();
        return ret;
    }
            
}
