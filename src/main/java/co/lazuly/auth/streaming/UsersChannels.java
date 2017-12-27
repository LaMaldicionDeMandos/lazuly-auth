package co.lazuly.auth.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by boot on 27/12/2017.
 */
public interface UsersChannels {
    String NEW_USER_INPUT = "new_user_input";
    String NEW_OWNER_OUTPUT = "new_owner_output";

    @Input(NEW_USER_INPUT)
    SubscribableChannel newUserInput();

    @Output(NEW_OWNER_OUTPUT)
    MessageChannel newOwnerOutput();
}
