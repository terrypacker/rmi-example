/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Terry Packer
 */
public class Client {
    private Client() {}

    public static void main(String[] args) {
        String host = args.length < 1 ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host, Server.PORT);
            Processor stub = (Processor) registry.lookup("Processor");
            ProcessOutput response = stub.executeCommand(new String[] {"ls"});

            if (response.getExitCode() < 0)
                System.out.println("Error" + response.getErr());
            System.out.println(response.getOut());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
