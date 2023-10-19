package com.swp.cms.controllers;

import com.swp.cms.dto.MediaDto;
import com.swp.cms.dto.MediaDto;
import com.swp.cms.mapper.MediaMapper;
import com.swp.cms.reqDto.MediaRequest;
import com.swp.entities.Media;
import com.swp.entities.Media;
import com.swp.repositories.MediaRepository;
import com.swp.services.MediaService;
import com.swp.services.MediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/media")
public class MediaController {
    private final MediaRepository mediaRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MediaMapper mapper;
    @Autowired
    private MediaService mediaService;

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @GetMapping("/getall")
    public List<MediaDto> getAll() {
        List<Media> medias = mediaService.getAll();
        List<MediaDto> dto = mapper.fromEntityToMediaDtoList(medias);
        return dto;
    }

    @GetMapping("/{id}")
    public MediaDto getTypeById(@PathVariable Integer id) {

        Media type = mediaService.getById(id);
        MediaDto dto = mapper.fromEntityToMediaDto(type);
        return dto;
    }
    @PostMapping("/post")
    public MediaDto addMedia(@RequestBody MediaRequest mediaRequest) {
//        Media media = modelMapper.map(mediaRequest, Media.class);
        Media createdMedia = mediaService.createMedia(mediaRequest);
        MediaDto mediaDto = modelMapper.map(createdMedia, MediaDto.class);
        return mediaDto;
    }

    //Update a media by media id
    @PutMapping("/{mediaId}")
    public MediaDto updateMedia(@PathVariable Integer mediaId, @RequestBody MediaRequest mediaRequest) {
        Media updatedMedia = mediaService.updateMedia(mediaId, mediaRequest);
        MediaDto mediaDto = modelMapper.map(updatedMedia, MediaDto.class);
        return mediaDto;
    }
}
