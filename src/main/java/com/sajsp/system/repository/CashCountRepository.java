package com.sajsp.system.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sajsp.system.entity.CashCount;

@Repository
public interface CashCountRepository extends JpaRepository<CashCount, Long> {

	boolean existsByArNumber(Long arNumber);

	Page<CashCount> findAll(Specification<CashCount> spec, Pageable pageable);
	
	List<CashCount> findAll(Specification<CashCount> spec);
}
