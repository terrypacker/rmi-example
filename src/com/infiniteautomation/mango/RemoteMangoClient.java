/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import com.serotonin.m2m2.vo.dataSource.DataSourceVO;

/**
 * Client that can control the RemoteRuntimeManager
 *
 * @author Terry Packer
 */
public class RemoteMangoClient {
    
    public static void main(String[] args) {
        String host = args.length < 1 ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host, 2002);
            RemoteRuntimeManager stub = (RemoteRuntimeManager) registry.lookup("RuntimeManager");
            
            List<DataSourceVO<?>> dataSourceVos = stub.getDataSources();
            for(DataSourceVO<?> vo : dataSourceVos) {
                //This will be -1 due to custom serialization of the Data Source VO
                System.out.println("Data Source id: " + vo.getId());
                System.out.println("Data Source Enabled: " + vo.isEnabled() );
            }
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
