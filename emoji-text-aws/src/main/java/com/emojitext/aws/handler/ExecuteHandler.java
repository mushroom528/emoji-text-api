package com.emojitext.aws.handler;

import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

public class ExecuteHandler extends FunctionInvoker {

    public static final String EXECUTE_HANDLER = "UppercaseFunction";

    public ExecuteHandler() {
        super(EXECUTE_HANDLER);
    }
}
