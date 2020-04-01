package de.mhus.bwk.core;

import de.mhus.lib.core.IReadProperties;
import de.mhus.lib.core.MProperties;
import de.mhus.lib.core.parser.CompiledString;
import de.mhus.lib.core.parser.StringCompiler;
import de.mhus.lib.errors.MException;

public class ContextImpl implements Context {

    private StringCompiler compiler = new StringCompiler();
    private MProperties parameters;

    public ContextImpl(Bwk bwk) {
        parameters = new MProperties(bwk.getParameters());
    }

    public ContextImpl(Bwk bwk, IReadProperties additional) {
        parameters = new MProperties(bwk.getParameters());
        parameters.putReadProperties(additional);
    }
    
    @Override
    public String make(String in) throws MException {
        CompiledString cs = compiler.compileString(in);
        String ret = cs.execute(parameters);
        return ret;
    }

}
