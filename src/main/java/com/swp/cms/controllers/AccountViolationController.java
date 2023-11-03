package com.swp.cms.controllers;

import com.swp.cms.dto.AccountViolationDto;
import com.swp.cms.reqDto.AccountViolationRequest;
import com.swp.entities.AccountViolation;
import com.swp.services.AccountViolationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog/accountViolation")
public class AccountViolationController {

    @Autowired
    private final AccountViolationService accountViolationService;
    @Autowired
    private ModelMapper modelMapper;

    public AccountViolationController(AccountViolationService accountViolationService) {
        this.accountViolationService = accountViolationService;
    }

    @GetMapping("/getall")
    public List<AccountViolationDto> getAll() {
        List<AccountViolation> categories = accountViolationService.getAll();
        List<AccountViolationDto> accountViolationDtos = categories.stream()
                .map(accountViolation -> modelMapper.map(accountViolation, AccountViolationDto.class))
                .collect(Collectors.toList());

        return accountViolationDtos;
    }

    @GetMapping("/{id}")
    public AccountViolationDto getAccountViolationById(@PathVariable Integer id) {
        AccountViolation accountViolation = accountViolationService.getById(id);
        AccountViolationDto dto = modelMapper.map(accountViolation, AccountViolationDto.class);
        return dto;
    }

    //create a accountViolation
    @PostMapping("/post")
    public AccountViolationDto addAccountViolation(@RequestBody AccountViolationRequest accountViolationRequest) {
        System.out.println("hellooooooooooooooooooooooooooooooo");
        AccountViolation createdAccountViolation = accountViolationService.createAccountViolation(accountViolationRequest);
        System.out.println("hellooooooooooooooooooooooooooooooo1");
        AccountViolationDto accountViolationDto = modelMapper.map(createdAccountViolation, AccountViolationDto.class);
        System.out.println("hellooooooooooooooooooooooooooooooo2");
        return accountViolationDto;
    }
//
//    //Update a accountViolation by accountViolation id
//    @PutMapping("/{accountViolationId}")
//    public AccountViolationDto updateAccountViolation(@PathVariable Integer accountViolationId, @RequestBody AccountViolationRequest accountViolationRequest) {
//        AccountViolation updatedAccountViolation = accountViolationService.updateAccountViolation(accountViolationId, accountViolationRequest);
//        AccountViolationDto accountViolationDto = modelMapper.map(updatedAccountViolation, AccountViolationDto.class);
//        return accountViolationDto;
//    }
}