package com.best.javaSdk.kdTraceQuery.response;

import com.best.javaSdk.kdTraceQuery.response.Trace;
import java.util.List;


public class Traces {
	private List<Trace> trace;

    public List<Trace>  getTrace()
    {
        return this.trace;
    }

    public void setTrace(List<Trace>  value)
    {
        this.trace = value;
    }

}
