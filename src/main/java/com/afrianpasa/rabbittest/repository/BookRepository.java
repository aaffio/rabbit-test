package com.afrianpasa.rabbittest.repository;

import com.afrianpasa.rabbittest.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
