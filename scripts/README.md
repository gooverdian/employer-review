# Порядок устаноки и запуска приложения Отзывов по компаниям hh.ru

### Скрипты для созданя БД и пользователя hh:

`sctipts/init-db.sh` - создание бд employer_review в Unix системах

`sctipts/init-db.bat` - создание бд employer_review в Windows

### Для того чтобы накатить последнюю версию БД, нужно перейти в `backend` и воспользоваться командой:
```
mvn flyway:migrate
```

### После того как БД сознада заполним ее командой:
```
mvn exec:java@downloader 
```
Для ограничения количества скачиваемых компаний и регионов можно использовать параметры
```
mvn exec:java@downloader -Dexec.args="maxAreasSize maxEmployersSize"
```
`maxAreasSize` - максимальное кол-во регионов, `maxEmployersSize` - максимальное кол-во компаний

### После того как БД заполнена, запустим бэкнед сервиса командой:
```
mvn exec:java@main
```

### Для запуска фронтенда прейдем в папку `frontend` и выполним:
```
yarn install

yarn start
```
