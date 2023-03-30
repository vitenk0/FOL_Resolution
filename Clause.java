import java.util.*;

public class Clause {
    ArrayList<Predicate> preds;

    public Clause(){
        this.preds = null;
    }

    public Clause(ArrayList<Predicate> preds){
        this.preds = preds;
    }
}
