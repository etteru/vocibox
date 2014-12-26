package vocibox.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import vocibox.Application;
import vocibox.domain.Expression;
import vocibox.repository.ExpressionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExpressionResource REST controller.
 *
 * @see ExpressionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ExpressionResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final String DEFAULT_EXPRESSION = "SAMPLE_TEXT";
    private static final String UPDATED_EXPRESSION = "UPDATED_TEXT";
    
    private static final String DEFAULT_TRANSLATION = "SAMPLE_TEXT";
    private static final String UPDATED_TRANSLATION = "UPDATED_TEXT";
    
    private static final Boolean DEFAULT_MASCULINE = false;
    private static final Boolean UPDATED_MASCULINE = true;
    private static final Boolean DEFAULT_FEMININE = false;
    private static final Boolean UPDATED_FEMININE = true;
    private static final Boolean DEFAULT_SINGULAR = false;
    private static final Boolean UPDATED_SINGULAR = true;
    private static final Boolean DEFAULT_PLURAL = false;
    private static final Boolean UPDATED_PLURAL = true;
    private static final String DEFAULT_EXAMPLE = "SAMPLE_TEXT";
    private static final String UPDATED_EXAMPLE = "UPDATED_TEXT";
    
    private static final String DEFAULT_DEFINITION = "SAMPLE_TEXT";
    private static final String UPDATED_DEFINITION = "UPDATED_TEXT";
    
    private static final String DEFAULT_OPPOSITE = "SAMPLE_TEXT";
    private static final String UPDATED_OPPOSITE = "UPDATED_TEXT";
    
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";
    
    private static final String DEFAULT_PRONUNCIATION = "SAMPLE_TEXT";
    private static final String UPDATED_PRONUNCIATION = "UPDATED_TEXT";
    
    private static final Boolean DEFAULT_IMAGE = false;
    private static final Boolean UPDATED_IMAGE = true;
    private static final BigDecimal DEFAULT_LATITUDE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_LATITUDE = BigDecimal.ONE;
    
    private static final BigDecimal DEFAULT_LONGITUDE = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_LONGITUDE = BigDecimal.ONE;
    
    private static final Integer DEFAULT_PRIORITY = 0;
    private static final Integer UPDATED_PRIORITY = 1;
    
    private static final Boolean DEFAULT_MARKED = false;
    private static final Boolean UPDATED_MARKED = true;
   private static final DateTime DEFAULT_CREATED = new DateTime(0L);
   private static final DateTime UPDATED_CREATED = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_CREATED_STR = dateTimeFormatter.print(DEFAULT_CREATED);
    
   private static final DateTime DEFAULT_MODIFIED = new DateTime(0L);
   private static final DateTime UPDATED_MODIFIED = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_MODIFIED_STR = dateTimeFormatter.print(DEFAULT_MODIFIED);
    

    @Inject
    private ExpressionRepository expressionRepository;

    private MockMvc restExpressionMockMvc;

    private Expression expression;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExpressionResource expressionResource = new ExpressionResource();
        ReflectionTestUtils.setField(expressionResource, "expressionRepository", expressionRepository);
        this.restExpressionMockMvc = MockMvcBuilders.standaloneSetup(expressionResource).build();
    }

    @Before
    public void initTest() {
        expression = new Expression();
        expression.setExpression(DEFAULT_EXPRESSION);
        expression.setTranslation(DEFAULT_TRANSLATION);
        expression.setMasculine(DEFAULT_MASCULINE);
        expression.setFeminine(DEFAULT_FEMININE);
        expression.setSingular(DEFAULT_SINGULAR);
        expression.setPlural(DEFAULT_PLURAL);
        expression.setExample(DEFAULT_EXAMPLE);
        expression.setDefinition(DEFAULT_DEFINITION);
        expression.setOpposite(DEFAULT_OPPOSITE);
        expression.setComment(DEFAULT_COMMENT);
        expression.setPronunciation(DEFAULT_PRONUNCIATION);
        expression.setImage(DEFAULT_IMAGE);
        expression.setLatitude(DEFAULT_LATITUDE);
        expression.setLongitude(DEFAULT_LONGITUDE);
        expression.setPriority(DEFAULT_PRIORITY);
        expression.setMarked(DEFAULT_MARKED);
        expression.setCreated(DEFAULT_CREATED);
        expression.setModified(DEFAULT_MODIFIED);
    }

    @Test
    @Transactional
    public void createExpression() throws Exception {
        // Validate the database is empty
        assertThat(expressionRepository.findAll()).hasSize(0);

        // Create the Expression
        restExpressionMockMvc.perform(post("/app/rest/expressions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expression)))
                .andExpect(status().isOk());

        // Validate the Expression in the database
        List<Expression> expressions = expressionRepository.findAll();
        assertThat(expressions).hasSize(1);
        Expression testExpression = expressions.iterator().next();
        assertThat(testExpression.getExpression()).isEqualTo(DEFAULT_EXPRESSION);
        assertThat(testExpression.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
        assertThat(testExpression.getMasculine()).isEqualTo(DEFAULT_MASCULINE);
        assertThat(testExpression.getFeminine()).isEqualTo(DEFAULT_FEMININE);
        assertThat(testExpression.getSingular()).isEqualTo(DEFAULT_SINGULAR);
        assertThat(testExpression.getPlural()).isEqualTo(DEFAULT_PLURAL);
        assertThat(testExpression.getExample()).isEqualTo(DEFAULT_EXAMPLE);
        assertThat(testExpression.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
        assertThat(testExpression.getOpposite()).isEqualTo(DEFAULT_OPPOSITE);
        assertThat(testExpression.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testExpression.getPronunciation()).isEqualTo(DEFAULT_PRONUNCIATION);
        assertThat(testExpression.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testExpression.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testExpression.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testExpression.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testExpression.getMarked()).isEqualTo(DEFAULT_MARKED);
        assertThat(testExpression.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testExpression.getModified()).isEqualTo(DEFAULT_MODIFIED);
    }

    @Test
    @Transactional
    public void getAllExpressions() throws Exception {
        // Initialize the database
        expressionRepository.saveAndFlush(expression);

        // Get all the expressions
        restExpressionMockMvc.perform(get("/app/rest/expressions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(expression.getId().intValue()))
                .andExpect(jsonPath("$.[0].expression").value(DEFAULT_EXPRESSION.toString()))
                .andExpect(jsonPath("$.[0].translation").value(DEFAULT_TRANSLATION.toString()))
                .andExpect(jsonPath("$.[0].masculine").value(DEFAULT_MASCULINE.booleanValue()))
                .andExpect(jsonPath("$.[0].feminine").value(DEFAULT_FEMININE.booleanValue()))
                .andExpect(jsonPath("$.[0].singular").value(DEFAULT_SINGULAR.booleanValue()))
                .andExpect(jsonPath("$.[0].plural").value(DEFAULT_PLURAL.booleanValue()))
                .andExpect(jsonPath("$.[0].example").value(DEFAULT_EXAMPLE.toString()))
                .andExpect(jsonPath("$.[0].definition").value(DEFAULT_DEFINITION.toString()))
                .andExpect(jsonPath("$.[0].opposite").value(DEFAULT_OPPOSITE.toString()))
                .andExpect(jsonPath("$.[0].comment").value(DEFAULT_COMMENT.toString()))
                .andExpect(jsonPath("$.[0].pronunciation").value(DEFAULT_PRONUNCIATION.toString()))
                .andExpect(jsonPath("$.[0].image").value(DEFAULT_IMAGE.booleanValue()))
                .andExpect(jsonPath("$.[0].latitude").value(DEFAULT_LATITUDE.intValue()))
                .andExpect(jsonPath("$.[0].longitude").value(DEFAULT_LONGITUDE.intValue()))
                .andExpect(jsonPath("$.[0].priority").value(DEFAULT_PRIORITY))
                .andExpect(jsonPath("$.[0].marked").value(DEFAULT_MARKED.booleanValue()))
                .andExpect(jsonPath("$.[0].created").value(DEFAULT_CREATED_STR))
                .andExpect(jsonPath("$.[0].modified").value(DEFAULT_MODIFIED_STR));
    }

    @Test
    @Transactional
    public void getExpression() throws Exception {
        // Initialize the database
        expressionRepository.saveAndFlush(expression);

        // Get the expression
        restExpressionMockMvc.perform(get("/app/rest/expressions/{id}", expression.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(expression.getId().intValue()))
            .andExpect(jsonPath("$.expression").value(DEFAULT_EXPRESSION.toString()))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION.toString()))
            .andExpect(jsonPath("$.masculine").value(DEFAULT_MASCULINE.booleanValue()))
            .andExpect(jsonPath("$.feminine").value(DEFAULT_FEMININE.booleanValue()))
            .andExpect(jsonPath("$.singular").value(DEFAULT_SINGULAR.booleanValue()))
            .andExpect(jsonPath("$.plural").value(DEFAULT_PLURAL.booleanValue()))
            .andExpect(jsonPath("$.example").value(DEFAULT_EXAMPLE.toString()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION.toString()))
            .andExpect(jsonPath("$.opposite").value(DEFAULT_OPPOSITE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.pronunciation").value(DEFAULT_PRONUNCIATION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.marked").value(DEFAULT_MARKED.booleanValue()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED_STR))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED_STR));
    }

    @Test
    @Transactional
    public void getNonExistingExpression() throws Exception {
        // Get the expression
        restExpressionMockMvc.perform(get("/app/rest/expressions/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpression() throws Exception {
        // Initialize the database
        expressionRepository.saveAndFlush(expression);

        // Update the expression
        expression.setExpression(UPDATED_EXPRESSION);
        expression.setTranslation(UPDATED_TRANSLATION);
        expression.setMasculine(UPDATED_MASCULINE);
        expression.setFeminine(UPDATED_FEMININE);
        expression.setSingular(UPDATED_SINGULAR);
        expression.setPlural(UPDATED_PLURAL);
        expression.setExample(UPDATED_EXAMPLE);
        expression.setDefinition(UPDATED_DEFINITION);
        expression.setOpposite(UPDATED_OPPOSITE);
        expression.setComment(UPDATED_COMMENT);
        expression.setPronunciation(UPDATED_PRONUNCIATION);
        expression.setImage(UPDATED_IMAGE);
        expression.setLatitude(UPDATED_LATITUDE);
        expression.setLongitude(UPDATED_LONGITUDE);
        expression.setPriority(UPDATED_PRIORITY);
        expression.setMarked(UPDATED_MARKED);
        expression.setCreated(UPDATED_CREATED);
        expression.setModified(UPDATED_MODIFIED);
        restExpressionMockMvc.perform(post("/app/rest/expressions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(expression)))
                .andExpect(status().isOk());

        // Validate the Expression in the database
        List<Expression> expressions = expressionRepository.findAll();
        assertThat(expressions).hasSize(1);
        Expression testExpression = expressions.iterator().next();
        assertThat(testExpression.getExpression()).isEqualTo(UPDATED_EXPRESSION);
        assertThat(testExpression.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
        assertThat(testExpression.getMasculine()).isEqualTo(UPDATED_MASCULINE);
        assertThat(testExpression.getFeminine()).isEqualTo(UPDATED_FEMININE);
        assertThat(testExpression.getSingular()).isEqualTo(UPDATED_SINGULAR);
        assertThat(testExpression.getPlural()).isEqualTo(UPDATED_PLURAL);
        assertThat(testExpression.getExample()).isEqualTo(UPDATED_EXAMPLE);
        assertThat(testExpression.getDefinition()).isEqualTo(UPDATED_DEFINITION);
        assertThat(testExpression.getOpposite()).isEqualTo(UPDATED_OPPOSITE);
        assertThat(testExpression.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testExpression.getPronunciation()).isEqualTo(UPDATED_PRONUNCIATION);
        assertThat(testExpression.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExpression.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testExpression.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testExpression.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testExpression.getMarked()).isEqualTo(UPDATED_MARKED);
        assertThat(testExpression.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testExpression.getModified()).isEqualTo(UPDATED_MODIFIED);
    }

    @Test
    @Transactional
    public void deleteExpression() throws Exception {
        // Initialize the database
        expressionRepository.saveAndFlush(expression);

        // Get the expression
        restExpressionMockMvc.perform(delete("/app/rest/expressions/{id}", expression.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Expression> expressions = expressionRepository.findAll();
        assertThat(expressions).hasSize(0);
    }
}
