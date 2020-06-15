package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.contoller.converter.JSONConverter;
import com.finaltask.networkofgiving.dto.LoginRequest;
import com.finaltask.networkofgiving.dto.RegisterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthControllerTest {

    private final String url = "/api/auth";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @Test
    public void signUpWithNonExistingUsernameAndNullUsername() throws Exception {
        RegisterRequest user = new RegisterRequest("Martin Vasilev", null, "vasilevpass", 30, "male", "Sofia");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/signup")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    public void signUpWithNonExistingUsernameAndBlankUsername() throws Exception {
        RegisterRequest user = new RegisterRequest("Martin Vasilev", " ", "vasilevpass", 30, "male", "Sofia");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/signup")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    public void signUpWithNonExistingUsernameAndNullAge() throws Exception {
        RegisterRequest user = new RegisterRequest("Martin Vasilev", "vasilev90", "vasilevpass", null, "male", "Sofia");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/signup")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void signUpWithNonExistingUsername() throws Exception {
        RegisterRequest user = new RegisterRequest("Martin Vasilev", "vasilev90", "vasilevpass", 30, "male", "Sofia");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/signup")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void signUpWithExistingUsername() throws Exception {
        RegisterRequest user = new RegisterRequest("Ivan Vasilev", "iivanov", "testpass", 30, "male", "Sofia");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/signup")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginWithNonExistingUser() throws Exception {
        LoginRequest user = new LoginRequest("ivanov", "123456");


        mvc.perform(MockMvcRequestBuilders
                .post(url + "/login")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginWithExistingUserAndWrongPassword() throws Exception {
        LoginRequest user = new LoginRequest("iivanov", "wrongpass");

        mvc.perform(MockMvcRequestBuilders
                .post(url + "/login")
                .content(JSONConverter.asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}