package pl.mentoring.m16nosql.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mentoring.m16nosql.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {

    List<User> findByEmail(String email);

    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} AND ANY s IN sports SATISFIES s.sportName=$1 END")
    List<User> findBySportName(String sportName);
}
