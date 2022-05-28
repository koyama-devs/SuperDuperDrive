package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Credential {
  private Integer credentialid;
  private String url;
  private String username;
  private String key;
  private String password;
  private String decryptedPassword;
  private long userid;
}
