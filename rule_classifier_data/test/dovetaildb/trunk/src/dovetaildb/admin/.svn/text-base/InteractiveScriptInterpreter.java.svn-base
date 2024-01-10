package dovetaildb.admin;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class InteractiveScriptInterpreter
{
    final private ScriptEngine engine;
    final private Bindings bindings;
    final private StringBuilder sb;
    private int pendingLineCount;
    private boolean expectingMoreInput;

    /**
     * @param engine ScriptingEngine to use in this interpreter
     * @param bindings Bindings to use in this interpreter
     */
    public InteractiveScriptInterpreter(ScriptEngine engine, Bindings bindings) 
    { 
        this.engine = engine; 
        this.bindings = bindings;
        this.sb = new StringBuilder();
        reset();
    }       
    /** @return ScriptEngine used by this interpreter */
    public ScriptEngine getEngine() { return this.engine; }
    protected void reset() { 
        this.sb.setLength(0);
        this.pendingLineCount = 0;
        setExpectingMoreInput(false);
    }
    /** @return whether the interpreter is ready for a brand new statement. */
    public boolean isReady() { return this.sb.length() == 0; }
    /**
     * @return whether the interpreter expects more input
     * 
     * A true value means there is definitely more input needed.
     * A false value means no more input is needed, but it may not yet
     * be appropriate to evaluate all the pending lines.
     * (there's some ambiguity depending on the language)
     */
    public boolean isExpectingMoreInput() { return this.expectingMoreInput; }
    protected void setExpectingMoreInput(boolean b) { this.expectingMoreInput = b; }
    /**
     * @return number of lines pending execution
     */
    protected int getPendingLineCount() { return this.pendingLineCount; }
    /**
     * @param lineIsEmpty whether the last line is empty
     * @return whether we should evaluate the pending input
     * The default behavior is to evaluate if we only have one line of input,
     * or if the user enters a blank line.
     * This behavior should be overridden where appropriate.
     */
    protected boolean shouldEvaluatePendingInput(boolean lineIsEmpty) 
    {
        if (isExpectingMoreInput())
            return false;
        else
            return (getPendingLineCount() == 1) || lineIsEmpty; 
    } 
    /**
     * @param line line to interpret
     * @return value of the line (or null if there is still pending input)
     * @throws ScriptException in case of an exception
     */
    public final static Object NOT_DONE = new Object(); 
    public Object interpret(String line) throws ScriptException
    {
    	CompiledScript cs = addInput(line);
    	if (cs != null) {
	        try {
	            Object result = cs.eval(this.bindings);
	            return result;
	        }
	        finally
	        {
	            reset();
	        }
    	} else {
    		return NOT_DONE;
    	}
    }
    private CompiledScript tryCompiling(String string, int lineCount, int lastLineLength)
        throws ScriptException 
    {
        CompiledScript result = null;
        try
        {
            Compilable c = (Compilable)this.engine;
            result = c.compile(string);
        } catch (ScriptException se) {}
        setExpectingMoreInput(result == null);
        return result;
    }
	public CompiledScript addInput(String line) throws ScriptException {
        if (line.isEmpty()) {
            if (!shouldEvaluatePendingInput(true))
                return null;
        }
        ++this.pendingLineCount;        
        this.sb.append(line);
        this.sb.append("\n");
        CompiledScript cs = tryCompiling(this.sb.toString(), getPendingLineCount(), line.length());

        if (cs == null) {
            return null;
        } else if (shouldEvaluatePendingInput(line.isEmpty())) {
        	return cs;
        } else {
            return null;
        }
	}
	public String getPendingInput() {
		return sb.toString();
	}

}
