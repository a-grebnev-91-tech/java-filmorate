# java-filmorate
![database diagram](https://github.com/a-grebnev-91-tech/java-filmorate/blob/database-diagram/filmorate_sprint_10.png)

Основные таблицы базы - film и user

Так как подразумевается, что топ фильмов будет запрашиваться достаточно часто, в таблице film добавлено расчетное поле количества лайков, для оптимизации данной операции.

Пример запроса на получения топа:
```
SELECT *
FROM film
ORDER BY likes_count
LIMIT {count}
```