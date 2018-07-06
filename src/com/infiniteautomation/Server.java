/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class to register an RMI class 'Processor' that will
 * execute any command and return the output via RMI.
 *
 * @author Terry Packer
 */
public class Server implements Processor {
    
    //The port that the RMI Registry will run on
    public static final int PORT = 2001;
    
    public Server() {}

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Processor stub = (Processor) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", PORT);
            try {
                registry.lookup("Processor");
            } catch (NotBoundException e) {
                //The registry is running but the Processor class was not bound
                registry.bind("Processor", stub);
            } catch (RemoteException e) {
                //The registry isn't running so we need to start it and add the Processor 
                registry = server.startRegistry(PORT);
                registry.bind("Processor", stub);
            }

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Start the registry on the given port
     * @param port
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Registry startRegistry(int port) throws IOException, InterruptedException {
        return LocateRegistry.createRegistry(port);
    }

    /**
     * Execute any command
     */
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
