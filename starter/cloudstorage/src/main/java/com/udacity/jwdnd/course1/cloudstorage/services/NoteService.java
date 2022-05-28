package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;
    private AuthenticationService authenticationService;

    public NoteService(NoteMapper noteMapper, UserService userService, AuthenticationService authenticationService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public void addNote(Note note) {
        noteMapper.insert(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

    public void editNote(Note note) {
        noteMapper.update(note);
    }
}
