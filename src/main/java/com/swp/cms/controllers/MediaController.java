package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.mapper.MediaMapper;
import com.swp.entities.Media;
import com.swp.repositories.MediaRepository;
import com.swp.services.MediaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/media")
public class MediaController {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaMapper mediaMapper;
    @Autowired
    public MediaMapper mapper;
    @Autowired
    public MediaService mediaService;

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @GetMapping
    public List<MediaDto> getAll() {
        List<Media> medias = mediaService.getAll();
        List<MediaDto> mediaDtos = mapper.fromEntityToMediaDtoList((Media) medias);
        return mediaDtos;
    }

    @GetMapping("/{id}")
    public MediaDto getMediaById(@PathVariable Integer id) {
        Media media = mediaService.getById(id);
        MediaDto dto = mapper.fromEntityToMediaDto((List<Media>) media);
        return dto;
    }
}
