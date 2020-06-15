package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.contoller.converter.JSONConverter;
import com.finaltask.networkofgiving.dto.RegisterRequest;
import com.finaltask.networkofgiving.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/create-user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    private final String url = "/api/user";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void getUserInfoWithoutAuthorization() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void getUserInfoAfterAuthorization() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void editValidInfoWithoutAuthorization() throws Exception {
        UserDto userDto = new UserDto("Ivan", "iivanov", "12345", 19, "male", "Plovdiv");

        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit")
                .content(JSONConverter.asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void editValidInfoAfterAuthorization() throws Exception {
        UserDto userDto = new UserDto("Ivan", "iivanov", "12345", 19, "male", "Plovdiv");

        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit")
                .content(JSONConverter.asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void editInvalidInfoAfterAuthorization() throws Exception {

        UserDto userDto = new UserDto(null, "iivanov", "12345", 19, null, "Plovdiv");

        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit")
                .content(JSONConverter.asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void getUserTransactionsAfterAuthorization() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(url + "/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void getUserTransactionsWithoutAuthorization() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}