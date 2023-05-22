package pl.mentoring.m16nosql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mentoring.m16nosql.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
