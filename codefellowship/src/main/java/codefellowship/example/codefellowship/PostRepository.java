package codefellowship.example.codefellowship;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {
    public Post findByUserId(ApplicationUser user);

}
