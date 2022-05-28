package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, AuthenticationService authenticationService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials() {
        List<Credential> allCredentials = credentialMapper.getAllCredentials();
        for (Credential credential: allCredentials) {
            System.out.println("KKKKKKKKKKKK getAllCredentials: Key = " + credential.getKey());
            System.out.println("KKKKKKKKKKKK getAllCredentials: Pass = " + credential.getPassword());
            credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return allCredentials;
    }

    public void addCredential(Credential credential) {
        encryptPassword(credential);
        System.out.println("KKKKKKKKKKKK addCredential: " + credential.getKey());
        credentialMapper.insert(credential);
    }

    private void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedKey));
        credential.setKey(encodedKey);
    }

    public void editCredential(Credential credential) {
//        Credential oldCredential = credentialMapper.getCredential(credential.getCredentialid());
//        String oldDecryptedPassword = encryptionService.decryptValue(oldCredential.getPassword(), oldCredential.getKey());
//        System.out.println("KKKKKKKKKKKK editCredential: oldKey = " + oldCredential.getKey());
//        System.out.println("KKKKKKKKKKKK editCredential: oldEnPassword = " + oldCredential.getPassword());
//
//        System.out.println("KKKKKKKKKKKK editCredential: oldPassword = " + oldDecryptedPassword);
//        System.out.println("KKKKKKKKKKKK editCredential: newPassword = " + credential.getDecryptedPassword());
//        if(!credential.getDecryptedPassword().equals(oldDecryptedPassword)){
//            encryptPassword(credential);
//        }

        encryptPassword(credential);
        System.out.println("KKKKKKKKKKKK editCredential: Key = " + credential.getKey());
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }
}
