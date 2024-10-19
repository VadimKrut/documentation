insert into documentation.roles (name) values ('Админ') on conflict do nothing;
insert into documentation.roles (name) values ('Пользователь') on conflict do nothing;

insert into documentation.role_permissions (permission_id, role_id) values (1, 1) on conflict do nothing;
insert into documentation.role_permissions (permission_id, role_id) values (2, 2) on conflict do nothing;