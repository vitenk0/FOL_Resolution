public class Predicate {
    String predicate;
    Constant constant;
    boolean negated;

    public Predicate(String p, boolean negated){
        this.predicate = p;
        this.negated = negated;
    }

    public Predicate(String p, boolean negated, Constant constant){
        this.predicate = p;
        this.negated = negated;
        this.constant = constant;
    }
}
