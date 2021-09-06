package com.best.javaSdk.kdTraceQuery.response;

import com.best.javaSdk.kdTraceQuery.response.Trace;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    public void sortByTimeDesc() {
        if (trace == null) {
            return;
        }
        this.trace = this.trace
                .stream()
                .sorted(Comparator.comparing(Trace::getLongAcceptTime).reversed())
                .collect(Collectors.toList());
    }
}
