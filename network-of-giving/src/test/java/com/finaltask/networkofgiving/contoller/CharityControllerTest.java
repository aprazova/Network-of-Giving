package com.finaltask.networkofgiving.contoller;

import com.finaltask.networkofgiving.contoller.converter.JSONConverter;
import com.finaltask.networkofgiving.dto.CharityDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = {"/create-user.sql", "/create-charity.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class  CharityControllerTest {

    private final String url = "/api/charity";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void createCharityWithoutAuthorization() throws Exception {
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, 20);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void createCharityAfterAuthorization() throws Exception {
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, 20);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void createCharityAfterAuthorizationWithEmptyBudgetAndEmptyVolunteers() throws Exception {
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", null, null);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void createCharityAfterAuthorizationAndEmptyBudget() throws Exception {
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", null, 20);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void createCharityAfterAuthorizationAndEmptyVolunteers() throws Exception {
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, null);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void createCharityAfterAuthorizationAndBlankTitle() throws Exception {
        CharityDto charityDto = new CharityDto(" ", "charity.png", "Test Description", 250.60, 20);

        mvc.perform(MockMvcRequestBuilders
                .post(url)
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void showAllCharitiesAfterAuthorization() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(url + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void showAllCharitiesWithoutAuthorization() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(url + "/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void getExistingSingleCharity() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/get/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void getNonExistingSingleCharityAfterAuthorization() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/get/14165")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getNonExistingSingleCharityWithoutAuthorization() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/get/14165")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void getAllUsersCharityAfterAuthorization() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/own"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersCharityWithoutAuthorization() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .get(url + "/own"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void deleteCharityWithoutAuthorization() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete(url + "/delete/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void deleteNonExistingCharityAfterAuthorization() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete(url + "/delete/13323"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void deleteExistingCharityAfterAuthorization() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete(url + "/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void editNonExistingCharityAfterAuthorization() throws Exception{
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, 20);

        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit/1254")
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "iivanov" , password = "123456")
    public void editExistingCharityAfterAuthorization() throws Exception{

        CharityDto secondCharityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, 20);

        System.out.println("AFTER ADDING");
        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit/1")
                .content(JSONConverter.asJsonString(secondCharityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void editExistingCharityWithoutAuthorization() throws Exception{
        CharityDto charityDto = new CharityDto("Test Title", "charity.png", "Test Description", 250.60, 20);

        mvc.perform(MockMvcRequestBuilders
                .put(url + "/edit/1")
                .content(JSONConverter.asJsonString(charityDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}