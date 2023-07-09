INSERT INTO users (username, password, enabled)
values ('user', '$2a$10$0lEau2iitu5HMeqquONsU.G3O/zN9JodmgYF8hMfVSa32XjvziIpO', true);
INSERT INTO users (username, password, enabled)
values ('admin', '$2a$10$0lEau2iitu5HMeqquONsU.G3O/zN9JodmgYF8hMfVSa32XjvziIpO', true);

INSERT INTO authorities (username, authority)
values ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority)
values ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority)
values ('admin', 'ROLE_ADMIN');
