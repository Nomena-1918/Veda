create table Dept(
    id serial primary key,
    nom varchar(100) not null
);

create table Emp(
    id serial primary key,
    nom varchar(100) not null,
    iddept int references Dept(id)
);