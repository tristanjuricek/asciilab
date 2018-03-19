package com.tristanjuricek.asciilab.api

import com.tristanjuricek.asciilab.api.repository.schema.initializeSchema
import org.apache.commons.dbcp2.DriverManagerConnectionFactory
import org.apache.commons.dbcp2.PoolableConnection
import org.apache.commons.dbcp2.PoolableConnectionFactory
import org.apache.commons.dbcp2.PoolingDataSource
import org.apache.commons.pool2.impl.GenericObjectPool
import org.jetbrains.exposed.sql.Database
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class APIApplication {

    private val httpHandler: HttpHandler

    private val server: HttpServer

    private var nettyContext: BlockingNettyContext? = null

    private val dataSource: PoolingDataSource<PoolableConnection>

    constructor(port: Int = 8080) {
        val context = GenericApplicationContext().apply {
            beans(this).initialize(this)
            refresh()
        }

        server = HttpServer.create(port)

        httpHandler = WebHttpHandlerBuilder
                .applicationContext(context)
                .build()

        initDatabaseDriver()
        dataSource = createDataSource(context.getBean(DbConfig::class.java))
        Database.connect(dataSource)

        initializeSchema()
    }

    private fun initDatabaseDriver() {
        Class.forName("org.postgresql.Driver")
    }

    private fun createDataSource(dbConfig: DbConfig): PoolingDataSource<PoolableConnection> {
        val connectionFactory = DriverManagerConnectionFactory(dbConfig.uri, null)

        val poolableConnectionFactory = PoolableConnectionFactory(connectionFactory, null)

        val connectionPool = GenericObjectPool<PoolableConnection>(poolableConnectionFactory)
        poolableConnectionFactory.pool = connectionPool

        return PoolingDataSource(connectionPool)
    }

    fun start() {
        nettyContext = server.start(ReactorHttpHandlerAdapter(httpHandler))
    }

    fun startAndAwait() {
        server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler), { nettyContext = it })
    }

    fun stop() {
        nettyContext?.shutdown()
        dataSource.close()
    }
}

fun main(args: Array<String>) {
    APIApplication().startAndAwait()
}

