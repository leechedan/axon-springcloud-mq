package org.github.axon.tag.common.security;

/**
 * @author lee
 * @date 2020/2/25 22:38
 */
public enum SecurityConsts {
    ;
    public static final String SHA256_WITH_RSA = "SHA256withRSA";
    public static final String SHA256 = "SHA256";
    public static final String RSA = "RSA";
    public static final String AES = "AES";
    // 演示项目,随便弄个密码
    public static final String DEFAULT_CODE = "suibiandemima";
    // 自签名KeyStore存放路径
    public static final String SELF_SIGN_CERT_PATH = "src/main/resources/cert/axon.pfx";
    public static final String CERT_PATH = "src/main/resources/cert";
    public static final String SELF_SIGN_CERT_ALIAS = "AXON";
}
