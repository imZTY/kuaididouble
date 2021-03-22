package com.best.javaSdk.kdTraceQuery.response;

import com.best.javaSdk.kdTraceQuery.response.Problem;
import java.util.List;


public class Problems {
	private List<Problem> problem;

    public List<Problem>  getProblem()
    {
        return this.problem;
    }

    public void setProblem(List<Problem>  value)
    {
        this.problem = value;
    }

}
