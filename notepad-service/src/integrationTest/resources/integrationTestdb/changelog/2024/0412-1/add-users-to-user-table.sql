BEGIN TRANSACTION;

INSERT INTO fixchaos.notepad.user (id, user_name, password) VALUES('5404f0b6-0545-4c79-9c15-d12aed0f5747', 'user1', 'password1');

INSERT INTO fixchaos.notepad.user (id, user_name, password) VALUES('6404f0b7-0545-4c79-9c15-d12aed0f5747', 'user2', 'password2');

COMMIT TRANSACTION;