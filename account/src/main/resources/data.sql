INSERT INTO t_user (username, last_name, first_name, middle_name, date_birth, email, phone_number)
VALUES ('ivanov', '������', '����', '��������', '1980-01-01', 'ivanov@mail.ru', '+79012345678'),
       ('petrov', '������', '����', '��������', '1990-02-15', 'petrov@mail.ru', '+79012345679'),
       ('sidorov', '�������', '�����', '���������', '1985-03-20', 'sidorov@mail.ru', '+79012345680'),
       ('kuznetsov', '��������', '�������', '����������', '1995-04-25', 'kuznetsov@mail.ru', '+79012345681'),
       ('smirnov', '�������', '����', '���������', '1988-05-30', 'smirnova@mail.ru', '+79012345682'),
       ('admin', '�������', '������', '���������', '1982-12-17', 'admin@mail.ru', '+79012345617')
ON CONFLICT (username) DO NOTHING;