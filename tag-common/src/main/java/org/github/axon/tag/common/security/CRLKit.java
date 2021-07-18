package org.github.axon.tag.common.security;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.github.axon.tag.common.exception.BusinessError;
import org.github.axon.tag.common.exception.BusinessException;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;

/**
 * CRL颁发
 *
 * @author lee
 * @date 2020/2/19 10:56
 */
@Slf4j
public enum CRLKit {
    ;

    /**
     * @param revokedCerts 要吊销的证书序列号数组
     * @param revokedDates 吊销日期数组
     * @param crlReasons   吊销原因
     * @param dirName      dn
     * @param privateKey   颁发者私钥
     * @param outputPath   输出目录
     * @return
     * @throws Exception
     */
    public static CertificateList genV2CRL(BigInteger[] revokedCerts, Date[] revokedDates, int[] crlReasons,
                                           String dirName, PrivateKey privateKey, String outputPath) {
        Security.addProvider(new BouncyCastleProvider());
        X509v2CRLBuilder builder = new X509v2CRLBuilder(new X500Name(dirName), new Date());

        for (int i = 0; i < revokedCerts.length; i++) {
            builder.addCRLEntry(revokedCerts[i], revokedDates[i], crlReasons[i]);
        }
        BcRSAContentSignerBuilder bcRSAContentSignerBuilder = new BcRSAContentSignerBuilder(
                new DefaultSignatureAlgorithmIdentifierFinder().find(SecurityConsts.SHA256_WITH_RSA),
                new DefaultDigestAlgorithmIdentifierFinder().find(SecurityConsts.SHA256));
        bcRSAContentSignerBuilder.setSecureRandom(new SecureRandom());
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            // 此处使用的私钥必须是ca的私钥
            AsymmetricKeyParameter foo = PrivateKeyFactory.createKey(privateKey.getEncoded());
            ContentSigner signer = bcRSAContentSignerBuilder.build(foo);
            X509CRLHolder x509CRLHolder = builder.build(signer);
            byte[] bytes = x509CRLHolder.getEncoded();
            fos.write(bytes);
            return x509CRLHolder.toASN1Structure();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(BusinessError.BU_5000);
        }
    }
}
