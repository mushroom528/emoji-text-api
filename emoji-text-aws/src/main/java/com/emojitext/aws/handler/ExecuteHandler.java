package com.emojitext.aws.handler;

import org.springframework.cloud.function.adapter.aws.FunctionInvoker;

public class ExecuteHandler extends FunctionInvoker {

    public static final String EXECUTE_HANDLER = "emojiAppender";

    public ExecuteHandler() {
        super(EXECUTE_HANDLER);
    }
}
