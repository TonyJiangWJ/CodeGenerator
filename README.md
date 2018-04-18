# CodeGenerator
- 基于velocity实现的代码生成工具，目前只是生成ibatis的sqlMap和实体类
- 数据库相关的配置需要在generate-config.xml里面修改
- 模板所在路径和待生成的文件路径在template-path-config.xml里面配置
- 上述配置完毕之后执行Application.java即可自动生成，具体的根据每个项目自行修改模板文件即可
## 后续需要实现的功能（看心情） 
- 实体类按指定的包生成到对应的路径
- Action类 等等
- 。。。 