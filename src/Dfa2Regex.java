import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Dfa2Regex {
    public static String epsilon = "\u03B5";
    public static String union = "\u222A";
    public static ArrayList<String> alphabet = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dfa file name (e.g. DFA1.txt) :");
        String fileName = "DFA2.txt";
        ArrayList<String> lines = readFromFile(fileName);
        ArrayList<Vertex> graph = createGraph(lines);
        printGraph(graph);
        System.out.println("=====DFA TO "+ (graph.size()+2) +" state GNFA=====");
        graph = dfa2FirstGnfa(graph);
        printGraph(graph);
    }

    /*Read from file operation. Takes txt file name as(filename.txt) in same direction with class.
    returns a Arraylist includes file lines.*/
    public static ArrayList<String> readFromFile(String fileName) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        URL path = Dfa2Regex.class.getResource(fileName);
        File file = new File(path.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            lines.add(st);
        }
        return lines;
    }

    public static ArrayList<Vertex> dfa2FirstGnfa(ArrayList<Vertex> graph){
        Vertex start = new Vertex("S",true,false);
        Vertex accept = new Vertex("A",false,true);
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).isStart()){
                //Start state change
                graph.get(i).setStart(false);
                Edge e = new Edge(epsilon,start);
                graph.get(i).setComingEdge(e);
            }
            if (graph.get(i).isAccept()){
                //Accept states change
                graph.get(i).setAccept(false);
                Edge e = new Edge(epsilon,graph.get(i));
                accept.setComingEdge(e);
            }
        }
        graph.add(start);
        graph.add(accept);
        //set first state to q-rip
        graph.get(0).setStateLabel("q-rip");
        return graph;
    }
    
    public static ArrayList<Vertex> gnfa2Regex(ArrayList<Vertex> graph){
        //Make a copy vertex qRip
        Vertex qRip;
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).stateLabel.equals("q-rip")){
                qRip = graph.get(i);
                break;
            }
        }
        for (int i = 0; i < graph.size(); i++) {
            if (!graph.get(i).stateLabel.equals("q-rip")){
                for (int j = 0; j < graph.get(i).comingEdges.size(); j++) {
                    if (graph.get(i).comingEdges.get(j).edgeLabel.equals("q-rip")){

                    }
                }
            }
        }
        return graph;
    }

    //Creating the graph with given vertices info in txt file
    public static ArrayList<Vertex> createGraph(ArrayList<String> graphInfo){
        ArrayList<Vertex> graph = new ArrayList<>();
        String[] splitArrEquals = new String[10];
        String[] splitArrComma = new String[10];
        //Create graph
        splitArrEquals = graphInfo.get(3).split("=");
        splitArrComma = splitArrEquals[1].split(",");
        for (int i = 0; i < splitArrComma.length; i++) {
            if (!splitArrComma[i].equals("")){
                graph.add(new Vertex(splitArrComma[i]));
            }
        }

        //Define start vertex
        splitArrEquals = graphInfo.get(0).split("=");
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).stateLabel.equals(splitArrEquals[1])) {
                graph.get(i).setStart(true);
            }
        }

        //Define accept vertices
        splitArrEquals = graphInfo.get(1).split("=");
        splitArrComma = splitArrEquals[1].split(",");

        for (int i = 0; i < splitArrComma.length; i++) {
            for (int j = 0; j < graph.size(); j++) {
                if (graph.get(j).stateLabel.equals(splitArrComma[i])) {
                    graph.get(j).setAccept(true);
                }
            }
        }

        //Define Alphabet
        splitArrEquals = graphInfo.get(2).split("=");
        splitArrComma = splitArrEquals[1].split(",");
        for (int i = 0; i < splitArrComma.length; i++) {
            if (!splitArrComma[i].equals("")){
                alphabet.add(splitArrComma[i]);
            }
        }

        //Create edge relations
        int transactionNum = graph.size() * 2;
        for (int i = 0; i < transactionNum; i++) {
            splitArrComma = graphInfo.get(i+4).split(",");
            splitArrEquals = splitArrComma[1].split("=");
            //get vertex
            for (int j = 0; j < graph.size(); j++) {
                if (splitArrEquals[1].equals(graph.get(j).stateLabel)){
                    //read edge source
                    for (int k = 0; k < graph.size(); k++) {
                       if (splitArrComma[0].equals(graph.get(k).stateLabel)){
                           //create and set edge source
                           Edge e = new Edge(splitArrEquals[0],graph.get(k));
                           graph.get(j).setComingEdge(e);
                       }
                    }
                }
            }
        }
        return graph;
    }

    //Print the graph
    public static void printGraph(ArrayList<Vertex> graph){
        System.out.print("\nAlphabet: ");
        for (int i = 0; i < alphabet.size(); i++) {
            System.out.print(alphabet.get(i) + " ");
        }
        System.out.println();
        for (int i = 0; i < graph.size(); i++) {
            System.out.print("State-" + (i+1) + ":" + graph.get(i).getStateLabel());
            if (graph.get(i).isStart()){
                System.out.print(" (Start)");
            }
            if (graph.get(i).isAccept()){
                System.out.print(" (Accept)");
            }
            System.out.println();
        }
        System.out.println("===== GRAPH =====");
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j <graph.get(i).comingEdges.size() ; j++) {
                System.out.print(graph.get(i).comingEdges.get(j).source.getStateLabel() + "--");//q1
                System.out.print(graph.get(i).comingEdges.get(j).edgeLabel);//a
                System.out.println("-->" + graph.get(i).stateLabel);//a
            }
            System.out.println();
        }
    }
}
