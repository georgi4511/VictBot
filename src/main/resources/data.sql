INSERT INTO bookmark (id, user_id, created_time, message, response, guild_id) VALUES (1, 'example1','20250213 10:34:09','message_example','response_example','guild_id');
INSERT INTO bookmark (id, user_id, created_time, message, response, guild_id) VALUES (2, 'example2','20250213 10:34:09','message_example','response_example','guild_id');
INSERT INTO bookmark (id, user_id, created_time, message, response, guild_id) VALUES (3, 'example3','20250213 10:34:09','message_example','response_example','guild_id');
INSERT INTO bookmark (id, user_id, created_time, message, response, guild_id) VALUES (4, 'example4','20250213 10:34:09','message_example','response_example','guild_id');
INSERT INTO bookmark (id, user_id, created_time, message, response, guild_id) VALUES (5, 'example5','20250213 10:34:09','message_example','response_example','guild_id');

INSERT INTO reminder (id, user_id, created_time, message, target_time, guild_id, channel_sent_from, personal) VALUES (1, 'example1', '20250213 10:34:09', 'message_example', '20260218 10:34:09', 'guild_id', 'channel_id', false);
INSERT INTO reminder (id, user_id, created_time, message, target_time, guild_id, channel_sent_from, personal) VALUES (2, 'example2', '20250213 10:34:09', 'message_example', '20260218 10:34:09', 'guild_id', 'channel_id', true);
INSERT INTO reminder (id, user_id, created_time, message, target_time, guild_id, channel_sent_from, personal) VALUES (3, 'example3', '20250213 10:34:09', 'message_example', '20260218 10:34:09', 'guild_id', 'channel_id', false);
INSERT INTO reminder (id, user_id, created_time, message, target_time, guild_id, channel_sent_from, personal) VALUES (4, 'example4', '20250213 10:34:09', 'message_example', '20260218 10:34:09', 'guild_id', 'channel_id', false);
INSERT INTO reminder (id, user_id, created_time, message, target_time, guild_id, channel_sent_from, personal) VALUES (5, 'example5', '20250213 10:34:09', 'message_example', '20260218 10:34:09', 'guild_id', 'channel_id', false);

INSERT INTO impressions (id, bad_bot_count, good_bot_count, guild_id) VALUES (1,0,0, 'guild_id');
INSERT INTO impressions (id, bad_bot_count, good_bot_count, guild_id) VALUES (2,10001,10001, 'guild_id');
INSERT INTO impressions (id, bad_bot_count, good_bot_count, guild_id) VALUES (3,1,1, 'guild_id');
INSERT INTO impressions (id, bad_bot_count, good_bot_count, guild_id) VALUES (4,0,1, 'guild_id');
INSERT INTO impressions (id, bad_bot_count, good_bot_count, guild_id) VALUES (5,1,0, 'guild_id');