package com.swp.cms.controllers;
import com.swp.cms.dto.TagDto;
import com.swp.cms.reqDto.TagRequest;
import com.swp.entities.Tag;
import com.swp.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/tag")
public class TagController {

    @Autowired
    private final TagService tagService;
    @Autowired
    private ModelMapper modelMapper;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getall")
    public List<TagDto> getAll() {
        List<Tag> categories = tagService.getAll();
        List<TagDto> tagDtos = categories.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());

        return tagDtos;
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Integer id) {
//                    System.out.println(" ID: hellosfdsdddddddddddddddddddddddddddddddddddddddddddddddd");
        Tag tag = tagService.getById(id);
//                    System.out.println(" ID: " + cate.getCateId());
//            System.out.println("Post ID: " + cate.getContent());
//            System.out.println("Status: " + cate.getParentTag());
//            System.out.println("Created Date: " + cate.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        TagDto dto = modelMapper.map(tag, TagDto.class);
//                    System.out.println(" ID: " + dto.getId());
//            System.out.println("Post ID: " + dto.getContent());
//            System.out.println("Status: " + dto.getParentTag());
//            System.out.println("Created Date: " + dto.getCreatedDate());
//            System.out.println("sucesssssssssssssssssssssssssssDto");
        return dto;
    }

    //create a tag
    @PostMapping("/post")
    public TagDto addTag(@RequestBody TagRequest tagRequest) {
//        Tag tag = modelMapper.map(tagRequest, Tag.class);
        Tag createdTag = tagService.createTag(tagRequest);
        TagDto tagDto = modelMapper.map(createdTag, TagDto.class);
        return tagDto;
    }

    //Update a tag by tag id
    @PutMapping("/{tagId}")
    public TagDto updateTag(@PathVariable Integer tagId, @RequestBody TagRequest tagRequest) {
        Tag updatedTag = tagService.updateTag(tagId, tagRequest);
        TagDto tagDto = modelMapper.map(updatedTag, TagDto.class);
        return tagDto;
    }
}
