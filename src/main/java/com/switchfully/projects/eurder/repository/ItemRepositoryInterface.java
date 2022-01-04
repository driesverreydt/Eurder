package com.switchfully.projects.eurder.repository;

import com.switchfully.projects.eurder.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepositoryInterface extends JpaRepository<Item, UUID> {
}
