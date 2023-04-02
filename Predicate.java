import java.util.*;

public class Predicate {
    public String predicate;
    public ArrayList<Term> terms;
    public boolean negated;

    public Predicate(){
        this.predicate = "[]";
    }

    public Predicate(String p, boolean negated){
        this.predicate = p;
        this.negated = negated;
    }

    public Predicate(String p, boolean negated, ArrayList<Term> terms){
        this.predicate = p;
        this.negated = negated;
        this.terms = terms;
    }
}
