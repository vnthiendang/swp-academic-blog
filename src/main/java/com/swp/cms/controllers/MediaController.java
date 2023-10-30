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
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;

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

//    @GetMapping("/getall")
//    public List<MediaDto> getAll() {
//        List<Media> medias = mediaService.getAll();
//        List<MediaDto> dto = mapper.fromEntityToMediaDtoList(medias);
//        return dto;
//    }

//    @GetMapping("/{id}")
//    public MediaDto getTypeById(@PathVariable Integer id) {
//
//        Media type = mediaService.getById(id);
//        MediaDto dto = mapper.fromEntityToMediaDto(type);
//        return dto;
//    }
//    @PostMapping("/post")
//    public MediaDto addMedia(@RequestBody MediaRequest mediaRequest) {
//        Media createdMedia = mediaService.createMedia(mediaRequest);
//        MediaDto mediaDto = modelMapper.map(createdMedia, MediaDto.class);
//        return mediaDto;
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) {
//        Media media = mediaService.getById(id);
//        if (media != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(media.getContentType()));
//            return new ResponseEntity<>(media.getData(), headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


//    @PostMapping
//    public Media uploadImage(@RequestParam String filename, @RequestParam String contentType, @RequestParam("file") MultipartFile file) throws IOException {
//        byte[] data = file.getBytes();
//        return mediaService.saveImage(filename, contentType, data);
//    }


//    @PostMapping("/post")
//    public ResponseEntity<Media> uploadMedia(@RequestBody @Valid MediaRequest mediaRequest) {
//        try {
//            Media savedMedia = mediaService.createMedia(mediaRequest);
//            return new ResponseEntity<>(savedMedia, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    //Update a media by media id
//    @PutMapping("/{mediaId}")
//    public MediaDto updateMedia(@PathVariable Integer mediaId, @RequestBody MediaRequest mediaRequest) {
//        Media updatedMedia = mediaService.updateMedia(mediaId, mediaRequest);
//        MediaDto mediaDto = modelMapper.map(updatedMedia, MediaDto.class);
//        return mediaDto;
//    }

    @PostMapping()
    public ResponseEntity<?> uploadImage(@RequestParam("media") MultipartFile file, @RequestParam("postId") Integer postId) throws IOException {
        String uploadImage = mediaService.uploadImage(file, postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);

    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = mediaService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }


//chi lay mot image trong mot response thoi, de lay list image cua post, tren frontend phai co vong lap goi n lan api de lay n image


//    @GetMapping("/byPostId")
//    public ResponseEntity<?> downloadImagesByPostId(@RequestParam("postId") Integer postId) {
//        System.out.println("helllllllllllllllllllllllllllllllllllllllllll1111111111111111111111111");
//        List<byte[]> images = mediaService.downloadImagesOfaPostId(postId);
//        System.out.println("222222222222222222222222222222222222222222222222222222222222222222222");
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(images);
//    }


}
