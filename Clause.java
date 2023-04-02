import java.util.*;

public class Clause {
    public ArrayList<Predicate> preds;

    public Clause(){
        this.preds = new ArrayList<Predicate>();
        this.preds.add(new Predicate());
    }

    public Clause(ArrayList<Predicate> preds){
        this.preds = preds;
    }
}
