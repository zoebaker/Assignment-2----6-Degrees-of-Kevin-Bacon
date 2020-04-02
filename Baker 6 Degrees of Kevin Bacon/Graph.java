import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;



//This creates the Graph to use find the path
public class Graph
{
    private  HashMap<String, Integer> NamesMap;
    private  HashMap<Integer, String> IndexMap;
    private  LinkedList[] graph;


    public Graph()
    {
        NamesMap = new HashMap<String, Integer>();
        IndexMap = new HashMap<Integer, String>();
        graph = new LinkedList[54202];
        for(int i = 0; i < graph.length; i++)
        {
            graph[i] = new LinkedList<>();
        }
    }


    public void addAllEdges(Set<Integer> set)
    {
        for(int index : set)
        {
            graph[index].addAll(set);
            Iterator<Integer> iterator = getNeighbors(index).iterator();
            while(iterator.hasNext())
            {
                int temp = iterator.next();
                if(temp == index)
                {
                    removeEdge(index, temp);
                }
            }
        }
    }

    //Removes edge from the graph
    public boolean removeEdge(int position, Object index)
    {
        if(position < 0 || position > graph.length)
        {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        if(graph[position].contains(index) == false)
        {
            throw new NullPointerException("Does not contain index " + index);
        }
        return graph[position].remove(index);
    }



    //This checks if two actors are directly connected to each other.
    public boolean containsNeighborOfIndex(int actor1, int actor2)
    {
        return graph[actor1].contains(actor2);
    }


    //Gets the neighbors of vertex and appends it to a LinkedList.
    public LinkedList<Integer> getNeighbors(int vertex)
    {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        Iterator<Integer> iterator = graph[vertex].iterator();
        while(iterator.hasNext())
        {
            linkedList.add(iterator.next());
        }
        return linkedList;
    }


    //Adds names into the namesMap
    public void addToNamesMap(int position, String name)
    {
        NamesMap.put(name, position);
    }

    //Adds names into the indexMap
    public void addToIndexMap(int position, String name)
    {
        IndexMap.put(position, name);
    }

    //This gets the index of the name
    public int getIndex(String name)
    {
        if(NamesMap.containsKey(name) == false)
        {
            throw new NullPointerException(name + " not found in index.");
        }
        return NamesMap.get(name);
    }

    //Gets the name in that position
    public String getName(int position)
    {
        if(IndexMap.containsKey(position) == false)
        {
            throw new NullPointerException(position + " not found.");
        }
        return IndexMap.get(position);
    }

    //Checks if the name is in the Graph
    public boolean containsActor(String name)
    {
        return NamesMap.containsKey(name);
    }

    //Returns the number of elements in NamesMap
    public int namesMapElements()
    {
        return NamesMap.size();
    }


}