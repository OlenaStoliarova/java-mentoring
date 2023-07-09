INSERT INTO users (username, password, enabled, failedLoginAttempts, locked, lockStart)
values ('user', '$2a$10$0lEau2iitu5HMeqquONsU.G3O/zN9JodmgYF8hMfVSa32XjvziIpO', true, 0, false, null);
INSERT INTO users (username, password, enabled, failedLoginAttempts, locked, lockStart)
values ('admin', '$2a$10$0lEau2iitu5HMeqquONsU.G3O/zN9JodmgYF8hMfVSa32XjvziIpO', true, 0, false, null);
INSERT INTO users (username, password, enabled, failedLoginAttempts, locked, lockStart)
values ('combo', '$2a$10$0lEau2iitu5HMeqquONsU.G3O/zN9JodmgYF8hMfVSa32XjvziIpO', true, 0, false, null);

INSERT INTO authorities (username, authority)
values ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority)
values ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority)
values ('combo', 'ROLE_USER');
INSERT INTO authorities (username, authority)
values ('combo', 'ROLE_ADMIN');
