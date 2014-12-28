package vocibox.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vocibox.domain.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Expression entity.
 */
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

    @Query("select e from Expression e left join fetch e.tags where e.id = :id")
    Expression findOneWithEagerRelationships(@Param("id") Long id);

}
