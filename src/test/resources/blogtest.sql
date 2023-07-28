INSERT INTO public.blog_users (name, username, password) VALUES ('test user', 'test', '$2a$10$iiEBi8aTweGNwjWrHPuZkekcio1lTwsDxvnjwci0ZJ1bX4iKsnDBS');
INSERT INTO public.blog_articles (title, content, author, releasedate) VALUES ('article 1', 'my new content', 1, CURRENT_TIMESTAMP());

