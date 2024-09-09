package xmut.cs.ojbackend;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Codegen {

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://192.168.1.103:5433/onlinejudge");
        dataSource.setUsername("onlinejudge");
        dataSource.setPassword("onlinejudge");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("xmut.cs.ojbackend");

        //设置表前缀和只生成哪些表
        globalConfig.setGenerateTable("user");


// 设置生成 Entity 并启用 Lombok、设置父类
        globalConfig.enableEntity()
                .setWithLombok(true);

        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);

        return globalConfig;
    }

    public static GlobalConfig createGlobalConfigUseStyle2() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.getPackageConfig()
                .setBasePackage("xmut.cs.tmp");

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setGenerateTable( "grade" );
//                .setGenerateTable( "course", "course_user", "exam", "exam_profile",
//                    "exam_submission","options_sysoptions",
//                    "problem", "problem_tag", "problem_tags", "submission", "user"
//                );
        //设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setWithLombok(true);

        //设置生成 mapper
        globalConfig.enableMapper();

        //设置生成 service()
        globalConfig.enableService();

        globalConfig.enableServiceImpl();

        globalConfig.enableController();


        return globalConfig;
    }
}