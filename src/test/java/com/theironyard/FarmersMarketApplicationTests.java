package com.theironyard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.Inventory;
import com.theironyard.entities.User;
import com.theironyard.services.InventoryRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FarmersMarketApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmersMarketApplicationTests {

    @Autowired
    UserRepository users;

    @Autowired
    InventoryRepository inventories;

    @Autowired
    WebApplicationContext wap;

    MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    public void test1CreateUser() throws Exception {
        User user = new User("Alice", "password", "Farmer", "Limehouse Produce", "charleston", "8888", "alice@alice", "password");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .content(json)
                        .contentType("application/json")
        );
        Assert.assertTrue(users.count() == 2);
    }

    @Test
    public void test2UpdateUser() throws Exception {
        User user = users.findOne(2);
        user.setCompanyName("Rawl Produce");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/2")
                        .content(json)
                        .contentType("application/json")
                        .sessionAttr("userName", "Alice")
        );
        Assert.assertTrue(users.findOne(2).getCompanyName().equals("Rawl Produce"));
    }

    @Test
    public void test3Login() throws Exception {
        User user = new User();
        user.setUserName("Alice");
        user.setPasswordHash("password");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        ResultActions resAct =
                mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content(json)
                        .contentType("application/json")
        );
        MvcResult result = resAct.andReturn();
        MockHttpServletRequest request = result.getRequest();
        HttpSession session = request.getSession();
        Assert.assertTrue(session.getAttribute("userName").equals("Alice"));
    }

    @Test
    public void test4CreateInventory() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setCategory("Banana");
        inventory.setName("Golden Yellow Bananas");
        inventory.setQuantityAvailable(9);
        inventory.setFarmer("Rawl Produce");
        inventory.setPrice(2.55);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(inventory);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/inventory")
                        .content(json)
                        .contentType("application/json")
                        .sessionAttr("userName", "Alice")
        );
        Assert.assertTrue(inventories.count() == 1);
    }
    @Test
    public void test5UpdateInventory() throws Exception {
        Inventory i = inventories.findOne(1);
        i.setCategory("Tomato");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(i);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/inventory/1")
                        .content(json)
                        .contentType("application/json")
                        .sessionAttr("userName", "Alice")
        );
        Assert.assertTrue(inventories.findOne(1).getCategory().equals("Tomato"));
    }
    @Test
    public void test6DeleteInventory() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/1")
        );
        Assert.assertTrue(inventories.count() == 0);
    }

//     @Test
//    public void test7DeleteUser() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/users/2")
//        );
//        Assert.assertTrue(users.count() == 1);
//    }

}
