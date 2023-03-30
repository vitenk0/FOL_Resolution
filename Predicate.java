import java.util.*;

public class Predicate {
    String predicate;
    ArrayList<Term> terms;
    boolean negated;

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
