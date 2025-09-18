package com.example.board.service;

import com.example.board.entity.MemberEntity;
import com.example.board.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        String providerId = response.get("id").toString();
        String name = response.get("name").toString();
        String email = response.get("email").toString();
        String birthdayStr = response.get("birthday").toString();
        String username = provider+"-"+providerId.substring(0,3);
        String fullDateStr = LocalDate.now().getYear()+"-"+birthdayStr;
        LocalDate birth = LocalDate.parse(fullDateStr);

        Optional<MemberEntity> userOptional = memberRepository.findByProviderAndProviderId(provider,providerId);
        MemberEntity user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setRealname(name);
            user.setEmail(email);

        }
        else{
            user = new MemberEntity();
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setRealname(name);
            user.setEmail(email);
            user.setBirth(birth);
            user.setUsername(username);
            user.setRole("role_user");
            user.setPassword(UUID.randomUUID().toString());
        }
        memberRepository.save(user);

        Collection<GrantedAuthority> authorities = Collections.emptyList();

        Map<String,Object> newAttributes = new HashMap<>();

        newAttributes.put("name",name);

        String principalNameAttributeKey = "name";

        return new DefaultOAuth2User(authorities,newAttributes,principalNameAttributeKey);
    }


}
