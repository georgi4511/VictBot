INSERT INTO vict_user (id, discord_id) VALUES (1,'1');
INSERT INTO vict_user (id, discord_id) VALUES (2,'2');
INSERT INTO vict_user (id, discord_id) VALUES (3,'3');
INSERT INTO vict_user (id, discord_id) VALUES (4,'4');
INSERT INTO vict_user (id, discord_id) VALUES (5,'5');
INSERT INTO vict_guild (id, discord_id) VALUES (1,'1');
INSERT INTO vict_guild (id, discord_id) VALUES (2,'2');
INSERT INTO vict_guild (id, discord_id) VALUES (3,'3');
INSERT INTO vict_guild (id, discord_id) VALUES (4,'4');
INSERT INTO vict_guild (id, discord_id) VALUES (5,'5');



INSERT INTO bookmark (id, vict_user_id, created_time, message, response, vict_guild_id) VALUES (1, 1,'20250213 10:34:09','message_example','response_example',1);
INSERT INTO bookmark (id, vict_user_id, created_time, message, response, vict_guild_id) VALUES (2, 2,'20250213 10:34:09','message_example','response_example',2);
INSERT INTO bookmark (id, vict_user_id, created_time, message, response, vict_guild_id) VALUES (3, 2,'20250213 10:34:09','message_example','response_example',3);
INSERT INTO bookmark (id, vict_user_id, created_time, message, response, vict_guild_id) VALUES (4, 3,'20250213 10:34:09','message_example','response_example',4);
INSERT INTO bookmark (id, vict_user_id, created_time, message, response, vict_guild_id) VALUES (5, 4,'20250213 10:34:09','message_example','response_example',4);

INSERT INTO reminder (id, vict_user_id, created_time, message, target_time, vict_guild_id, channel_sent_from, personal) VALUES (1, 1, '20250213 10:34:09', 'message_example', '20260218 10:34:09', 1, 'channel_id', false);
INSERT INTO reminder (id, vict_user_id, created_time, message, target_time, vict_guild_id, channel_sent_from, personal) VALUES (2, 1, '20250213 10:34:09', 'message_example', '20260218 10:34:09', 1, 'channel_id', true);
INSERT INTO reminder (id, vict_user_id, created_time, message, target_time, vict_guild_id, channel_sent_from, personal) VALUES (3, 3, '20250213 10:34:09', 'message_example', '20260218 10:34:09', 1, 'channel_id', false);
INSERT INTO reminder (id, vict_user_id, created_time, message, target_time, vict_guild_id, channel_sent_from, personal) VALUES (4, 5, '20250213 10:34:09', 'message_example', '20260218 10:34:09', 1, 'channel_id', false);
INSERT INTO reminder (id, vict_user_id, created_time, message, target_time, vict_guild_id, channel_sent_from, personal) VALUES (5, 5, '20250213 10:34:09', 'message_example', '20260218 10:34:09', 1, 'channel_id', false);

INSERT INTO impressions (vict_guild_id, bad_bot_count, good_bot_count) VALUES (1,0,0);
INSERT INTO impressions (vict_guild_id, bad_bot_count, good_bot_count) VALUES (2,10001,10001);
INSERT INTO impressions (vict_guild_id, bad_bot_count, good_bot_count) VALUES (3,1,1);
INSERT INTO impressions (vict_guild_id, bad_bot_count, good_bot_count) VALUES (4,0,1);
INSERT INTO impressions (vict_guild_id, bad_bot_count, good_bot_count) VALUES (5,1,0);