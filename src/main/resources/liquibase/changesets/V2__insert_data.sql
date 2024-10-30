insert into client (username, password, name, birthday)
values ('ivan11', '$2a$12$Vm4IqYWgL1yp3z5dL29dPeHIUqKJudNBmV.qIv4Rami498MR.Nm7.', 'Иванов Иван Иванович', '1990-01-31'),
       ('serg22', '$2a$12$Vm4IqYWgL1yp3z5dL29dPeHIUqKJudNBmV.qIv4Rami498MR.Nm7.', 'Сергеев Сергей Сергеевич', '1992-03-18');

insert into bank_account (client_id, balance, initial_deposit)
values (1, 100.00, 100.00),
       (2, 150.00, 150.00);

insert into phone (client_id, phone_number)
values (1, '+7-911-999-99-99'),
       (1, '+7-918-888-88-88'),
       (2, '+7-938-555-55-55');

insert into email (client_id, email)
values (1, 'ivan@mail.ru'),
       (2, 'serg@mail.ru'),
       (2, 'serg@yandex.ru');