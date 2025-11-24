package com.gdn.training;

import com.gdn.training.dummy.entity.Member;
import com.gdn.training.dummy.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberService Test")
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("suspendMember - success: member suspended and saved")
    void suspendMember_success() {
        // Arrange
        Member member = Member.builder().id("1").name("Alice").email("a@example.com").suspended(false).build();
        when(memberRepository.getMember("1")).thenReturn(member);

        // Act
        memberService.suspendMember("1");

        // Assert: verify suspended flag set and saved
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository, times(1)).save(captor.capture());
        Member saved = captor.getValue();
        assertTrue(saved.isSuspended(), "Saved member must be suspended");
        assertEquals(member, saved, "Saved instance should be the same member returned by repository");
    }

    @Test
    @DisplayName("suspendMember - not found: throws RuntimeException")
    void suspendMember_notFound_throws() {
        when(memberRepository.getMember("unknown")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberService.suspendMember("unknown"));
        assertEquals("Member not found", ex.getMessage());

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("suspendMember - already suspended: throws RuntimeException")
    void suspendMember_alreadySuspended_throws() {
        Member member = Member.builder().id("2").suspended(true).build();
        when(memberRepository.getMember("2")).thenReturn(member);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberService.suspendMember("2"));
        assertEquals("Member already suspended", ex.getMessage());

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("suspendMember - repository throws: exception propagated and save not called")
    void suspendMember_repositoryThrows_propagates() {
        when(memberRepository.getMember("x")).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberService.suspendMember("x"));
        assertEquals("DB error", ex.getMessage());

        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("suspendMember - null id treated as not found")
    void suspendMember_nullId_throwsNotFound() {
        // by default mock returns null for any arg unless stubbed, so this simulates not found
        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberService.suspendMember(null));
        assertEquals("Member not found", ex.getMessage());

        verify(memberRepository, never()).save(any());
    }
}