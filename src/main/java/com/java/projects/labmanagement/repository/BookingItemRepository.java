package com.java.projects.labmanagement.repository;

import com.java.projects.labmanagement.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {
}
