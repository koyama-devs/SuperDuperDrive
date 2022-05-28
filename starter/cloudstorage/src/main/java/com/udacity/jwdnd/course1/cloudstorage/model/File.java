package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

  private Integer fileId;
  private String filename;
  private String contenttype;
  private String filesize;
  private Integer userid;
  private String filedata;

  public File(String filename) {
    this.filename = filename;
  }
  public File(Integer fileId, String filename, String contenttype, String filesize, Integer userid, String filedata) {
    this.fileId = fileId;
    this.filename = filename;
    this.contenttype = contenttype;
    this.filesize = filesize;
    this.userid = userid;
    this.filedata = filedata;
  }

  public long getFileId() {
    return fileId;
  }

  public void setFileId(Integer fileId) {
    this.fileId = fileId;
  }


  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }


  public String getContenttype() {
    return contenttype;
  }

  public void setContenttype(String contenttype) {
    this.contenttype = contenttype;
  }


  public String getFilesize() {
    return filesize;
  }

  public void setFilesize(String filesize) {
    this.filesize = filesize;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }


  public String getFiledata() {
    return filedata;
  }

  public void setFiledata(String filedata) {
    this.filedata = filedata;
  }

}
