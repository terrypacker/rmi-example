/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.io.Serializable;

/**
 *
 * @author Terry Packer
 */

public class ProcessOutput implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String out;
    private final String err;
    private final int exitCode;

    public ProcessOutput(String out, String err, int exitCode) {
        this.out = out;
        this.err = err;
        this.exitCode = exitCode;
    }

    public String getOut() {
        return out;
    }

    public String getErr() {
        return err;
    }

    public int getExitCode() {
        return exitCode;
    }
}
