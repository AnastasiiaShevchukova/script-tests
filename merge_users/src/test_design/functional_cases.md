# Функциональные тест-кейсы merge_users.sh

1) Все пользователи с полными данными объединяются
2) Один пользователь
3) Несколько пользователей
4) Пользователь есть в txt, но нет в json
5) Пользователь есть в txt, но нет в csv
6) Пустой users.txt
7) Пустой users.json
8) Пустой users.csv
9) Отсутствует users.txt
10) Отсутствует users.json
11) Отсутствует users.csv
12) Дубликаты логинов в CSV
13) Проверка формата full_users.csv
    
## Тест кейс "Все пользователи с полными данными объединяются"

given
- создание файла users.txt
- установка пользователей:
    - alice
    - bob
    - carol
- создание файла users.csv
- установка header (login,email)
- установка данных:
    - alice,alice@example.com
    - bob,bob@example.com
    - carol,carol@example.com
- создание файла users.json
- установка данных:
```json
  {
  "alice": "Alice Smith",
  "bob": "Bob Johnson",
  "carol": "Carol Lee"
  }
```
when
- запуск скрипта с параметрами:
    - usersTXTFilePath
    - usersJSONFilePath
    - usersCSVFilePath
- успешный запуск скрипта (exit code 0)

then
- проверка создания файла full_users.csv
- проверка содержимого full_users.csv:
```csv
login,name,email
alice,Alice Smith,alice@example.com
bob,Bob Johnson,bob@example.com
carol,Carol Lee,carol@example.com
```

---
## Тест кейс "Один пользователь"
given
- создание файла users.txt
- установка пользователя:
    - alice
- создание файла users.csv
- установка header (login,email)
- установка данных:
    - alice,alice@example.com
- создание файла users.json
- установка данных:
```json
{
"alice": "Alice Smith"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
alice,Alice Smith,alice@example.com
```

---

## Тест кейс "Несколько пользователей"
given
- создание файла users.txt
- установка пользователей:
    - alice
    - bob
    - carol
- создание файла users.csv
- установка header (login,email)
- установка данных:
    - alice,alice@example.com
    - bob,bob@example.com
    - carol,carol@example.com
- создание файла users.json
- установка данных:
```json
{
"alice": "Alice Smith",
"bob": "Bob Johnson",
"carol": "Carol Lee"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
alice,Alice Smith,alice@example.com
bob,Bob Johnson,bob@example.com
carol,Carol Lee,carol@example.com
```

---
## Тест кейс "Пользователь есть в txt, но нет в json"
given
- создание файла users.txt
- установка пользователей:
    - alice
    - bob
- создание файла users.csv
- установка header (login,email)
- установка данных:
    - alice,alice@example.com
    - bob,bob@example.com

- создание файла users.json
- установка данных:
```json
{
"alice": "Alice Smith"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
alice,Alice Smith,alice@example.com
```
- проверка сообщения:
⚠️ Пропущен пользователь bob: нет имени или email

---
##  Тест кейс "Пользователь есть в txt, но нет в csv"
given
- создание файла users.txt
- установка пользователей:
    - alice
    - bob
- создание файла users.csv
- установка header (login,email)
- установка данных:
    - alice,alice@example.com
- создание файла users.json
- установка данных:
```json
{
"alice": "Alice Smith",
"bob": "Bob Johnson"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
alice,Alice Smith,alice@example.com
```
- проверка сообщения:
⚠️ Пропущен пользователь bob: нет имени или email

---
## Тест кейс "Пустой users.txt"
given
- создание пустого файла users.txt
- создание файла users.csv
login,email
- создание файла users.json
{}
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
```

---
## Тест кейс "Пустой users.json"
given
- создание файла users.txt
- установка пользователя:
    - alice
- создание файла users.csv
```csv
login,email
alice,alice@example.com
```
- создание пустого users.json
```json
{}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
```
- проверка сообщения:
⚠️ Пропущен пользователь alice: нет имени или email

---
## Тест кейс "Пустой users.csv"
given
- создание файла users.txt
- установка пользователя:
    - alice
- создание файла users.csv
```csv
login,email
```
- создание файла users.json
```json
{
"alice": "Alice Smith"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
```

---
## Тест кейс "Отсутствует users.txt"
given
- отсутствие файла users.txt
- создание файла users.csv
- создание файла users.json

when
- запуск скрипта

then
- проверка exit code:1

- проверка ошибки:
Один из входных файлов не найден

---
## Тест кейс "Отсутствует users.json"
given
- создание файла users.txt
- создание файла users.csv
- отсутствие users.json

when
- запуск скрипта

then
- проверка exit code:1
- проверка ошибки: Один из входных файлов не найден
---
## Тест кейс "Отсутствует users.csv"
given
- создание файла users.txt
- создание файла users.json
- отсутствие users.csv

when
- запуск скрипта

then
- проверка exit code:1
- проверка ошибки: Один из входных файлов не найден

---
## Тест кейс "Дубликаты логинов в CSV"
given
- создание файла users.txt
- установка пользователя:
    - alice
- создание файла users.csv
```csv
login,email
alice,old@example.com
alice,new@example.com
```
- создание файла users.json
```json
{
"alice": "Alice Smith"
}
```
when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка full_users.csv:
```csv
login,name,email
alice,Alice Smith,new@example.com
```

---
## Тест кейс "Проверка формата full_users.csv"
given
- создание файлов users.txt, users.csv, users.json
- установка одного пользователя с полными данными

when
- запуск скрипта
- успешный запуск скрипта (exit code 0)

then
- проверка header:
login,name,email
- проверка порядка колонок:
login
name
email