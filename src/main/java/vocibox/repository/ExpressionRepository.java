package vocibox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vocibox.domain.Expression;

import java.util.Set;

/**
 * Spring Data JPA repository for the Expression entity.
 */
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    @Query("select distinct e from Expression e left join e.tags t where " +
        "(e.priority in (:priorities)) and " +
        "(:marked is null or e.marked = :marked) and " +
        "(-1L in (:tags) or t.id in (:tags))")
    Page<Expression> search(@Param("priorities") Set<Integer> priorities, @Param("marked") Boolean marked,
                            @Param("tags") Set<Long> tags, Pageable pageable);


    @Query("select e from Expression e left join fetch e.tags where e.id = :id")
    Expression findOneWithEagerRelationships(@Param("id") Long id);

}
