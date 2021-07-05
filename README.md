## axonframework可能的最佳实现

### 关于序列化
建议使用xstream，否则很容易出问题，不会对aggregate和saga中的bean进行序列化，但是在upcaster中会比较难处理

如果使用jackson，该序列化方式依赖于entry的get/set函数，那么在saga、aggregate、event中引入的相关bean 不能设置get和set函数，否则会报序列化失败。该序列化方便的地方在于upcaster过程中，可以使用jsonNode的数据结构直接修改event的数据

### mq特性
使用axon.amqp.enable=true/false axon.kafka.enable=true/false来进行切换

### springcloud
建议使用nacos，将readme.md中的配置上传，并适当修改nacos中的name配置

建议discovert的ip/port一定要配置正确，这代表了server互相调用时的地址，否则会报no handler found

### messageConverter
建议一定谨慎修改系统的messageConverter以及objectmapper，该问题会影响组件springcloud的restTemplate处理body，处理不当会报objectType cannot be null

### subscribe 和tracking
前者不能被replay，后者可以，这是主要区别，目前已经确定amqp正常，kafka的tracking还未确认

### jpa/jdbc
两者一定不能混用，后者还未集成，不知是否能集成mybatis

### 调优
在框架使用过程中，调优的方向有缓存和快照
缓存：相关event进行内存型缓存处理，加快load和存储速度
快照：使用最近快照进行rebuild

### 特性
在具体的项目开发中，由于axon的异步特性，我们需要提供的功能有
1. - [x] springcloud分布式命令总线集成
2. - [x] mq-amqp集成
3. - [x] mq-kafka集成(streamingMessageSource未验证)
4. - [x] saga集成(带deadline的向前重试和向后回滚)
5. - [x] listener集成
6. - [x] 命令拦截器
7. - [x] admin端-replay
8. - [x] upcaster特性，用户可以针对任何命令的版本升级进行链式处理
9. - [x] 事件的历史，以及事件的处理状态(目前使用了sent字段来标注，可能还需要更多信息)
10. - [x]  查询用户事件历史
11. - [ ]  单服务多实例运行时，processor的调优处理[tuning-event-processing](https://docs.axoniq.io/reference-guide/v/4.2/operations-guide/runtime-tuning/tuning-event-processing)
12. - [ ] 用户端：查询command的简单处理
13. - [ ] 用户端：stream聚合类复杂查询command处理
14. - [ ] 组件端：使用cache类组件，加快相关event/replay的速度
15. - [ ] codeGenerator组件
16. - [ ] metrics处理
17. - [ ] ideaPlugin(官方插件已经长久失修，无法使用)
