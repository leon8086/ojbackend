package xmut.cs.ojbackend.entity.entitymapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import xmut.cs.ojbackend.entity.DTO.DTOUserImport;
import xmut.cs.ojbackend.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T05:30:33+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class DTOUserImportWrapperImpl implements DTOUserImportWrapper {

    @Override
    public User toEntity(DTOUserImport vo) {
        if ( vo == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( vo.getId() );
        user.password( vo.getPassword() );
        user.username( vo.getUsername() );
        user.email( vo.getEmail() );
        user.adminType( vo.getAdminType() );
        user.isDisabled( vo.getIsDisabled() );
        user.avatar( vo.getAvatar() );
        user.realName( vo.getRealName() );
        if ( vo.getGrade() != null ) {
            user.grade( Integer.parseInt( vo.getGrade() ) );
        }

        return user.build();
    }

    @Override
    public DTOUserImport toO(User entity) {
        if ( entity == null ) {
            return null;
        }

        DTOUserImport.DTOUserImportBuilder dTOUserImport = DTOUserImport.builder();

        dTOUserImport.id( entity.getId() );
        dTOUserImport.username( entity.getUsername() );
        dTOUserImport.password( entity.getPassword() );
        dTOUserImport.adminType( entity.getAdminType() );
        dTOUserImport.isDisabled( entity.getIsDisabled() );
        dTOUserImport.avatar( entity.getAvatar() );
        dTOUserImport.realName( entity.getRealName() );
        dTOUserImport.email( entity.getEmail() );
        if ( entity.getGrade() != null ) {
            dTOUserImport.grade( String.valueOf( entity.getGrade() ) );
        }

        return dTOUserImport.build();
    }

    @Override
    public List<User> toEntity(List<DTOUserImport> voList) {
        if ( voList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( voList.size() );
        for ( DTOUserImport dTOUserImport : voList ) {
            list.add( toEntity( dTOUserImport ) );
        }

        return list;
    }

    @Override
    public List<DTOUserImport> toVo(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DTOUserImport> list = new ArrayList<DTOUserImport>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toO( user ) );
        }

        return list;
    }
}
