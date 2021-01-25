import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Dfa2Regex {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter dfa file name (e.g. DFA1.txt) :");
        String fileName = sc.nextLine();
        ArrayList<String> lines = readFromFile(fileName);
    }

    /*Read from file operation. Takes txt file name as(filename.txt) in same direction with class.
    returns a Arraylist includes file lines.*/
    public static ArrayList<String> readFromFile(String fileName) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        URL path = Dfa2Regex.class.getResource(fileName);
        File file = new File(path.getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            lines.add(st);

        return lines;
    }
}
