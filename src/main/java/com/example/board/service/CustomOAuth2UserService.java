package com.example.board.service;

import com.example.board.entity.MemberEntity;
import com.example.board.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        //카카오
        String providerId;
        String realname =null;
        String email = null;
        String nickname = null;
        LocalDate birth = null;

        if("naver".equals(provider)){

            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            providerId = response.get("id").toString();
            realname = response.get("name").toString();
            email = response.get("email").toString();
            if(response.containsKey("birthday")) {
                String birthdayStr = response.get("birthday").toString();
                String fullDateStr = LocalDate.now().getYear() + "-" + birthdayStr;
                birth = LocalDate.parse(fullDateStr);
            }
        }
        else if("kakao".equals(provider)){
            providerId = oAuth2User.getAttributes().get("id").toString();
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");

            if(kakaoAccount !=null){
                Map<String,Object> profile =(Map<String, Object>) kakaoAccount.get("profile");

                if(profile !=null){
                    nickname = (String) profile.get("nickname");
                }
            }
            // 카카오의 email, realname, birth는 스코프 부족으로 null이므로, DB 제약조건 충족을 위해 임시값 할당
            // 이메일은 임시값으로 할당
            email = providerId + "@" + provider + ".com";
            // realname은 닉네임과 동일하게 설정 (닉네임만 가져오므로)
            realname = (nickname != null) ? nickname : provider + " 사용자";
            birth = LocalDate.now();
        }
        else{
            throw new OAuth2AuthenticationException("지원하지 않는 provider입니다. : "+provider);
        }

        Optional<MemberEntity> userOptional = memberRepository.findByProviderAndProviderId(provider,providerId);
        MemberEntity user;
        String username = provider + "-" + providerId.substring(0, 3);

        String principalNickname;


        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setRealname(realname !=null ?realname:user.getRealname());
            user.setEmail(email !=null?email:user.getEmail());

            if(user.getNickname() ==null &&nickname!=null){
                user.setNickname(nickname);
            }
            principalNickname = user.getNickname();
        }
        else{
            user = new MemberEntity();
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setRealname(realname);
            user.setEmail(email);
            user.setBirth(birth);
            user.setUsername(username);
            user.setRole("role_user");
            user.setPassword(UUID.randomUUID().toString());

            if(nickname !=null){
                user.setNickname(nickname);
            }
            else{
                user.setNickname(provider+"_"+username);
            }
            principalNickname = user.getNickname();
        }
        memberRepository.save(user);

        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));

        Map<String,Object> newAttributes = new HashMap<>();

        newAttributes.put("name",principalNickname);

        String principalNameAttributeKey = "name";

        return new DefaultOAuth2User(authorities,newAttributes,principalNameAttributeKey);
    }


}
