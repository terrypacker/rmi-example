/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.io.IOException;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



public class Server implements Processor {
    public Server() {}

    public static void main(String[] args) {
        int port = 2001;
        try {
            Server server = new Server();
            Processor stub = (Processor) UnicastRemoteObject.exportObject(server, 0);


            Registry registry = LocateRegistry.getRegistry("localhost", port);
            try {
                registry.lookup("Processor");
            } catch (NotBoundException e) {
                registry.bind("Processor", stub);
            } catch (RemoteException e) {
                registry = server.startRegistry(port);
                registry.bind("Processor", stub);
            }

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public Registry startRegistry(int port) throws IOException, InterruptedException {
        return LocateRegistry.createRegistry(port);
    }



    public ProcessOutput executeCommand(String... commands)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();
        InputReader out = new InputReader(process.getInputStream());
        InputReader err = new InputReader(process.getErrorStream());
        process.waitFor();

        out.join();
        String outMsg = out.getInput();
        err.join();
        String errMsg = err.getInput();

        return new ProcessOutput(outMsg, errMsg, process.exitValue());
    }
}
