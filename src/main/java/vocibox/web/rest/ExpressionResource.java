package vocibox.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vocibox.domain.Expression;
import vocibox.repository.ExpressionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

/**
 * REST controller for managing Expression.
 */
@RestController
@RequestMapping("/app")
public class ExpressionResource {

    private static final String DEFAULT_PAGE_INDEX = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";
    private static final String DEFAULT_PRIORITIES = "1,2,3,4,5";

    private final Logger log = LoggerFactory.getLogger(ExpressionResource.class);

    @Inject
    private ExpressionRepository expressionRepository;

    /**
     * POST  /rest/expressions -> Create a new expression.
     */
    @RequestMapping(value = "/rest/expressions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Expression expression) {
        log.debug("REST request to save Expression : {}", expression);
        expressionRepository.save(expression);
    }

    /**
     * Returns all expressions that match the specified search parameters.
     *
     * @param tags If this parameter is set, an expression is only returned if it has at least one of the specified tags.
     *             Please not that the current interface does not allow to search for expressions that don't have a tag.
     */
    @RequestMapping(value = "/rest/expressions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Page<Expression> getAll(
            @RequestParam(required = false, defaultValue = DEFAULT_PRIORITIES) Set<Integer> priorities,
            @RequestParam(required = false) Boolean marked,
            @RequestParam(required = false, defaultValue = "-1") Set<Long> tags,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_INDEX) Integer page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        log.debug("REST request to get all Expressions");
        Page<Expression> result = expressionRepository.search(priorities, marked, tags, createPageable(page, size));
        return result;
    }

    private Pageable createPageable(int page, int size) {
        return new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createdDate"));
    }

    /**
     * GET  /rest/expressions/:id -> get the "id" expression.
     */
    @RequestMapping(value = "/rest/expressions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Expression> get(@PathVariable Long id) {
        log.debug("REST request to get Expression : {}", id);
        return Optional.ofNullable(expressionRepository.findOneWithEagerRelationships(id))
            .map(expression -> new ResponseEntity<>(
                expression,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/expressions/:id -> delete the "id" expression.
     */
    @RequestMapping(value = "/rest/expressions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Expression : {}", id);
        expressionRepository.delete(id);
    }
}
