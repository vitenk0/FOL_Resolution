
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

        for(ArrayList<String> clause: cl){
            ArrayList<Predicate> preds = new ArrayList<Predicate>();
            for(String string: clause){
                String[] lits = string.split("\\(", 2);
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
                    for(int i = 0; i < termsString.length; i++){
                        String tString;
                        Term term = null;
                        String[] posFunc = termsString[i].split("\\(");
                        if(i == termsString.length-1){
                            tString = termsString[i].split("\\)")[0];
                        }
                        else{
                            tString = termsString[i];
                        }
                        if(c.contains(tString)){
                            term = new Term(tString, Term.Type.constant);
                        }
                        else if(v.contains(tString)){
                            term = new Term(tString, Term.Type.variable);
                        }  
                        else if(f.contains(posFunc[0])){
                            tString = posFunc[1].split("\\)")[0];
                            if(c.contains(tString)){
                                term = new Function(posFunc[0], new Term(tString, Term.Type.constant));
                            }
                            else if(v.contains(tString)){
                                term = new Function(posFunc[0], new Term(tString, Term.Type.variable));
                            }
                        }
                        terms.add(term);
                    }
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
        Clause resolvent;
        while(true){
            for(int i = 0; i < clauses.size(); i++){
                for(int j = i + 1; j < clauses.size(); j++){
                    Clause c1 = clauses.get(i);
                    Clause c2 = clauses.get(j);
                    resolvent = resolve(clauses, clauses.get(i), clauses.get(j));
                    if(resolvent != null){
                        if(resolvent.preds.get(0).predicate.equals("[]")){
                            System.out.println("no");
                            return;
                        }
                        newClauses.add(resolvent);
                    }
                }
            }
            if(clauses.containsAll(newClauses)){
                System.out.println("yes");
                return;
            }
            clauses.addAll(newClauses);
        }
    }

    public static Clause resolve(ArrayList<Clause> clauses, Clause clause1, Clause clause2){
        ArrayList<Predicate> preds = new ArrayList<Predicate>();
        preds.addAll(clause1.preds);
        preds.addAll(clause2.preds);
        int initSize = preds.size();
        for(int i = 0; i < preds.size(); i++){
            for(int j = i + 1; j < preds.size(); j++){
                Substitution theta = unify(preds.get(i), preds.get(j));
                if(theta != null){
                    preds.remove(i);
                    preds.remove(j-1);
                    for(Predicate pred: preds){
                        if(pred.terms != null){
                            for(Term term: pred.terms){
                                for(Term oldVariable: theta.map.keySet()){
                                    if(term.t.equals(oldVariable.t)){
                                        term = theta.map.get(oldVariable);
                                    }
                                }
                            }
                        }
                    }
                    i = 0;
                    j = 1;
                }
            }
        }
        if(preds.size() == 0){
            return new Clause();
        }
        else if(preds.size() == initSize){
            return null;
        }
        Clause newClause = new Clause(preds);
        for(Clause cl: clauses){
            if(cl.preds.containsAll(preds) && preds.containsAll(cl.preds)){
                newClause = cl;
            }
        }
        return newClause;
    }

    public static Substitution unify(Predicate p1, Predicate p2){
        Substitution theta = null;
        if(!p1.predicate.equals(p2.predicate)){
            return theta;
        }
        if((p1.negated && p2.negated) || (!p1.negated && !p2.negated)){
            return theta;
        }
        if(p1.terms == null && p2.terms == null){
            return new Substitution();
        }

        for(int i = 0; i < p1.terms.size(); i++){
            Term t1 = p1.terms.get(i);
            Term t2 = p2.terms.get(i);

            if(t1.isVariable() && t2.isVariable() && !t1.equals(t2)){
                theta = new Substitution(t1,t2);
            }
            else if(t1.isVariable()){
                theta = new Substitution(t1,t2); 
            }
            else if(t2.isVariable()){
                theta = new Substitution(t2, t1);
            }
            else if(t1.t.equals(t2.t)){
                theta = new Substitution();
            }
            else if(!t1.t.equals(t2.t) && t1.isConstant() && t2.isConstant()){
                theta = null;
            }
        }
        return theta;
    }
}