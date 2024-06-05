package com.backend.api.application.usercase;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.in.DeleteArticleUserCase;
import com.backend.api.domain.port.out.ArticleRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteArticleUserCaseImpl implements DeleteArticleUserCase {

    private final ArticleRepositoryPort articleRepositoryPort;
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepositoryPort userRepositoryPort;


    @Override
    public void deleteArticle(String objectId) {

        String username = authenticationFacade.getAuthentication().getName();
        User user = userRepositoryPort.findByUsername(username);
        articleRepositoryPort.deleteArticle(objectId, user.getId());
    }
}
