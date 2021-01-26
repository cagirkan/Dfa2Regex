import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Dfa2Regex {
    public static String epsilon = "\u03B5";
    public static ArrayList<String> alphabet = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dfa file name (e.g. DFA1.txt) :");
        String fileName = "DFA2.txt";
        ArrayList<String> lines = readFromFile(fileName);
        ArrayList<Vertex> vertices = createGraph(lines);
        printGraph(vertices);
        System.out.println("=====DFA TO GNFA=====");
        vertices = dfa2Gnfa(vertices);
        printGraph(vertices);
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

    public static ArrayList<Vertex> dfa2Gnfa(ArrayList<Vertex> vertices){
        Vertex start = new Vertex("S",true,false);
        Vertex accept = new Vertex("A",false,true);
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).isStart()){
                //Start state change
                vertices.get(i).setStart(false);
                Edge e = new Edge(epsilon,start);
                vertices.get(i).setComingEdge(e);
            }
            if (vertices.get(i).isAccept()){
                //Accept states change
                vertices.get(i).setAccept(false);
                Edge e = new Edge(epsilon,vertices.get(i));
                accept.setComingEdge(e);
            }
        }
        vertices.add(start);
        vertices.add(accept);
        return vertices;
    }

    //Creating the graph with given graph info in txt file
    public static ArrayList<Vertex> createGraph(ArrayList<String> graphInfo){
        ArrayList<Vertex> vertices = new ArrayList<>();
        String[] splitArrEquals = new String[10];
        String[] splitArrComma = new String[10];
        //Create Vertices
        splitArrEquals = graphInfo.get(3).split("=");
        splitArrComma = splitArrEquals[1].split(",");
        for (int i = 0; i < splitArrComma.length; i++) {
            if (!splitArrComma[i].equals("")){
                vertices.add(new Vertex(splitArrComma[i]));
            }
        }

        //Define start vertex
        splitArrEquals = graphInfo.get(0).split("=");
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).stateLabel.equals(splitArrEquals[1])) {
                vertices.get(i).setStart(true);
            }
        }

        //Define accept vertices
        splitArrEquals = graphInfo.get(1).split("=");
        splitArrComma = splitArrEquals[1].split(",");

        for (int i = 0; i < splitArrComma.length; i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (vertices.get(j).stateLabel.equals(splitArrComma[i])) {
                    vertices.get(j).setAccept(true);
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
        int transactionNum = vertices.size() * 2;
        for (int i = 0; i < transactionNum; i++) {
            splitArrComma = graphInfo.get(i+4).split(",");
            splitArrEquals = splitArrComma[1].split("=");
            //get vertex
            for (int j = 0; j < vertices.size(); j++) {
                if (splitArrEquals[1].equals(vertices.get(j).stateLabel)){
                    //read edge source
                    for (int k = 0; k < vertices.size(); k++) {
                       if (splitArrComma[0].equals(vertices.get(k).stateLabel)){
                           //create and set edge source
                           Edge e = new Edge(splitArrEquals[0],vertices.get(k));
                           vertices.get(j).setComingEdge(e);
                       }
                    }
                }
            }
        }
        return vertices;
    }

    //Print the graph
    public static void printGraph(ArrayList<Vertex> vertices){
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print("State-" + (i+1) + ": Label: " + vertices.get(i).getStateLabel());
            if (vertices.get(i).isStart()){
                System.out.print(" (Start)");
            }
            if (vertices.get(i).isAccept()){
                System.out.print(" (Accept)");
            }
            System.out.println();
        }
        System.out.println("===== GRAPH =====");
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j <vertices.get(i).comingEdges.size() ; j++) {
                System.out.print(vertices.get(i).comingEdges.get(j).source.getStateLabel() + "--");//q1
                System.out.print(vertices.get(i).comingEdges.get(j).edgeLabel);//a
                System.out.println("-->" + vertices.get(i).stateLabel);//a
            }
            System.out.println();
        }
    }
}
