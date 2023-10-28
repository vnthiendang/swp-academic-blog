package com.swp.services;


import com.swp.cms.reqDto.MediaRequest;
import com.swp.entities.Media;
import com.swp.repositories.MediaRepository;
import com.swp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private PostRepository postRepository;

    public Media getById(int id) {
        return mediaRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return mediaRepository.existsById(id);
    }

    public void deleteById(int id) {
        mediaRepository.deleteById(id);
    }

    public Media addMedia(Media media) {
        return mediaRepository.save(media);
    }

    public List<Media> getAll() {
        return mediaRepository.findAll();
    }

    public Media createMedia(MediaRequest mediaRequest){
        Media media = new Media();
        media.setMediaUrl(mediaRequest.getMediaURL());
        media.setContentType(mediaRequest.getContentType());
        byte[] imageData = Base64.getDecoder().decode(mediaRequest.getData());
        media.setData(imageData);
        media.setPost(postRepository.findById(mediaRequest.getPostID()).
                orElseThrow(() -> new IllegalArgumentException("Invalid Post")));
        return mediaRepository.save(media);
    }
    public Media updateMedia(Integer mediaID, MediaRequest mediaRequest){
        Media media = getById(mediaID);
        media.setMediaUrl(mediaRequest.getMediaURL());
        return mediaRepository.save(media); // Save and return the updated post
    }
}
