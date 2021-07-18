package org.github.axon.tag.common.security;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

import java.security.MessageDigest;

/**
 * 消息摘要
 *
 * @author lee
 * @date 2020/2/17 13:56
 */
@Slf4j
public enum DigestKit {
    ;

    /**
     * sha3
     *
     * @param digestLenth 摘要长度,可为224, 256, 384 and 512
     * @param bytes
     * @return
     */
    public static byte[] sha3(int digestLenth, byte[] bytes) {
        Digest digest = new SHA3Digest(digestLenth);
        digest.update(bytes, 0, bytes.length);
        byte[] rsData = new byte[digest.getDigestSize()];
        digest.doFinal(rsData, 0);
        return rsData;
    }

    /**
     * sha
     *
     * @param digestLenth 摘要长度,可为1, 224, 256, 384 and 512
     * @param bytes
     * @return
     */
    public static byte[] sha(int digestLenth, byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha-" + digestLenth);
            return messageDigest.digest(bytes);
        } catch (Exception e) {
            throw new BusinessException(BusinessError.BU_5000);
        }
    }

    public static byte[] sm3(byte[] bytes) {
        Digest digest = new SM3Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] rsData = new byte[digest.getDigestSize()];
        digest.doFinal(rsData, 0);
        return rsData;
    }
}
