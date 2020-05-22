# 自习室座位管理系统
基于种子项目, 进行二次修改的 SpringBoot进行开发
## 项目路径
```
├── README.md
├── pom.xml
├── seat-of-study-room.iml
└── target
src
├── main
│   ├── java
│   │   └── com
│   │       └── study
│   │           └── room
│   │               ├── Application.java (主入口文件)
│   │               ├── configurer (配置文件夹)
│   │               │   ├── MybatisConfigurer.java (Mybatis工具的配置文件)
│   │               │   ├── PassToken.java (注解开发判定用户请求是否需要携带 TOKEN)
│   │               │   ├── UserLoginToken.java (需要携带 TOKEN)
│   │               │   └── WebMvcConfigurer.java (SpringBoot拦截器等相关配置类)
│   │               ├── core (核心基础类)
│   │               │   ├── AbstractService.java (业务逻辑包基础操作抽象类)
│   │               │   ├── Mapper.java (数据持久层的基础类)
│   │               │   ├── ProjectConstant.java (项目路径相关配置 -- 代码生成器相关)
│   │               │   ├── Result.java (返回前端数据结构的基础类)
│   │               │   ├── ResultCode.java (返回状态码的基础类)
│   │               │   ├── ResultGenerator.java (返回前端数据结构的构建类 -- 封装了基础的 成功/失败类型)
│   │               │   ├── Service.java (业务逻辑层的基础接口类)
│   │               │   └── ServiceException.java (业务逻辑异常定义类)
│   │               ├── dao (DAO层)
│   │               │   ├── FootprintMapper.java (足迹)
│   │               │   ├── SeatMapper.java (座位)
│   │               │   └── UserMapper.java (用户)
│   │               ├── dto (Data Transfer Object)
│   │               │   ├── BoardDTO.java (榜单)
│   │               │   ├── CreateUserDTO.java (创建用户)
│   │               │   ├── FootprintDTO.java (足迹)
│   │               │   ├── RoomsReportDTO.java (自习室报告)
│   │               │   └── UserDTO.java (用户操作)
│   │               ├── model (实体类)
│   │               │   ├── Footprint.java (足迹实体类)
│   │               │   ├── Seat.java (座位实体类)
│   │               │   └── User.java (用户实体类)
│   │               ├── service (业务逻辑层 -- 接口(不含方法))
│   │               │   ├── FootprintService.java (足迹)
│   │               │   ├── SeatService.java (座位)
│   │               │   ├── UserService.java (用户)
│   │               │   └── impl (-- 实际操作)
│   │               │       ├── FootprintServiceImpl.java
│   │               │       ├── SeatServiceImpl.java
│   │               │       └── UserServiceImpl.java
│   │               ├── utils (工具类)
│   │               │   ├── FileUtils.java (文件操作 -- 好像没用到)
│   │               │   ├── MD5Utils.java (MD5加密)
│   │               │   └── Tools.java (杂项工具类)
│   │               └── web (控制器)
│   │                   ├── FootprintController.java (足迹)
│   │                   ├── SeatController.java (座位)
│   │                   └── UserController.java (用户)
│   └── resources (资源文件夹)
│       ├── application-dev.properties (开发配置文件)
│       ├── application-prod.properties (生产配置文件)
│       ├── application-test.properties (测试配置文件)
│       ├── application.properties (配置文件主入口)
│       ├── banner.txt (有趣的东西)
│       └── mapper (映射层 -- 数据库语句)
│           ├── FootprintMapper.xml (足迹)
│           ├── SeatMapper.xml (座位)
│           └── UserMapper.xml (用户信息)
└── test
    ├── java (测试调试类包)
    │   ├── CodeGenerator.java
    │   └── com
    │       └── conpany
    │           └── project
    │               └── Tester.java
    └── resources (代码自动生成相关)
        ├── demo-user.sql
        └── generator
            └── template
                ├── controller-restful.ftl
                ├── controller.ftl
                ├── service-impl.ftl
                └── service.ftl
```
