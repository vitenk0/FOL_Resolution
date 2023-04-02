public class Function extends Term{
    public String function;
    public Type type;
    public Term term;

    public Function(String function, Term term){
        super(function, Type.function);
        this.term = term;
    }
}
