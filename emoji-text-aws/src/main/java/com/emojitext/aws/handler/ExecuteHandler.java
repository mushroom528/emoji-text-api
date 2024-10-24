package com.emojitext.aws.handler;

import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

public class ExecuteHandler extends FunctionInvoker {

    public static final String FUNCTION_NAME = "emojiAppender";

    public ExecuteHandler() {
        super(FUNCTION_NAME);
    }
}
