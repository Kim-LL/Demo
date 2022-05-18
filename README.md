# Demo
安卓黑盒测试代码覆盖率项目

使用 Jacoco 进行代码统计
统计策略为定时 1min 生成一次 ec 文件，最后 MainActivity onDestroy 方法被掉用的时候再次生成一个 ec 文件
最后将 生成的 ec 文件放入到 源码中 app/build/outputs/coverages/ 下，在执行 app 模块任务 jacocoTestReport 即可生成 html 代码覆盖率报告
