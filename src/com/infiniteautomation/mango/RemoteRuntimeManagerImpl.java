/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.serotonin.m2m2.rt.dataImage.DataPointListener;
import com.serotonin.m2m2.rt.dataImage.DataPointRT;
import com.serotonin.m2m2.rt.dataImage.PointValueTime;
import com.serotonin.m2m2.rt.dataImage.SetPointSource;
import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataSource.DataSourceRT;
import com.serotonin.m2m2.rt.publish.PublisherRT;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.dataSource.mock.MockDataSourceVO;
import com.serotonin.m2m2.vo.publish.PublishedPointVO;
import com.serotonin.m2m2.vo.publish.PublisherVO;

/**
 * 
 * This class represents a mock up of the real runtime manager
 * that could be remotely controlled.
 *
 * @author Terry Packer
 */
public class RemoteRuntimeManagerImpl implements RemoteRuntimeManager {

    private int rmiPort = 2002;
    private int state = PRE_INITIALIZE;
    private List<DataSourceVO<?>> dataSourceVos;
    
    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getState()
     */
    @Override
    public int getState() {
        return state;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#initialize(boolean)
     */
    @Override
    public void initialize(boolean safe) {
        state = INITIALIZE;
        try {
            RemoteRuntimeManager stub = (RemoteRuntimeManager) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", rmiPort);
            try {
                registry.lookup("RuntimeManager");
            } catch (NotBoundException e) {
                //The registry is running but the Processor class was not bound
                registry.bind("RuntimeManager", stub);
            } catch (RemoteException e) {
                //The registry isn't running so we need to start it and add the Processor 
                registry = LocateRegistry.createRegistry(rmiPort);
                registry.bind("RuntimeManager", stub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Create a list of data source VOs
        dataSourceVos = new ArrayList<>();
        MockDataSourceVO ds1 = new MockDataSourceVO();
        ds1.setId(1); //This will NOT get serialized due to the custom serialization not using id
        ds1.setEnabled(true); //This will get serialized
        dataSourceVos.add(ds1); 
        
        
        state = RUNNING;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#terminate()
     */
    @Override
    public void terminate() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#joinTermination()
     */
    @Override
    public void joinTermination() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getRunningDataSource(int)
     */
    @Override
    public DataSourceRT<? extends DataSourceVO<?>> getRunningDataSource(int dataSourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#isDataSourceRunning(int)
     */
    @Override
    public boolean isDataSourceRunning(int dataSourceId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getDataSources()
     */
    @Override
    public List<DataSourceVO<?>> getDataSources() {
        return dataSourceVos;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getDataSource(int)
     */
    @Override
    public DataSourceVO<?> getDataSource(int dataSourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#deleteDataSource(int)
     */
    @Override
    public void deleteDataSource(int dataSourceId) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#saveDataSource(com.serotonin.m2m2.vo.dataSource.DataSourceVO)
     */
    @Override
    public void saveDataSource(DataSourceVO<?> vo) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#initializeDataSourceStartup(com.serotonin.m2m2.vo.dataSource.DataSourceVO)
     */
    @Override
    public boolean initializeDataSourceStartup(DataSourceVO<?> vo) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#stopDataSourceShutdown(int)
     */
    @Override
    public void stopDataSourceShutdown(int id) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#saveDataPoint(com.serotonin.m2m2.vo.DataPointVO)
     */
    @Override
    public void saveDataPoint(DataPointVO point) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#enableDataPoint(com.serotonin.m2m2.vo.DataPointVO, boolean)
     */
    @Override
    public void enableDataPoint(DataPointVO point, boolean enabled) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#deleteDataPoint(com.serotonin.m2m2.vo.DataPointVO)
     */
    @Override
    public void deleteDataPoint(DataPointVO point) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#restartDataPoint(com.serotonin.m2m2.vo.DataPointVO)
     */
    @Override
    public void restartDataPoint(DataPointVO vo) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#isDataPointRunning(int)
     */
    @Override
    public boolean isDataPointRunning(int dataPointId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getDataPoint(int)
     */
    @Override
    public DataPointRT getDataPoint(int dataPointId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#addDataPointListener(int, com.serotonin.m2m2.rt.dataImage.DataPointListener)
     */
    @Override
    public void addDataPointListener(int dataPointId, DataPointListener l) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#removeDataPointListener(int, com.serotonin.m2m2.rt.dataImage.DataPointListener)
     */
    @Override
    public void removeDataPointListener(int dataPointId, DataPointListener l) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getDataPointListeners(int)
     */
    @Override
    public DataPointListener getDataPointListeners(int dataPointId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#setDataPointValue(int, com.serotonin.m2m2.rt.dataImage.types.DataValue, com.serotonin.m2m2.rt.dataImage.SetPointSource)
     */
    @Override
    public void setDataPointValue(int dataPointId, DataValue value, SetPointSource source) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#setDataPointValue(int, com.serotonin.m2m2.rt.dataImage.PointValueTime, com.serotonin.m2m2.rt.dataImage.SetPointSource)
     */
    @Override
    public void setDataPointValue(int dataPointId, PointValueTime valueTime,
            SetPointSource source) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#relinquish(int)
     */
    @Override
    public void relinquish(int dataPointId) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#forcePointRead(int)
     */
    @Override
    public void forcePointRead(int dataPointId) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#forceDataSourcePoll(int)
     */
    @Override
    public void forceDataSourcePoll(int dataSourceId) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValues()
     */
    @Override
    public long purgeDataPointValues() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValuesWithoutCount()
     */
    @Override
    public void purgeDataPointValuesWithoutCount() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValues(int, int, int)
     */
    @Override
    public long purgeDataPointValues(int dataPointId, int periodType, int periodCount) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValues(int)
     */
    @Override
    public long purgeDataPointValues(int dataPointId) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValuesWithoutCount(int)
     */
    @Override
    public boolean purgeDataPointValuesWithoutCount(int dataPointId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValue(int, long)
     */
    @Override
    public long purgeDataPointValue(int dataPointId, long ts) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValues(int, long)
     */
    @Override
    public long purgeDataPointValues(int dataPointId, long before) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValuesBetween(int, long, long)
     */
    @Override
    public long purgeDataPointValuesBetween(int dataPointId, long startTime, long endTime) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#purgeDataPointValuesWithoutCount(int, long)
     */
    @Override
    public boolean purgeDataPointValuesWithoutCount(int dataPointId, long before) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getRunningPublisher(int)
     */
    @Override
    public PublisherRT<?> getRunningPublisher(int publisherId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#isPublisherRunning(int)
     */
    @Override
    public boolean isPublisherRunning(int publisherId) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#getPublisher(int)
     */
    @Override
    public PublisherVO<? extends PublishedPointVO> getPublisher(int publisherId) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#deletePublisher(int)
     */
    @Override
    public void deletePublisher(int publisherId) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.serotonin.m2m2.rt.RuntimeManager#savePublisher(com.serotonin.m2m2.vo.publish.PublisherVO)
     */
    @Override
    public void savePublisher(PublisherVO<? extends PublishedPointVO> vo) {
        // TODO Auto-generated method stub

    }

}
