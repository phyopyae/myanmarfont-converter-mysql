## Description

This is a SpringBoot application which is to convert all Zawgyi texts from MySQL to Myanmar Unicode by selecting database table and column.

This repository is using Myanmar Tools (Zawgyi detection & conversion) from https://github.com/google/myanmar-tools.
Ref Usage of myanmar-tools for converting zawgyi to unicode (https://jonathanphyo.wixsite.com/techbook/post/convert-from-zawgyi-to-myanmar-unicode-myanmar-tools)

Using
- SpringBoot
- Hibernate
- Thymeleaf
- MySQL
- JDBC

## Database Setting

Firstly, run the initial_db.sql to create necessary database and config table.

- database.user= username of MySQL
- database.password= password of MySQL
- database.name= mmfontconverter

### Remark

This repository is not finished testing yet. There may be a lot of bugs or issues.
