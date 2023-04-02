public class Term {
    public enum Type{
        constant,
        variable,
        function
    }

    public String t;
    public Type type;

    public Term(String t, Type type){
        this.t = t;
        this.type = type;
    }

    public boolean isVariable(){
        if(this.type == Type.variable)
            return true;
        return false;
    }

    public boolean isConstant(){
        if(this.type == Type.constant)
            return true;
        return false;
    }
}
