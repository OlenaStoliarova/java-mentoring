package pl.mentoring.m16nosql.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;
import pl.mentoring.m16nosql.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {

    List<User> findByEmail(String email);
}
