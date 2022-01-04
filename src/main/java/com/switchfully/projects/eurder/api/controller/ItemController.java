package com.switchfully.projects.eurder.api.controller;

import com.switchfully.projects.eurder.api.dto.ItemDto;
import com.switchfully.projects.eurder.security.AuthorizationService;
import com.switchfully.projects.eurder.security.EurderFeature;
import com.switchfully.projects.eurder.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final AuthorizationService authorizationService;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemService itemService, AuthorizationService authorizationService) {
        this.itemService = itemService;
        this.authorizationService = authorizationService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestBody ItemDto itemDto, @RequestHeader(required = false) String authorization) {
        logger.info("Add item method called");
        authorizationService.authorize(EurderFeature.ITEM_CREATE, authorization);
        ItemDto savedItemDto = itemService.saveItemDto(itemDto);
        logger.info("Add item method successfully finished");
        return savedItemDto;
    }
}
