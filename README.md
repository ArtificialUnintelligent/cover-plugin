# 单元测试覆盖率统计插件--for jacoco
为了更少的bug，为了更美好的明天

## 添加插件依赖
### 单模块项目
1.配置jacoco 
引入依赖
```xml
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.2</version>
</dependency>
```
2.引入插件
```xml
<plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.2</version>
        <configuration>
          <!--这里指定单元测试覆盖率扫描的包-->
          <includes>
            <include>**/service/**</include>
            <include>**/remote/**</include>
          </includes>
          <!--这里指定单元测试覆盖率排除的包-->
          <excludes>
            <exclude>**/domain/**</exclude>
            <exclude>**/dto/**</exclude>
            <exclude>**/po/**</exclude>
          </excludes>
           <!-- 指定报告率生成路经，与父pom同级别，且必须生成在这路经下 -->
          <outputDirectory>${basedir}/../target/site/jacoco</outputDirectory>
        </configuration>
        <executions>
          <execution>
            <id>jacoco-initialize</id>
            <goals>
              <goal>prepare-agent</goal>
            </goals>
          </execution>
          <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
              <goal>report</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
3.pom中引入本插件
```xml
<plugin>
        <groupId>com.souche</groupId>
        <artifactId>cover-maven-plugin</artifactId>
        <version>1.2.2-SNAPSHOT</version>
        <configuration>
          <skip>false</skip>
        </configuration>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>cover-report</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
4.执行下列命令：
```
mvn test jacoco:report 
mvn cover:cover-report
```

### 2 多模块项目
1.父pom中引入jacoco插件及依赖
```xml
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.2</version>
</dependency>
```
```xml
<plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>0.8.2</version>
 </plugin>
```
2.父pom下引入本插件
```xml
<plugin>
        <groupId>com.souche</groupId>
        <artifactId>cover-maven-plugin</artifactId>
        <version>1.2.2-SNAPSHOT</version>
</plugin>
```
3.每个子pom文件新增jacoco插件及依赖，同时配置jacoco
```xml
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
</dependency>
```
```xml
<plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <configuration>
          <!--这里指定单元测试覆盖率扫描的包-->
          <includes>
            <include>**/service/**</include>
            <include>**/remote/**</include>
          </includes>
          <!--这里指定单元测试覆盖率排除的包-->
          <excludes>
            <exclude>**/domain/**</exclude>
            <exclude>**/dto/**</exclude>
            <exclude>**/po/**</exclude>
          </excludes>
           <!-- 指定报告率生成路经，与父pom同级别，且必须生成在这路经下 -->
          <outputDirectory>${basedir}/../target/site/jacoco</outputDirectory>
        </configuration>
        <executions>
          <execution>
            <id>jacoco-initialize</id>
            <goals>
              <goal>prepare-agent</goal>
            </goals>
          </execution>
          <execution>
            <id>jacoco-site</id>
            <phase>package</phase>
            <goals>
              <goal>report</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
4.子pom文件中引入本插件
```xml
<plugin>
        <groupId>com.souche</groupId>
        <artifactId>cover-maven-plugin</artifactId>
        <version>1.2.2-SNAPSHOT</version>
        <configuration>
          <skip>false</skip>
        </configuration>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>cover-report</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
```
5.在父pom下执行命令
```
mvn test jacoco:report 
mvn cover:cover-report
```

## 与gitlab集成
1. 进入工程/项目组->settings->CI/CD->Runners，目前管理员还没有配置共享的runner，因此需要自行配置，参考install gitlab runner，记得在config.toml文件中（文件目录/etc/gitlab-runner/）新增一行，设置日志输出大一些：output_limit = 8192
2. 新建.gitlab-ci.yml文件，并写入以下内容，需要注意：tags里面指定的tag对应runner配置的tag
```yaml
stages:
  - test

test:
  stage: test
  script:
    - mvn test jacoco:report 
    - mvn cover:cover-report
  only:
    - unit-test
  tags:
    - test-tag
```
3. 完成上述步骤后就可以在提交代码到gitlab时触发插件