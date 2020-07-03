## AISubtitlesServer

- #### 使用准备：

  - Java环境
  - MySql：建立database AIsubtitle
  - 修改resources目录下application.yml中datasource的username和password，腾讯云服务的ID和KEY：tencent-Id、tencent-Key
  - 修改src/main/resources/python目录下的audio2zhSubtitle.py、audio2zhSubtitle_words.py、demo_translate.py中的ID和KEY，改为使用的腾讯云服务的对应值
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
