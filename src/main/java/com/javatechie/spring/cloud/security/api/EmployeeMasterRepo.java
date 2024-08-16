package com.javatechie.spring.cloud.security.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMasterRepo extends JpaRepository<EmployeeMaster, Long> {

 
	EmployeeMaster findByEmailId(String string);

}
