import java.util.*;

public class Substitution {
    public Map<Term, Term> map = new HashMap<>(); // (variable, term)
    
    public Substitution() {}
    
    public Substitution(Term x, Term y) {
        map.put(x, y);
    }

    public void put(Term x, Term y) {
        map.put(x, y);
    }
}
