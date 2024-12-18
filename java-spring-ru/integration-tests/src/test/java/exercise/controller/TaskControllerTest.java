package exercise.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.MvcResult;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }
    // BEGIN
    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    @Test
    public void testShow() throws  Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .create();

        taskRepository.save(task);

        ObjectMapper objectMapper = new ObjectMapper();


        var request = get("/tasks/"+ task.getId())
                .contentType(MediaType.APPLICATION_JSON);

        objectMapper.registerModule(new JavaTimeModule());

        var result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        var bode = result.getResponse().getContentAsString();

        Task responseTask = objectMapper.readValue(bode, Task.class);

        assertThatJson(responseTask).isEqualTo(taskRepository.findById(task.getId()));
    }


    @Test
    public void testUpdate() throws  Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .create();

        taskRepository.save(task);

        HashMap<String, String> data = new HashMap<>();

        data.put("description", "my description");

        var request = put("/tasks/"+ task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                // ObjectMapper конвертирует Map в JSON
                .content(om.writeValueAsString(data));

        var result = mockMvc.perform(request).andExpect(status().isOk());


        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getDescription()).isEqualTo(("my description"));
    }


    @Test
    public void testCreate() throws  Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .create();

        int repoSize = taskRepository.findAll().size();

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                // ObjectMapper конвертирует Map в JSON
                .content(om.writeValueAsString(task));

        var result = mockMvc.perform(request).andExpect(status().isCreated());

        int newRepoSize = taskRepository.findAll().size();

        assertThat(repoSize+1).isEqualTo(newRepoSize);
    }

    @Test
    public void testDelete() throws  Exception{
        var task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .create();

        taskRepository.save(task);
        taskRepository.save(task);
        taskRepository.save(task);

        var request = delete("/tasks/" + taskRepository.findAll().getLast().getId())
                .contentType(MediaType.APPLICATION_JSON);

        var result = mockMvc.perform(request).andExpect(status().isOk());

        int newRepoSize = taskRepository.findAll().size();

        assertThat(newRepoSize).isEqualTo(2);
    }
    // END

//    Просмотр конкретной задачи
//    Создание новой задачи
//    Обновление существующей задачи
//    Удаление задачи
}
