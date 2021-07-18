package org.github.axon.tag.user.domain.user;

import javax.validation.constraints.NotBlank;

public interface UserInterface {

    @NotBlank
    String getName();
}
