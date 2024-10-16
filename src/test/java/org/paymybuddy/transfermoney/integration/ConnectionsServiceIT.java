package org.paymybuddy.transfermoney.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.paymybuddy.transfermoney.Mapper.BankAccountMapper;
import org.paymybuddy.transfermoney.Mapper.ConnectionMapper;
import org.paymybuddy.transfermoney.Mapper.ContactMapper;
import org.paymybuddy.transfermoney.TransfermoneyApplicationTest;
import org.paymybuddy.transfermoney.entity.BankAccount;
import org.paymybuddy.transfermoney.entity.Connection;
import org.paymybuddy.transfermoney.entity.Contact;
import org.paymybuddy.transfermoney.model.BankAccountDTO;
import org.paymybuddy.transfermoney.model.ConnectionDTO;
import org.paymybuddy.transfermoney.model.ContactDTO;
import org.paymybuddy.transfermoney.repository.BankAccountRepository;
import org.paymybuddy.transfermoney.repository.ConnectionsRepository;
import org.paymybuddy.transfermoney.repository.ContactRepository;
import org.paymybuddy.transfermoney.service.BankAccountService;
import org.paymybuddy.transfermoney.service.ConnectionsService;
import org.paymybuddy.transfermoney.service.ContactService;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTest.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConnectionsServiceIT {
    @InjectMocks
    private ConnectionsService connectionsService;
    @Mock
    private ConnectionsRepository connectionsRepository;
    @Mock
    private ConnectionMapper connectionMapper;
    @InjectMocks
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private BankAccountService bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private BankAccountMapper bankAccountMapper;
    

    /**
     *
     * Should fill the creditor accounts dropdown
     *
     */
    @Test
    public void shouldFillDropdownIT(){
        //Arrange
        List<BankAccount> userAccountList = new ArrayList<>();
        List<BankAccountDTO> userAccountDTOList = new ArrayList<>();

        ConnectionDTO connectionDTO = ConnectionDTO.builder()
                .id(2L)
                .name("Gerard")
                .email("gerard@email.com")
                .password("Mo@depa2")
                .build();

        Connection connection = new Connection();
        connection.setId(2L);
        connection.setName("Gerard");
        connection.setEmail("gerard@email.com");
        connection.setPassword("Mo@depa2");

        BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .id(1L)
                .balance(50.00)
                .connectionBankAccount(connectionDTO)
                .build();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBalance(50.00);
        bankAccount.setConnectionBankAccount(connection);

        userAccountList.add(bankAccount);
        userAccountDTOList.add(bankAccountDTO);

        when(connectionsRepository.findById(any(Long.class))).thenReturn(Optional.of(connection));
        when(connectionMapper.convertToDTO(connection)).thenReturn(connectionDTO);
        when(bankAccountRepository.findAllByConnectionBankAccountId(connectionDTO.getId())).thenReturn(userAccountList);
        when(bankAccountMapper.convertListToDTO(userAccountList)).thenReturn(userAccountDTOList);

        //Act
        ConnectionDTO connectionDTOResponse = connectionsService.getCreditor(2L);
        List<BankAccountDTO> bankAccountDTOResponseList =  bankAccountService.getUserAccountsList(connectionDTOResponse);

        //Assert
        verify(connectionsRepository,times(1)).findById(2L);
        assertNotNull(bankAccountDTOResponseList);
        assertEquals(userAccountDTOList.size(),bankAccountDTOResponseList.size());

    }
}
