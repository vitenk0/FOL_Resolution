
import java.io.*;
import java.util.*;


public class lab2{
    public static void main(String[] args){
        ArrayList<String> p = new ArrayList<String>();
        ArrayList<String> v = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();
        ArrayList<String> f = new ArrayList<String>();
        ArrayList<ArrayList<String>> cl = new ArrayList<ArrayList<String>>();


        try{
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);

            p.addAll(Arrays.asList(sc.nextLine().split(" ")));
            v.addAll(Arrays.asList(sc.nextLine().split(" ")));
            c.addAll(Arrays.asList(sc.nextLine().split(" ")));
            f.addAll(Arrays.asList(sc.nextLine().split(" ")));
            sc.nextLine();

            while(sc.hasNextLine()){
                ArrayList<String> newClause = new ArrayList<String>();
                newClause.addAll(Arrays.asList(sc.nextLine().split(" ")));
                cl.add(newClause);
            }

            p.remove(0);
            v.remove(0);
            c.remove(0);
            f.remove(0);

            sc.close();
        } catch (IOException e) {
            System.out.println("Loading files failed");
            e.printStackTrace();
        }

        ArrayList<Predicate> preds = new ArrayList<Predicate>();
        ArrayList<Constant> cons = new ArrayList<Constant>();
        ArrayList<Clause> clauses = new ArrayList<Clause>();

        /*
        for(String pred: p){
            preds.add(new Predicate(pred));
        }

        for(String con: c){
            cons.add(new Constant(con));
        }*/

        for(ArrayList<String> clause: cl){
            for(String string: clause){
                String[] lits = string.split("(");
                Predicate newPred;
                switch(lits.length){
                    case 1: if(string.charAt(0) == '!'){
                                newPred = new Predicate(lits[0], true);
                            } else{
                                newPred = new Predicate(lits[0], false);
                            }
                            preds.add(newPred);
                            break;
                    case 2: if(string.charAt(0) == '!'){
                                newPred = new Predicate(lits[0], true, new Constant(lits[1]));
                            } else{
                                newPred = new Predicate(lits[0], false, new Constant(lits[1]));
                            }
                            preds.add(newPred);
                            break;
                }
            }
            Clause newClause = new Clause(preds);
            clauses.add(newClause);
        }

        System.out.println(clauses);
        
        /* 
        ArrayList<ArrayList<String>> newClauses = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> resolvents = new ArrayList<ArrayList<String>>();
        while(true){
            for(int i = 0; i < clauses.size(); i++){
                for(int j = i + 1; j < clauses.size(); i++){
                    resolvents = resolve(clauses.get(i), clauses.get(j));
                    if(resolvents.contains("[]")){
                        System.out.println("no");
                        return;
                    }
                    newArr.addAll(resolvents);
                }
            }
            if(clauses.containsAll(newArr)){
                System.out.println("yes");
                return;
            }
            clauses.addAll(newArr);
        }
    }

    public static ArrayList<ArrayList<String>> resolve(ArrayList<String> clause1, ArrayList<String> clause2){

    }*/
    }}