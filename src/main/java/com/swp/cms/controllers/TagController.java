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

    @GetMapping("/GetAll")
    public List<TagDto> getAll(@RequestParam(name = "keyword", required = false) String keyword) {
        List<Tag> categories = tagService.getAll();
        if (keyword != null && !keyword.trim().isEmpty()) {
            categories = tagService.GetTagsByKeyword(categories, keyword);
        }
        List<TagDto> tagDtos = categories.stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());

        return tagDtos;
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Integer id) {
        Tag tag = tagService.getById(id);
        TagDto dto = modelMapper.map(tag, TagDto.class);
        return dto;
    }

    //create a tag
    @PostMapping("/create")
    public TagDto addTag(@RequestBody TagRequest tagRequest) {
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
