package org.github.axon.tag.common.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Enumeration;

/**
 * 生成自签名证书
 *
 * @author lee
 * @date 2020/2/17 16:04
 */
@Slf4j
public enum CertificateKit {
    ;
    public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    public static final String END_CERT = "-----END CERTIFICATE-----";

    static void saveCER(X509Certificate x509Certificate, String filepath) {
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            fos.write(x509Certificate.getEncoded());
            fos.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    static void savePEM(X509Certificate x509Certificate, String filepath) {
        try (FileOutputStream fos = new FileOutputStream(filepath);
             PrintStream printStream = new PrintStream(fos)) {
            Base64.Encoder encoder = Base64.getMimeEncoder(64, File.separator.getBytes());
            byte[] rawCrtText = x509Certificate.getEncoded();
            String encodedCertText = new String(encoder.encode(rawCrtText));
            String prettifiedCert = BEGIN_CERT + File.separator + encodedCertText + File.separator + END_CERT;
            printStream.print(prettifiedCert);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    static void saveCRT(X509Certificate x509Certificate, String filepath) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream((x509Certificate.getEncoded()));
             FileOutputStream fos = new FileOutputStream(filepath)) {
            IOUtils.copy(bis, fos);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    /**
     * 保存证书,含密钥
     *
     * @param type       类型 JKS/pkcs12 二选一
     * @param alias      别名
     * @param privateKey 私钥
     * @param certPass   使用证书时的密码,可为空
     * @param storePwd   密钥库密码,可为空
     * @param certChain  证书链
     * @param filepath   输出文件
     * @throws Exception
     */
    static void saveKeyStore(String type, String alias, PrivateKey privateKey, String certPass, String storePwd,
                             X509Certificate[] certChain, String filepath) {
        if (storePwd == null) {
            storePwd = "";
        }
        if (certPass == null) {
            certPass = "";
        }
        // 实例化密钥库,指定类型
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            KeyStore keyStore = KeyStore.getInstance(type);
            // 第一个参数为null,为创建秘钥库
            keyStore.load(null, storePwd.toCharArray());
            keyStore.setKeyEntry(alias, privateKey, certPass.toCharArray(), certChain);
            keyStore.store(fos, storePwd.toCharArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    /**
     * 读取密钥库
     *
     * @param type     密钥库类型 JKS/pkcs12 二选一
     * @param storePwd 密钥库访问密码
     * @param filepath keystore文件路径
     * @return
     * @throws Exception
     */
    static KeyStore getKeyStore(String type, String storePwd, String filepath) {
        if (storePwd == null) {
            storePwd = "";
        }
        try (FileInputStream fis = new FileInputStream(filepath)) {
            // 实例化密钥库,指定类型
            KeyStore keyStore = KeyStore.getInstance(type);
            // 加载密钥库
            keyStore.load(fis, storePwd.toCharArray());
            return keyStore;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    /**
     * 读取密钥库证书及私钥
     *
     * @param certPass 证书访问密码
     * @return
     * @throws Exception
     */
    static PrivateKey getCertPrivateKeyByFirstAlias(KeyStore keyStore, String certPass) {
        if (certPass == null) {
            certPass = "";
        }
        String alias = getFirstAlias(keyStore);
        try {
            return (PrivateKey) keyStore.getKey(alias, certPass.toCharArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_9205);
        }
    }

    /**
     * 读取密钥库证书及私钥
     *
     * @return
     * @throws Exception
     */
    public static Certificate getCertByFirstAlias(KeyStore keyStore) {
        try {
            String alias = getFirstAlias(keyStore);
            return keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            log.error(BusinessError.BU_9204 + "_" + e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_9203);
        }
    }

    private static String getFirstAlias(KeyStore keyStore) {
        try {
            Enumeration aliases = keyStore.aliases();
            String alias = null;
            if (aliases.hasMoreElements()) {
                alias = (String) aliases.nextElement();
            }
            return alias;
        } catch (KeyStoreException e) {
            log.error(BusinessError.BU_9204 + "_" + e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_9203);
        }
    }

    public static X509Certificate getCert(String filepath) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) certificateFactory.generateCertificate(new FileInputStream(filepath));
        } catch (Exception e) {
            log.error(BusinessError.BU_9203 + "_" + e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_9203);
        }
    }
}
