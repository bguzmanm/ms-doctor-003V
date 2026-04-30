create table specialty (
    id int not null auto_increment,
    name varchar(100) not null,
    description varchar(500) not null,
    primary key (id)
);

create table doctor (
    id int not null auto_increment,
    name varchar(100) not null,
    specialty_id int not null,
    last_name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(20),

    primary key (id),
    foreign key (specialty_id) references specialty(id)
);

insert into specialty (name, description) values ('Cardiology', 'Specialty focused on heart and blood vessels');
insert into specialty (name, description) values ('Dermatology', 'Specialty focused on skin, hair, and nails');
insert into specialty (name, description) values ('Pediatrics', 'Specialty focused on medical care for infants, children, and adolescents');
insert into specialty (name, description) values ('Orthopedics', 'Specialty focused on musculoskeletal system');
insert into specialty (name, description) values ('Neurology', 'Specialty focused on nervous system and disorders');


insert into doctor (name, specialty_id, last_name, email) values
('John', 1, 'Doe', 'john@doctor.com'),
('Jane', 2, 'Smith', 'jane@doctor.com'),
('Emily', 3, 'Johnson', 'emily@doctor.com'),
('Michael', 4, 'Brown', 'michael@doctor.com'),
('Sarah', 5, 'Davis', 'sarah@doctor.com');