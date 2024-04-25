create sequence sesion_seq start with 1 increment by 50;
create table datosalud (sesion_id bigint not null, dato varchar(255));
create table multimedia (sesion_id bigint not null, valor varchar(255));
create table sesion (id bigint not null, descripcion varchar(255), fin date, id_plan bigint, inicio date, presencial boolean, trabajo_realizado varchar(255), primary key (id));
alter table if exists datosalud add constraint FK7v6tbqai5tolf9uy06hcd8dil foreign key (sesion_id) references sesion;
alter table if exists multimedia add constraint FKmgn56ldtdrd8j86ucfg8bwp04 foreign key (sesion_id) references sesion;
