package com.flyhtml.payment.channel.alipay;

import com.flyhtml.payment.channel.BaseConfig;

/**
 * @author xiaowei
 * @time 17-3-29 上午10:03
 * @describe 支付宝配置文件类
 */
public class AlipayConfig extends BaseConfig {

    /***
     * 沙箱环境
     */

    // 商户appid
    public static String APPID             = "2016080100140284";
    // 请求网关地址
    public static String URL               = "https://openapi.alipaydev.com/gateway.do";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY   = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcMOneZkAS697pBu+LSvqAu23QAYq/Bom7+xS3Py3otnfGnJwWo95BxuKYtK5H3DONFZb9yCcVZUUqXUrSJfs0EnXQllS7lABwLKss+eOq7PROHUiJ6ABFqdY25CKEGUgn7wiaem4DHEifLxBY+TqwYjJN1hXRQ5Q4fmOMTvyk7sz1BwbmuEu8CMbsIDIAPK0dPL8cf2eGy6CXb7cKZ5xdgoRpeAqSJltvoy52cWsrTm3IGho9rcyLgbTSpMG6eX6oN7klKGwjdRP8deYhxEI1kLLQgEf3YNM/CfDxSUiE1w0o8msV2LH9q6MH6LkLqua64EAS3174Nl0os2zMC+mHAgMBAAECggEAFi7sSGYFW6qcckRF6WawxW/ZXN2bMx5emZBDlPMoNhEUWeUMVjrX6kexDzc6OYKQ38zdMRYoVuWXOTke01IJp4+npAR17F5M4aG2fiDPsQsOd7m4/70vrLU+c3VbYZ+lm+ZbNnWFAnQV7GXm0Hw1Va/crSlm193dQ1jQErF1OIdpT1S3eEOvUfMgPA1/2gZFE6IKLCTdEmh+cdM/luqVLIS/BkNb7YfRIjkGPlXsoNDZD4nClg04wL/APD6v+5w+9CWFo6rB/bYWNxH79hJ0yzvus65X8zrDCQv9M8GlZ3R3yrrXyE5/IsQ78s0ousjct/HlUCCRuLuNMd8IemEhgQKBgQDJYeGL40uWRclZNM4YyMe6Lx7ggzniK1hSr8Xc0113eBtmT6q0KJzZp7ADJiSFgGlfQ0Zisl0jnE7r1lwTIr76RAwwair0FoNkQOSEZkBn3dBVyEJK10k9mbogxGNTtKNFRM3vq2nyqcTsks1oHlqE2DIdDG+LF/B+DFTge3Yh9wKBgQDGjV64X6OBtfzrtBUna9/n/I17oxbXjpA22TSKLRQnHv8q/+IuA1eZDDViVnTcPbirfyOHJMnRJRdBOs0FcI7FghSaMJU9uxeklu2ZZb+e/9TFxmz5c6CZK+JmBRrzWlO0jQzCnhNzttH0pClpIaLn2+XEzGiEFLbNP+WweF2Q8QKBgFLP43YpA/5JuGDsrAvZFPjlQ6dDj1wIQwvNqGoEqRzl0S6jeCkZwlZm9KR23PHhOnOoMJuJiW+JaZLl1XsxAKPds719CyjQ0Ts6zD395mJP/sonzcsE+ONSAg5TdB0J3VIw1xbFwI2/bnFydPFp+gNC/GihooTqVUUgQQ8G9i9FAoGAc4y6XxppkYdT1ehLl1GUUPINdLKJDJGPJBxsEy4A2HOVlHSuMJEJXaxZNQ2TnMjevA+VoYSrXufzDKcnwc6riA4X51P2xqInNEYRqIAqcGH6dc7whIviHsIHU9AMyyr6I80ia/CK6YCv12viHajRoZW7tjgvaw6TBAf3qCekbEECgYA1VLSJprCj0NF0LrZfqTz8OjauYQhaWa3gVNc76ewfMuu7L2rIK4+I4qtc/Rm3fsh4sAdvTJdPwhkTDqNdlaJPOTjxtLHno1Ht1fGoAbMwQPCr0KykSTV0IZYbjYzmDVFkv8rzMQQigrswDwbwEX1l8F7bSrcryzXZAjv0U4IkTg==";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9jxODLYHC9WAfgxHI4jKlGX37r55gvIC1pi1ENAMfTvviok6xH7S126imLa7m0+WLa6D6eEZer1gcmY7ll0c0HOzTVOins3rC/besqukf/Drrmf7/B9MnN815b6DSSp9T0E8EePDPzu/9L1lQ9+/tHKpBaaRkTC9hKW37y/ja4elo9MkqxdNgQ2mk0bpxG7oiDuKfKuEuVB7Fs/O1zAdceLsaZ5g3OfmMHklvx8qizH9ncvysfJNhA4mqIhfwnaSu6OZCJzow4JRHtzZnB85s9bvNxjs+m7NDPVA9iJAdzxu2FNpDQrJcAujwDFMvaym6x6eREYDKkV2aHtYZC5WUwIDAQAB";

    /***
     * 正式环境
     */
    // // 商户appid
    // public static String APPID = "2017022005779574";
    // // 请求网关地址
    // public static String URL = "https://openapi.alipay.com/gateway.do";
    // // 私钥 pkcs8格式的
    // public static String RSA_PRIVATE_KEY =
    // "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC2G+mbM2gR3ej4QbOCd0meZ1I7W/phL5Idn1LaYlxiHtBKBp96aD46CG8JMnwTz2aPRSfbDHHbh1fAQ6xYad5z6wpOtufJCw9n8HUufbTOIUkf87OSE1ueUoD4AJKOkUr9hk7LOSrp/VodIwru5mzt5A7f2vjTcu5V2QEBOgaWCGiYn2v6MzrSSpkDwmwZ99rMsedriY0KLFhgtGLqDXKScGeI8QyUrMyJi3NYQpTZksoIS3zIwSakc3AJ8Euviur9+nHbkMQZuWcuMvTbFzJYRbA8I5pVV+o27daOnY5rHuItkQovAY4HjgUvpZk+n+WN3MgjIowVnJ3nMfNKQLIvAgMBAAECggEAI0ae5uB7XVKmKUjY1AH1ccjD5hm4E5FadYCCVSlOifc8E8rao6SDwUwW7iooXNIFJOWZhdYQTB0Yg3bYcLv5Sm0SLxNBLPGGh3TNzjatimxqNNVJSJeVXMLQlrnNqe49H4UfyEAHrxHTnedG2+Yo3aAavYQ2RQdzYY/KoQkzc2ZT7PqrCw+nisw9HwHBqhrJ3yfSTTHFZyKZ1Nz43Vl9p9ZwbR3qmX4S2NZYC2d6kgdmsou6+qUf+HnbvVrjbi8Z36OnyfCX5d8EKe53m2VhvwS8KxPerf8YbY5/a/I7R/QW6JiRMXlk0pe6IgFd+OKro0PezUhLmWj7XGXaI2NAOQKBgQDaF18cZErc3P3ulPuKjr5x0dAkTL3pOOTeS102AxWtXvDyXI7mbCJ+JMHfv50tvlrhRrszvxXHj3DXchFeZ27cRKoYeer/dmOWgz9crZWzfC+jshd/EtGqxVypjqJ2tbGj9gAL+fEf1f/j+MAuHQTD0pJmqYeWfG8KgHvhVl+3BQKBgQDVw2boM/M+gt15qvP1FkF04PSROxDgPCfmBP2Ggsdks/hImLmsNbcBkLQu9KunkJ8+CWeFOVafuGTRRiCwzdsp/iXfRI+wtIm9iCdJqAf50gONs9l0sjmmqEHYb/dl02e9m70UL88wd63vpzpGZ6r4osJX7wbOcWuWzTAFD+aiowKBgQCjwXLIBcJ0jncZAsLRKhdzrUAlUX4ztWWcSGhn2u8W67fts3QVLlLD9uOgFud1Zn4ksxBzbWJ0Io9vJhZGlVGXkPNLR5gdU465gDk+ZJSIXZIDzwB3iPDn0iOeqRvyGMGWxGsKkxTre6sVST08IsvmIaGN9nN5mltiPlFGRK2/5QKBgGyFSd4CWa/XkgZtgnJfPRXQCnkcamHcSEkfKXI7PGzkbNy2JDTIBENYKhFYhziHVECTV6mrxU1KuGwB1SDNMUFtU964GNaVoQPGdmOPWZU7SDCNKI102Y3BB8SQeyGHpLf+s4vnBaFOcNAkrpIFmSemwVEQ+PyfWgZx3roDK4xPAoGAQvazGfI1Qjw2F9Iy5D9dUZdVGHsrgorrtEvMWwO/XemASeLvVDtJis1muf2ycGB1m5xZGRJnK2ORzaHQmDxrEwG9VZZbrfm9OiqAHcgNaYZ3U+/eee1KlR/Eq+hjSePS12KwWiyfl5SjoAY5l7Z06t+HX/5OmVPYgXc6RDB6kcw=";
    // // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // public static String NOTIFY_URL = "http://helloxw.viphk.ngrok.org/";
    // // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    // public static String RETURN_URL = "http://helloxw.viphk.ngrok.org/";
    // // 支付宝公钥
    // public static String ALIPAY_PUBLIC_KEY =
    // "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAodJtHtkyomDQWPDmVVnIXD8dECIA0yfeY0J9VI7RsGB2n98SENyZDpNKTs1cy6+/vd3aL+B3+p194hLRZYBaQaJk007kJ/pUOaSSOkHowbycAefsLTJV9521dsCguDzM7VkHI9L730fVqi+75kb8o6VJkw0FgDCS7xSQpMFVrjmpx4hKIYlqgpKzGwoz41LR0IJXyaE0r6tgedeatIFTnkhqi/FQGSXrbUdoiODtQpXB9Q48dB7fupaxcop/2Fqqis4thxjNcUu1lhK1YocOMviqzEs2DJypdTQo2TdXtiH4GX0Eq4yW3U0NguAUd3M+D42VDl7vDoOjxxfh2EzGPwIDAQAB";

    // =====================以下为常用属性=========================
    // 日志记录目录
    public static String LOG_PATH          = "/log";
    // RSA2
    public static String SIGNTYPE          = "RSA2";
    // 交易超时时间
    public static String TIMEOUT_EXPRESS   = "1d";
    // 编码
    public static String CHARSET           = "UTF-8";
    // 返回格式
    public static String FORMAT            = "json";
}
