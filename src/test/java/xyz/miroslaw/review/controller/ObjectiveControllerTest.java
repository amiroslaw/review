package xyz.miroslaw.review.controller;

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
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.ObjectiveRepository;
import xyz.miroslaw.review.repository.TaskRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ObjectiveControllerTest {
    private static LocalDate localDate = LocalDate.of(2017, 10, 22);
    private static Date date1 = Date.valueOf(localDate);
    private static final List<Task> TASKS = Collections.singletonList(new Task("task", (float) 8.3));
    private static final Objective OBJECTIVE = new Objective(date1, 66.4f, "great", "not good", "typical comment", TASKS);
    private static final List<Objective> OBJECTIVES = Arrays.asList(
            OBJECTIVE,
            new Objective(date1, 30.3f, "avg", "bad", "my thoughts")
    );

    private MockMvc mockMvc;
    @InjectMocks
    private ObjectiveController controller;
    @Mock
    private ObjectiveRepository objectiveRepository;
    @Mock
    private TaskRepository taskRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        OBJECTIVE.setId(1);
    }


    @Test
    public void getAllObjectives_shouldReturnObjectives() throws Exception {
        when(objectiveRepository.findAll()).thenReturn(OBJECTIVES);

        mockMvc.perform(get("/aims").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].date").value("2017-10-22"))
                .andExpect(jsonPath("$[0].totalDuration").value("66.4"))
                .andExpect(jsonPath("$[0].success").value("great"))
                .andExpect(jsonPath("$[0].failure").value("not good"))
                .andExpect(jsonPath("$[0].comment").value("typical comment"))
                .andExpect(jsonPath("$[1].date").value("2017-10-22"))
                .andExpect(jsonPath("$[1].totalDuration").value("30.3"))
                .andExpect(jsonPath("$[1].success").value("avg"))
                .andExpect(jsonPath("$[1].failure").value("bad"))
                .andExpect(jsonPath("$[1].comment").value("my thoughts"));

        verify(objectiveRepository, times(1)).findAll();
        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void getObjective_shouldReturnObjective() throws Exception {
        when(objectiveRepository.findOne(OBJECTIVE.getId())).thenReturn(OBJECTIVE);

        mockMvc.perform(get("/aims/{id}", OBJECTIVE.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2017-10-22"))
                .andExpect(jsonPath("$.totalDuration").value("66.4"))
                .andExpect(jsonPath("$.success").value("great"))
                .andExpect(jsonPath("$.failure").value("not good"))
                .andExpect(jsonPath("$.comment").value("typical comment"));

        verify(objectiveRepository, times(1)).findOne(OBJECTIVE.getId());
        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void getObjective_shouldReturnNotFound() throws Exception {
        when(objectiveRepository.findOne(anyInt())).thenReturn(null);

        mockMvc.perform(get("/aims/{id}", OBJECTIVE.getId()).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

        verify(objectiveRepository, times(1)).findOne(anyInt());
    }

    @Test
    public void createObjective_shouldCreateObjective() throws Exception {
        when(objectiveRepository.save(any(Objective.class))).thenReturn(OBJECTIVE);

        mockMvc.perform(post("/aims").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(OBJECTIVE)))
                .andExpect(status().isCreated());

        verify(objectiveRepository, times(1)).save(any(Objective.class));
        verify(taskRepository, times(1)).save(any(Task.class));
        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void updateObjective_shouldUpdateObjective() throws Exception {
        when(objectiveRepository.findOne(OBJECTIVE.getId())).thenReturn(OBJECTIVE);
        when(objectiveRepository.save(OBJECTIVE)).thenReturn(OBJECTIVE);

        mockMvc.perform(put("/aims/{id}", OBJECTIVE.getId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(OBJECTIVE)))
                .andExpect(status().isOk());

        verify(objectiveRepository, times(1)).findOne(OBJECTIVE.getId());
        verify(objectiveRepository, times(1)).save(any(Objective.class));
        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void updateObjective_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(objectiveRepository).findOne(anyInt());
        when(objectiveRepository.save(any(Objective.class))).thenReturn(null);

        mockMvc.perform(put("/aims/6").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(OBJECTIVE)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteObjective_shouldDeleteObjective() throws Exception {
        when(objectiveRepository.findOne(OBJECTIVE.getId())).thenReturn(OBJECTIVE);
        doNothing().when(objectiveRepository).delete(OBJECTIVE);

        mockMvc.perform(delete("/aims/{id}", OBJECTIVE.getId()).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(objectiveRepository, times(1)).findOne(OBJECTIVE.getId());
        verify(objectiveRepository, times(1)).delete(any(Objective.class));

        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void deleteObjective_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(objectiveRepository).findOne(anyInt());

        mockMvc.perform(delete("/aims/{id}", anyInt()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getTasksByObjective_shouldReturnTask() throws Exception {
        when(objectiveRepository.findOne(OBJECTIVE.getId())).thenReturn(OBJECTIVE);
        when(taskRepository.findAllByObjective(OBJECTIVE)).thenReturn(TASKS);

        mockMvc.perform(get("/aims/1/tasks").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("task"))
                .andExpect(jsonPath("$[0].duration").value("8.3"));

        verify(objectiveRepository, times(1)).findOne(OBJECTIVE.getId());
        verify(taskRepository, times(1)).findAllByObjective(any(Objective.class));
        verifyNoMoreInteractions(objectiveRepository);
    }

    @Test
    public void getTasksByObjective_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(objectiveRepository).findOne(anyInt());
        when(taskRepository.findAllByObjective(OBJECTIVE)).thenReturn(TASKS);

        mockMvc.perform(get("/aims/1/tasks"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTask_shouldCreateTask() throws Exception {
        when(objectiveRepository.findOne(OBJECTIVE.getId())).thenReturn(OBJECTIVE);
        when(taskRepository.save(any(Task.class))).thenReturn(any(Task.class));

        mockMvc.perform(post("/aims/1/tasks").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(TASKS.get(0))))
                .andExpect(status().isCreated());

        verify(objectiveRepository, times(1)).findOne(OBJECTIVE.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void createTask_shouldNotFound() throws Exception {
        doThrow(NotFoundException.class).when(objectiveRepository).findOne(anyInt());

        mockMvc.perform(post("/aims/1/tasks").contentType(MediaType.APPLICATION_JSON_UTF8).content(UtilTest.asJsonString(TASKS.get(0))))
                .andExpect(status().isNotFound());
    }

}