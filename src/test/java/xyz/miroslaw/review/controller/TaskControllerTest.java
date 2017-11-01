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
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {

    private static final Task TASK = new Task("jog", 2.1f);
    private static final List<Task> TASKS = Arrays.asList(TASK, new Task("skating", 3.3f));
    @Mock
    private TaskRepository repository;
    @InjectMocks
    private TaskController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        TASK.setId(1);
    }

    @After
    public void checkNoMoreInteractions() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getAllTasks_shouldReturnTasks() throws Exception {
        when(repository.findAll()).thenReturn(TASKS);

        mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("jog"))
                .andExpect(jsonPath("$[0].duration").value("2.1"))
                .andExpect(jsonPath("$[1].name").value("skating"))
                .andExpect(jsonPath("$[1].duration").value("3.3"));

        verify(repository, times(1)).findAll();
    }

    @Test
    public void getTask_shouldReturnTask() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(TASK);

        mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jog"))
                .andExpect(jsonPath("$.duration").value("2.1"));

        verify(repository, times(1)).findOne(anyInt());
    }

    @Test
    public void getTask_shouldReturnNotFound() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(null);

        mockMvc.perform(get("/tasks/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findOne(anyInt());
    }

    @Test
    public void findTasksByName_shouldReturnTask() throws Exception {
        List<Task> tasksResult = Arrays.asList(TASK);
        when(repository.findByName(TASK.getName())).thenReturn(tasksResult);

        mockMvc.perform(get("/tasks/name/jog").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("jog"))
                .andExpect(jsonPath("$[0].duration").value("2.1"));

        verify(repository, times(1)).findByName(TASK.getName());
    }

    @Test
    public void updateTask_shouldUpdate() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(TASK);
        when(repository.save(TASK)).thenReturn(any(Task.class));

        mockMvc.perform(put("/tasks/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(TASK)))
                .andExpect(status().isOk());

        verify(repository, times(1)).findOne(anyInt());
        verify(repository, times(1)).save(any(Task.class));

    }
    @Test
    public void updateTask_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(repository).findOne(anyInt());
        when(repository.save(any(Task.class))).thenReturn(null);

        mockMvc.perform(put("/tasks/6").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(TASK)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTask_shouldDelete() throws Exception {
        when(repository.findOne(anyInt())).thenReturn(TASK);
        doNothing().when(repository).delete(any(Task.class));

        mockMvc.perform(delete("/tasks/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(repository, times(1)).findOne(anyInt());
        verify(repository, times(1)).delete(any(Task.class));
    }

    @Test
    public void deleteTask_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(repository).findOne(anyInt());

        mockMvc.perform(delete("/tasks/{id}", anyInt()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(repository, times(1)).findOne(anyInt());
    }

}