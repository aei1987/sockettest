create table tb_user
(
  id int auto_increment primary key,
  username varchar(20) not null unique,
  passwd varchar(30)
);

create table tb_file
(
  fileid int auto_increment primary key,
  filename varchar(50) not null,
  filecontent blob
);