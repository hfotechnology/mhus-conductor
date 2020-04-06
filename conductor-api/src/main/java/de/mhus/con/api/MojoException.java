package de.mhus.con.api;

import de.mhus.lib.errors.MException;

public class MojoException extends MException {

    private static final long serialVersionUID = 1L;
    private Context context;
    
    public MojoException(Context context, Object ... in) {
        super(in);
        this.context = context;
    }
    
    @Override
    public String toString() {
        return (context == null ? "" :
              "step=" + context.getStep() 
              + "\nproject=" + context.getProject()
              + "\nplugin=" + context.getPlugin()
              + "\n"
              ) + "message=" + super.toString();
    }

}
