package edu.iastate.cs228.hw5;

/**
 * @author JiaHan Tan
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javafx.scene.effect.Light.Distant;

public class Dijkstra {

    /**
     * First, computes a shortest path from a source vertex to
     * a destination vertex in a graph by using Dijkstra's algorithm.
     * Second, visits and saves (in a stack) each vertex in the path,
     * in reverse order starting from the destination vertex,
     * by using the map object pred.
     * Third, uses a StringBuilder object to generate the return String
     * object by poping up the vertices from the stack;
     * the vertices in the String object are in the right order.
     * Note that the get_index() method is called from a Graph.Vertex object
     * to get its oringinal integer name.
     *
     * @param G
     *          - The graph in which a shortest path is to be computed
     * @param source
     *          - The first vertex of the shortest path
     * @param dest
     *          - The last vertex of the shortest path
     * @return a String object with three lines (separated by a newline character)
     *         such that line 1 shows the length of the shortest path,
     *         line 2 shows the cost of the path,
     *         and line 3 gives a list of the vertices (in the path)
     *         with a space between adjacent vertices.
     *
     *         The contents of an example String object:
     *         Path Length: 5
     *         Path Cost: 4
     *         Path: 0 4 2 5 7 9
     *
     * @throws NullPointerException
     *           - If any arugment is null
     *
     * @throws RuntimeException
     *           - If the given source or dest vertex is not in the graph
     *
     */
    public static String Dijkstra(Graph G, Graph.Vertex source, Graph.Vertex dest)
    {
      // TODO
    	HashMap<Graph.Vertex, Integer> dist = new HashMap<Graph.Vertex, Integer>();
        HashMap<Graph.Vertex, Graph.Vertex> pred = new HashMap<Graph.Vertex, Graph.Vertex>();
        Heap<Vpair<Graph.Vertex, Integer>> priq = new Heap<Vpair<Graph.Vertex, Integer>>();
        HashSet<Graph.Vertex> vset = new HashSet<Graph.Vertex>();
        dist.put(source, 0);
        priq.add( new Vpair<Graph.Vertex, Integer>(source, 0) );
        while ( ! priq.isEmpty() ){
        	Vpair<Graph.Vertex, Integer> pair = priq.removeMin();
        	Graph.Vertex u = pair.getVertex();
        	if ( ! vset.contains(u) ){
        		vset.add(u);
        		for ( Iterator <Graph.Edge> iter = u.get_edges().iterator(); iter.hasNext();) { //
        			Graph.Edge graphEdge = iter.next();
        			Vpair<Graph.Vertex , Integer> pair1 = new Vpair <Graph.Vertex , Integer>(graphEdge.to , graphEdge.get_weight());
        			
        			Graph.Vertex v = pair1.getVertex();
        			Integer altdist = dist.get(u) + pair1.getCost();
        			Integer vdist = dist.get(v);
        			if ( vdist == null || vdist > altdist ){
        				dist.put(v, altdist);
        				pred.put(v, u);
        				priq.add( new Vpair<Graph.Vertex, Integer>(v, altdist) );
        			} // if
        		} // for
        	} // if
        } // while
        // use stack to reverse the output (path) from dijkstra's algorithm
        LinkedStack<Graph.Vertex> s = new LinkedStack <Graph.Vertex> (); // stack
        Graph.Vertex graphVertex = dest;
        while(graphVertex != source && graphVertex != null){
            	s.push(graphVertex);
            	graphVertex = pred.get(graphVertex);
        }
        s.push(source);
        
        //return pred.toString();
        int pathLength = -1;
        if( dist.get(dest) == null ) // or use if ( !dist.containsKey(dest) )
        	return null; // that means there was no path to dest
        int pathCost = dist.get(dest);
        String path = "" ;
        while(!s.isEmpty()){ //  && graphVertex!=null
            	path = path + s.pop().get_index() + " " ;
            	pathLength++;
        }
        
        // throw Exception
        //dest=null; // to test if the exception works
    	if ( G == null || source == null || dest == null ) // G == null || dist.get(source) == null || dist.get(dest) == null
    		throw new NullPointerException("Argument is null.");
    	if( !G.check_vertex(source) || !G.check_vertex(dest) )
    		throw new RuntimeException("Source or Destination Vertex is not in the graph.");
    	
    	// return, using string builder
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append("Path Length: " + pathLength + "\n" );
    	stringBuilder.append("Path Cost: " + pathCost + "\n" );
    	stringBuilder.append("Path: " + path );
    	String all = stringBuilder.toString();
    	return all;
    	
    	// return values in a simple way
		//return "Path Length: " + pathLength + "\n" +
		//		     "Path Cost: " + pathCost + "\n" +
		//   		 "Path: " + path ;
    }

/**
 * A pair class with two components of types V and C, where
 * V is a vertex type and C is a cost type.
 */

private static class Vpair<V, C extends Comparable<? super C> > implements Comparable<Vpair<V, C>>
{
     private V  node;
     private C  cost;

     Vpair(V n, C c)
     {
       node = n;
       cost = c;
     }

     public V getVertex() { return node;}
     public C getCost() { return cost;}
     public int compareTo( Vpair<V, C> other )
     {
       return cost.compareTo(other.getCost() );
     }

     public String toString()
     {
       return "<" +  node.toString() + ", " + cost.toString() + ">";
     }

     public int hashCode()
     {
       return node.hashCode();
     }

     public boolean equals(Object obj)
     {
       if(this == obj) return true;
       if((obj == null) || (obj.getClass() != this.getClass()))
        return false;
       // object must be Vpair at this point
       Vpair<?, ?> test = (Vpair<?, ?>)obj;
       return
         (node == test.node || (node != null && node.equals(test.node)));
     }
}

}
