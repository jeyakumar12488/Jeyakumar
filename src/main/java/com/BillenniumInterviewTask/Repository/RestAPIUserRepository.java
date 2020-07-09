package com.BillenniumInterviewTask.Repository;

import java.io.Console;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BillenniumInterviewTask.Entity.RestAPIUser;

@Repository
public interface RestAPIUserRepository extends JpaRepository<RestAPIUser, Long> {
	@Query("from RestAPIUser ru where ru.firstName like %:firstName% ")
	List<RestAPIUser> findByFirstName(@Param("firstName") String firstName);

	@Query("from RestAPIUser ru where  ru.email like %:email% ")
	List<RestAPIUser> validateUser(@Param("email") String email);

}
