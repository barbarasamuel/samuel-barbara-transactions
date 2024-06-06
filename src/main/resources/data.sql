alter table Connection modify email varchar(75);
alter table Connection modify name varchar(50);
alter table Connection modify password varchar(8);

alter table bank_account modify balance double not null;
alter table bank_account modify id_connection bigint not null;

alter table Transactions modify amount double not null;
alter table Transactions modify id_creditor_account bigint not null;
alter table Transactions modify id_debtor_account bigint not null;
alter table Transactions modify transaction_date datetime(6) not null;
alter table Transactions modify description varchar(100) not null;
alter table Transactions modify type_transaction varchar(6);
commit;

insert into Connection(name,email,password) values('Auguste','auguste@email.com','motdepa1');
insert into Connection(name,email,password) values('Gérard','gerard@email.com','motdepa2');
insert into Connection(name,email,password) values('Amandine','amandine@email.com','motdepa3');
insert into Connection(name,email,password) values('Georgette','georgette@email.com','motdepa4');
insert into Connection(name,email,password) values('Alceste','alceste@email.com','motdepa5');
insert into Connection(name,email,password) values('Jimmy','jimmy@email.com','motdepa6');
insert into Connection(name,email,password) values('Coralie','coralie@email.com','motdepa7');
insert into Connection(name,email,password) values('Céline','celine@email.com','motdepa8');
insert into Connection(name,email,password) values('André','andre@email.com','motdepa9');
insert into Connection(name,email,password) values('Annie','annie@email.com','motdepa10');

insert into bank_account(id_connection,balance) values(1,'15.00');
insert into bank_account(id_connection,balance) values(2,'150.00');
insert into bank_account(id_connection,balance) values(3,'1000.00');
insert into bank_account(id_connection,balance) values(4,'25.00');
insert into bank_account(id_connection,balance) values(5,'35.00');
insert into bank_account(id_connection,balance) values(6,'100.00');
insert into bank_account(id_connection,balance) values(7,'105.00');
insert into bank_account(id_connection,balance) values(8,'1005.00');
insert into bank_account(id_connection,balance) values(9,'15.00');
insert into bank_account(id_connection,balance) values(10,'15.00');

insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(1,2,'15.00','2024-01-02 17:51:04.789463','Restaurant');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(2,3,'150.00','2024-01-12 16:42:04.789463','Remboursement prêt');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(3,4,'200.00','2024-01-15 15:31:04.789463','Mariage');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(4,5,'100.00','2024-02-01 14:20:04.789463','Anniversaire');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(5,6,'5.00','2024-02-05 13:11:04.789463','Remboursement');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(6,7,'10.90','2024-03-22 12:21:04.789463','Remboursement');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(7,8,'25.00','2024-03-29 11:41:04.789463','Restaurant');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(10,2,'14.00','2024-04-19 10:22:04.789463','Remboursement');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(8,2,'12.00','2024-04-21 18:45:04.789463','Restaurant');
insert into Transactions(id_creditor_account,id_debtor_account,amount,transaction_date,description) values(9,4,'24.00','2024-05-03 21:56:04.789463','Anniversaire');

insert into relation(id_connection,id_user) values(2,1);
commit;
