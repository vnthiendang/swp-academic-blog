package com.swp.services;

import com.swp.cms.reqDto.TagRequest;
import com.swp.cms.reqDto.TagRequest;
import com.swp.entities.Tag;
import com.swp.entities.Tag;
import com.swp.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag getById(int id) {
        return tagRepository.findById(id).orElseThrow();
    }

    public Boolean existsById(int id) {
        return tagRepository.existsById(id);
    }

    public void deleteById(int id) {
        tagRepository.deleteById(id);
    }

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag createTag(TagRequest tagRequest){
        Tag tag = new Tag();
        tag.setTagName(tagRequest.getTagName());
        tag.setTagDescription(tagRequest.getTagDescription());
        tag.setCreatedDate(OffsetDateTime.now());
        return tagRepository.save(tag);
    }
    public Tag updateTag(Integer tagID, TagRequest tagRequest){
        Tag tag = getById(tagID);
        tag.setTagName(tagRequest.getTagName());
        tag.setTagDescription(tagRequest.getTagDescription());
        tag.setCreatedDate(OffsetDateTime.now());
        return tagRepository.save(tag); // Save and return the updated post
    }
}
