package uca.security.auth.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by andy.lv
 * on: 2019/2/25 15:24
 */
public interface UserStream {

    String INPUT_CHANGE_PHONE = "input-change-phone";

    String INPUT_CHANGE_EMAIL = "input-change-email";

    @Input(INPUT_CHANGE_PHONE)
    SubscribableChannel inputChangePhone();

    @Input(INPUT_CHANGE_EMAIL)
    SubscribableChannel inputChangeEmail();

}
