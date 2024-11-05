package org.paymybuddy.transfermoney.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.paymybuddy.transfermoney.mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.mapper.ContactMapper;
import org.paymybuddy.transfermoney.mapper.RelationMapper;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTest;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.*;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.paymybuddy.transfermoney.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConnectionsServiceTests {
        @Autowired//@InjectMocks
        private ConnectionsService connectionsService;
        @Autowired//@Mock
        private ConnectionsRepository connectionsRepository;
        @Autowired//@Mock
        private ConnectionMapper connectionMapper;

        @InjectMocks
        private ContactService contactService;
        @Mock
        private ContactRepository contactRepository;
        @Mock
        private ContactMapper contactMapper;

        @InjectMocks
        private RelationService relationService;
        @Mock
        private RelationRepository relationRepository;
        @Mock
        private RelationMapper relationMapper;

        /*@Test
        public void shouldSave(){
            //Arrange
            TransactionForm transactionForm = new TransactionForm();
            transactionForm.setIdCreditorAccount(24L);
            transactionForm.setDescription("Restaurant");
            transactionForm.setIdDebtorAccount(2L);
            transactionForm.setAmount(14.00);

            //Act
            List <TransactionDTO> transactionsList = connectionsService.saveTransaction(transactionForm);

            //
            assertNotNull(transactionsList);
        }*/


        /**
         *
         * Should update the email
         *
         */
        @Test
        @WithMockUser(username="gerard@email.fr",roles={"USER"})
        public void shouldUpdateEmail(){
            //Arrange
            ProfileForm expectedProfileForm = new ProfileForm();
            expectedProfileForm.setEmail("gerard@myemail.fr");
            expectedProfileForm.setOldPassword("Mo@depa2");

           Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");/* */
            //when(connectionsService.getIdentifiant(expectedProfileForm.getEmail())).thenReturn(connection);

            //Act
            connectionsService.emailUpdating(expectedProfileForm);

            //Assert
            Optional<Connection> gotConnection = connectionsRepository.findById(2L);
            assertNotEquals(expectedProfileForm.getEmail(),gotConnection.get().getEmail());

        }

        /**
         *
         * Not should update the email
         *
         */
        @Test
        @WithMockUser(username="gerard@email.fr",roles={"USER"})
        public void NotShouldUpdateEmailIfSameEmail(){
            //Arrange
            ProfileForm expectedProfileForm = new ProfileForm();
            expectedProfileForm.setEmail("auguste@email.fr");
            expectedProfileForm.setOldPassword("Mo@depa2");

            //Act
            Exception exception = assertThrows(Exception.class, () -> connectionsService.emailUpdating(expectedProfileForm));

            //Assert
            assertTrue(exception instanceof DataIntegrityViolationException);
        }

        /**
         *
         * Should get the profile
         *
         */
        @Test
        @WithMockUser(username="gerard@email.fr",roles={"USER"})
        public void shouldGetProfile(){
            //Arrange
            ProfileForm expectedProfileForm = new ProfileForm();
            expectedProfileForm.setEmail("gerard@email.fr");
            expectedProfileForm.setOldPassword("Mo@depa2");
            expectedProfileForm.setNewPassword("Mo@depa1");
            expectedProfileForm.setConfirmPassword("Mo@depa1");

            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            when(connectionsService.getIdentifiant("gerard@email.fr")).thenReturn(connection);

            //Act
            ProfileForm gotProfileForm = connectionsService.getProfile(expectedProfileForm);

            //Assert
            assertEquals(expectedProfileForm, gotProfileForm);

        }

        /**
         *
         * Should get the user email
         *
         */
        @Test
        @WithMockUser(username="gerard@email.fr",roles={"USER"})
        public void shouldPasswordUpdatingStart(){
            //Arrange
            ProfileForm profileForm = new ProfileForm();
            profileForm.setEmail("gerard@email.fr");
            profileForm.setOldPassword("Mo@depa2");
            profileForm.setNewPassword("Mo@depa1");
            profileForm.setConfirmPassword("Mo@depa1");

            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            when(connectionsService.getIdentifiant("gerard@email.fr")).thenReturn(connection);

            //Act
            connectionsService.passwordUpdatingStart(profileForm);

            //Assert
            verify(connectionMapper, times(1)).convertToDTO(connection);

        }

        /**
         *
         * Should get the new user password
         *
         */
        @Test
        @WithMockUser(username="gerard@email.fr",roles={"USER"})
        public void shouldPasswordUpdatingFollowing(){
            //Arrange
            ProfileForm profileForm = new ProfileForm();
            profileForm.setEmail("gerard@email.fr");
            profileForm.setOldPassword("Mo@depa2");
            profileForm.setNewPassword("Mo@depa1");
            profileForm.setConfirmPassword("Mo@depa1");

            //Act
            ConnectionDTO connectionDTO = connectionsService.passwordUpdatingStart(profileForm);
            connectionsService.passwordUpdatingFollowing(connectionDTO,profileForm);

            //Assert
            //verify(connectionsRepository, times(1)).save(connection);
            Connection connection = connectionsRepository.findByEmail(profileForm.getEmail());
            assertNotEquals(profileForm.getOldPassword(),connection.getPassword());

        }

        /**
         *
         * Should return the identifiant from the email
         *
         */
        @Test
        public void shouldGetIdentifiantTest() {
            //Arrange
            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            //Act
            Connection connectionResponse = connectionsService.getIdentifiant(connection.getEmail());

            //Assert
            assertNotNull(connectionResponse);
            assertEquals(connection.getEmail(),connectionResponse.getEmail());
            assertEquals(connection.getId(),connectionResponse.getId());

        }

        /**
         *
         * Should get the id creditor
         *
         */
        @Test
        public void shouldGetCreditorTest(){
            //Arrange
            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            //Act
            Connection connectionResponse = connectionsService.getCreditor(2L);

            //Assert
            assertNotNull(connectionResponse);
            assertEquals(connection.getEmail(), connectionResponse.getEmail());
        }

    /**
     *
     * Should add a friend in the list
     *
     */
    @Test
    public void shouldAddConnectionTest(){
        /*//Arrange
        List<RelationsConnection> relationsConnectionList = new ArrayList<>();

        ConnectionDTO userDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();

        Connection user = new Connection();
        user.setId(2L);
        user.setName("Gerard");
        user.setEmail("gerard@email.com");
        user.setPassword("Mo@depa2");

        ConnectionDTO friendDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();

        Connection friend = new Connection();
        friend.setId(2L);
        friend.setName("Gerard");
        friend.setEmail("gerard@email.com");
        friend.setPassword("Mo@depa2");


        RelationDTO relationDTO = RelationDTO.builder()
                .id(1L)
                .user(userDTO)
                .connectionFriend(friendDTO)
                .build();

        Relation relation = new Relation();
        relation.setId(1L);
        relation.setUser(user);
        relation.setConnectionFriend(friend);

        Relation newRelation = new Relation();
        relation.setUser(user);
        relation.setConnectionFriend(friend);

        relationsConnectionList.add()
        ;
        //getIdentifiant
        when(connectionsRepository.findByEmail(any(String.class))).thenReturn(user);
        when(connectionMapper.convertToDTO(any(Connection.class))).thenReturn(userDTO);
        //getCreditor
        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(friend));
        when(connectionMapper.convertToDTO(friend)).thenReturn(friendDTO);
        //getRelation
        when(connectionMapper.convertToEntity(any(ConnectionDTO.class))).thenReturn(friend);
        when(connectionMapper.convertToEntity(any(ConnectionDTO.class))).thenReturn(user);
        when(relationRepository.findByConnectionFriendAndUser(any(Connection.class), any(Connection.class))).thenReturn(relation);
        when(relationMapper.convertToDTO(any(Relation.class))).thenReturn(relationDTO);
        //newRelation
        when(relationMapper.convertToEntity(any(RelationDTO.class))).thenReturn(newRelation);
        when(relationRepository.save(any(Relation.class))).thenReturn(relation);
        //getRelations
        */
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        ConnectionDTO connectionDTO = getIdentifiant(userDetails.getUsername());
        ConnectionDTO newConnectionDTO = getCreditor(Long.valueOf(friendName));

        RelationDTO foundRelationDTO = relationService.getRelation(newConnectionDTO, connectionDTO);

        if (foundRelationDTO != null) {
            //error..rejectValue("name", null, "There is already a relation "+connectionDTO.getEmail() +" with that email");
            log.error("There is already a relation with "+ newConnectionDTO.getEmail());

        }else {
            RelationDTO relationDTO = RelationDTO.builder()
                    .user(connectionDTO)
                    .connectionFriend(newConnectionDTO)
                    .build();

            relationService.newRelation(relationDTO);
            log.info("Created relation with "+ newConnectionDTO.getEmail());
        }*/

        /*return relationService.getRelations(connectionDTO);*/
    }

        /**
         *
         * Should call the save method to save a message
         *
         */
        @Test
        public void shouldAddMessageTest() {
            //Arrange
            /*ConnectionDTO connectionDTO = ConnectionDTO.builder()
                    .id(2L)
                    .name("Gerard")
                    .email("gerard@email.com")
                    .password("Mo@depa2")
                    .build();*/
            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            /*ContactDTO contactDTOTest = ContactDTO.builder()
                    .id(8L)
                    .sender(connectionDTO)
                    .message("Message du test unitaire")
                    .build();*/
            Contact contactTest = new Contact();
            contactTest.setId(8L);
            contactTest.setSender(connection);
            contactTest.setMessage("Message du test unitaire");

            //Contact contact = contactMapper.convertToEntity(contactDTOTest);

            when(connectionsService.getIdentifiant("gerard@email.com")).thenReturn(connection);
            //when(contactMapper.convertToEntity(any(Contact.class))).thenReturn(contact);
            when(contactRepository.save(any(Contact.class))).thenReturn(contactTest);

            //Act
            contactService.addedMessage(contactTest);


            //Contact contactTest = contactRepository.findTop1BySenderOrderByIdDesc(connection);

            //Assert
            Mockito.verify(contactRepository, times(1)).save(contactTest);
        /*assertNotNull(contactTest);
        assertEquals("Message du test unitaire",contactTest.getMessage());*/
        }

        /**
         *
         * Should return all the connections which are in the database
         *
         */
        @Test
        public void shouldGetAllConnectionsTest(){
            //Arrange
            List<Connection> connectionsList = new ArrayList<>();
            /*List<ConnectionDTO> connectionsDTOList = new ArrayList<>();
            ConnectionDTO connectionDTO = ConnectionDTO.builder()
                    .id(2L)
                    .name("Gerard")
                    .email("gerard@email.com")
                    .password("Mo@depa2")
                    .build();
            Connection connection = connectionMapper.convertToEntity(connectionDTO);*/

            Connection connection = new Connection();
            connection.setId(2L);
            connection.setName("Gerard");
            connection.setEmail("gerard@email.com");
            connection.setPassword("Mo@depa2");

            connectionsList.add(connection);

            /*ConnectionDTO connectionResponseDTO = ConnectionDTO.builder()
                    .id(1L)
                    .name("Auguste")
                    .email("auguste@email.com")
                    .password("Mo@depa1")
                    .build();
            connectionsDTOList.add(connectionResponseDTO);*/

            when(connectionsRepository.findAllByOrderByEmailAsc()).thenReturn(connectionsList);
            //when(connectionMapper.convertListToDTO(connectionsList)).thenReturn(connectionsDTOList);

            //Act
            List<Connection> connectionsResponseList = connectionsService.getAllConnections();

            //Assert
            verify(connectionsRepository, times(1)).findAllByOrderByEmailAsc();
            assertEquals(connectionsList.size(),connectionsResponseList.size());
        }


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
