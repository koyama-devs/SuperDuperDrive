package com.udacity.jwdnd.course1.cloudstorage.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();

	List<File> getAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
