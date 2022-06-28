# java-filmorate
![database diagram](https://github.com/a-grebnev-91-tech/java-filmorate/blob/database-diagram/filmorate_sprint_10.png)

1. Основные таблицы базы - film и user

2. Так как подразумевается, что топ фильмов будет запрашиваться достаточно часто, в таблице film добавлено расчетное поле количества лайков, для оптимизации данной операции.
   - Пример запроса на получения топа из n-фильмов:
```
SELECT *
FROM film
ORDER BY likes_count DESC
LIMIT n
```

3. Так как вероятность того, что обозначения возрастных рейтингов будут меняться, [крайне мала](https://i.ytimg.com/vi/Dtjsm4TS0co/hqdefault.jpg),
было принято решение не заводить отдельную таблицу под рейтинги, в целях улучшения производительности запросов.

4. Пример запроса на получение друзей пользователя с id = 1234:
```
SELECT 
	name
FROM user
WHERE user_id IN 
(
	SELECT friend_id
	FROM friends
	WHERE user_id = 1234 
);
```   