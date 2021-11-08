spring:
  rabbitmq:
    host: 47.52.30.71
    port: 25672
    username: guest
    password: guest
    template:
      exchange: ${EXCHANGE_NAME:BankEx}
  datasource:
    url: jdbc:mysql://${JDBC_HOST:localhost}:3306/${JDBC_DB}?useUnicode=true&autoReconnect=true&rewriteBatchedStatements=TRUE
    username: ${JDBC_NAME:root}
    password: ${JDBC_PWD:123456}
  jpa:
    hibernate:
      ddl-auto: update
      use-new-id-generator-mappings: false
    show-sql: false
    properties:
      hibernate.dialect: org.hibernate.dialect.MySQL57Dialect
axon:
  queue: user
  amqp.exchange: ${EXCHANGE_NAME:BankEx}
  serializer:
    general: jackson
  distributed:
    enabled: true
    spring-cloud:
      fallback-to-http-get: true
      fallback-url: /axon-routing
  kafka:
    bootstrap-servers: ${KAFKA_HOST:47.52.30.71}:9092
    client-id: kafka-axon-user
    default-topic: localevent
    properties:
      security.protocol: PLAINTEXT

    publisher:
      confirmation-mode: transactional
    producer:
      transaction-id-prefix: kafka-sample
      retries: 1
      event-processor-mode: tracking
      # For additional unnamed properties, add them to the `properties` map like so
      properties:
        some-key: [some-value]

    fetcher:
      poll-timeout: 3000

    consumer:
      enable-auto-commit: true
      auto-commit-interval: 3000
      event-processor-mode: tracking
      # For additional unnamed properties, add them to the `properties` map like so
      properties:
        some-key: [some-value]
server:
  port: ${SERVER_PORT:8090}

management:
  endpoints:
    web:
      exposure:
        # 开放监控内容
        include: "*"
  endpoint:
    shutdown:
      enabled: true #启用shutdown端点
    health:
      # health/detail 细节（）
      show-details: always

# swagger 开关 true为开启，false或没有为关闭
swagger:
  enable: @swagger.enable@


## 日志配置
logging:
  level.org:
    springframework:
      web: INFO
      cloud.sleuth: INFO
    apache.ibatis: INFO
    java.sql: INFO
    hibernate:
      SQL: INFO
#      type.descriptor.sql: TRACE

---
#控制台输出彩色
spring:
  output:
    ansi:
      enabled: always