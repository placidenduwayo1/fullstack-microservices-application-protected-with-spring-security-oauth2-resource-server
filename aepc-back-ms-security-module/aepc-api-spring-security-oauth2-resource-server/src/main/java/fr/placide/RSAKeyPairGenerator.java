package fr.placide;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
@Slf4j

public class RSAKeyPairGenerator {
    private static final String FILE_PATH= "spring-security-service/src/main/resources/keys/";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte [] publicKey = keyPair.getPublic().getEncoded();
        byte [] privateKey = keyPair.getPrivate().getEncoded();

        // prepare writing keys into files
        try {
            PemWriter pemWriterPublicKey = new PemWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH+"PUBLIC_KEY.pem")));
            PemWriter pemWriterPrivateKey = new PemWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH+"PRIVATE_KEY.pem")));
            PemObject pemObjectPublicKey = new PemObject("PUBLIC KEY", publicKey);
            PemObject pemObjectPrivateKey = new PemObject("PRIVATE KEY", privateKey);
            pemWriterPublicKey.writeObject(pemObjectPublicKey);
            pemWriterPrivateKey.writeObject(pemObjectPrivateKey);
            pemWriterPublicKey.close();
            pemWriterPrivateKey.close();
        }
        catch (IOException exception){
            log.error(exception.getMessage());
        }


    }
}
