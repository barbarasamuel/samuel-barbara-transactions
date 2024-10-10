package org.paymybuddy.transfermoney.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.paymybuddy.transfermoney.controller.ConnectionsController;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Relation;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionsServiceTests {

       /* @Autowired
        private ConnectionsController connectionsController;

        @MockBean
        private ConnectionsRepository connectionsRepository;
        @MockBean
        private RelationRepository relationRepository;
        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        @Test
        public void testMyFunction() throws Exception {
            // Arrange
            Connection expectedUser = new Connection();
            expectedUser.setEmail("gerard@email.com");
            expectedUser.setPassword("Mo@depa2");
            expectedUser.setName("Gerard");

            when(connectionsRepository.findByEmail("gerard@email.com")).thenReturn(expectedUser);

            Connection expectedConnection = new Connection();
            expectedConnection.setEmail("bertrand@email.com");
            expectedConnection.setPassword("Mo@depa0");
            expectedConnection.setName("Bertrand");

            when(connectionsRepository.findById(1L)).thenReturn(Optional.of(expectedConnection));

            when(relationRepository.findByConnectionFriendAndUser(expectedConnection, expectedUser)).thenReturn(null);

            List<Relation> expectedList = Arrays.asList(
                    new Relation(),
                    new Relation(),
                    new Relation(),
                    new Relation(),
                    new Relation());
//3,2;1,2;4,2;,2,2;5,2
            when(relationRepository.findByUserId(expectedUser.getId())).thenReturn(expectedList);

            // Act
            MvcResult result = mockMvc.perform(post("/connection/list"))
                    .andExpect(status().isOk())
                    .andReturn();

            // Assert
            String viewName = result.getModelAndView().getViewName();
            assertEquals("transferTest", viewName);

            String renderedHtml = result.getResponse().getContentAsString();

            for (Relation currentRelation : expectedList) {
                assertTrue(renderedHtml.contains(String.valueOf(currentRelation.getId())));
            }
        }*/
    /*
    @Test
        public void addMessageTest() throws Exception {
    // Arrange
    ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .name("Gerard")
                .email("gerard@email.fr")
                .password("Mo@depa2")
                .build();

        ContactDTO contactDTO = ContactDTO.builder()
                .sender(connectionDTO)
                .message("Test")
                .build();

        contactService.addedMessage(contactDTO);
        List<RelationsConnection> connectionsList = new ArrayList<RelationsConnection>();

        RelationsConnection relationsConnection1 = new RelationsConnection(3L,"Amandine");
        RelationsConnection relationsConnection2 = new RelationsConnection(1L,"Auguste");
        RelationsConnection relationsConnection3 = new RelationsConnection(1L,"Elise");
        RelationsConnection relationsConnection4 = new RelationsConnection(4L,"Georgette");
        RelationsConnection relationsConnection5 = new RelationsConnection(2L,"Gerard");
        connectionsList.add(relationsConnection1);
        connectionsList.add(relationsConnection2);
        connectionsList.add(relationsConnection3);
        connectionsList.add(relationsConnection4);
        connectionsList.add(relationsConnection5);

        when(contactService.addedMessage(contactDTO)).thenReturn(connectionsList);*/
}
