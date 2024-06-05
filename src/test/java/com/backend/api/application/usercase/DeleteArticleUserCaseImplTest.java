package com.backend.api.application.usercase;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.api.application.config.security.IAuthenticationFacade;
import com.backend.api.domain.model.User;
import com.backend.api.domain.port.out.ArticleRepositoryPort;
import com.backend.api.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeleteArticleUserCaseImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DeleteArticleUserCaseImplTest {
    @MockBean
    private ArticleRepositoryPort articleRepositoryPort;

    @Autowired
    private DeleteArticleUserCaseImpl deleteArticleUserCaseImpl;

    @MockBean
    private IAuthenticationFacade iAuthenticationFacade;

    @MockBean
    private UserRepositoryPort userRepositoryPort;


    @Test
    void testDeleteArticle() {
        doNothing().when(articleRepositoryPort).deleteArticle(Mockito.any(), Mockito.<Long>any());
        when(iAuthenticationFacade.getAuthentication())
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(userRepositoryPort.findByUsername(Mockito.any())).thenReturn(new User());
        String objectId = "42";

        deleteArticleUserCaseImpl.deleteArticle(objectId);

        verify(iAuthenticationFacade).getAuthentication();
        verify(articleRepositoryPort).deleteArticle(eq(objectId), isNull());
        verify(userRepositoryPort).findByUsername("Principal");
    }
}
