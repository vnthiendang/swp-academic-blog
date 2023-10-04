package com.swp.services;

import com.swp.entities.Media;
import com.swp.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    public Media getById(int id) {
        return mediaRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return mediaRepository.existsById(id);
    }

    public void deleteById(int id) {
        mediaRepository.deleteById(id);
    }

    public Media add(Media media) {
        return mediaRepository.save(media);
    }

    public List<Media> getAll() {
        return mediaRepository.findAll();
    }
}
