/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.io.IOException;
import java.rmi.Remote;


/**
 *
 * @author Terry Packer
 */
public abstract interface Processor extends Remote {
    public abstract ProcessOutput executeCommand(String... paramVarArgs)
            throws IOException, InterruptedException;
}
