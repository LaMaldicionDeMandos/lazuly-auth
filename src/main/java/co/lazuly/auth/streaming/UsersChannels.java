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
    String DELETE_USER_INPUT = "delete_user_input";
    String CHANGE_ROLES_INPUT = "change_roles_input";
    String NEW_OWNER_OUTPUT = "new_owner_output";

    @Input(NEW_USER_INPUT)
    SubscribableChannel newUserInput();

    @Input(CHANGE_ROLES_INPUT)
    SubscribableChannel changeRolesInput();

    @Input(DELETE_USER_INPUT)
    SubscribableChannel deleteUserInput();

    @Output(NEW_OWNER_OUTPUT)
    MessageChannel newOwnerOutput();
}
