#!/usr/bin/env bash

USERS_FILE=$1
LOGINS_FILE=$2
BANNED_FILE=$3

if [[ ! -f "$USERS_FILE" || ! -f "$LOGINS_FILE" || ! -f "$BANNED_FILE" ]]; then
  echo "❌ Один из входных файлов не найден."
  exit 1
fi

# Читаем список забаненных
BANNED=()
while IFS= read -r user; do
  BANNED+=("$user")
done < <(jq -r '.[]' "$BANNED_FILE")

# Получаем сегодняшнюю дату в секундах
TODAY=$(date +%s)

# Словарь логин → дата последнего входа
declare -A LAST_LOGINS

while IFS=',' read -r login date; do
  [[ -z "$login" || -z "$date" ]] && continue
  LAST_LOGINS["$login"]="$date"
done < <(tail -n +2 "$LOGINS_FILE")

# DEBUG (можешь удалить потом)
echo "DEBUG LAST_LOGINS size: ${#LAST_LOGINS[@]}"

# Создаём файл результата
echo "login,last_login" > active_users.csv

# Проверка каждого пользователя
while IFS= read -r user; do

  # пропуск пустых строк
  [[ -z "$user" ]] && continue

  # если пользователь в бане → пропускаем
  if printf '%s\n' "${BANNED[@]}" | grep -qx "$user"; then
    continue
  fi

  last_login="${LAST_LOGINS[$user]}"

  # если нет логина → пропускаем
  if [[ -z "$last_login" ]]; then
    continue
  fi

  # конвертация даты (Linux/macOS-safe)
  if date -d "$last_login" +%s >/dev/null 2>&1; then
    login_ts=$(date -d "$last_login" +%s)
  else
    login_ts=$(date -j -f "%Y-%m-%d" "$last_login" +%s 2>/dev/null)
  fi

  [[ -z "$login_ts" ]] && continue

  days=$(( (TODAY - login_ts) / 86400 ))

  if [[ "$days" -le 30 ]]; then
    echo "$user,$last_login" >> active_users.csv
  fi

done < "$USERS_FILE"

echo "✅ Готово: active_users.csv"