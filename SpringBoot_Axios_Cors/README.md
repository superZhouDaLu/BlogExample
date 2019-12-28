### 前言

前后台分离越来越来成为主流，刚接触这种开发模式时，我们必定会遇到一个问题： **跨域**。

### 跨域

产生跨域问题的罪魁祸首是[浏览器同源策略](https://developer.mozilla.org/zh-CN/docs/Web/Security/Same-origin_policy)，当协议、子域名、主域名、端口号中任意一个不相同时，都算作不同域，不同域之间的网络请求就会触发跨域问题。跨域并不是请求发不出去，请求能发出去，服务端能收到请求并正常返回结果，只是结果被浏览器拦截了。

![](http://ww1.sinaimg.cn/large/005yqb1Zly1gacj705ophj318a024q33.jpg)

### 解决方法

前后台分离模式目前主流解决方案有三种：

* webpack proxy：仅开发模式有效，打包后会失效。
* cors：开发和生产环境都可以用。
* nginx： 仅生产环境可用。

本文主要讲的是 cors 方式，需要前后台进行配合。

#### 前台

前台使用 [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template) 脚手架，网络请求使用 Axios。Axios 是一个基于 promise 的 HTTP 客户端，可以发送 get、post 请求。

>找到项目中 Axios 封装的文件，在 Axios 创建时增加属性  **withCredentials: true** ：

```
    const service = axios.create({
        baseURL:  process.env.VUE_APP_BASE_API,
        withCredentials:  true,
        timeout:  5000
    })
```

#### 后台

>后台新建 `WebMvcConfig` 类，继承 `WebMvcConfigurer`，统一配置全局跨域请求。

```
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        /**
         *  允许跨域访问
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                  registry.addMapping("/**")  // 可限制哪个请求可以通过跨域
                  .allowedHeaders("*")  // 可限制固定请求头可以通过跨域
                  .allowedMethods("*") // 可限制固定methods可以通过跨域
                  .allowedOrigins("*")  // 可限制访问ip可以通过跨域
                  .allowCredentials(true) // 是否允许发送cookie
                  .exposedHeaders(HttpHeaders.SET_COOKIE);
          }
          
}
```