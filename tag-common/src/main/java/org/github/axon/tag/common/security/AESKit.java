package org.github.axon.tag.common.security;

import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lee
 * @date 2020/2/20 22:36
 */
@Slf4j
public enum AESKit {
    ;

    /**
     * @param cipherMode ENCRYPT_MODE: 1; DECRYPT_MODE:2
     * @param workMode   ECB,CBC,CTR,CFB,OFB五选一
     * @param data       加/解密数据
     * @param secretKey  密钥
     * @param padding    填充,当workMode为ECB时可为空
     * @return
     * @throws Exception
     */
    public static byte[] cryption(int cipherMode, String workMode, byte[] data, String secretKey, String padding) {
        try {
            Cipher cipher = Cipher.getInstance(SecurityConsts.AES + "/" + workMode + "/PKCS5Padding");
            byte[] raw = secretKey.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, SecurityConsts.AES);
            if ("ECB".equalsIgnoreCase(workMode)) {
                cipher.init(cipherMode, secretKeySpec);
            } else {
                IvParameterSpec iv = new IvParameterSpec(padding.getBytes());
                cipher.init(cipherMode, secretKeySpec, iv);
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BusinessException(BusinessError.BU_5000);
        }
    }


}
