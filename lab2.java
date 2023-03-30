
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

        ArrayList<Clause> clauses = new ArrayList<Clause>();
        System.out.println(cl);

        for(ArrayList<String> clause: cl){
            ArrayList<Predicate> preds = new ArrayList<Predicate>();
            for(String string: clause){
                String[] lits = string.split("\\(");
                Predicate newPred;
                if(lits.length == 1){
                    if(string.charAt(0) == '!'){
                        newPred = new Predicate(lits[0].substring(1,lits[0].length()), true);
                    } else{
                        newPred = new Predicate(lits[0], false);
                    }
                    preds.add(newPred);
                }
                else{
                    ArrayList<Term> terms = new ArrayList<Term>(); 
                    String[] termsString = lits[1].split(",");
                    for(int i = 0; i < termsString.length-1; i++){
                        Term term = new Term(termsString[i]);
                        terms.add(term);
                    }
                    Term term = new Term(termsString[termsString.length-1].substring(0, termsString[termsString.length-1].length()-1));
                    terms.add(term);
                    if(string.charAt(0) == '!'){
                        newPred = new Predicate(lits[0].substring(1,lits[0].length()), true, terms);
                    } else{
                        newPred = new Predicate(lits[0], false, terms);
                    }
                    preds.add(newPred);
                }
            }
            Clause newClause = new Clause(preds);
            clauses.add(newClause);
        }
        
        ArrayList<Clause> newClauses = new ArrayList<Clause>();
        Clause resolvents;
        while(true){
            for(int i = 0; i < clauses.size()-1; i++){
                for(int j = i + 1; j < clauses.size()-1; i++){
                    resolvents = resolve(clauses.get(i), clauses.get(j));
                    if(resolvents.preds == null){
                        System.out.println("no");
                        return;
                    }
                    newClauses.add(resolvents);
                }
            }
            if(clauses.containsAll(newClauses)){
                System.out.println("yes");
                return;
            }
            clauses.addAll(newClauses);
        }
    }

    public static Clause resolve(Clause clause1, Clause clause2){
        ArrayList<Predicate> preds = new ArrayList<Predicate>();
        preds.addAll(clause1.preds);
        preds.addAll(clause2.preds);
        for(int i = 0; i < preds.size(); i++){
            for(int j = i + 1; j < preds.size(); j++){
                if(compare(preds.get(i), preds.get(j))){
                    preds.remove(i);
                    preds.remove(j);
                    i = 0;
                    j = 1;
                }
            }
        }
        if(preds.size() == 0){
            return new Clause();
        }
        return new Clause(preds);
    }

    public static boolean compare(Predicate p1, Predicate p2){
        if(p1.predicate.equals(p2.predicate) && ((p1.negated && !p2.negated) || (!p1.negated && p2.negated)) 
            && unify(p1,p2)){
            return true;
        }
        return false;
    }

    public static boolean unify(Predicate p1, Predicate p2){
        return true;
    }
}