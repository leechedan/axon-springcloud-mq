package org.github.axon.tag.common.exception;

/**
 * 第1位为级别(1为http模块,请求异常等;2为业务模块) 2~3位为服务模块 4~6位为自增的错误编码
 *
 * @author lee
 * @date 2018/7/25 15:59
 */
public abstract class BusinessError {

    public static final BusinessErrorCode BU_4004 = new BusinessErrorCode("4004", "uri不存在");
    public static final BusinessErrorCode BU_5000 = new BusinessErrorCode("5000", "系统异常,请检查日志");
    public static final BusinessErrorCode BU_5001 = new BusinessErrorCode("5001", "线程超时");
    public static final BusinessErrorCode BU_9000 = new BusinessErrorCode("9000", "请刷新重试");
    public static final BusinessErrorCode BU_9001 = new BusinessErrorCode("9001", "登录已过期");
    public static final BusinessErrorCode BU_9002 = new BusinessErrorCode("9002", "请求资源失败");
    // 公共部分
    public static final BusinessErrorCode BU_9200 = new BusinessErrorCode("9200", "邮件发送失败");
    public static final BusinessErrorCode BU_9201 = new BusinessErrorCode("9201", "验证码错误");
    public static final BusinessErrorCode BU_9202 = new BusinessErrorCode("9202", "验证失败");
    public static final BusinessErrorCode BU_9203 = new BusinessErrorCode("9203", "获取证书异常");
    public static final BusinessErrorCode BU_9204 = new BusinessErrorCode("9204", "KeyStore需要先初始化");
    public static final BusinessErrorCode BU_9205 = new BusinessErrorCode("9205", "获取私钥异常");
    // admin 部分
    public static final BusinessErrorCode BU_9400 = new BusinessErrorCode("9400", "");
    // user 部分
    public static final BusinessErrorCode BU_9600 = new BusinessErrorCode("9600", "用户不存在");
    public static final BusinessErrorCode BU_9601 = new BusinessErrorCode("9601", "");
    private BusinessError() {
    }
}