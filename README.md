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

### 需求
在具体的项目开发中，由于axon的异步特性，我们需要提供的功能有
admin端：
1. replay
2. 事件的历史，以及事件的处理状态(目前使用了sent字段来标注，可能还需要更多信息)
用户端：
1.查询用户command历史
2.查询用户事件历史

组件端：
1. 使用cache类组件，加快相关event/replay的速度
2. codeGenerator组件
3. ideaPlugin(官方插件已经长久失修，无法使用)
