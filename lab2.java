import java.io.*;
import java.util.*;

public class lab2{
    public static void main(String[] args){
        ArrayList<String> predicates = new ArrayList<String>();
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<String> constants = new ArrayList<String>();
        ArrayList<String> functions = new ArrayList<String>();
        ArrayList<ArrayList<String>> clauses = new ArrayList<ArrayList<String>>();


        try{
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);

            predicates.addAll(Arrays.asList(sc.nextLine().split(" ")));
            variables.addAll(Arrays.asList(sc.nextLine().split(" ")));
            constants.addAll(Arrays.asList(sc.nextLine().split(" ")));
            functions.addAll(Arrays.asList(sc.nextLine().split(" ")));
            sc.nextLine();

            while(sc.hasNextLine()){
                ArrayList<String> newClause = new ArrayList<String>();
                newClause.addAll(Arrays.asList(sc.nextLine().split(" ")));
                clauses.add(newClause);
            }

            predicates.remove(0);
            variables.remove(0);
            constants.remove(0);
            functions.remove(0);

            sc.close();
        } catch (IOException e) {
            System.out.println("Loading files failed");
            e.printStackTrace();
        }

        System.out.println(predicates);
        System.out.println(variables);
        System.out.println(constants);
        System.out.println(functions);
        System.out.println(clauses);
    }
}