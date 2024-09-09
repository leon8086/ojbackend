package xmut.cs.ojbackend.entity.entitymapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.User;
import xmut.cs.ojbackend.entity.VO.VOUserLogin;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T05:30:33+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class VoUserLoginWrapperImpl implements VoUserLoginWrapper {

    @Override
    public User toEntity(VOUserLogin vo) {
        if ( vo == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( vo.getId() );
        user.username( vo.getUsername() );
        user.email( vo.getEmail() );
        user.adminType( vo.getAdminType() );
        user.isDisabled( vo.getIsDisabled() );
        user.avatar( vo.getAvatar() );
        user.realName( vo.getRealName() );
        user.grade( vo.getGrade() );
        user.firstLogin( vo.getFirstLogin() );

        return user.build();
    }

    @Override
    public VOUserLogin toO(User entity) {
        if ( entity == null ) {
            return null;
        }

        VOUserLogin.VOUserLoginBuilder vOUserLogin = VOUserLogin.builder();

        vOUserLogin.id( entity.getId() );
        vOUserLogin.username( entity.getUsername() );
        vOUserLogin.adminType( entity.getAdminType() );
        vOUserLogin.isDisabled( entity.getIsDisabled() );
        vOUserLogin.firstLogin( entity.getFirstLogin() );
        vOUserLogin.avatar( entity.getAvatar() );
        vOUserLogin.realName( entity.getRealName() );
        vOUserLogin.grade( entity.getGrade() );
        vOUserLogin.email( entity.getEmail() );

        return vOUserLogin.build();
    }

    @Override
    public List<User> toEntity(List<VOUserLogin> voList) {
        if ( voList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( voList.size() );
        for ( VOUserLogin vOUserLogin : voList ) {
            list.add( toEntity( vOUserLogin ) );
        }

        return list;
    }

    @Override
    public List<VOUserLogin> toVo(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VOUserLogin> list = new ArrayList<VOUserLogin>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toO( user ) );
        }

        return list;
    }
}
