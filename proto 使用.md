### 一、idea下 .proto 文件高亮显示

需要安装Protobuf Support插件

依次点击Intellij中的“File”-->"Settings"-->"Plugins"-->"Browse repositories"进行安装即可。

### 二、将 .proto 文件转换成 java 文件
1. 首先引入相应的包文件，指定相应的版本

```
<dependencies>
	<dependency>
	    <groupId>io.grpc</groupId>
	    <artifactId>grpc-netty</artifactId>
	    <version>${grpc.version}</version>
	    <scope>provided</scope>
	</dependency>
	<dependency>
	    <groupId>io.grpc</groupId>
	    <artifactId>grpc-protobuf</artifactId>
	    <version>${grpc.version}</version>
	    <scope>provided</scope>
	</dependency>
	<dependency>
	    <groupId>io.grpc</groupId>
	    <artifactId>grpc-stub</artifactId>
	    <version>${grpc.version}</version>
	    <scope>provided</scope>
	</dependency>
	<dependency>
	    <groupId>com.google.protobuf</groupId>
	    <artifactId>protobuf-java</artifactId>
	    <version>${protobuf.version}</version>
	</dependency>
</dependencies>
```

2. 添加相应的构建配置

```
<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.4.1.Final</version>
        </extension>
    </extensions>

    <plugins>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.5.0</version>
            <extensions>true</extensions>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.0.0:exe:${os.detected.classifier}</protocArtifact>
                <clearOutputDirectory>false</clearOutputDirectory>
                <outputDirectory>src/main/java</outputDirectory>
                <pluginId>grpc-java</pluginId>
                <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.0.0:exe:${os.detected.classifier}</pluginArtifact>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>compile-custom</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

3. 在maven插件中使用相应的功能就可以生成 java 文件

<img src='https://images2017.cnblogs.com/blog/380081/201709/380081-20170911161621641-1667317613.png' />