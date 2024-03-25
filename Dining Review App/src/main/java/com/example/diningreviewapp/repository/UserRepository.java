package com.example.diningreviewapp.repository;
import com.example.diningreviewapp.model.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends CrudRepository<User, Integer> {
}
