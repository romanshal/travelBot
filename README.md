# travelBot
Этот бот расскажет Вам всё,что следует знать о городах мира.
BotUsername - TravelSolutionsBot

BotToken - 1066501856:AAHZZ1x-jc2b2EF35_SjwYAOQrk24UFUUmI
Для запуска проекта нужно:
1. Открываем папку в которую хотим скачать проект. Вызываем git bash. Вводим команду git clone git@github.com:romanshal/travelBot.git (git clone https://github.com/romanshal/travelBot.git)
2. Создаем БД в MySQL (таблицу создаст сам, но дополнительно есть database.sql)
3. В файле application.properties конфигурируется подключение к БД (название БД, пользователь и пароль)
4. Собрираем проект с помощью maven.
5. В telegram находим бота по BotUsername - TravelSolutionsBot, подключаемся к нему.
6. В папке target открываем консоль и запускаем проект с помощью команды java -jar travelBot-1.0-SNAPSHOT.jar
