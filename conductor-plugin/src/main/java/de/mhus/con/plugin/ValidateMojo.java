package de.mhus.con.plugin;

import java.util.Map;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.Context;
import de.mhus.con.api.ExecutePlugin;
import de.mhus.con.api.MojoException;
import de.mhus.con.api.Validator;
import de.mhus.con.core.ConductorImpl;
import de.mhus.lib.core.MLog;

@AMojo(name="con.validate")
public class ValidateMojo extends MLog implements ExecutePlugin {

    @Override
    public boolean execute(Context context) throws Exception {
        Map<String, Validator> validators = ((ConductorImpl)context.getConductor()).getValidators();
        for (String arg : context.getStep().getArguments()) {
            Validator validator = validators.get(arg);
            if (validator == null)
                throw new MojoException(context, "validator not found",arg);
            if (context.getProject() == null) {
                System.out.println(">>> Validate " + arg);
                validator.validate(context.getConductor());
            } else {
                System.out.println(">>> Validate " + context.getProject() + " with " + arg);
                validator.validate(context);
            }
        }
        return true;
    }

}
