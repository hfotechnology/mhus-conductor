package de.mhus.cur.core;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.parser.CompiledString;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;

public class ContextImpl implements Context {

    private StringCompiler compiler = new StringCompiler();
    private MProperties parameters;

    public ContextImpl(Conductor cur) {
        parameters = new MProperties(cur.getProperties());
    }

    public ContextImpl(Conductor cur, IReadProperties additional) {
        parameters = new MProperties(cur.getProperties());
        parameters.putReadProperties(additional);
    }
    
    @Override
    public String make(String in) throws MException {
        CompiledString cs = compiler.compileString(in);
        String ret = cs.execute(parameters);
        return ret;
    }

}
