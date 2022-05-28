package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@NoArgsConstructor
public class File {

  private Integer fileId;
  private String filename;
  private String contenttype;
  private String filesize;
  private Integer userid;
  private InputStream filedata;

  public File(String filename) {
    this.filename = filename;
  }
  public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, InputStream filedata) {
    this.fileId = fileId;
    this.filename = filename;
    this.contenttype = contenttype;
    this.filesize = filesize;
    this.userid = userid;
    this.filedata = filedata;
  }
}
