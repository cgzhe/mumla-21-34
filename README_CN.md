# Mumla

Mumla 是 [Plumble](https://github.com/acomminos/Plumble) 的一个分支和延续版本，Plumble 是由 Andrew Comminos 编写的 Android 端 Mumble 客户端，采用 GPLv3 许可证。

本仓库基于原 Mumla 项目（[https://github.com/quite/mumla](https://github.com/quite/mumla)）进行二次开发，**完整保留了原有的 Git 提交历史**，并在其基础上进行了定制化修改。

## 关于本分支

本分支在原 Mumla 基础上进行了以下适配和优化：

- **降低 SDK 要求**：仅适配 Android 5.0 至 Android 15.0（API 21-34），移除了对更高版本 Android 的支持
- **证书问题修复**：解决了原版本中的证书使用问题
- **小屏设备适配**：优化了在小尺寸屏幕上的显示效果
- **无触摸屏设备支持**：适配了没有触摸屏的 Android 设备（如电视盒子、车载设备等）

## 与原项目的关系

- **原项目地址**：[https://github.com/quite/mumla](https://github.com/quite/mumla)
- **本仓库**：基于原项目 fork，保留了完整的提交历史
- **主要差异**：如上所述，专注于低版本 Android 设备的兼容性和特定场景的适配

## 原项目维护状态

> 以下内容来自原 Mumla 项目说明：

当前维护者 [Daniel Lublin](https://lublin.se) ([@quite@mstdn.social](https://mstdn.social/@quite)) 用于处理 Mumla 志愿工作的时间非常有限。维护工作主要集中在保持稳定性和安全性上，包括随着 Google/Alphabet 对 Google Play 发布更新的要求而迁移到更新的 Android SDK。

预计 Mumla 最终可能会从 Google Play 下架，因为某些新要求无法及时满足。项目最终也可能因未适配新版 Android 而无法正常运行。

Mumla 需要一位能够投入时间的新维护者来接手这些工作，以保持稳定性和安全性，并希望未来能与社区合作，实现与桌面版 Mumble 的协议同步、支持各种硬件配件、提升易用性以及添加新功能。

## 原项目说明

Mumla 应该能在 Android 5.0（Lollipop，API 21）及以上版本运行。

Mumla 可在 [F-Droid](https://f-droid.org/packages/se.lublin.mumla/) 获取。

项目主页：[https://mumla-app.gitlab.io/](https://mumla-app.gitlab.io/)

### 常见问题

#### 用户有权限的操作未显示在溢出菜单中

问：我使用的 Mumble 服务器通过 ACL 授予了我的用户（或其所在组）执行某项操作（如"移动"）的权限。为什么 Mumla 没有在频道或用户的溢出菜单（三个点）中显示该操作？

答：尝试断开连接后重新连接服务器。是否显示菜单项取决于用户是否拥有所需权限，这个判断是在连接时（UI 初始化时）完成的。如果连接后权限发生变化，菜单不会实时更新。

### 翻译

如果您想帮助翻译 Mumla，该项目已上线 [Weblate](https://hosted.weblate.org/engage/mumla/)——感谢为我们自由软件项目提供免费托管！

### 仓库子模块

注意：本 Mumla git 仓库包含子模块。克隆时需要使用 `git clone --recursive`，或者在克隆后执行以下命令获取子模块：

    git submodule update --init --recursive

### 在 GNU/Linux 上构建

使用 JDK 21 已验证可以成功构建。通常需要设置并导出 JAVA_HOME 环境变量，例如：`export JAVA_HOME=/usr/lib/jvm/java-21-openjdk`

Android SDK 路径需要按常规方式指定，例如设置 `ANDROID_SDK_ROOT`，或写入 local.properties 文件：`echo >local.properties sdk.dir=/home/user/Android/Sdk`

构建步骤：

    git submodule update --init --recursive

    pushd libraries/humla/libs/humla-spongycastle
    ../../gradlew jar
    popd

    ./gradlew assembleDebug


如果遇到 Java 堆内存不足的错误，请尝试提高 `./gradle.properties` 中的 `-Xmx` 值。

#### NDK 说明

NDK 是用于构建 Humla 原生代码（C/C++）的工具链。我们通过 `libraries/humla/build.gradle` 中的 `ndkVersion` 指定所需版本。

当前使用 Android Gradle Plugin (AGP) 8.x 版本，内置 NDK 25.1.8937393。NDK 通常安装在 `~/Android/Sdk/ndk/` 目录下。使用更新的 NDK 可能会导致构建错误。

如果 Android Studio 未自动安装指定版本的 NDK，可通过 SDK Manager 手动安装：

- 点击 SDK Tools 选项卡
- 勾选 "Show Package Details"
- 在列表中展开 "NDK (Side by side)"
- 勾选 25.1.8937393
- 点击 OK

### 容器镜像

我维护了一个用于构建 Mumla 的容器镜像，在 [.gitlab-ci.yml](.gitlab-ci.yml) 中使用。源码地址：https://gitlab.com/quite/android-sdk-ndk

## 许可证

Mumla 的 [LICENSE](LICENSE) 采用 GNU GPL v3 许可证。