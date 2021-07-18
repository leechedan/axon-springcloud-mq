package org.github.axon.tag.common.security;

import lombok.extern.slf4j.Slf4j;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import javax.crypto.Cipher;

/**
 * RSA非对称加解密
 *
 * @author lee
 * @date 2020/2/17 14:44
 */
@Slf4j
public enum AsymmetricEncryptionKit {
    ;

    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;


    public static byte[] encrypt(byte[] bytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(SecurityConsts.RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    public static byte[] decrypt(byte[] bytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(SecurityConsts.RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SecurityConsts.RSA);
            keyPairGenerator.initialize(KEYSIZE, new SecureRandom());
            return keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }
}
