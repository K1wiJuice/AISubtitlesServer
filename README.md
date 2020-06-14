## AISubtitlesServer

- #### 使用准备：

  - MySql:

    - 建立database test01

    - 建表 user_info和update_user_info_event，建表信息在sql.txt中

  - 修改resources目录下application.yml中datasource的username和password

- #### 使用方法：

  ```sh
  ./mvnw spring-boot:run
  ```

- #### 我们提供的接口：

  /user/regist

  /user/login

  /user/logout

  /user/userModify

  /email/sendCode

  /findpassword/validatecode

  /findpassword/update

