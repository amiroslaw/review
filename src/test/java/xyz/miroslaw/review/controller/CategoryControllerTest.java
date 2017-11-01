package xyz.miroslaw.review.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.review.UtilTest;
import xyz.miroslaw.review.exception.NotFoundException;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CategoryControllerTest {
    private static final List<Category> CATEGORIES = Arrays.asList(
            new Category("Private"),
            new Category("Job")
    );
    private static final Category CATEGORY = new Category("Private");

    private MockMvc mockMvc;

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        CATEGORY.setId(1);
    }

    @After
    public void checkNoMoreInteractions() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getAllCategories_shouldReturnCategories() throws Exception {
        when(repository.findAll()).thenReturn(CATEGORIES);

        mockMvc.perform(get("/categories").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Private"))
                .andExpect(jsonPath("$[1].name").value("Job"));

        verify(repository, times(1)).findAll();
    }

    @Test
    public void getCategoryById_shouldReturnCategory() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(CATEGORY);

        mockMvc.perform(get("/categories/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Private"));

        verify(repository, times(1)).findOne(anyInt());
    }

    @Test
    public void getCategoryById_shouldNotFound() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(null);

        mockMvc.perform(get("/categories/66").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findOne(anyInt());
    }

    @Test
    public void getCategoryByName_shouldReturnCategory() throws Exception {
        when(repository.findByName("Private")).thenReturn(CATEGORIES);

        mockMvc.perform(get("/categories/name/Private").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Private"));

        verify(repository, times(1)).findByName(anyString());
    }

    @Test
    public void createCategory_shouldCreateCategory() throws Exception {
        when(repository.save(CATEGORY)).thenReturn(any(Category.class));

        mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(UtilTest.asJsonString(CATEGORY)))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(any(Category.class));
    }

    @Test
    public void updateCategory_shouldUpdateCategory() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(CATEGORY);
        when(repository.save(CATEGORY)).thenReturn(any(Category.class));

        mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(CATEGORY)))
                .andExpect(status().isOk());

        verify(repository, times(1)).findOne(anyInt());
        verify(repository, times(1)).save(any(Category.class));
    }

    @Test
    public void updateCategory_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(repository).findOne(anyInt());
        when(repository.save(any(Category.class))).thenReturn(null);

        mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(CATEGORY)))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findOne(anyInt());
    }

    @Test
    public void deleteCategory_shouldDeleteCategory() throws Exception {
        when(repository.findOne(CATEGORY.getId())).thenReturn(CATEGORY);
        doNothing().when(repository).delete(CATEGORY.getId());

        mockMvc.perform(delete("/categories/{id}", CATEGORY.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(repository, times(1)).findOne(CATEGORY.getId());
        verify(repository, times(1)).delete(CATEGORY.getId());
    }

    @Test
    public void deleteCategory_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(repository).findOne(anyInt());

        mockMvc.perform(delete("/categories/{id}", anyInt()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findOne(anyInt());
    }

}