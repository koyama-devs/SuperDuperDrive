package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {

  private Integer noteid;
  private String notetitle;
  private String notedescription;
  private Integer userid;

  public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
    this.noteid = noteid;
    this.notetitle = notetitle;
    this.notedescription = notedescription;
    this.userid = userid;
  }
}
