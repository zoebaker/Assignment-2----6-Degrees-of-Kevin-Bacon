

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

public void readFile (File file, Graph index) throws IOException{
    JSONParser parser = new JSONParser();
    JSONArray jsonArr;
    BufferedReader reader = new BufferedReader( new FileReader(file));
    String temp;
    Set <Integer> set = new HashSet<Integer>();
    int position = 0;

    try {
        reader.readLine();


        while((temp = reader.readLine()) != null){

            if (temp.contains("cast_id")){

                temp = temp.substring(temp.indexOf("\"[")+1, temp.lastIndexOf("[")-2).replaceAll("\"\"", "\"");

                try{
                    jsonArr = (JSONArray)parser.parse(temp);
                    for (int i = 0; i < jsonArr.size(); i++){
                        temp = (String) ((JSONObject)jsonArr.get(i)).get("name");
                        temp = temp.toLowerCase();

                        if (!index.containsActor(temp)){
                            index.addToNamesMap(position, temp);
                            index.addToIndexMap(position, temp);
                            position++;
                        }
                        set.add(index.getIndex(temp));
                    }
                    index.addAllEdges(set);
                    set.clear();
                }
                catch (ParseException e){
                    System.out.println(temp);
                }
            }
        }
    }
    finally{
        reader.close();
    }
}
    //Finds shortest path between actor1 and actor2
    public void BFS(Graph graph, String actor1, String actor2)
    {

        int actor1index = graph.getIndex(actor1);
        int actor2index = graph.getIndex(actor2);

        boolean[] checked = new boolean[graph.namesMapElements()];


        String actor1Name = graph.getName(actor1index);
        String actor2Name = graph.getName(actor2index);
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> path = new HashMap<>();

        if(graph.containsNeighborOfIndex(actor1index, actor2index))
        {
            printPathSameMovie(graph, actor1Name, actor2Name);
            return;
        }

        queue.add(actor1index);
        checked[actor1index] = true;
        while(!queue.isEmpty())
        {
            int index = queue.poll();
            Iterator<Integer> it = graph.getNeighbors(index).iterator();
            while(it.hasNext())
            {
                int current = it.next();
                if(!checked[current])
                {
                    checked[current] = true;
                    queue.add(current);
                    path.put(current, index);
                }

                if (current == actor2index){
                    printPath(path, current, graph, actor1Name, actor2Name);
                    return;
                }
            }
        }
        System.out.println("There is no path");
    }

    public static String capitalizeWord(String str){
        String[] words =str.split("\\s");
        StringBuilder capitalizeWord= new StringBuilder();
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord.append(first.toUpperCase()).append(afterfirst).append(" ");
        }
        return capitalizeWord.toString().trim();
    }

    //Prints the path to the user if they are in the same movie
    public void printPathSameMovie(Graph graph, String actorName1, String actorName2){
        actorName1 = capitalizeWord(actorName1);
        actorName2 = capitalizeWord(actorName2);
        System.out.println("The path between " + actorName1 + " and " + actorName2 + ": ");
        System.out.println(actorName1 + " --> " + actorName2);

    }

    //Prints the path to the user if actor1 and actor2 are not in the same movie
    public void printPath(Map<Integer, Integer> map, int current, Graph graph, String actorName1, String actorName2){

        StringBuilder output = new StringBuilder();

        while(map.get(current) != null)
        {
            output.insert(0, " --> " + capitalizeWord(graph.getName(current)));
            current = map.get(current);
        }
        actorName1 = capitalizeWord(actorName1);
        actorName2 = capitalizeWord(actorName2);
        System.out.println("The Path between " + actorName1 + " and " + actorName2 + ": ");
        System.out.println(actorName1 + output);


    }



}
