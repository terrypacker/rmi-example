/**
 * Copyright (C) 2018 Infinite Automation Software. All rights reserved.
 */
package com.infiniteautomation.mango;

import java.io.IOException;
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
import com.serotonin.m2m2.vo.publish.PublishedPointVO;
import com.serotonin.m2m2.vo.publish.PublisherVO;

/**
 * 
 * Mock of what changes would need to be made to the real RuntimeManager
 * interface in Mango
 * 
 * To generalize the method signatures I have replaced the requirement
 * of each method to throw java.rmi.RemoteException with IOException 
 * its superclass.
 * 
 * NOTE that any VO will not serialize properly because we use 
 * custom serialization to store them into the database without 
 * an id,xid,name
 *
 * @author Terry Packer
 */
public interface RuntimeManager {

    public static final int NOT_STARTED = 0;
    public static final int PRE_INITIALIZE = 10;
    public static final int INITIALIZE = 20;
    public static final int RUNNING = 30;
    public static final int TERMINATE = 40;
    public static final int POST_TERMINATE = 50;
    public static final int TERMINATED = 60;
    
    /**
     * Check the state of the RuntimeManager
     *  useful if you are a task that may run before/after the RUNNING state
     * @return
     */
    int getState() throws IOException;

    //
    // Lifecycle
    void initialize(boolean safe) throws IOException;

    void terminate() throws IOException;

    void joinTermination() throws IOException;

    //
    //
    // Data sources
    //
    DataSourceRT<? extends DataSourceVO<?>> getRunningDataSource(int dataSourceId) throws IOException;

    boolean isDataSourceRunning(int dataSourceId) throws IOException;

    List<DataSourceVO<?>> getDataSources() throws IOException;

    DataSourceVO<?> getDataSource(int dataSourceId) throws IOException;

    void deleteDataSource(int dataSourceId) throws IOException;

    void saveDataSource(DataSourceVO<?> vo) throws IOException;
    
    /**
     * Initialize a data source (only to be used at system startup)
     * @param vo
     * @return
     */
    boolean initializeDataSourceStartup(DataSourceVO<?> vo) throws IOException;
    
    /**
     * Stop a data source (only to be used at system shutdown)
     * @param id
     */
    void stopDataSourceShutdown(int id) throws IOException;

    //
    //
    // Data points
    //
    void saveDataPoint(DataPointVO point) throws IOException;
    
    void enableDataPoint(DataPointVO point, boolean enabled) throws IOException;

    void deleteDataPoint(DataPointVO point) throws IOException;

    void restartDataPoint(DataPointVO vo) throws IOException;

    boolean isDataPointRunning(int dataPointId) throws IOException;

    DataPointRT getDataPoint(int dataPointId) throws IOException;

    void addDataPointListener(int dataPointId, DataPointListener l) throws IOException;

    void removeDataPointListener(int dataPointId, DataPointListener l) throws IOException;

    DataPointListener getDataPointListeners(int dataPointId) throws IOException;

    //
    // Point values
    void setDataPointValue(int dataPointId, DataValue value, SetPointSource source) throws IOException;

    void setDataPointValue(int dataPointId, PointValueTime valueTime, SetPointSource source) throws IOException;

    void relinquish(int dataPointId) throws IOException;

    /**
     * This method forces a point read ONLY if the 
     * underlying data source has implemented that ability.
     * 
     * Currently only a few data sources implement this functionality
     * @param dataPointId
     */
    void forcePointRead(int dataPointId) throws IOException;

    void forceDataSourcePoll(int dataSourceId) throws IOException;

    long purgeDataPointValues() throws IOException;

    void purgeDataPointValuesWithoutCount() throws IOException;

    long purgeDataPointValues(int dataPointId, int periodType, int periodCount) throws IOException;

    long purgeDataPointValues(int dataPointId) throws IOException;

    /**
     * @param id
     */
    boolean purgeDataPointValuesWithoutCount(int dataPointId) throws IOException;

    /**
     * Purge a value at a given time
     * @param dataPointId
     * @param ts
     * @return
     */
    long purgeDataPointValue(int dataPointId, long ts) throws IOException;

    long purgeDataPointValues(int dataPointId, long before) throws IOException;

    long purgeDataPointValuesBetween(int dataPointId, long startTime, long endTime) throws IOException;

    /**
     * Purge values before a given time
     * @param dataPointId
     * @param before
     * @return true if any data was deleted
     */
    boolean purgeDataPointValuesWithoutCount(int dataPointId, long before) throws IOException;

    //
    //
    // Publishers
    //
    PublisherRT<?> getRunningPublisher(int publisherId) throws IOException;

    boolean isPublisherRunning(int publisherId) throws IOException;

    PublisherVO<? extends PublishedPointVO> getPublisher(int publisherId) throws IOException;

    void deletePublisher(int publisherId) throws IOException;

    void savePublisher(PublisherVO<? extends PublishedPointVO> vo) throws IOException;
    
}
