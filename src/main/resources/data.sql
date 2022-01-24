CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO sec_roles (id, name, status) VALUES
  (uuid_generate_v4(),'ROLE_ADMIN', 'ACT'),
  (uuid_generate_v4(),'ROLE_CUSTOMER', 'ACT'),
  (uuid_generate_v4(),'ROLE_SUPER', 'ACT'),
  (uuid_generate_v4(),'ROLE_USER', 'ACT');

INSERT INTO sec_permissions (id, name, status) VALUES
  (uuid_generate_v4(),'READ_PERMISSIONS', 'ACT'),
  (uuid_generate_v4(),'WRITE_PERMISSIONS', 'ACT'),
  (uuid_generate_v4(),'ALL_PERMISSIONS', 'ACT'),
  (uuid_generate_v4(),'VIEW_ALL_PERMISSIONS', 'ACT');

INSERT INTO sec_users (id, created_date, "username", email, "password", status)
	   VALUES(uuid_generate_v4(), now(), 'Admin', 'diego1503bsc@gmail.com',
	  '$2a$10$Iq/whWhDWGF4o0CJjRhXHe9y6p5m9ZprgFTPnJoplPELdSzHSFl32', 'ACT'::character varying);

INSERT INTO public.sec_user_rol (user_id, rol_id) values
			((select id from sec_users where username = 'Admin'),
			 (select id from sec_roles where name = 'ROLE_ADMIN'));

INSERT INTO public.sec_rol_permissions (rol_id, permission_id) values
			((select id from sec_roles where name = 'ROLE_ADMIN'),
			 (select id from sec_permissions where name = 'ALL_PERMISSIONS'));