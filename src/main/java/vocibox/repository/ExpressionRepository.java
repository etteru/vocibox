package vocibox.repository;

import vocibox.domain.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Expression entity.
 */
public interface ExpressionRepository extends JpaRepository<Expression, Long> {

}
