package com.swp.services;


import com.swp.cms.reqDto.MediaRequest;
import com.swp.entities.Media;
import com.swp.repositories.MediaRepository;
import com.swp.repositories.PostRepository;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

    public void upLoadImage(MultipartFile file, Integer postId) throws IOException {
        Media media = mediaRepository.save(Media.builder()
                .post(postRepository.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Post")))
                .mediaUrl("chua co tinh nang nay")
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (media!= null){
            System.out.println("file uploaded successfully: " + file.getOriginalFilename());
        } else {

        }
    }
    public String uploadImage(MultipartFile file, Integer postId) throws IOException {
        Media media = mediaRepository.save(Media.builder()
                .post(postRepository.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Post")))
                .mediaUrl("chua co tinh nang nay")
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (media!= null){
            return "file uploaded successfully: " + file.getOriginalFilename();
        }
        return null;
    }
@Transactional
    public byte[] downloadImage(String fileName){
        Optional<Media> media = mediaRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(media.get().getData());
        return images;
    }

//@Autowired
//private DataSource dataSource; // Autowire your data source
//
//    @Transactional
//    public byte[] downloadImage(String fileName) {
//        Optional<Media> mediaOptional = mediaRepository.findByName(fileName);
//
//        if (mediaOptional.isPresent()) {
//            try (Connection connection = dataSource.getConnection()) {
//                int oid = mediaOptional.get().getOid();
//                LargeObject largeObject = null;
//
//                try {
//                    // Create a LargeObject
//                    LargeObjectManager largeObjectManager = LargeObjectManager.open(connection);
//                    largeObject = largeObjectManager.open(oid, LargeObjectManager.READ);
//
//                    byte[] data = largeObject.read(largeObject.size());
//
//                    return data;
//                } catch (SQLException e) {
//                    // Handle the exception as needed
//                    e.printStackTrace();
//                    return null; // or throw a custom exception
//                } finally {
//                    if (largeObject != null) {
//                        largeObject.close();
//                    }
//                }
//            } catch (SQLException e) {
//                // Handle the exception as needed
//                e.printStackTrace();
//                return null; // or throw a custom exception
//            }
//        } else {
//            // Handle the case where the media is not found
//            return null;
//        }
//    }

    public List<byte[]> downloadImagesOfaPostId(Integer postId) {
        List<byte[]> imagesList = new ArrayList<>();
        System.out.println("33333333333333333333333333333333333333333333333333333333333333333333333333");
        List<Media> mediaList = mediaRepository.findByPostPostsId(postId);
        System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444");
        for (Media media : mediaList) {
            byte[] image = ImageUtils.decompressImage(media.getData());
            //getName cho nay tra ve mot dong tk co name= null, nen bi looi
            imagesList.add(image);
        }

        return imagesList;
    }




}
