-- Database: "SAMPLE"

-- DROP DATABASE "SAMPLE";

CREATE DATABASE "SAMPLE"
  WITH OWNER = admin
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;

-- Table: public.board

-- DROP TABLE public.board;

create  table board
(
  uid int auto_increment primary key,
  title character varying(100),
  context character varying(100),
  use_yn character varying(1),
  crtr_user character varying(100),
  crtr_datetime datetime,
  upd_user character varying(100),
  upd_datetime datetime
);