/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.kernal.processors.cache.distributed.near;

import org.apache.log4j.*;
import org.gridgain.grid.*;
import org.gridgain.grid.cache.*;
import org.gridgain.grid.kernal.processors.cache.*;
import org.gridgain.grid.spi.discovery.tcp.*;
import org.gridgain.grid.spi.discovery.tcp.ipfinder.*;
import org.gridgain.grid.spi.discovery.tcp.ipfinder.vm.*;

import static org.gridgain.grid.cache.GridCacheAtomicityMode.*;
import static org.gridgain.grid.cache.GridCacheMode.*;
import static org.gridgain.grid.cache.GridCachePreloadMode.*;

/**
 * Tests for partitioned cache transactions.
 */
public class GridCachePartitionedTxSingleThreadedSelfTest extends GridCacheTxSingleThreadedAbstractTest {
    /** Cache debug flag. */
    private static final boolean CACHE_DEBUG = false;

    /** */
    private GridTcpDiscoveryIpFinder ipFinder = new GridTcpDiscoveryVmIpFinder(true);

    /** {@inheritDoc} */
    @SuppressWarnings({"ConstantConditions"})
    @Override protected GridConfiguration getConfiguration(String gridName) throws Exception {
        GridConfiguration c = super.getConfiguration(gridName);

        c.getTransactionsConfiguration().setTxSerializableEnabled(true);

        GridCacheConfiguration cc = defaultCacheConfiguration();

        cc.setCacheMode(PARTITIONED);
        cc.setBackups(1);
        cc.setDistributionMode(GridCacheDistributionMode.NEAR_PARTITIONED);
        cc.setAtomicityMode(TRANSACTIONAL);

        cc.setEvictionPolicy(null);

        cc.setWriteSynchronizationMode(GridCacheWriteSynchronizationMode.FULL_ASYNC);

        cc.setPreloadMode(NONE);

        GridTcpDiscoverySpi spi = new GridTcpDiscoverySpi();

        spi.setIpFinder(ipFinder);
        spi.setMaxMissedHeartbeats(Integer.MAX_VALUE);

        c.setDiscoverySpi(spi);

        c.setCacheConfiguration(cc);

        if (CACHE_DEBUG)
            resetLog4j(Level.DEBUG, false, GridCacheProcessor.class.getPackage().getName());

        return c;
    }

    /** {@inheritDoc} */
    @Override protected int gridCount() {
        return 4;
    }

    /** {@inheritDoc} */
    @Override protected int keyCount() {
        return 3;
    }

    /** {@inheritDoc} */
    @Override protected int maxKeyValue() {
        return 3;
    }

    /** {@inheritDoc} */
    @Override protected int iterations() {
        return 3000;
    }

    /** {@inheritDoc} */
    @Override protected boolean isTestDebug() {
        return false;
    }

    /** {@inheritDoc} */
    @Override protected boolean printMemoryStats() {
        return true;
    }
}
