package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.member.LoginDTO;
import com.codingrecipe.member.dto.member.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @return Stirng userId
     */
    public String save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        MemberEntity save = memberRepository.save(memberEntity);

        return save.getUserId();
    }

    /**
     * 로그인
     * @return MemberDTO
     */
    public MemberDTO login(LoginDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(memberDTO.getUserId());
        if (byUserId.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byUserId.get();
            if (memberEntity.getUserPassword().equals(memberDTO.getUserPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    /**
     * 관리자 모드용 회원 전체 확인
     */
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    /**
     * 마이페이지 (return Member)
     */
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        return optionalMemberEntity.map(MemberDTO::toMemberDTO).orElse(null);

    }

    /**
     * 회원정보 수정 페이지 (Id에 관련된 회원 정보를 넘겨줌)
     * @return MemberDTO
     */
    public MemberDTO updateForm(String userId) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUserId(userId);
        if (memberEntity.isEmpty()) {
            return MemberDTO.toMemberDTO(memberEntity.get());
        } else {
            return null;
        }
    }

    /**
     * 회원 정보 수정 하기
     */
    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    /**
     * 회원 삭제
     */
    public void deleteById(String userid) {
        Optional<MemberEntity> findId = memberRepository.findByUserId(userid);
        if (findId.isPresent()) {
            Long id = findId.get().getMemberId();
            memberRepository.deleteById(id);
        } else {
            System.out.println("오류, 아이디를 찾을 수 없음" + userid);
        }
    }

    /**
     * 아이디 중복 확인
     * @return ok, null
     */
    public String idCheck(String userid) {
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userid);
        if (byUserId.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    /**
     * 넌 뭐세요..? (추후에 리팩토링 예정)
     * @param id
     * @return
     * @throws NotFoundMemberException
     */
    public Optional<MemberEntity> findByMemberId(Long id) throws NotFoundMemberException {
        Optional<MemberEntity> memberId = memberRepository.findById(id);
        if (memberId.isPresent()){
            return memberId;
        }
        throw new NotFoundMemberException();
    }

}












