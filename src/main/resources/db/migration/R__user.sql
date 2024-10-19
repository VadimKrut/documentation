insert into documentation.user (login, password, role_id)
values ('admin', '$2y$10$trhhiAcZrEsTvhh6bwqVuOvF0CrFhBNKZU7BnPZTTG192/fHo5Aee', 1)
on conflict do nothing;