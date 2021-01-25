import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Dfa2Regex {
    public static ArrayList<String> alphabet = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dfa file name (e.g. DFA1.txt) :");
        String fileName = "DFA1.txt";
        ArrayList<String> lines = readFromFile(fileName);
        ArrayList<Vertex> vertices = createGraph(lines);
        printGraph(vertices);
    }

    /*Read from file operation. Takes txt file name as(filename.txt) in same direction with class.
    returns a Arraylist includes file lines.*/
    public static ArrayList<String> readFromFile(String fileName) throws Exception {
        String[] splittedArray = new String[10];
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
        if (splitArrComma.length <=1) {
            for (int j = 0; j < vertices.size(); j++) {
                if (vertices.get(j).stateLabel.equals(splitArrEquals[1])) {
                    vertices.get(j).setAccept(true);
                }
            }
        }
        else {
            for (int i = 0; i < splitArrComma.length; i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (vertices.get(j).stateLabel.equals(splitArrComma[j])) {
                        vertices.get(j).setAccept(true);
                    }
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
        int transactionNum = vertices.size() * vertices.size();
        for (int i = 0; i < transactionNum; i++) {
            splitArrComma = graphInfo.get(i+4).split(",");
            splitArrEquals = splitArrComma[1].split("=");
            for (int j = 0; j < vertices.size(); j++) {
                if (vertices.get(j).stateLabel.equals(splitArrEquals[1])){
                    Edge e = new Edge(splitArrEquals[0],vertices.get(j));
                    vertices.get(j).setComingEdge(e);
                }
            }
        }
        return vertices;
    }

    public static void printGraph(ArrayList<Vertex> vertices){
        System.out.println();
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(vertices.get(i).getStateLabel());
            System.out.println("isStart: " + vertices.get(i).isStart());
            System.out.println("isAccept: " + vertices.get(i).isAccept());
            System.out.println("====Edge Info=====");
            for (int j = 0; j <vertices.get(i).comingEdges.size() ; j++) {
                System.out.print(vertices.get(i).comingEdges.get(j).source.getStateLabel() + "->");
                System.out.println(vertices.get(i).comingEdges.get(j).edgeLabel);

            }
            System.out.println();
        }
    }
}
