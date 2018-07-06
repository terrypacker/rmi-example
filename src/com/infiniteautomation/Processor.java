/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author Terry Packer
 */
public abstract interface Processor extends Remote {
    
    /**
     * Execute the command
     * @param paramVarArgs
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws RemoteException
     */
    public abstract ProcessOutput executeCommand(String... paramVarArgs)
            throws IOException, InterruptedException, RemoteException;
}
