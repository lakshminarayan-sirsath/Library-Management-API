package com.task.library.management.JpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.library.management.Entity.Fine;

public interface FineRepo extends JpaRepository<Fine, Integer> {

}
