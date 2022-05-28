package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private AuthenticationService authenticationService;

    public CredentialService(CredentialMapper credentialMapper, AuthenticationService authenticationService) {
        this.credentialMapper = credentialMapper;
        this.authenticationService = authenticationService;
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public void addCredential(Credential credential) {
        credentialMapper.insert(credential);
    }

    public void editCredential(Credential credential) {
        credentialMapper.update(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }
}
