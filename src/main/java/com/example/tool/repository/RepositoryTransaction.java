package com.example.tool.repository;


import com.example.tool.model.TransactionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryTransaction extends JpaRepository<TransactionDTO,Long> {
}
