/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango;

import java.io.IOException;

/**
 * 
 * Simple lifecycle that starts up our RuntimeManager so 
 * we can access it from the RemoteMangoClient
 *
 * @author Terry Packer
 */
public class Lifecycle {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        RemoteRuntimeManager runtimeManager = new RemoteRuntimeManagerImpl();
        try {
            runtimeManager.initialize(false);
            System.out.println("Runtime Manager running.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
