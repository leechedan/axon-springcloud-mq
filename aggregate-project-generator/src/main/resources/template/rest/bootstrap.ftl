server.port: ${SERVER_PORT:8092}
spring:
  profiles:
    active:
      - dev
  application:
    name: ${className}
  cloud:
    nacos:
      config:
        name: user
        password: nacos
        username: nacos
        file-extension: yml
        enabled: true
        server-addr: ${(NACOS_ADDR)!}
      discovery:
        ip: localhost
        port: ${server.port}
        password: nacos
        username: nacos
        server-addr: ${(NACOS_ADDR)!}
    consul:
      host: ${DOCKER_HOST:localhost}
      port: 8500
      discovery:
        hostname: ${DISCOVERY_HOST:lolcalhost}
        port: ${SERVER_PORT:8090}
        service-name: ${DISCOVERY_HOST:localhost}
        instance-id: ${DISCOVERY_ID:accountservice}
    zookeeper:
      connect-string: ${DOCKER_HOST:localhost}:${ZK_PORT:12181}
      discovery:
        service-name: ${DISCOVERY_SERVICE:accountservice}
        instance-host: ${DISCOVERY_HOST:localhost}
        instance-id: ${DISCOVERY_ID:accountservice}
        instance-port: ${SERVER_PORT:8090}

