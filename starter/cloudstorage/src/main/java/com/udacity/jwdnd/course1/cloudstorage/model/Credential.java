package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
  private Integer credentialid;
  private String url;
  private String username;
  private String key;
  private String password;

  private String decryptedPassword;
  private long userid;

  public Integer getCredentialid() {
    return credentialid;
  }

  public void setCredentialid(Integer credentialid) {
    this.credentialid = credentialid;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }

  public String getDecryptedPassword() {
    return decryptedPassword;
  }

  public void setDecryptedPassword(String decryptedPassword) {
    this.decryptedPassword = decryptedPassword;
  }
}
