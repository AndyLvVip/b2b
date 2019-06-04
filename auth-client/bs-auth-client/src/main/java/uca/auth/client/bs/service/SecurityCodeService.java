package uca.auth.client.bs.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import uca.auth.client.bs.util.ImageUtils;
import uca.auth.client.bs.vo.SecurityCodeReqVo;
import uca.auth.client.bs.vo.SecurityCodeRespVo;
import uca.auth.client.bs.vo.SecurityCodeVo;
import uca.platform.StdStringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/4 16:29
 */
@Service
public class SecurityCodeService {

    private final StringRedisTemplate stringRedisTemplate;

    public final String CACHE_POSTFIX = "_SECURITY_CODE_KEY";

    public SecurityCodeService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public SecurityCodeRespVo genSecurityCode() {
        SecurityCodeVo code = ImageUtils.createSecurityCodeImage();

        String key = StdStringUtils.uuid();

        SecurityCodeRespVo result = new SecurityCodeRespVo(key, code.asBase64Image());

        this.stringRedisTemplate.opsForValue().set(genSecurityCodeKey(key), code.getCode().toUpperCase(), 2, TimeUnit.MINUTES);

        return result;
    }

    private String genSecurityCodeKey(String key) {
        return key + CACHE_POSTFIX;
    }

    public void validateSecurityCode(SecurityCodeReqVo vo) {
        String expected = this.stringRedisTemplate.opsForValue().get(genSecurityCodeKey(vo.getKey()));
        if(StringUtils.isNotEmpty(expected)) {
            this.stringRedisTemplate.expire(genSecurityCodeKey(vo.getKey()), 0, TimeUnit.SECONDS);
        }
        if(StringUtils.isEmpty(vo.getValue()) || !Objects.equals(vo.getValue().toUpperCase(), expected)) {
            throw new IllegalArgumentException("验证码错误");
        }
    }
}
