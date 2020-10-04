Семнадцатое домашнее задание (Docker):
1. Для запука приложения и вспомогательной инфраструктуры в Docker-контейнере, сперва следует выполнить создания образа,
используя возможности buildpacks (SpringBoot 2.3). Для этого нужно выполнить скрипт prepareDockerImage.bat/prepareDockerImage.sh 
в зависимости от Вашей ОС
2. После успешной установки образа достаточно вызвать команду 
docker-compose up -d
дождавшись старта БД и приложения
3. Открыть адрес http://localhost:8080. Для входа использовать учетку admin/admin.