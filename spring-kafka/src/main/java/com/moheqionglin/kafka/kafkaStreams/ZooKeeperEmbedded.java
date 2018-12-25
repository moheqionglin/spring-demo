package com.moheqionglin.kafka.kafkaStreams;

import org.apache.curator.test.TestingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Runs an in-memory, "embedded" instance of a ZooKeeper server.
 *
 * The ZooKeeper server instance is automatically started when you create a new instance of this class.
 */
public class ZooKeeperEmbedded {

  private static final Logger log = LoggerFactory.getLogger(ZooKeeperEmbedded.class);

  private static final int DEFAULT_PORT = 2188;

  private final TestingServer server;

  /**
   * Starts a ZooKeeper instance that listens on port 2181.
   * @throws Exception
   */
  public ZooKeeperEmbedded() throws Exception {
    this(DEFAULT_PORT);
  }

  /**
   * Starts a ZooKeeper instance that listens at the defined port.
   *
   * @param port The port (aka `clientPort`) to listen to.  Default: 2181.
   * @throws Exception
   */
  public ZooKeeperEmbedded(int port) throws Exception {
    log.debug("Starting embedded ZooKeeper server on port {} ...", port);
    this.server = new TestingServer(port);
  }

  public void stop() throws IOException {
    log.debug("Shutting down embedded ZooKeeper server at {} ...", server.getConnectString());
    server.close();
    log.debug("Shutdown of embedded ZooKeeper server at {} completed", server.getConnectString());
  }

  /**
   * The ZooKeeper connection string aka `zookeeper.connect` in `hostnameOrIp:port` format.
   * Example: `127.0.0.1:2181`.
   *
   * You can use this to e.g. tell Kafka brokers how to connect to this instance.
   */
  public String connectString() {
    return server.getConnectString();
  }

  /**
   * The hostname of the ZooKeeper instance.  Example: `127.0.0.1`
   */
  public String hostname() {
    // "server:1:2:3" -> "server:1:2"
    return connectString().substring(0, connectString().lastIndexOf(':'));
  }

}