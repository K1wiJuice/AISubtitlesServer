## AISubtitlesServer

- #### 使用准备：

  - Java环境

  - MySql：建立database AIsubtitle

  - 修改resources目录下application.yml中datasource的username和password
  - 将src/main/resources/python目录上传至服务器/home/ubuntu路径下

- #### 打包命令：

  ```sh
  ./mvnw clean package -Dmaven.test.skip=true
  ```
  
- #### 运行方法：

  ```sh
  nohup java -jar Server-0.0.1-SNAPSHOT.jar > logs.txt &
  ```

- #### [提供的部分接口](https://docs.qq.com/doc/DRWVxTkZVZ256dmVG)
