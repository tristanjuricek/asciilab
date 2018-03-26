package com.tristanjuricek.asciilab.api

import org.apache.commons.dbcp2.DriverManagerConnectionFactory
import org.apache.commons.dbcp2.PoolableConnection
import org.apache.commons.dbcp2.PoolableConnectionFactory
import org.apache.commons.dbcp2.PoolingDataSource
import org.apache.commons.pool2.impl.GenericObjectPool

fun initDatabaseDriver() {
    Class.forName("org.postgresql.Driver")
}

fun createDataSource(uri: String): PoolingDataSource<PoolableConnection> {
    val connectionFactory = DriverManagerConnectionFactory(uri, null)

    val poolableConnectionFactory = PoolableConnectionFactory(connectionFactory, null)

    val connectionPool = GenericObjectPool<PoolableConnection>(poolableConnectionFactory)
    poolableConnectionFactory.pool = connectionPool

    return PoolingDataSource(connectionPool)
}
