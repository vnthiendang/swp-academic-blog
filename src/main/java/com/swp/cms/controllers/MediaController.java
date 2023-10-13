package com.swp.cms.controllers;

import com.swp.services.MediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/media")
public class MediaController {
    private final MediaService mediaService;

    @Autowired
    private ModelMapper modelMapper;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    //add media
}
